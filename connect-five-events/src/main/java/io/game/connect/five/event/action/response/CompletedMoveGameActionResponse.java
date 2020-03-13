package io.game.connect.five.event.action.response;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;

/**
 * Successfully completed a player move during the game.
 * The player has successfully selected a column to place a tile. 
 *
 */
public class CompletedMoveGameActionResponse implements GameAction {
	
	private String gameId;
	private String playerName;
	
	private int columnSelected;
	
	public CompletedMoveGameActionResponse() {
	}
	
	public CompletedMoveGameActionResponse(String gameId, String playerName, int columnSelected) {
		this.gameId= gameId;
		this.playerName = playerName;
		this.columnSelected = columnSelected;
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

	public int getColumnSelected() {
		return columnSelected;
	}

	public void setColumnSelected(int columnSelected) {
		this.columnSelected = columnSelected;
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
		return GameEventType.MAKE_MOVE_COMPLETED;
	}
}
