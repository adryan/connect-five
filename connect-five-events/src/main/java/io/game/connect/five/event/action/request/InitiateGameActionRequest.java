package io.game.connect.five.event.action.request;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;

/**
 * Start a new game.
 *
 */
public class InitiateGameActionRequest implements GameAction {

	private String playerName;

	public InitiateGameActionRequest() {
	}

	public InitiateGameActionRequest(String playerName) {
		this.playerName = playerName;
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
		builder.append("InitiateGameEvent[")
		.append("playerName=").append(playerName)
		.append("]");
		return builder.toString();
	}

	@Override
	public GameEventType type() {
		return GameEventType.INITIATE;
	}
}
