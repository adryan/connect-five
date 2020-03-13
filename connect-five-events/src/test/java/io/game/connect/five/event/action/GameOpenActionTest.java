package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.response.GameOpenActionResponse;

public class GameOpenActionTest {
	
	@Test
	public void testGameId() {
		GameOpenActionResponse a = new GameOpenActionResponse();
		assertNull(a.getGameId());
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testParameterConstructor() {
		final String gameId = "Test GameId";
		GameOpenActionResponse a = new GameOpenActionResponse(gameId);
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testGameEvent() {
		final String gameId = "Test GameId";
		GameOpenActionResponse a = new GameOpenActionResponse(gameId);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.GAME_OPEN, g.getType());
	}
}
