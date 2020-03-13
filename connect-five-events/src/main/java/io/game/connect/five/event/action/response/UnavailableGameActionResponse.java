package io.game.connect.five.event.action.response;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;

/**
 * An attempt has been made to join a game that does not exist.
 * 
 *
 */
public class UnavailableGameActionResponse implements GameAction {
	private String playerName;

	public UnavailableGameActionResponse() {
	}

	public UnavailableGameActionResponse(String playerName) {
		this.playerName =playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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
		builder.append("UnavailableGameAction[")
		.append("playerName=").append(playerName)
		.append("]");
		return builder.toString();
	}

	@Override
	public GameEventType type() {
		return GameEventType.GAME_UNAVAILABLE;
	}
}
