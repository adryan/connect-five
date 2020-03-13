package io.game.connect.five.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.game.connect.five.event.model.MatchType;
import io.game.connect.five.event.model.Player;
import io.game.connect.five.model.BasicBoard;
import io.game.connect.five.model.Board;
import io.game.connect.five.strategy.BasicConnectedStrategy;
import io.game.connect.five.strategy.ConnectedStrategy;

public class BasicConnectedStrategyTest {
	
	@DisplayName("Test Play Step By Step Hortizontal Connect First Row")
    @Test
    void testExampleStepByStepPlayHortizontalFirstRowConnect() {
		
		Player playerOne = new Player("Player1", 'x');
		Player playerTwo = new Player("Player2", 'o');
		
		ConnectedStrategy strategy = new BasicConnectedStrategy();
		
        Board board = new BasicBoard(6, 9);
        System.out.println(board.toString());
        
        assertAddTile(playerOne, board, 0, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 0, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 6, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 0, 2);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 5, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 2, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 4, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 2, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 7, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 2, 2);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 8, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.HORTIZONTAL);
        
    }
	
	@DisplayName("Test Play Step By Step Hortizontal Connect Top Row")
    @Test
    void testExampleStepByStepPlayHortizontalTopRowConect() {
		
		Player playerOne = new Player("Player1",'x');
		Player playerTwo = new Player("Player2",'o');
		
		ConnectedStrategy strategy = new BasicConnectedStrategy();
		
        Board board = new BasicBoard(6, 9);
        System.out.println(board.toString());
        
        assertAddTile(playerOne, board, 0, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 0, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 0, 2);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 0, 3);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 0, 4);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 1, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 1, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 1, 2);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 1, 3);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 1, 4);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 1, 5);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 2, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 2, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 2, 2);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 2, 3);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 2, 4);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 3, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 3, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 3, 2);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 3, 3);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 3, 4);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 4, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 0, 5);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 5, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 2, 5);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 5, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 3, 5);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 7, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 4, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 4, 2);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 4, 3);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 4, 4);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 4, 5);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.HORTIZONTAL);
    }
	
	@DisplayName("Test Play Step By Step Vertical Connect")
    @Test
    void testExampleStepByStepPlayVerticleConnect() {
		
		Player playerOne = new Player("Player1",'x');
		Player playerTwo = new Player("Player2",'o');
		
		Board board = new BasicBoard(6, 9);
        System.out.println(board.toString());
        
        ConnectedStrategy strategy = new BasicConnectedStrategy();
        
        assertAddTile(playerOne, board, 0, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 1, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 0, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 1, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 0, 2);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 1, 2);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 0, 3);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 2, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 0, 4);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.VERTICAL);
	}	
	
	@Test
    void testExampleStepByStepPlayAcendingDiagonal() {
		
		Player playerOne = new Player("Player1",'x');
		Player playerTwo = new Player("Player2",'o');
		
		Board board = new BasicBoard(6, 9);
        System.out.println(board.toString());
        
        ConnectedStrategy strategy = new BasicConnectedStrategy();
        
        assertAddTile(playerOne, board, 0, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 1, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 1, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 2, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 2, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 7, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 2, 2);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 3, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 4, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 3, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 3, 2);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 3, 3);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 3, 4);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 4, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 4, 2);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 4, 3);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 7, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 4, 4);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.ACENDING_DIAGONAL);
        
	}	
	
	@Test
    void testExampleStepByStepPlayDescendingDiagonal() {
		
		Player playerOne = new Player("Player1",'x');
		Player playerTwo = new Player("Player2",'o');
		
		Board board = new BasicBoard(6, 9);
        System.out.println(board.toString());
        
        ConnectedStrategy strategy = new BasicConnectedStrategy();
        
        assertAddTile(playerOne, board, 0, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 0, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 0, 2);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 0, 3);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 0, 4);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 1, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 1, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
         
        assertAddTile(playerTwo, board, 1, 2);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 1, 3);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 7, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 2, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 2, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 2, 2);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 3, 0);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 3, 1);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.NONE);
        
        assertAddTile(playerTwo, board, 7, 1);
        assertTrue(strategy.isConnected(playerTwo, board) == MatchType.NONE);
        
        assertAddTile(playerOne, board, 4, 0);
        assertTrue(strategy.isConnected(playerOne, board) == MatchType.DECENDING_DIAGONAL);
        
	}
	
	private static void assertAddTile(Player player, Board board, int x, int expectedY) {
		assertTrue(board.addTile(x, player.getTile()));        
        System.out.println(board.toString());        
        assertTrue(board.getPosition(x, expectedY) == player.getTile());
	}

}
