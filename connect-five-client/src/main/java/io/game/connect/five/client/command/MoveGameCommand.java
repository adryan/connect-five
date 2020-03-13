package io.game.connect.five.client.command;

import java.util.concurrent.CountDownLatch;

import org.springframework.messaging.simp.stomp.StompSession;

import io.game.connect.five.client.reader.CommandLineReader;
import io.game.connect.five.event.action.request.MakeMoveGameActionRequest;

public class MoveGameCommand extends AbstractCommandLineInteractionCommand<Void> {
	
	private StompSession session;
	private String gameId;
	private String playerName;
	
	private CountDownLatch shutdownLatch;

	public MoveGameCommand(final CommandLineReader commandLineReader,
			final StompSession session,
			final String gameId,
			final String playerName,
			final CountDownLatch shutdownLatch) {
		super(commandLineReader);
		this.session = session;
		this.playerName = playerName;
		this.gameId= gameId;
		this.shutdownLatch= shutdownLatch;
	}

	@Override
	public Void run() {
		
		boolean isValidOption = false;
		
		while (!isValidOption) {
			String option = commandLineReader.readString("Move [M] or End Game [E]:"); 
			
			 if ("M".equals(option)) {
				 
				 boolean isValidColumn = false;
				 int columnSelection = 0;
				 while (!isValidColumn) {
					 columnSelection = commandLineReader.readInt("Select a column [1-9]:");
					 if (columnSelection >= 1 && columnSelection <= 9) {
						 isValidColumn = true;
					 } else {
						 System.out.println("Invalid column. Please try again.");
					 }
				 }
				 
				session.send("/app/game", 
						new MakeMoveGameActionRequest(gameId, playerName, columnSelection - 1)
						.asGameEvent());
				isValidOption = true;
	        } else if ("E".equals(option)) {
	        	
	        	CompletedGameCommand completed = new CompletedGameCommand(commandLineReader, session, gameId, playerName);
				completed.run();
				isValidOption = true;
				
	        	shutdownLatch.countDown();
	        } else {
	        	System.out.println("Invalid input. Please try again.");
	        }
		}
		return null;
	}
}
