package io.game.connect.five.client.command;

import io.game.connect.five.client.command.result.IntroduceResult;
import io.game.connect.five.client.reader.CommandLineReader;
import io.game.connect.five.client.reader.CommandLineReaderImpl;

public class IntroduceCommand extends AbstractCommandLineInteractionCommand<IntroduceResult> {
	
	public IntroduceCommand(CommandLineReader commandLineReader) {
		super(commandLineReader);
	}

	@Override
	public IntroduceResult run() {
		final CommandLineReader cmdLineReader = new CommandLineReaderImpl();
		
		IntroduceResult result = new IntroduceResult();
		
		result.playerName = cmdLineReader.readString("Please enter your name : ");
        
        boolean isOptionValue = false;
        while (!isOptionValue) {
        	
        	String gameStartOption = cmdLineReader.readString("Initiate [I], Join [J] a particular game or Join Any [A] game : [Enter I, J or A]");
        	
        	if ("J".equals(gameStartOption)) {
            	result.gameOption = GameOption.JOIN;
            	isOptionValue = true;
            } else if ("A".equals(gameStartOption)) {
            	result.gameOption = GameOption.JOIN_ANY;
            	isOptionValue = true;
            } else if ("I".equals(gameStartOption)) {
            	result.gameOption = GameOption.NEW;
            	isOptionValue = true;
            } else {
            	System.out.println("Invalid game selection. Please try again.");
            }
        }
        
        
        
        System.out.println("Hello " + result.playerName + ".");
        
        return result;
	}
}
