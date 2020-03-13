package io.game.connect.five.client.reader;

public interface CommandLineReader {
	
	String readString(String message);
	
	int readInt(String message);
	
	void close();
}
