package io.game.connect.five.ws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;

public class CustomHandshakeHandlerTest {

	@Test
	public void testDetermineUser() {
		
		CustomHandshakeHandler h = new CustomHandshakeHandler();
		
		ServerHttpRequest request = Mockito.mock(ServerHttpRequest.class);
		WebSocketHandler wsHandler = Mockito.mock(WebSocketHandler.class);
		Map<String, Object> attributes = new HashMap<>();
		
		Principal p = h.determineUser(request, wsHandler, attributes);
		assertNotNull(p.getName());
		
		Principal p2 = h.determineUser(request, wsHandler, attributes);
		assertEquals(p.getName(), p2.getName());
	}
}
