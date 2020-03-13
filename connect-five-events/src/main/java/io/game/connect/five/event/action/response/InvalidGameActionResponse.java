package io.game.connect.five.event.action.response;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;

/**
 * An issue occurred during the game. This could be during any lifecycle of the game, 
 * from initiation, to joining a game, and during the run of a game.
 * 
 *
 */
public class InvalidGameActionResponse implements GameAction {
	
	private String gameId;
	private String playerName;
	
	private GameEventType actionType;
	private Object event;
	private String reason;
	
	public InvalidGameActionResponse() {
	}
	
	public InvalidGameActionResponse(
			String gameId, 
			String playerName, 
			GameAction event, 
			String reason) 
	{
		this.gameId= gameId;
		this.playerName = playerName;
		this.event = event;
		this.reason = reason;
		this.setActionType(event.type());
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

	public Object getGameAction() {
		return event;
	}

	public void setGameAction(Object event) {
		this.event = event;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public GameEventType getActionType() {
		return actionType;
	}

	public void setActionType(GameEventType actionType) {
		this.actionType = actionType;
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
		return GameEventType.INVALID;
	}
}
