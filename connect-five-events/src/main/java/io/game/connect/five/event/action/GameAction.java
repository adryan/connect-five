package io.game.connect.five.event.action;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;


/**
 * GameAction is a marker interface that represents an action that occurs during a game.
 *
 */
public interface GameAction {
	
	/**
	 * Wraps a GameAction in a GameEvent envelope.
	 * This is a helper method to provide ease of conversion from GameAction to a GameEvent.
	 * 
	 * @return GameEvent
	 */
	GameEvent asGameEvent();
	
	GameEventType type();
}
