package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.request.GameRemoveActionRequest;

public class GameRemoveActionTest {
	
	@Test
	public void testGameId() {
		GameRemoveActionRequest a = new GameRemoveActionRequest();
		assertNull(a.getGameId());
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testParameterConstructor() {
		final String gameId = "Test GameId";
		GameRemoveActionRequest a = new GameRemoveActionRequest(gameId);
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testGameEvent() {
		final String gameId = "Test GameId";
		GameRemoveActionRequest a = new GameRemoveActionRequest(gameId);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.GAME_REMOVE, g.getType());
	}
}
