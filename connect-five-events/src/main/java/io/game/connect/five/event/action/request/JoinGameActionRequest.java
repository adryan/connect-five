package io.game.connect.five.event.action.request;

import java.util.Objects;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;

/**
 * Join a game that has already started.
 * 
 *
 */
public class JoinGameActionRequest implements GameAction {
	private String gameId;
	private String playerName;

	public JoinGameActionRequest() {
	}

	public JoinGameActionRequest(String gameId, String playerName) {
		Objects.requireNonNull(playerName);
		
		this.gameId = gameId;
		this.playerName =playerName;
	}

	public String getGameId() {
		return gameId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public GameEvent asGameEvent() {
		GameEvent e = new GameEvent();
		e.setType(type());
		e.setData(this);
		return e;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JoinGameEvent[")
		.append("playerName=").append(playerName)
		.append(",gameId=").append(gameId)
		.append("]");
		return builder.toString();
	}

	@Override
	public GameEventType type() {
		return GameEventType.GAME_JOIN;
	}
}
