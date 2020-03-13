package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.response.GameDeletedActionResponse;

public class GameDeletedActionTest {
	
	@Test
	public void testGameId() {
		GameDeletedActionResponse a = new GameDeletedActionResponse();
		assertNull(a.getGameId());
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testParameterConstructor() {
		final String gameId = "Test GameId";
		GameDeletedActionResponse a = new GameDeletedActionResponse(gameId);
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testGameEvent() {
		final String gameId = "Test GameId";
		GameDeletedActionResponse a = new GameDeletedActionResponse(gameId);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.GAME_DELETED, g.getType());
	}
}
