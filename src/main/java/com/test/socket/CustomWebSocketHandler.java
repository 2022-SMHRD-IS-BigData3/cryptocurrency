package com.test.socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AtomicReference<JsonNode> latestCandle = new AtomicReference<>();
    private final ZoneId desiredTimeZone = ZoneId.of("Asia/Seoul").normalized();

    // MySQL database connection details
    private final String jdbcUrl = "jdbc:mysql://project-db-cgi.smhrd.com:3307/cgi_2_230701_1";
    private final String username = "cgi_2_230701_1";
    private final String password = "smhrd1";

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Mono<Void> sendMono = session.send(Mono.just(session.textMessage("{\"method\": \"SUBSCRIBE\",\"params\": [\"btcusdt@kline_1m\"],\"id\": 1}")));
        Flux<WebSocketMessage> receiveFlux = session.receive();

        return sendMono.thenMany(receiveFlux)
                .filter(this::isCandleMessage)
                .flatMap(this::processMessage)
                .then();
    }

    private boolean isCandleMessage(WebSocketMessage message) {
        String payload = message.getPayloadAsText();
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            return jsonNode.has("k") && jsonNode.get("k").has("x") && jsonNode.get("k").get("x").asBoolean();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Mono<Void> processMessage(WebSocketMessage message) {
        String payload = message.getPayloadAsText();
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            if (jsonNode.has("k")) {
                JsonNode klineNode = jsonNode.get("k");
                latestCandle.set(klineNode);
                saveCandleData(klineNode);
                processCandle(klineNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }

    private void saveCandleData(JsonNode klineNode) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            long closeTime = klineNode.get("T").asLong();
            double openPrice = klineNode.get("o").asDouble();
            double closePrice = klineNode.get("c").asDouble();
            double highPrice = klineNode.get("h").asDouble();
            double lowPrice = klineNode.get("l").asDouble();
            LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(closeTime), desiredTimeZone);

            // Prepare the SQL statement
            String sql = "INSERT INTO tbl_minute5 ( m5_start, m5_end, m5_max, m5_min) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, openPrice);
            statement.setDouble(2, closePrice);
            statement.setDouble(3, highPrice);
            statement.setDouble(4, lowPrice);

            // Execute the SQL statement
            statement.executeUpdate();
        }
    }


    private void processCandle(JsonNode klineNode) {
        double openPrice = klineNode.get("o").asDouble();
        double closePrice = klineNode.get("c").asDouble();
        double highPrice = klineNode.get("h").asDouble();
        double lowPrice = klineNode.get("l").asDouble();

        System.out.println("Open Price: " + openPrice);
        System.out.println("Close Price: " + closePrice);
        System.out.println("High Price: " + highPrice);
        System.out.println("Low Price: " + lowPrice);
    }
}
