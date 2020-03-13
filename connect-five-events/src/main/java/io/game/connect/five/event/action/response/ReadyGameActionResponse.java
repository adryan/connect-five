package io.game.connect.five.event.action.response;

import java.util.ArrayList;
import java.util.Collection;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;
import io.game.connect.five.event.model.Player;

/**
 * The Game is started and all players have been added. It can now be played.
 * 
 *
 */
public class ReadyGameActionResponse implements GameAction {
	
	private String gameId;
	
	private Collection<Player> players = new ArrayList<>();

	public ReadyGameActionResponse() {		
	}
	
	public ReadyGameActionResponse(String gameId, Collection<Player> players) {
		this.gameId = gameId;
		this.players = players;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	public Collection<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<Player> players) {
		this.players = players;
	}

	@Override
	public GameEvent asGameEvent() {
		GameEvent e = new GameEvent();
		e.setType(GameEventType.GAME_READY);
		e.setData(this);
		return e;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JoinGameEvent[")
		.append("gameId=").append(gameId)
		.append("players=[");
		players.forEach(p -> {
			builder.append("{")
				.append("name=").append(p.getName())
				.append(",tile=").append(p.getTile())
				.append("},");
		});
		builder.append("]");
		return builder.toString();
	}

	@Override
	public GameEventType type() {
		return GameEventType.GAME_READY;
	}
}
