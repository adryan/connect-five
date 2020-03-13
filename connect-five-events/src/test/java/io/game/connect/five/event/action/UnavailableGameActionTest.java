package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.response.UnavailableGameActionResponse;

public class UnavailableGameActionTest {
	
	@Test
	public void testPlayerName() {
		UnavailableGameActionResponse a = new UnavailableGameActionResponse();
		assertNull(a.getPlayerName());
		
		final String name = "Alice";
		a.setPlayerName(name);
		
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testParameterConstructor() {
		final String name = "Alice";
		UnavailableGameActionResponse a = new UnavailableGameActionResponse(name);
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testGameEvent() {
		final String name = "Alice";
		UnavailableGameActionResponse a = new UnavailableGameActionResponse(name);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.GAME_UNAVAILABLE, g.getType());
	}
}
