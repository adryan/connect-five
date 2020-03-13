package io.game.connect.five.client.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompSession;

import io.game.connect.five.client.reader.CommandLineReader;
import io.game.connect.five.event.action.request.JoinGameActionRequest;

public class JoinAnyGameCommand extends AbstractCommandLineInteractionCommand<Void> {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(JoinAnyGameCommand.class);
	
	private StompSession session;
	private String playerName;
	
	public JoinAnyGameCommand(final CommandLineReader commandLineReader, 
			final StompSession session,
			final String playerName) {
		super(commandLineReader);
		this.session = session;
		this.playerName = playerName;
	}

	@Override
	public Void run() {
		session.send("/app/join-games", new JoinGameActionRequest(null, playerName).asGameEvent());	
		LOGGER.info("Session {} - Sent JoinGameEvent", session.getSessionId());
		return null;
	}

}
