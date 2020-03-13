package io.game.connect.five.client.game;

import java.util.concurrent.CountDownLatch;

import org.springframework.messaging.simp.stomp.StompSession;

import io.game.connect.five.client.GameEventFrameHandler;
import io.game.connect.five.client.command.BoardStatusNotifyCommand;
import io.game.connect.five.client.command.CompletedGameCommand;
import io.game.connect.five.client.command.MoveGameCommand;
import io.game.connect.five.client.reader.CommandLineReader;
import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.response.BoardStatusGameActionResponse;
import io.game.connect.five.event.model.MatchType;

public class GamePlayInteractionHandler extends GameEventFrameHandler {

	private CommandLineReader commandLineReader;
	private StompSession session;
	private String playerName;

	private CountDownLatch shutdownLatch;

	public GamePlayInteractionHandler(
			final String playerName,
			final CommandLineReader commandLineReader, 
			final StompSession session, 
			final CountDownLatch shutdownLatch) {
		
		this.playerName = playerName;
		this.commandLineReader = commandLineReader;
		this.session = session;
		this.shutdownLatch = shutdownLatch;
	}

	@Override
	protected void process(GameEvent event) {

		if (GameEventType.GAME_BOARD_STATUS.equals(event.getType())) {
			
			BoardStatusGameActionResponse s = convertGameEventData(event.getData(), BoardStatusGameActionResponse.class);
			
			BoardStatusNotifyCommand c = new BoardStatusNotifyCommand(playerName, s);
			c.run();
			
			if (MatchType.NONE.equals(s.getMatchType())) {
				String nextMoveMessage = playerName.equals(s.getNextPlayerName()) ? 
						"Your move next." : s.getNextPlayerName() + " moves next.";
				
				System.out.println(nextMoveMessage);
				
				if (playerName.equals(s.getNextPlayerName())) {
					
					final MoveGameCommand command = new MoveGameCommand(
							commandLineReader, 
							session, 
							s.getGameId(), 
							playerName, 
							shutdownLatch);
					
					command.run();
				}
			} else {
				String winMessage = playerName.equals(s.getCurrentPlayerName()) ? 
						"You have" : s.getCurrentPlayerName() + " has";
				
				System.out.println(winMessage + " won with a " + s.getMatchType() + " connect!");
				
				final CompletedGameCommand completed = new CompletedGameCommand(
						commandLineReader, 
						session, 
						s.getGameId(), 
						playerName);
				
				completed.run();
				
				shutdownLatch.countDown();
			}
		} else if (GameEventType.GAME_DELETED.equals(event.getType())) {
			
			System.out.println("Game has ended.");
			shutdownLatch.countDown();
			
		}
	}
}