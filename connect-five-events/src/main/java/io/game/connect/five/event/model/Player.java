package io.game.connect.five.event.model;

import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Representation of a player in a game.
 *
 */
public class Player {
	
	private UUID id;

	private String name;

	private char tile;
	
	public Player() {
	}

	public Player(String name, char tile) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(tile);
		
		this.tile = tile;
		this.name = name;
		this.id = UUID.randomUUID();
	}

	public char getTile() {
		return tile;
	}

	public String getName() {
		return name;
	}
	
	public UUID getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(name)
				.hashCode();
	}

	@Override
	public boolean equals(Object o) {

		if (o == this)
			return true;
		if (!(o instanceof Player)) {
			return false;
		}

		Player p = (Player) o;

		return new EqualsBuilder()
				.append(name, p.name)
				.isEquals();
	}
}
