package io.game.connect.five.model;

public class BasicBoard implements Board  {
	
	private static final char EMPTY_CELL = ' ';
	private int rows;
	private int columns;
	
	private char[][] board; 
	
	public BasicBoard() {
		this(6, 9);
	}
	
	public BasicBoard(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.board = new char[columns][rows];
		
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				board[c][r] = EMPTY_CELL;
			}
		}
	}

	@Override
	public char getPosition(int column, int row) {
		if (row >= rows || column >= columns) {
			throw new IllegalStateException("Invalid column or row");
		}
		return board[column][row];
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getColumns() {
		return columns;
	}

	@Override
	public boolean addTile(int column, char tile) {
		
		if (column >= columns) {
			return false;
		}
		
		for (int r = 0; r < rows; r++) {
			if (board[column][r] == EMPTY_CELL) {
				board[column][r] = tile;
				return true;
			}
		}			
		return false;
	}
	
	protected void initializeBoard(char[][] board) {
		this.board = board;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int r = (rows-1); r >= 0; r--) {
			for (int c = 0; c < columns; c++) {
				builder.append("[").append(board[c][r]).append("]");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	@Override
	public char getEmptyCellCharacter() {
		return EMPTY_CELL;
	}
}
