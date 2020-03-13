package io.game.connect.five.client;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import io.game.connect.five.client.command.GameOption;
import io.game.connect.five.client.command.InteractionCommand;
import io.game.connect.five.client.command.IntroduceCommand;
import io.game.connect.five.client.command.factory.GameSetupCommandFactory;
import io.game.connect.five.client.command.result.IntroduceResult;
import io.game.connect.five.client.game.GameSetupInteractionHandler;
import io.game.connect.five.client.reader.CommandLineReader;
import io.game.connect.five.client.reader.CommandLineReaderImpl;

public class GameSessionHandler extends StompSessionHandlerAdapter {
	
	private static final String TOPIC_GAMES = "/topic/games";

	private final static Logger LOGGER = LoggerFactory.getLogger(GameSessionHandler.class);
	
	private CountDownLatch shutdownLatch;
	
	public GameSessionHandler(final CountDownLatch shutdownLatch) {
		this.shutdownLatch = shutdownLatch;
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		LOGGER.info("Session {} - established.", session.getSessionId());	
		
		final CommandLineReader commandLineReader = new CommandLineReaderImpl();
		
		IntroduceCommand introduce = new IntroduceCommand(commandLineReader);
		IntroduceResult r = introduce.run();		
		
		final String playerName = r.playerName;
        final GameOption gameOption = r.gameOption;
        
        InteractionCommand<?> command = GameSetupCommandFactory.getCommand(gameOption, session, playerName, commandLineReader );
        command.run();

		LOGGER.info("Subscribe to Games topic");
		
		session.subscribe(TOPIC_GAMES, new GameSetupInteractionHandler(gameOption,
				playerName,
				commandLineReader, 
				session, 
				shutdownLatch));
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers,
			byte[] payload, Throwable exception) {
		LOGGER.error("Exception occured", exception);
		
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		LOGGER.error("Transport exception occured", exception);
	}
}
