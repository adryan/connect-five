package io.game.connect.five.client;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class ConnectClientApplication implements CommandLineRunner {
	
	private final static Logger LOG = LoggerFactory.getLogger(ConnectClientApplication.class);

	@Override
	public void run(String... args) throws Exception {
		WebSocketClient client = new StandardWebSocketClient();
		
		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		
		final CountDownLatch shutdownLatch = new CountDownLatch(1);
		
		LOG.info("Creating stomp session");
		StompSession stompSession = stompClient.connect(
				"ws://localhost:8080/connect-five-game", 
				new GameSessionHandler(shutdownLatch)).get();
		LOG.info("Created stomp session {}", stompSession.getSessionId());
		
		shutdownLatch.await();
	}
	
	public static void main(String[] args) {
        SpringApplication.run(ConnectClientApplication.class, args);
    }
}
