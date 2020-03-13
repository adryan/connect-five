package io.game.connect.five.client.game;

import java.util.concurrent.CountDownLatch;

import org.springframework.messaging.simp.stomp.StompSession;

import io.game.connect.five.client.GameEventFrameHandler;
import io.game.connect.five.client.command.MoveGameCommand;
import io.game.connect.five.client.reader.CommandLineReader;
import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.response.InvalidGameActionResponse;

public class GameUserInteractionHandler extends GameEventFrameHandler {

	private CommandLineReader commandLineReader;
	private StompSession session;
	private String playerName;

	private CountDownLatch shutdownLatch;

	public GameUserInteractionHandler(
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

		if (GameEventType.MAKE_MOVE_COMPLETED.equals(event.getType())) {
			
			System.out.println("Successfully completed move.");
			
		} else if (GameEventType.INVALID.equals(event.getType())) {
			
			final InvalidGameActionResponse r = convertGameEventData(
					event.getData(), 
					InvalidGameActionResponse.class);
			
			System.out.println("Invalid action: " + r.getReason());

			if (GameEventType.MAKE_MOVE.equals(r.getActionType())) {
				
				System.out.println("Move issues" + r.getReason());
				
				final MoveGameCommand command = new MoveGameCommand(
						commandLineReader, 
						session, 
						r.getGameId(),
						playerName, 
						shutdownLatch);
				command.run();
			}
		}
	}
}