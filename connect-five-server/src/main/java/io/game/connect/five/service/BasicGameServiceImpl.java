package io.game.connect.five.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.game.connect.five.event.model.MatchType;
import io.game.connect.five.event.model.Player;
import io.game.connect.five.exception.InvalidMoveGameException;
import io.game.connect.five.exception.JoinGameException;
import io.game.connect.five.model.BasicBoard;
import io.game.connect.five.model.Board;
import io.game.connect.five.model.BoardState;
import io.game.connect.five.model.Game;
import io.game.connect.five.model.Game.GameStatus;
import io.game.connect.five.strategy.ConnectedStrategy;

public class BasicGameServiceImpl implements GameService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BasicGameServiceImpl.class);
	
	//Use insertion order.
	private Map<UUID, Game> games = new LinkedHashMap<>();
	
	private ConnectedStrategy strategy;
	
	public BasicGameServiceImpl(ConnectedStrategy strategy) {
		Objects.requireNonNull(strategy);
		this.strategy = strategy;
	}	
	
	@Override
	public UUID createGame(Player player) {
		
		Objects.requireNonNull(player);
		
		LOGGER.info("Creating a game for Player {}", player.getName());
		
		Game game = new Game(new BasicBoard());
		game.addPlayer(player);
		
		synchronized(games) {
			games.put(game.getId(), game);
		}
		
		LOGGER.info("Successfully created a game for Player {} - GameId={}", player.getName(), game.getId());
	
		return game.getId();
	}

	@Override
	public Optional<Game> joinGame(Player player) throws JoinGameException {
		
		Objects.requireNonNull(player);
		
		synchronized(games) {
			for (Game game : games.values()) {
				if (GameStatus.OPEN.equals(game.getGameStatus())) {
					game.addPlayer(player);
					game.setGameStatus(GameStatus.READY);
					return Optional.of(game);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public Game joinGame(UUID gameId, Player player) throws JoinGameException {
		
		Objects.requireNonNull(gameId);
		Objects.requireNonNull(player);
		
		LOGGER.info("Attempting to join Game {} By Player {}", gameId, player.getName());
		
		Game game;
		synchronized(games) {
			game = games.get(gameId);
		}
		
		if (game == null) {
			throw new JoinGameException("Could not join game as UUID does not exist");
		}
		
		if (!GameStatus.OPEN.equals(game.getGameStatus())) {
			throw new JoinGameException("The game is not open and available to join");
		}
		
		game.addPlayer(player);
		game.setGameStatus(GameStatus.READY);
		
		return game;
	}

	@Override
	public Optional<Game> getGameById(UUID gameId) {
		Game game;
		synchronized(games) {
			game = games.get(gameId);
		}
		return game == null ? Optional.empty() : Optional.of(game);
	}

	@Override
	public BoardState makeMove(UUID gameId, Player player, int columnSelected) throws InvalidMoveGameException {
		
		LOGGER.info("Make move in Game {} by Player {} - column {}", gameId, player.getName(), columnSelected);
		
		Game game;
		synchronized(games) {
			game = games.get(gameId);
		}
		
		if (game == null) {
			throw new InvalidMoveGameException("Game is invalid");
		}
		
		Board board = game.getBoard();
		
		if (!board.addTile(columnSelected, player.getTile())) {
			throw new InvalidMoveGameException("Cannot add tile to column " + (columnSelected + 1));
		}
		
		MatchType matchType = strategy.isConnected(player, board);
		
		BoardState boardState = new BoardState();
		boardState.setState(board.toString());
		boardState.setMatchType(matchType);
		boardState.setIsConnected(matchType != MatchType.NONE);
		
		return boardState;
	}

	@Override
	public Collection<Game> getGames() {
		return new ArrayList<>(games.values());
	}

	@Override
	public void deleteGameById(UUID gameId) {
		Objects.requireNonNull(gameId);
		
		synchronized(games) {
			games.remove(gameId);
		}
	}
	
	//For test purposes
	protected void setGames(Map<UUID, Game> games) {
		Objects.requireNonNull(games);
		this.games = games;
	}
}
