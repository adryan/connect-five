package io.game.connect.five.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.game.connect.five.event.model.Player;
import io.game.connect.five.model.Game.GameStatus;

public class GameTest {
	
	@Test
	public void testBoard() {
		Board board = Mockito.mock(Board.class);
		Game g = new Game(board);
		
		assertEquals(board, g.getBoard());
	}
	
	@Test
	public void testGameStatus() {
		Board board = Mockito.mock(Board.class);
		Game g = new Game(board);
		
		assertEquals(GameStatus.OPEN, g.getGameStatus());
		
		g.setGameStatus(GameStatus.COMPLETED);
		assertEquals(GameStatus.COMPLETED, g.getGameStatus());
	}
	
	@Test
	public void testId() {
		Board board = Mockito.mock(Board.class);
		Game g = new Game(board);
		
		assertNotNull(g.getId());
	}
	
	@Test
	public void testAddPlayer() {
		Board board = Mockito.mock(Board.class);
		Game g = new Game(board);
		
		assertTrue(g.getPlayers().isEmpty());
		
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getName()).thenReturn("Alice");
		g.addPlayer(player);
		
		assertFalse(g.getPlayers().isEmpty());
		assertTrue(g.getPlayers().size() == 1);
		
		Optional<Player> p = g.getPlayerByName("Alice");
		assertTrue(p.isPresent());
	}
	
	@Test
	public void testGetPlayerByNameMissing() {
		Board board = Mockito.mock(Board.class);
		Game g = new Game(board);
		Optional<Player> p = g.getPlayerByName("Alice");
		assertFalse(p.isPresent());
	}
}
