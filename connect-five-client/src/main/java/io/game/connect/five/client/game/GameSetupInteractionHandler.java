package io.game.connect.five.client.game;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompSession;

import io.game.connect.five.client.GameEventFrameHandler;
import io.game.connect.five.client.command.GameOption;
import io.game.connect.five.client.command.MoveGameCommand;
import io.game.connect.five.client.reader.CommandLineReader;
import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.response.GameOpenActionResponse;
import io.game.connect.five.event.action.response.ReadyGameActionResponse;
import io.game.connect.five.event.action.response.UnavailableGameActionResponse;

public class GameSetupInteractionHandler extends GameEventFrameHandler {

	private static final String USER_QUEUE_GAME = "/user/queue/game";

	private static final String TOPIC_GAMES = "/topic/games/";

	private final static Logger LOGGER = LoggerFactory.getLogger(GameSetupInteractionHandler.class);

	private GameOption gameOption;
	private CommandLineReader commandLineReader;
	private StompSession session;
	private String playerName;

	private CountDownLatch shutdownLatch;

	public GameSetupInteractionHandler(final GameOption gameOption, 
			final String playerName,
			final CommandLineReader commandLineReader, 
			final StompSession session, 
			final CountDownLatch shutdownLatch) {
		
		this.gameOption = gameOption;
		this.playerName = playerName;
		this.commandLineReader = commandLineReader;
		this.session = session;
		this.shutdownLatch = shutdownLatch;
	}

	@Override
	protected void process(GameEvent event) {

		if (GameEventType.GAME_OPEN.equals(event.getType())) {
			final GameOpenActionResponse o = convertGameEventData(event.getData(), GameOpenActionResponse.class);
			LOGGER.info("Game {} is created", o.getGameId());

			System.out.println("Started Game " + o.getGameId());
			System.out.println("Waiting for another player to join ... ");

		} else if (GameEventType.GAME_READY.equals(event.getType())) {

			final ReadyGameActionResponse r = convertGameEventData(event.getData(), ReadyGameActionResponse.class);

			final String gameId = r.getGameId();
			LOGGER.info("Game {} is ready", r.getGameId());
			LOGGER.info("Start playing .....");

			// Initiator always starts.
			if (GameOption.NEW.equals(gameOption)) {

				System.out.println("Ready to play?");

				final MoveGameCommand command = new MoveGameCommand(
						commandLineReader, 
						session, 
						r.getGameId(),
						playerName, 
						shutdownLatch);

				command.run();
			}

			session.subscribe(TOPIC_GAMES + gameId, new GamePlayInteractionHandler(playerName,commandLineReader, session,shutdownLatch));

			session.subscribe(USER_QUEUE_GAME, new GameUserInteractionHandler(playerName,commandLineReader, session,shutdownLatch));
		
		} else if (GameEventType.GAME_DELETED.equals(event.getType())) {
			
			System.out.println("The game has ended");
			
			shutdownLatch.countDown();
			
		} else if (GameEventType.GAME_UNAVAILABLE.equals(event.getType())) {
			
			UnavailableGameActionResponse r = convertGameEventData(event.getData(), UnavailableGameActionResponse.class);
			
			if (playerName.equals(r.getPlayerName())) {
				System.out.println("The game is unavailable");
				shutdownLatch.countDown();
			}
		}
	}
}