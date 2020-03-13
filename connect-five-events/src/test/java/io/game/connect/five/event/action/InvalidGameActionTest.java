package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.response.InvalidGameActionResponse;

public class InvalidGameActionTest {
	@Test
	public void testGameId() {
		InvalidGameActionResponse a = new InvalidGameActionResponse();
		assertNull(a.getGameId());
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testPlayerName() {
		InvalidGameActionResponse a = new InvalidGameActionResponse();
		assertNull(a.getPlayerName());
		
		final String name = "Alice";
		a.setPlayerName(name);
		
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testGameAction() {
		InvalidGameActionResponse a = new InvalidGameActionResponse();
		assertNull(a.getGameAction());
		
		final GameAction action = Mockito.mock(GameAction.class);
		a.setGameAction(action);
		
		assertEquals(action, a.getGameAction());
	}
	
	@Test
	public void testReason() {
		InvalidGameActionResponse a = new InvalidGameActionResponse();
		assertNull(a.getReason());
		
		final String reason = "Some Erroe";
		a.setReason(reason);
		
		assertEquals(reason, a.getReason());
	}
	
	@Test
	public void testParameterConstructor() {
		final String gameId = "Test GameId";
		final String name = "Alice";
		final GameAction action = Mockito.mock(GameAction.class);
		final String reason = "Some Error";
		InvalidGameActionResponse a = new InvalidGameActionResponse(gameId, name, action, reason);
		
		assertEquals(name, a.getPlayerName());
		assertEquals(reason, a.getReason());
		assertEquals(action, a.getGameAction());
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testGameEvent() {
		final String gameId = "Test GameId";
		final String name = "Alice";
		final GameAction action = Mockito.mock(GameAction.class);
		final String reason = "Some Error";
		InvalidGameActionResponse a = new InvalidGameActionResponse(gameId, name, action, reason);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.INVALID, g.getType());
	}
}
