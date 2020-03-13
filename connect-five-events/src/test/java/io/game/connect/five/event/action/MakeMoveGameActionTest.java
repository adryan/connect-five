package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.request.MakeMoveGameActionRequest;

public class MakeMoveGameActionTest {

	@Test
	public void testGameId() {
		MakeMoveGameActionRequest a = new MakeMoveGameActionRequest();
		assertNull(a.getGameId());
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testPlayerName() {
		MakeMoveGameActionRequest a = new MakeMoveGameActionRequest();
		assertNull(a.getPlayerName());
		
		final String name = "Alice";
		a.setPlayerName(name);
		
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testColumnSelected() {
		MakeMoveGameActionRequest a = new MakeMoveGameActionRequest();
		assertEquals(0, a.getColumnSelected());
		
		final int columnSelected = 3;
		a.setColumnSelected(columnSelected);
		
		assertEquals(columnSelected, a.getColumnSelected());
	}
	
	@Test
	public void testParameterConstructor() {
		final String gameId = "Test GameId";
		final String name = "Alice";
		final int columnSelected = 5;
		MakeMoveGameActionRequest a = new MakeMoveGameActionRequest(gameId, name, columnSelected);
		assertEquals(gameId, a.getGameId());
		assertEquals(name, a.getPlayerName());
	}
	
	@Test
	public void testGameEvent() {
		final String gameId = "Test GameId";
		final String name = "Alice";
		final int columnSelected = 5;
		MakeMoveGameActionRequest a = new MakeMoveGameActionRequest(gameId, name, columnSelected);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.MAKE_MOVE, g.getType());
	}
}
