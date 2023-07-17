package com.test.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import java.net.URI;

@Component
public class BinanceWebSocketClient {

    private final ReactorNettyWebSocketClient webSocketClient = new ReactorNettyWebSocketClient();

    @Value("${binance.websocket.url}")
    private String binanceWebSocketUrl;

    private final CustomWebSocketHandler customWebSocketHandler;

    @Autowired
    public BinanceWebSocketClient(CustomWebSocketHandler customWebSocketHandler) {
        this.customWebSocketHandler = customWebSocketHandler;
    }

    public void connect() {
        webSocketClient.execute(
                URI.create(binanceWebSocketUrl),
                customWebSocketHandler
        ).subscribe();
    }
}
