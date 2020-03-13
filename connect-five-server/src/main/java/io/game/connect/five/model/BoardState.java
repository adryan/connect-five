package io.game.connect.five.model;

import io.game.connect.five.event.model.MatchType;

public class BoardState {
	
	private String state;
	
	private Boolean isConnected = false;
	
	private MatchType matchType = MatchType.NONE;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}	
}
