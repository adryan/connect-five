package io.game.connect.five.client.reader;

import java.util.Scanner;

public class CommandLineReaderImpl implements CommandLineReader {
	
	final Scanner scanner = new Scanner(System.in);
	
	public String readString(String message) {
		System.out.println(message);
	    return scanner.next();
	}
	
	public int readInt(String message) {
		System.out.println(message);
    	return scanner.nextInt();
	}
	
	public void close() {
		scanner.close();
	}
}
