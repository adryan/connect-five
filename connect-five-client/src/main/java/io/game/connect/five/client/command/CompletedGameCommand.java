package io.game.connect.five.client.command;

import org.springframework.messaging.simp.stomp.StompSession;

import io.game.connect.five.client.reader.CommandLineReader;
import io.game.connect.five.event.action.request.CompletedGameActionRequest;

public class CompletedGameCommand extends AbstractCommandLineInteractionCommand<Void> {
	
	private StompSession session;
	private String gameId;
	private String playerName;

	public CompletedGameCommand(final CommandLineReader commandLineReader,
			final StompSession session,
			final String gameId,
			final String playerName) {
		super(commandLineReader);
		this.session = session;
		this.playerName = playerName;
		this.gameId= gameId;
	}

	@Override
	public Void run() {
		
		session.send("/app/game", 
				new CompletedGameActionRequest(gameId, playerName)
				.asGameEvent());
		return null;
	}
}
