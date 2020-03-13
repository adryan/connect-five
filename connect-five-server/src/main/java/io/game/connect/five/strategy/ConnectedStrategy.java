package io.game.connect.five.strategy;

import io.game.connect.five.event.model.MatchType;
import io.game.connect.five.event.model.Player;
import io.game.connect.five.model.Board;

public interface ConnectedStrategy {
	
	MatchType isConnected(Player player, Board board);
}
