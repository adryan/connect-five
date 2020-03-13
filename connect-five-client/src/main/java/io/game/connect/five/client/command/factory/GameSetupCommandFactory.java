package io.game.connect.five.client.command.factory;

import org.springframework.messaging.simp.stomp.StompSession;

import io.game.connect.five.client.command.GameOption;
import io.game.connect.five.client.command.InteractionCommand;
import io.game.connect.five.client.command.JoinAnyGameCommand;
import io.game.connect.five.client.command.JoinGameCommand;
import io.game.connect.five.client.command.NewGameCommand;
import io.game.connect.five.client.reader.CommandLineReader;

public class GameSetupCommandFactory {
	
	 public static InteractionCommand<?> getCommand(
			 final GameOption option, 
			 final StompSession session,
			 final String playerName,
			 final CommandLineReader commandLineReader) {
		 
		 if (GameOption.NEW.equals(option)) {
			 return new NewGameCommand(commandLineReader, session, playerName);
		 } else if (GameOption.JOIN.equals(option)) {
			 return new JoinGameCommand(commandLineReader, session, playerName);
		 } else if (GameOption.JOIN_ANY.equals(option)) {
			 return new JoinAnyGameCommand(commandLineReader, session, playerName);
		 }
		 throw new UnsupportedOperationException("Invalid command");
	 }
}
