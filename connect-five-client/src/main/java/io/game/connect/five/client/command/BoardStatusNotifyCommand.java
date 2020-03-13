package io.game.connect.five.client.command;

import io.game.connect.five.event.action.response.BoardStatusGameActionResponse;

public class BoardStatusNotifyCommand implements InteractionCommand<Void> {
	
	private String playerName;
	private BoardStatusGameActionResponse status;
	
	public BoardStatusNotifyCommand(final String playerName, BoardStatusGameActionResponse status) {
		this.playerName = playerName;
		this.status = status;
	}

	@Override
	public Void run() {
		
		String currentMoveMessage = playerName.equals(status.getCurrentPlayerName()) ? 
				"You have added a tile." : status.getCurrentPlayerName() + " has added a tile.";
		
		System.out.println(currentMoveMessage);
		System.out.println(status.getState());
		return null;
	}

}
