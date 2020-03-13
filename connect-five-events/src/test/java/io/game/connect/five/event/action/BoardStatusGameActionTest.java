package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.response.BoardStatusGameActionResponse;
import io.game.connect.five.event.model.MatchType;

public class BoardStatusGameActionTest {
	
	@Test
	public void testGameId() {
		BoardStatusGameActionResponse a = new BoardStatusGameActionResponse();
		assertNull(a.getGameId());
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testState() {
		BoardStatusGameActionResponse a = new BoardStatusGameActionResponse();
		assertNull(a.getState());
		
		final String state = "Test State";
		a.setState(state);
		
		assertEquals(state, a.getState());
	}
	
	@Test
	public void testCurrentPlayerName() {
		BoardStatusGameActionResponse a = new BoardStatusGameActionResponse();
		assertNull(a.getCurrentPlayerName());
		
		final String name = "Alice";
		a.setCurrentPlayerName(name);
		
		assertEquals(name, a.getCurrentPlayerName());
	}
	
	@Test
	public void testNextPlayerName() {
		BoardStatusGameActionResponse a = new BoardStatusGameActionResponse();
		assertNull(a.getNextPlayerName());
		
		final String name = "Bob";
		a.setNextPlayerName(name);
		
		assertEquals(name, a.getNextPlayerName());
	}
	
	@Test
	public void testIsConnected() {
		BoardStatusGameActionResponse a = new BoardStatusGameActionResponse();
		assertFalse(a.getIsConnected());
		
		final boolean isConnected = true;
		a.setIsConnected(isConnected);
		
		assertEquals(isConnected, a.getIsConnected());
	}
	
	@Test
	public void testMatchType() {
		BoardStatusGameActionResponse a = new BoardStatusGameActionResponse();
		assertEquals(MatchType.NONE, a.getMatchType());
		
		final MatchType type = MatchType.ACENDING_DIAGONAL;
		a.setMatchType(type);
		
		assertEquals(type, a.getMatchType());
	}
	
	@Test
	public void testGameEvent() {
		BoardStatusGameActionResponse a = new BoardStatusGameActionResponse();
		
		final MatchType type = MatchType.ACENDING_DIAGONAL;
		a.setMatchType(type);
		
		final boolean isConnected = true;
		a.setIsConnected(isConnected);
		
		final String currentName = "Alice";
		a.setCurrentPlayerName(currentName);
		
		final String nextName = "Bob";
		a.setNextPlayerName(nextName);
		
		final String state = "Test State";
		a.setState(state);
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.GAME_BOARD_STATUS, g.getType());
	}
}
