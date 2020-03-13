package io.game.connect.five.client;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.game.connect.five.event.GameEvent;

/**
 * General STOMP frame handler to receive GameEvents.
 *
 */
public abstract class GameEventFrameHandler implements StompFrameHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GameEventFrameHandler.class);
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	
	protected abstract void process(GameEvent event);

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return GameEvent.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		GameEvent event = (GameEvent) payload;
		LOGGER.info("Received a GameEvent - Type={}, Data={}", event.getType(), event.getData().toString());
		process(event);
	}
	
	protected <T> T convertGameEventData(Object data, Class<T> type) {
		return OBJECT_MAPPER.convertValue(data, type);  
	}

}
