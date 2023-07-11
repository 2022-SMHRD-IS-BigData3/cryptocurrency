package com.test.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import java.net.URI;

@Component
public class BinanceWebSocketClient {

    private final ReactorNettyWebSocketClient webSocketClient = new ReactorNettyWebSocketClient();

    @Value("${binance.websocket.url}")
    private String binanceWebSocketUrl;

    public void connect() {
        webSocketClient.execute(
                URI.create(binanceWebSocketUrl),
                new CustomWebSocketHandler()
        ).subscribe();
    }
}


