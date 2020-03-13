package io.game.connect.five.event;

/**
 * General Game Event envelope providing type of data and data.
 *
 */
public class GameEvent {
	
	private GameEventType type;
	private Object data;
	
	public GameEventType getType() {
		return type;
	}
	public void setType(GameEventType type) {
		this.type = type;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
