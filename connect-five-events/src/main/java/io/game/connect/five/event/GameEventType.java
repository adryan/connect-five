package io.game.connect.five.event;

/**
 * Identifies type of data for a GameEvent.
 *
 */
public enum GameEventType {
	INITIATE,
	GAME_OPEN,
	GAME_JOIN,
	GAME_READY,
	GAME_COMPLETED,
	GAME_REMOVE,
	GAME_DELETED,
	GAME_UNAVAILABLE,
	MAKE_MOVE,
	MAKE_MOVE_COMPLETED,
	GAME_BOARD_STATUS,
	INVALID;
}
