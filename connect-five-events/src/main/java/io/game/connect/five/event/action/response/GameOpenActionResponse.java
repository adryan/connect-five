package io.game.connect.five.event.action.response;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;

/**
 * 
 * Game has started and is open for a further player to join.
 *
 */
public class GameOpenActionResponse implements GameAction {
	
	private String gameId;

	public GameOpenActionResponse() {
	}

	public GameOpenActionResponse(String gameId) {
		this.gameId = gameId;
	}

	public String getGameId() {
		return gameId;
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

	@Override
	public GameEventType type() {
		return GameEventType.GAME_OPEN;
	}
}
