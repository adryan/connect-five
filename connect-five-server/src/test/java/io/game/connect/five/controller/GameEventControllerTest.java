package io.game.connect.five.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.request.CompletedGameActionRequest;
import io.game.connect.five.event.action.request.GameRemoveActionRequest;
import io.game.connect.five.event.action.request.InitiateGameActionRequest;
import io.game.connect.five.event.action.request.JoinGameActionRequest;
import io.game.connect.five.event.action.request.MakeMoveGameActionRequest;
import io.game.connect.five.event.action.response.BoardStatusGameActionResponse;
import io.game.connect.five.event.action.response.CompletedMoveGameActionResponse;
import io.game.connect.five.event.action.response.GameOpenActionResponse;
import io.game.connect.five.event.action.response.InvalidGameActionResponse;
import io.game.connect.five.event.action.response.ReadyGameActionResponse;
import io.game.connect.five.event.action.response.UnavailableGameActionResponse;
import io.game.connect.five.event.model.MatchType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameEventControllerTest {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GameEventControllerTest.class);

	private static final String APP_GAME = "/app/game";

	private static final String USER_QUEUE_GAME = "/user/queue/game";

	private static final String APP_REMOVE_GAMES = "/app/remove-games";

	private static final String APP_JOIN_GAMES = "/app/join-games";

	private static final String APP_CREATE_GAMES = "/app/create-games";

	private static final String TOPIC_GAMES = "/topic/games";

	@LocalServerPort
	private int port;

	private String websocketServerUrl;

	
	
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private class GameEventStompFrameHandler implements StompFrameHandler {
		
		private BlockingQueue<GameEvent> eventQueue = new ArrayBlockingQueue<>(100);
		
		@Override
		public Type getPayloadType(StompHeaders stompHeaders) {
			System.out.println(stompHeaders.toString());
			return GameEvent.class;
		}

		@Override
		public void handleFrame(StompHeaders stompHeaders, Object o) {
			System.out.println((GameEvent) o);
			eventQueue.add((GameEvent) o);
		}
		
		public BlockingQueue<GameEvent> eventQueue() {
			return eventQueue;
		}
	}

	@BeforeEach
	public void setup() {
		
		websocketServerUrl = "ws://localhost:" + port + "/connect-five-game";
	}

	@Test
	public void testCreateAndDeleteGame() throws InterruptedException, ExecutionException, TimeoutException {
		StompSession session = createSession();
		
		GameEventStompFrameHandler handler = new GameEventStompFrameHandler();
		session.subscribe(TOPIC_GAMES, handler);

		final String playerName = "Alice";
		session.send(APP_CREATE_GAMES, new InitiateGameActionRequest(playerName).asGameEvent());

		GameEvent gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_OPEN, gameEvent.getType());
		
		GameOpenActionResponse a = convertGameEventData(gameEvent.getData(), GameOpenActionResponse.class);
		assertNotNull(a);
		assertNotNull(a.getGameId());
		
		session.send(APP_REMOVE_GAMES, new GameRemoveActionRequest(a.getGameId()).asGameEvent());
		gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_DELETED, gameEvent.getType());
	}
	
	@Test
	public void testJoinExistingGame() throws InterruptedException, ExecutionException, TimeoutException {
		StompSession session = createSession();

		GameEventStompFrameHandler handler = new GameEventStompFrameHandler();
		session.subscribe(TOPIC_GAMES, handler);

		final String playerName = "Alice";
		session.send(APP_CREATE_GAMES, new InitiateGameActionRequest(playerName).asGameEvent());

		GameEvent gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_OPEN, gameEvent.getType());
		GameOpenActionResponse a = convertGameEventData(gameEvent.getData(), GameOpenActionResponse.class);
		
		final String playerName2 = "Bob";
		session.send(APP_JOIN_GAMES, new JoinGameActionRequest(a.getGameId(), playerName2).asGameEvent());
		gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_READY, gameEvent.getType());
		
		ReadyGameActionResponse r = convertGameEventData(gameEvent.getData(), ReadyGameActionResponse.class);
		assertNotNull(r);
		assertEquals(a.getGameId(), r.getGameId());
		assertTrue(r.getPlayers().size() == 2);
		r.getPlayers().forEach(p -> {
			assertTrue(p.getName().equals(playerName) || p.getName().equals(playerName2));
		});
		
		session.send(APP_REMOVE_GAMES, new GameRemoveActionRequest(a.getGameId()).asGameEvent());
		gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_DELETED, gameEvent.getType());
	}
	
	@Test
	public void testJoinAnyGame() throws InterruptedException, ExecutionException, TimeoutException {
		StompSession session = createSession();

		GameEventStompFrameHandler handler = new GameEventStompFrameHandler();
		session.subscribe(TOPIC_GAMES, handler);

		final String playerName = "Alice";
		session.send(APP_CREATE_GAMES, new InitiateGameActionRequest(playerName).asGameEvent());

		GameEvent gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_OPEN, gameEvent.getType());
		GameOpenActionResponse a = convertGameEventData(gameEvent.getData(), GameOpenActionResponse.class);
		
		final String playerName2 = "Bob";
		//Omitting GameId to join any game ... 
		session.send(APP_JOIN_GAMES, new JoinGameActionRequest(null, playerName2).asGameEvent());
		gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_READY, gameEvent.getType());
		
		ReadyGameActionResponse r = convertGameEventData(gameEvent.getData(), ReadyGameActionResponse.class);
		assertNotNull(r);
		assertEquals(a.getGameId(), r.getGameId());
		assertTrue(r.getPlayers().size() == 2);
		r.getPlayers().forEach(p -> {
			assertTrue(p.getName().equals(playerName) || p.getName().equals(playerName2));
		});
		
		session.send(APP_REMOVE_GAMES, new GameRemoveActionRequest(a.getGameId()).asGameEvent());
		gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_DELETED, gameEvent.getType());
	}
	
	@Test
	public void testJoinInvalidGame() throws InterruptedException, ExecutionException, TimeoutException {
		StompSession session = createSession();

		GameEventStompFrameHandler handler = new GameEventStompFrameHandler();
		session.subscribe(TOPIC_GAMES, handler);

		final String playerName = "Alice";
		session.send(APP_CREATE_GAMES, new InitiateGameActionRequest(playerName).asGameEvent());

		GameEvent gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_OPEN, gameEvent.getType());
		GameOpenActionResponse a = convertGameEventData(gameEvent.getData(), GameOpenActionResponse.class);
		
		final String playerName2 = "Bob";
		//Providing a random to test an invalid
		session.send(APP_JOIN_GAMES, new JoinGameActionRequest(UUID.randomUUID().toString(), playerName2).asGameEvent());
		gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_UNAVAILABLE, gameEvent.getType());
		
		UnavailableGameActionResponse u = convertGameEventData(gameEvent.getData(), UnavailableGameActionResponse.class);
		assertNotNull(u);
		
		session.send(APP_REMOVE_GAMES, new GameRemoveActionRequest(a.getGameId()).asGameEvent());
		gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_DELETED, gameEvent.getType());
	}
	
	@Test
	public void testJoinGameBeforeExists() throws InterruptedException, ExecutionException, TimeoutException {
		StompSession session = createSession();

		GameEventStompFrameHandler handler = new GameEventStompFrameHandler();
		session.subscribe(TOPIC_GAMES, handler);
		
		final String playerName2 = "Bob";
		//Providing a random to test an invalid
		session.send(APP_JOIN_GAMES, new JoinGameActionRequest(UUID.randomUUID().toString(), playerName2).asGameEvent());
		GameEvent gameEvent = handler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_UNAVAILABLE, gameEvent.getType());
		
		UnavailableGameActionResponse u = convertGameEventData(gameEvent.getData(), UnavailableGameActionResponse.class);
		assertNotNull(u);
		
	}
	
	@Test
	public void testPlayGame() throws InterruptedException, ExecutionException, TimeoutException {
		StompSession session = createSession();

		GameEventStompFrameHandler generalGameHandler = new GameEventStompFrameHandler();
		session.subscribe(TOPIC_GAMES, generalGameHandler);

		final String playerOne = "Alice";
		session.send(APP_CREATE_GAMES, new InitiateGameActionRequest(playerOne).asGameEvent());

		GameEvent gameEvent = generalGameHandler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_OPEN, gameEvent.getType());
		GameOpenActionResponse a = convertGameEventData(gameEvent.getData(), GameOpenActionResponse.class);
		
		final String playerTwo = "Bob";
		session.send(APP_JOIN_GAMES, new JoinGameActionRequest(a.getGameId(), playerTwo).asGameEvent());
		gameEvent = generalGameHandler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_READY, gameEvent.getType());
		
		ReadyGameActionResponse r = convertGameEventData(gameEvent.getData(), ReadyGameActionResponse.class);
		assertNotNull(r);
		assertEquals(a.getGameId(), r.getGameId());
		assertTrue(r.getPlayers().size() == 2);
		r.getPlayers().forEach(p -> {
			assertTrue(p.getName().equals(playerOne) || p.getName().equals(playerTwo));
		});
		
		//Start Game. 
		// 1. Subscribe to Game Channel to get all game updates
		// 2. Subscribe to User Channel to get specific updates
		
		GameEventStompFrameHandler gameHandler = new GameEventStompFrameHandler();
		session.subscribe(TOPIC_GAMES + "/" + a.getGameId(), gameHandler);
		
		GameEventStompFrameHandler userHandler = new GameEventStompFrameHandler();
		session.subscribe(USER_QUEUE_GAME, userHandler);
		
		//Start making moves
		makeMove(0, a.getGameId(), playerOne, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		 
		makeMove(1, a.getGameId(), playerTwo, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(0, a.getGameId(), playerOne, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(2, a.getGameId(), playerTwo, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(0, a.getGameId(), playerOne, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(1, a.getGameId(), playerTwo, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(0, a.getGameId(), playerOne, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(1, a.getGameId(), playerTwo, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(0, a.getGameId(), playerOne, session, gameHandler, MatchType.VERTICAL);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		session.send(APP_REMOVE_GAMES, new GameRemoveActionRequest(a.getGameId()).asGameEvent());
		gameEvent = generalGameHandler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_DELETED, gameEvent.getType());
	}
	
	@Test
	public void testPlayGameWithInvalidMoves() throws InterruptedException, ExecutionException, TimeoutException {
		StompSession session = createSession();

		GameEventStompFrameHandler generalGameHandler = new GameEventStompFrameHandler();
		session.subscribe(TOPIC_GAMES, generalGameHandler);

		final String playerOne = "Alice";
		session.send(APP_CREATE_GAMES, new InitiateGameActionRequest(playerOne).asGameEvent());

		GameEvent gameEvent = generalGameHandler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_OPEN, gameEvent.getType());
		GameOpenActionResponse a = convertGameEventData(gameEvent.getData(), GameOpenActionResponse.class);
		
		final String playerTwo = "Bob";
		session.send(APP_JOIN_GAMES, new JoinGameActionRequest(a.getGameId(), playerTwo).asGameEvent());
		gameEvent = generalGameHandler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_READY, gameEvent.getType());
		
		ReadyGameActionResponse r = convertGameEventData(gameEvent.getData(), ReadyGameActionResponse.class);
		assertNotNull(r);
		assertEquals(a.getGameId(), r.getGameId());
		assertTrue(r.getPlayers().size() == 2);
		r.getPlayers().forEach(p -> {
			assertTrue(p.getName().equals(playerOne) || p.getName().equals(playerTwo));
		});
		
		//Start Game. 
		// 1. Subscribe to Game Channel to get all game updates
		// 2. Subscribe to User Channel to get specific updates
		
		GameEventStompFrameHandler gameHandler = new GameEventStompFrameHandler();
		session.subscribe(TOPIC_GAMES + "/" + a.getGameId(), gameHandler);
		
		GameEventStompFrameHandler userHandler = new GameEventStompFrameHandler();
		session.subscribe(USER_QUEUE_GAME, userHandler);
		
		//Start making moves
		makeMove(0, a.getGameId(), playerOne, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		 
		//PlayerOne moves again
		makeInvalidMove(1, a.getGameId(), playerOne, session, gameHandler);
		verifyUserChannel(GameEventType.INVALID, InvalidGameActionResponse.class, userHandler);
		
		makeMove(0, a.getGameId(), playerTwo, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(0, a.getGameId(), playerOne, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(0, a.getGameId(), playerTwo, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(0, a.getGameId(), playerOne, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		makeMove(0, a.getGameId(), playerTwo, session, gameHandler, MatchType.NONE);
		verifyUserChannel(GameEventType.MAKE_MOVE_COMPLETED, CompletedMoveGameActionResponse.class, userHandler);
		
		//Too many tiles on column 0
		makeInvalidMove(0, a.getGameId(), playerOne, session, gameHandler);
		verifyUserChannel(GameEventType.INVALID, InvalidGameActionResponse.class, userHandler);
		
		
		//End Game by Player 2.
		session.send(APP_GAME, new CompletedGameActionRequest(a.getGameId(), playerTwo).asGameEvent());
		gameEvent = gameHandler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_DELETED, gameEvent.getType());
		
		
		session.send(APP_REMOVE_GAMES, new GameRemoveActionRequest(a.getGameId()).asGameEvent());
	}
	
	private <T> T verifyUserChannel(
			final GameEventType eventType, 
			final Class<T> clazzType, 
			final GameEventStompFrameHandler userHandler) throws InterruptedException {
		
		final GameEvent gameEvent = userHandler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertEquals(eventType, gameEvent.getType());
		
		return convertGameEventData(gameEvent.getData(), clazzType);
	}

	private BoardStatusGameActionResponse makeMove(int selectedColumn, String gameId, String name, 
			StompSession session, 
			GameEventStompFrameHandler gameHandler, 
			MatchType expectedMatchType) throws InterruptedException {
		session.send(APP_GAME, 
				new MakeMoveGameActionRequest(gameId, name, selectedColumn)
				.asGameEvent());
		
		GameEvent gameEvent = gameHandler.eventQueue().poll(10, TimeUnit.SECONDS);
		assertNotNull(gameEvent);
		assertEquals(GameEventType.GAME_BOARD_STATUS, gameEvent.getType());
		BoardStatusGameActionResponse s = convertGameEventData(gameEvent.getData(), BoardStatusGameActionResponse.class);
		assertEquals(expectedMatchType, s.getMatchType());
		
		LOGGER.info(s.getState());
		
		return s;
	}
	
	private void makeInvalidMove(int selectedColumn, String gameId, String name, 
			StompSession session, 
			GameEventStompFrameHandler gameHandler) throws InterruptedException {
		session.send(APP_GAME, 
				new MakeMoveGameActionRequest(gameId, name, selectedColumn)
				.asGameEvent());
	}
	
	private StompSession createSession() throws InterruptedException, ExecutionException, TimeoutException {
		WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		return stompClient.connect(websocketServerUrl, new StompSessionHandlerAdapter() {
		}).get(1, TimeUnit.SECONDS);
	}

	private List<Transport> createTransportClient() {
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		return transports;
	}

	private <T> T convertGameEventData(Object data, Class<T> type) {
		return OBJECT_MAPPER.convertValue(data, type);
	}
}
