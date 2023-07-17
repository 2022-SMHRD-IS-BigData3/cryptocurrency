package com.test.socket;

import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public CustomWebSocketHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @Override
    public Mono<Void> handle(WebSocketSession session) {
       String subscribeMessage = "{\"method\": \"SUBSCRIBE\",\"params\": [";
        subscribeMessage += "\"bnbusdt@kline_1m\",";
        subscribeMessage += "\"btcusdt@kline_1m\",";
        subscribeMessage += "\"ethusdt@kline_1m\",";
        subscribeMessage += "\"solusdt@kline_1m\",";
        subscribeMessage += "\"maticusdt@kline_1m\",";
        subscribeMessage += "\"adausdt@kline_1m\"";
        subscribeMessage += "],\"id\": 1}";

        Mono<Void> sendMono = session.send(Mono.just(session.textMessage(subscribeMessage)));
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
            return jsonNode.has("k") && jsonNode.get("k").has("x") && jsonNode.has("s") && jsonNode.get("k").get("x").asBoolean();
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
//                saveCandleData(klineNode);
                processCandle(klineNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }

    private void processCandle(JsonNode klineNode) {
        double highPrice = klineNode.get("h").asDouble();
        double lowPrice = klineNode.get("l").asDouble();
        double averagePrice = (highPrice + lowPrice) / 2;
        String symbol = klineNode.get("s").asText();

        System.out.println("Symbol: " + symbol);
        System.out.println("Price: " + averagePrice);
        
        messagingTemplate.convertAndSend("/topic/data", symbol);
    }
}