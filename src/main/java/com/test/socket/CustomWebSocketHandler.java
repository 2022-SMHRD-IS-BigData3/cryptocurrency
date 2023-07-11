
package com.test.socket;

import java.time.Duration;
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
    private final AtomicReference<Instant> lastProcessedInstant = new AtomicReference<>(Instant.MIN);
    private final ZoneId desiredTimeZone = ZoneId.of("America/New_York").normalized(); // Specify the desired timezone here

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Mono<Void> sendMono = session.send(Mono.just(session.textMessage("{\"method\": \"SUBSCRIBE\",\"params\": [\"btcusdt@kline_5m\"],\"id\": 1}")));
        Flux<WebSocketMessage> receiveFlux = session.receive();

        return sendMono.thenMany(receiveFlux)
                .concatMap(message -> processMessage(message, session))
                .then();
    }

    private Mono<Void> processMessage(WebSocketMessage message, WebSocketSession session) {
        return Mono.just(message.getPayloadAsText())
                .filter(payload -> shouldProcessData())
                .flatMap(payload -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(payload);
                        if (jsonNode.has("k")) {
                            JsonNode klineNode = jsonNode.get("k");
                            long startTime = klineNode.get("t").asLong();
                            long closeTime = klineNode.get("T").asLong();
                            String symbol = klineNode.get("s").asText();
                            String interval = klineNode.get("i").asText();
                            String openPrice = klineNode.get("o").asText();
                            String closePrice = klineNode.get("c").asText();
                            String highPrice = klineNode.get("h").asText();
                            String lowPrice = klineNode.get("l").asText();
                            LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(closeTime), desiredTimeZone);

                            System.out.println("Symbol: " + symbol);
                            System.out.println("Interval: " + interval);
                            System.out.println("Current Time : " + currentTime);
                            System.out.println("Open Price: " + openPrice);
                            System.out.println("Close Price: " + closePrice);
                            System.out.println("High Price: " + highPrice);
                            System.out.println("Low Price: " + lowPrice);

                            updateLastProcessedInstant(Instant.ofEpochMilli(closeTime));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return Mono.empty();
                })
                .then();
    }

    private void updateLastProcessedInstant(Instant instant) {
        lastProcessedInstant.set(instant);
    }

    private boolean shouldProcessData() {
        Instant now = Instant.now();
        Instant lastProcessed = lastProcessedInstant.get();
        Duration duration = Duration.between(lastProcessed, now);
        return duration.compareTo(Duration.ofMinutes(5)) >= 0;
    }
}

