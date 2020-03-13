package io.game.connect.five.client.command;

import io.game.connect.five.client.reader.CommandLineReader;

public abstract class AbstractCommandLineInteractionCommand<T> implements InteractionCommand<T> {
	
	protected CommandLineReader commandLineReader;
	
	public AbstractCommandLineInteractionCommand(CommandLineReader commandLineReader) {
		this.commandLineReader = commandLineReader;
	}
}
