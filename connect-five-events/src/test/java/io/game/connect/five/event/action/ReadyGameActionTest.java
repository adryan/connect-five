package io.game.connect.five.event.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.response.ReadyGameActionResponse;
import io.game.connect.five.event.model.Player;

public class ReadyGameActionTest {
	
	@Test
	public void testGameId() {
		ReadyGameActionResponse a = new ReadyGameActionResponse();
		assertNull(a.getGameId());
		
		final String gameId = "Test GameId";
		a.setGameId(gameId);
		
		assertEquals(gameId, a.getGameId());
	}
	
	@Test
	public void testPlayers() {
		ReadyGameActionResponse a = new ReadyGameActionResponse();
		assertTrue(a.getPlayers().isEmpty());
		
		List<Player> players = new ArrayList<>();
		players.add(Mockito.mock(Player.class));
		
		a.setPlayers(players);
		
		assertEquals(players, a.getPlayers());
	}
	
	@Test
	public void testParameterConstructor() {
		final String gameId = "Test GameId";
		List<Player> players = new ArrayList<>();
		players.add(Mockito.mock(Player.class));
		
		ReadyGameActionResponse a = new ReadyGameActionResponse(gameId, players);
		assertEquals(gameId, a.getGameId());
		assertEquals(players, a.getPlayers());
	}
	
	@Test
	public void testGameEvent() {
		final String gameId = "Test GameId";
		List<Player> players = new ArrayList<>();
		players.add(Mockito.mock(Player.class));
		
		ReadyGameActionResponse a = new ReadyGameActionResponse(gameId, players);
		
		GameEvent g = a.asGameEvent();
		
		assertEquals(a, g.getData());
		assertEquals(GameEventType.GAME_READY, g.getType());
	}
	
	
}
