package io.game.connect.five;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import io.game.connect.five.controller.GameEventController;
import io.game.connect.five.ws.CustomHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	private Logger logger = LoggerFactory.getLogger(GameEventController.class);

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/queue");
		config.setApplicationDestinationPrefixes("/app");
		config.setUserDestinationPrefix("/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/connect-five-game")
		.setAllowedOrigins("*")
		.setHandshakeHandler(new CustomHandshakeHandler());
		
		registry.addEndpoint("/connect-five-game")
			.setAllowedOrigins("*")
			.setHandshakeHandler(new CustomHandshakeHandler())
			.withSockJS();
	}
	
	
	@EventListener
	public void handleSubscribeEvent(SessionSubscribeEvent event) {
		logger.info("<==> handleSubscribeEvent: username={}, event={}", event.getUser().getName(), event);
	}
	
	@EventListener
	public void handleConnectEvent(SessionSubscribeEvent event) {
		logger.info("===> handleConnectEvent: username={}, event={}", event.getUser().getName(), event);
	}
	
	@EventListener
	public void handleDisconnectEvent(SessionSubscribeEvent event) {
		logger.info("<=== handleDisconnectEvent: username={}, event={}", event.getUser().getName(), event);
	}
}
