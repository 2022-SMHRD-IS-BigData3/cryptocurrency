package com.test;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.test.socket.BinanceWebSocketClient;

//import com.test.socket.BinanceWebSocketClient;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

		BinanceWebSocketClient binanceWebSocketClient = context.getBean(BinanceWebSocketClient.class);
		binanceWebSocketClient.connect();

		// 종료를 위한 키 입력 처리
		System.out.println("Press 'q' to quit the application...");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		if (input.equalsIgnoreCase("q")) {
			context.close();

		}

	}
	
	

}
