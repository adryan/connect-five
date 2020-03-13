package io.game.connect.five.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.game.connect.five.event.model.Player;
import io.game.connect.five.exception.InvalidMoveGameException;
import io.game.connect.five.exception.JoinGameException;
import io.game.connect.five.model.BoardState;
import io.game.connect.five.model.Game;

public interface GameService {
	UUID createGame(Player player);
	
	Optional<Game> joinGame(Player player) throws JoinGameException;
	
	Game joinGame(UUID gameId, Player player) throws JoinGameException;
	
	Optional<Game> getGameById(UUID gameId);
	
	Collection<Game> getGames();
	
	void deleteGameById(UUID gameId);
	
	BoardState makeMove(UUID gameId, Player player, int columnSelected) throws InvalidMoveGameException;
}
