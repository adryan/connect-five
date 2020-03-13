package io.game.connect.five.model;

public interface Board {
	char getPosition(int x, int y);
	
	int getRows();
	int getColumns();
	
	boolean addTile(int x, char tile);
	
	char getEmptyCellCharacter();
}
