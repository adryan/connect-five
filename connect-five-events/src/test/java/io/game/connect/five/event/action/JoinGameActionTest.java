package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.request.JoinGameActionRequest;

public class JoinGameActionTest {
	
	@Test
	public void testGameId() {
		JoinGameActionRequest a = new JoinGameActionRequest();
		assertNull(a.getGameId());
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testPlayerName() {
		JoinGameActionRequest a = new JoinGameActionRequest();
		assertNull(a.getPlayerName());
		
		final String name = "Alice";
		a.setPlayerName(name);
		
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testParameterConstructor() {
		final String gameId = "Test GameId";
		final String name = "Alice";
		JoinGameActionRequest a = new JoinGameActionRequest(gameId, name);
		assertEquals(gameId, a.getGameId());
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testGameEvent() {
		final String gameId = "Test GameId";
		final String name = "Alice";
		JoinGameActionRequest a = new JoinGameActionRequest(gameId, name);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.GAME_JOIN, g.getType());
	}
}
