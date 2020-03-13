package io.game.connect.five.event.action.request;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;

/**
 * A Game Completed Action that indicates that the Game has completed for a player.
 *
 */
public class CompletedGameActionRequest implements GameAction {
	
	private String gameId;
	private String playerName;
	
	public CompletedGameActionRequest() {
	}
	
	public CompletedGameActionRequest(String gameId, String playerName) {
		this.gameId= gameId;
		this.playerName = playerName;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
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

	@Override
	public GameEventType type() {
		return GameEventType.GAME_COMPLETED;
	}
}
