package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.request.CompletedGameActionRequest;

public class CompletedGameActionTest {

	@Test
	public void testGameId() {
		CompletedGameActionRequest a = new CompletedGameActionRequest();
		assertNull(a.getGameId());
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testPlayerName() {
		CompletedGameActionRequest a = new CompletedGameActionRequest();
		assertNull(a.getPlayerName());
		
		final String name = "Alice";
		a.setPlayerName(name);
		
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testParameterConstructor() {
		final String gameId = "Test GameId";
		final String name = "Alice";
		CompletedGameActionRequest a = new CompletedGameActionRequest(gameId, name);
		assertEquals(gameId, a.getGameId());
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testGameEvent() {
		final String gameId = "Test GameId";
		final String name = "Alice";
		CompletedGameActionRequest a = new CompletedGameActionRequest(gameId, name);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.GAME_COMPLETED, g.getType());
	}
}
