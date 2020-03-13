package io.game.connect.five.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class GameEventTest {
	
	@Test
    void testGameEventType() {
		GameEvent event = new GameEvent();
		assertNull(event.getType());
		
		event.setType(GameEventType.INITIATE);
		
		assertEquals(GameEventType.INITIATE, event.getType());
	}
	
	@Test
	void testGameData() {
		GameEvent event = new GameEvent();
		assertNull(event.getData());
		
		String data = "Test data";
		event.setData(data);
		
		assertEquals(data, event.getData());
	}
}
