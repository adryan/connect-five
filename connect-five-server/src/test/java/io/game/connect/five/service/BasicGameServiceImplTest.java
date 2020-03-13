package io.game.connect.five.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.game.connect.five.event.model.Player;
import io.game.connect.five.exception.JoinGameException;
import io.game.connect.five.model.Game;
import io.game.connect.five.model.Game.GameStatus;
import io.game.connect.five.strategy.ConnectedStrategy;

public class BasicGameServiceImplTest {
	private GameService service;
	
	private ConnectedStrategy strategy;
	
	@BeforeEach
	public void setup() {
		strategy = Mockito.mock(ConnectedStrategy.class);
		service = new BasicGameServiceImpl(strategy);
	}
	
	@Test
	public void testNullConnectedStrategy() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			new BasicGameServiceImpl(null);
		});
	}
	
	@Test
	public void testCreateGame() {
		UUID playerId = UUID.randomUUID();
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getId()).thenReturn(playerId);
		
		UUID gameId = service.createGame(player);
		assertNotNull(gameId);
	}
	
	@Test
	public void testCreateGameWithNullPlayer() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			service.createGame(null);
		});
	}
	
	@Test
	public void testJoinGameNoAvailableGames() throws JoinGameException {
		UUID playerId = UUID.randomUUID();
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getId()).thenReturn(playerId);
		
		Optional<Game> gameIfPresent = service.joinGame(player);
		assertFalse(gameIfPresent.isPresent());
	}
	
	@Test
	public void testJoinGameOnlyNonOpenAvailableGames() throws JoinGameException {
		Map<UUID, Game> games = new HashMap<>();
		
		Game game1 = Mockito.mock(Game.class);
		Mockito.when(game1.getGameStatus()).thenReturn(GameStatus.READY);
		
		Game game2 = Mockito.mock(Game.class);
		Mockito.when(game2.getGameStatus()).thenReturn(GameStatus.COMPLETED);
		
		games.put(UUID.randomUUID(), game1);
		games.put(UUID.randomUUID(), game2);
		
		((BasicGameServiceImpl) service).setGames(games);
		
		UUID playerId = UUID.randomUUID();
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getId()).thenReturn(playerId);
		
		Optional<Game> gameIfPresent = service.joinGame(player);
		assertFalse(gameIfPresent.isPresent());
	}
	
	@Test
	public void testJoinAnyGame() throws JoinGameException {
		Map<UUID, Game> games = new HashMap<>();
		
		Game game1 = Mockito.mock(Game.class);
		Mockito.when(game1.getGameStatus()).thenReturn(GameStatus.OPEN);
		
		Game game2 = Mockito.mock(Game.class);
		Mockito.when(game2.getGameStatus()).thenReturn(GameStatus.COMPLETED);
		
		games.put(UUID.randomUUID(), game1);
		games.put(UUID.randomUUID(), game2);
		
		((BasicGameServiceImpl) service).setGames(games);
		
		UUID playerId = UUID.randomUUID();
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getId()).thenReturn(playerId);
		
		Optional<Game> gameIfPresent = service.joinGame(player);
		assertTrue(gameIfPresent.isPresent());
		
		Mockito.verify(game1).addPlayer(player);
		Mockito.verify(game1).setGameStatus(GameStatus.READY);
		
		Mockito.verify(game2, Mockito.never()).addPlayer(player);
		Mockito.verify(game2, Mockito.never()).setGameStatus(GameStatus.READY);
	}
	
	@Test
	public void testJoinGameById() throws JoinGameException {
		Map<UUID, Game> games = new HashMap<>();
		
		final UUID game1Id = UUID.randomUUID();
		Game game1 = Mockito.mock(Game.class);
		Mockito.when(game1.getGameStatus()).thenReturn(GameStatus.OPEN);
		Mockito.when(game1.getId()).thenReturn(game1Id);
		
		final UUID game2Id = UUID.randomUUID();
		Game game2 = Mockito.mock(Game.class);
		Mockito.when(game2.getGameStatus()).thenReturn(GameStatus.COMPLETED);
		Mockito.when(game2.getId()).thenReturn(game2Id);
		
		games.put(game1Id, game1);
		games.put(game2Id, game2);
		
		((BasicGameServiceImpl) service).setGames(games);
		
		UUID playerId = UUID.randomUUID();
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getId()).thenReturn(playerId);
		
		Game game = service.joinGame(game1Id, player);
		assertEquals(game1, game);
		
		Mockito.verify(game1).addPlayer(player);
		Mockito.verify(game1).setGameStatus(GameStatus.READY);
		
		Mockito.verify(game2, Mockito.never()).addPlayer(player);
		Mockito.verify(game2, Mockito.never()).setGameStatus(GameStatus.READY);
	}
	
	@Test
	public void testJoinGameByIdNotAvailable() throws JoinGameException {
		Map<UUID, Game> games = new HashMap<>();
		
		final UUID game1Id = UUID.randomUUID();
		Game game1 = Mockito.mock(Game.class);
		Mockito.when(game1.getGameStatus()).thenReturn(GameStatus.OPEN);
		Mockito.when(game1.getId()).thenReturn(game1Id);
		
		games.put(game1Id, game1);

		
		((BasicGameServiceImpl) service).setGames(games);
		
		UUID playerId = UUID.randomUUID();
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getId()).thenReturn(playerId);
		
		Assertions.assertThrows(JoinGameException.class, () -> {
			service.joinGame(UUID.randomUUID(), player);
		});
	}
	
	@Test
	public void testGetGameById() {
		Map<UUID, Game> games = new HashMap<>();
		
		final UUID game1Id = UUID.randomUUID();
		Game game1 = Mockito.mock(Game.class);
		Mockito.when(game1.getGameStatus()).thenReturn(GameStatus.OPEN);
		Mockito.when(game1.getId()).thenReturn(game1Id);
		
		games.put(game1Id, game1);

		
		((BasicGameServiceImpl) service).setGames(games);
		
		Optional<Game> gameIfPresent = service.getGameById(game1Id);
		assertTrue(gameIfPresent.isPresent());
		assertEquals(game1, gameIfPresent.get());
	}
	
	@Test
	public void testGetGameByIdNotAvailable() {
		Optional<Game> gameIfPresent = service.getGameById(UUID.randomUUID());
		assertFalse(gameIfPresent.isPresent());
	}
	
	@Test
	public void testDeleteGameById() {
		Map<UUID, Game> games = new HashMap<>();
		
		final UUID game1Id = UUID.randomUUID();
		Game game1 = Mockito.mock(Game.class);
		Mockito.when(game1.getGameStatus()).thenReturn(GameStatus.OPEN);
		Mockito.when(game1.getId()).thenReturn(game1Id);
		
		games.put(game1Id, game1);

		((BasicGameServiceImpl) service).setGames(games);
		
		Optional<Game> gameIfPresent = service.getGameById(game1Id);
		assertTrue(gameIfPresent.isPresent());
		
		service.deleteGameById(game1Id);
		
		gameIfPresent = service.getGameById(game1Id);
		assertFalse(gameIfPresent.isPresent());
	}
	
	@Test
	public void testGetGames() throws JoinGameException {
		Map<UUID, Game> games = new HashMap<>();
		
		final UUID game1Id = UUID.randomUUID();
		Game game1 = Mockito.mock(Game.class);
		Mockito.when(game1.getGameStatus()).thenReturn(GameStatus.OPEN);
		Mockito.when(game1.getId()).thenReturn(game1Id);
		
		final UUID game2Id = UUID.randomUUID();
		Game game2 = Mockito.mock(Game.class);
		Mockito.when(game2.getGameStatus()).thenReturn(GameStatus.COMPLETED);
		Mockito.when(game2.getId()).thenReturn(game2Id);
		
		games.put(game1Id, game1);
		games.put(game2Id, game2);
		
		((BasicGameServiceImpl) service).setGames(games);
		
	
		Collection<Game> gameList = service.getGames();
		assertTrue(gameList.contains(game1));
		assertTrue(gameList.contains(game2));
	}
}
