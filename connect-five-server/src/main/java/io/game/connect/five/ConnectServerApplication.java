package io.game.connect.five;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import io.game.connect.five.service.BasicGameServiceImpl;
import io.game.connect.five.service.GameService;
import io.game.connect.five.strategy.BasicConnectedStrategy;
import io.game.connect.five.strategy.ConnectedStrategy;

@SpringBootApplication
public class ConnectServerApplication {
	
	@Bean
	@Scope("singleton")
	public GameService gameService() {
		return new BasicGameServiceImpl(connectedStrategy());
	}
	
	@Bean
	public ConnectedStrategy connectedStrategy() {
		return new BasicConnectedStrategy();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ConnectServerApplication.class, args);
	}
}
