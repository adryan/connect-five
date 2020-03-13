package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.request.InitiateGameActionRequest;

public class InitiateGameActionTest {
	
	@Test
	public void testPlayerName() {
		InitiateGameActionRequest a = new InitiateGameActionRequest();
		assertNull(a.getPlayerName());
		
		final String name = "Alice";
		a.setPlayerName(name);
		
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testParameterConstructor() {
		final String name = "Alice";
		InitiateGameActionRequest a = new InitiateGameActionRequest(name);
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testGameEvent() {
		final String name = "Alice";
		InitiateGameActionRequest a = new InitiateGameActionRequest(name);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.INITIATE, g.getType());
	}
}	
