package io.game.connect.five.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.game.connect.five.event.model.Player;

public class Game {
	
	public enum GameStatus {
		OPEN,
		READY,
		COMPLETED;
	}
	
	private UUID id;
	
	private List<Player> players = new ArrayList<>();
	
	private Board board;
	
	private GameStatus status;
	
	private UUID nextPlayer;
	
	public Game(Board board) {
		this.id = UUID.randomUUID();
		this.board = board;
		this.status = GameStatus.OPEN;
	}

	public UUID getId() {
		return id;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public Board getBoard() {
		return board;
	}
	
	public GameStatus getGameStatus() {
		return status;
	}
	
	public void setGameStatus(GameStatus status) {
		this.status = status;
	}
	
	public Optional<Player> getPlayerByName(String name) {
		for (Player p : players) {
			if (p.getName().equals(name)) {
				return Optional.of(p);
			}
		}
		return Optional.empty();
	}
	
	public Optional<Player> getNextPlayer(String currentPlayer) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(currentPlayer)) {
				if (i == players.size() -1) {
					return Optional.of(players.get(0));
				}
				return Optional.of(players.get(i + 1));
			}
		}
		return Optional.empty();
	}

	public UUID getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(UUID nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
}
