package io.game.connect.five.event.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlayerTest {
	
	@Test
	public void testPlayerName() {
		final String name = "Alice";
		final char tile = 'x';
		
		Player p = new Player(name, tile);
	
		assertEquals(name, p.getName());
		assertEquals(tile, p.getTile());
		assertNotNull(p.getId());
	}
	
	@Test
	public void testEquals() {
		final String name = "Alice";
		final char tile = 'x';
		
		Player p1 = new Player(name, tile);
		Player p2 = new Player(name, tile);
		
		assertTrue(p2.equals(p1));
		
		assertFalse(p1.equals(null));
	}
	
	@Test
	public void testHashCode() {
		final String name = "Alice";
		final char tile = 'x';
		
		Player p1 = new Player(name, tile);
		Player p2 = new Player(name, tile);
		
		assertTrue(p2.hashCode() == p1.hashCode());
		
	}
}
