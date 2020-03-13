package io.game.connect.five.controller;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.game.connect.five.event.GameEvent;
import io.game.connect.five.event.GameEventType;
import io.game.connect.five.event.action.GameAction;
import io.game.connect.five.event.action.request.CompletedGameActionRequest;
import io.game.connect.five.event.action.request.GameRemoveActionRequest;
import io.game.connect.five.event.action.request.InitiateGameActionRequest;
import io.game.connect.five.event.action.request.JoinGameActionRequest;
import io.game.connect.five.event.action.request.MakeMoveGameActionRequest;
import io.game.connect.five.event.action.response.BoardStatusGameActionResponse;
import io.game.connect.five.event.action.response.CompletedMoveGameActionResponse;
import io.game.connect.five.event.action.response.GameDeletedActionResponse;
import io.game.connect.five.event.action.response.GameOpenActionResponse;
import io.game.connect.five.event.action.response.InvalidGameActionResponse;
import io.game.connect.five.event.action.response.ReadyGameActionResponse;
import io.game.connect.five.event.action.response.UnavailableGameActionResponse;
import io.game.connect.five.event.model.Player;
import io.game.connect.five.exception.InvalidMoveGameException;
import io.game.connect.five.exception.JoinGameException;
import io.game.connect.five.model.BoardState;
import io.game.connect.five.model.Game;
import io.game.connect.five.service.GameService;

@Controller
public class GameEventController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GameEventController.class);
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@MessageMapping("/create-games")
	@SendTo("/topic/games")
	public GameEvent initiateGame(GameEvent event) throws Exception {
		
		LOGGER.info("Received a GameEvent - Type={}, Data={}", event.getType(), event.getData().toString());
		
		InitiateGameActionRequest e = convertGameEventData(event.getData(), InitiateGameActionRequest.class);  
		
		Player player = new Player(e.getPlayerName(), 'x');
		
		UUID uuid = gameService.createGame(player);
		
		String gameId = uuid.toString();

		return new GameOpenActionResponse(gameId).asGameEvent();
	}
	
	@MessageMapping("/join-games")
	@SendTo("/topic/games")
	public GameEvent joinGame(GameEvent event) throws Exception {
		
		LOGGER.info("Received a GameEvent - Type={}, Data={}", event.getType(), event.getData().toString());
		
		JoinGameActionRequest e = convertGameEventData(event.getData(), JoinGameActionRequest.class);
		
		Player player = new Player(e.getPlayerName(), 'o');
		
		Game game = null;
		if (e.getGameId() == null || e.getGameId().isEmpty()) {
			Optional<Game> gameIfPresent = gameService.joinGame(player);
			if (gameIfPresent.isPresent()) {
				game = gameIfPresent.get();
			} else {
				return new UnavailableGameActionResponse(player.getName()).asGameEvent();
			}
			
		} else {
			
			UUID uuid;
			try {
				uuid = UUID.fromString(e.getGameId());
			} catch (IllegalArgumentException t) {
				return new UnavailableGameActionResponse(player.getName()).asGameEvent();
			}

			try {
				game = gameService.joinGame(uuid, player);
			} catch (JoinGameException ex) {
				return new UnavailableGameActionResponse(player.getName()).asGameEvent();
			}
		}
		
		final String gameId = game.getId().toString();
		
		return new ReadyGameActionResponse(gameId, game.getPlayers()).asGameEvent();
	}
	
	@MessageMapping("/remove-games")
	@SendTo("/topic/games")
	public GameEvent removeGame(GameEvent event) throws Exception {
		
		LOGGER.info("Received a GameEvent - Type={}, Data={}", event.getType(), event.getData().toString());
		
		GameRemoveActionRequest e = convertGameEventData(event.getData(), GameRemoveActionRequest.class);  
		
		UUID gameId = UUID.fromString(e.getGameId());
		
		gameService.deleteGameById(gameId);
		return new GameDeletedActionResponse(e.getGameId()).asGameEvent();
	}
	
	@MessageMapping("/game")
    @SendToUser("/queue/game")
    public GameEvent processGameEventFromClient(@Payload GameEvent event, Principal principal) throws Exception {
		
		LOGGER.info("Received a GameEvent - Type={}, Data={}", event.getType(), event.getData().toString());
		
		if (GameEventType.MAKE_MOVE.equals(event.getType())) {
			MakeMoveGameActionRequest e = convertGameEventData(event.getData(), MakeMoveGameActionRequest.class); 
			
			LOGGER.info("Looking up Game {}", e.getGameId());
			
			UUID gameId = UUID.fromString(e.getGameId());
			Optional<Game> gameIfPresent = gameService.getGameById(gameId);
			
			if (!gameIfPresent.isPresent()) {
				return new InvalidGameActionResponse(
						e.getGameId(), 
						e.getPlayerName(), 
						e, "Game Id is not valid")
						.asGameEvent();			
			}
			
			LOGGER.info("Found Game {}", e.getGameId());
			
			final Game game = gameIfPresent.get();
			
			Optional<Player> playerIfPresent = game.getPlayerByName(e.getPlayerName());
			if (!playerIfPresent.isPresent()) {
				return new InvalidGameActionResponse(
						e.getGameId(), 
						e.getPlayerName(), 
						e, "Could not find player in game")
						.asGameEvent();
			}
			
			LOGGER.info("Found Player {}", e.getPlayerName());
			
			final Player player = playerIfPresent.get();
			
			if (game.getNextPlayer() != null && !player.getId().equals(game.getNextPlayer())) {
				return new InvalidGameActionResponse(
						e.getGameId(), 
						e.getPlayerName(), 
						e, "Player " + player.getName() + " is not allowed to move next")
						.asGameEvent();
			}
			
			LOGGER.info("Making move for Player {} - Column {}", e.getPlayerName(), e.getColumnSelected());
			
			BoardState state;
			try {
				state = gameService.makeMove(gameId, player, e.getColumnSelected());
				game.setNextPlayer(game.getNextPlayer(player.getName()).get().getId());
				
			} catch (InvalidMoveGameException ex) {
				return new InvalidGameActionResponse(
						e.getGameId(), 
						e.getPlayerName(), e, ex.getMessage())
						.asGameEvent();
			}
			
			
			BoardStatusGameActionResponse action = new BoardStatusGameActionResponse();
			action.setGameId(e.getGameId());
			action.setIsConnected(state.getIsConnected());
			action.setMatchType(state.getMatchType());
			action.setState(state.getState());
			action.setCurrentPlayerName(e.getPlayerName());
			
			Optional<Player> n = game.getNextPlayer(e.getPlayerName());
			
			action.setNextPlayerName(n.get().getName());
			
			broadcastStatusOnGameChannel(action);
			
			return new CompletedMoveGameActionResponse(e.getGameId(), e.getPlayerName(), e.getColumnSelected()).asGameEvent();
			
		} else if (GameEventType.GAME_COMPLETED.equals(event.getType())) {
			CompletedGameActionRequest e = convertGameEventData(event.getData(), CompletedGameActionRequest.class); 
			
			gameService.deleteGameById(UUID.fromString(e.getGameId()));
			
			GameDeletedActionResponse a = new GameDeletedActionResponse(e.getGameId());
			
			broadcastDeleteOnGameChannel(a);
			
			return a.asGameEvent();
		}
		
		return new InvalidGameActionResponse("", "", ((GameAction) event.getData()), 
				"Unsupported Request")
				.asGameEvent();	
	  }
	
	private void broadcastStatusOnGameChannel(BoardStatusGameActionResponse event) throws Exception {
		this.messagingTemplate.convertAndSend("/topic/games/" + event.getGameId(), event.asGameEvent());
	}
	
	private void broadcastDeleteOnGameChannel(GameDeletedActionResponse event) throws Exception {
		this.messagingTemplate.convertAndSend("/topic/games/" + event.getGameId(), event.asGameEvent());
	}
	
	private <T> T convertGameEventData(Object data, Class<T> type) {
		return OBJECT_MAPPER.convertValue(data, type);  
	}
}
