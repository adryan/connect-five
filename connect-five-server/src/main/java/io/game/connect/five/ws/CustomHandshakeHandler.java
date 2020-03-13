package io.game.connect.five.ws;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
	
	protected static final String ATTRIBUTE_PRINCIPAL = "principal";

	@Override
	protected Principal determineUser(ServerHttpRequest request, 
			WebSocketHandler wsHandler, 
			Map<String, Object> attributes) {
		
		final String name;
		if (!attributes.containsKey(ATTRIBUTE_PRINCIPAL)) {
			name = UUID.randomUUID().toString();
			attributes.put(ATTRIBUTE_PRINCIPAL, name);
		} else {
			name = (String) attributes.get(ATTRIBUTE_PRINCIPAL);
		}
		
		return new Principal() {
			@Override
			public String getName() {
				return name;
			}
			
		};
	}
	
}
