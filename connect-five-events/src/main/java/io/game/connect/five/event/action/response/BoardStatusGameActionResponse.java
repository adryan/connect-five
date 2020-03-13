package io.game.connect.five.event.action.response;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;
import io.game.connect.five.event.model.MatchType;

/**
 * A status action that provides details about the state of the game board,
 * if a match occurred, the current and next players in the game.
 * 
 */
public class BoardStatusGameActionResponse implements GameAction {
	
	private String gameId;
	
	private String state;
	
	private Boolean isConnected = false;
	
	private MatchType matchType = MatchType.NONE;
	
	private String currentPlayerName;
	
	private String nextPlayerName;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

	public String getCurrentPlayerName() {
		return currentPlayerName;
	}

	public void setCurrentPlayerName(String currentPlayerName) {
		this.currentPlayerName = currentPlayerName;
	}

	public String getNextPlayerName() {
		return nextPlayerName;
	}

	public void setNextPlayerName(String nextPlayerName) {
		this.nextPlayerName = nextPlayerName;
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
		return GameEventType.GAME_BOARD_STATUS;
	}
}
