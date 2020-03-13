package io.game.connect.five.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.game.connect.five.event.model.MatchType;
import io.game.connect.five.event.model.Player;
import io.game.connect.five.model.Board;

/**
 * This algorithm is based on https://stackoverflow.com/questions/32770321/connect-4-check-for-a-win-algorithm
 * 
 * @author adria
 *
 */
public class BasicConnectedStrategy implements ConnectedStrategy {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BasicConnectedStrategy.class);
	
	@Override
	public MatchType isConnected(Player player, Board board) {
		
		// horizontalCheck 
	    for (int r = 0; r < board.getRows(); r++ ) {
	        for (int c = 0; c < board.getColumns()-4; c++) {
	        	
	        	LOGGER.debug("hortizontal[c=" + c + ",r=" + r + ",tile=" + board.getPosition(c, r) + "]");
	        	
	            if (board.getPosition(c, r) == player.getTile() 
	            		&& board.getPosition(c+1,r) == player.getTile() 
	            		&& board.getPosition(c+2,r) == player.getTile() 
	            		&& board.getPosition(c+3,r) == player.getTile()
	            		&& board.getPosition(c+4,r) == player.getTile()) {
	            	
	            	LOGGER.debug("hortizontal match.");
	            	
	                return MatchType.HORTIZONTAL;
	            }  
	        }
	    }
	    
	    // verticalCheck
	    for (int c = 0; c < board.getColumns(); c++) {
	    	for (int r = 0; r < board.getRows()-4; r++ ) {
	        	
	    		LOGGER.debug("row[c=" + c + ",r=" + r + ",tile=" + board.getPosition(c, r) + "]");
	        	
	            if (board.getPosition(c,r) == player.getTile() 
	            		&& board.getPosition(c,r+1) == player.getTile() 
	            		&& board.getPosition(c,r+2) == player.getTile() 
	            		&& board.getPosition(c,r+3) == player.getTile()
	            		&& board.getPosition(c,r+4) == player.getTile()) {
	            	
	            	LOGGER.debug("vertical match.");
	            	
	                return MatchType.VERTICAL;
	            }           
	        }
	    }
	    
	    
	    // descendingDiagonalCheck 
	    for (int c = 4; c < board.getColumns(); c++){
	        for (int r = 0; r < board.getRows()-4; r++){
	            if (board.getPosition(c,r) == player.getTile() 
	            		&& board.getPosition(c-1,r+1) == player.getTile() 
	            		&& board.getPosition(c-2,r+2) == player.getTile() 
	            		&& board.getPosition(c-3,r+3) == player.getTile()
	            		&& board.getPosition(c-4,r+4) == player.getTile())
	                return MatchType.DECENDING_DIAGONAL;
	        }
	    }
	    
	    // ascendingDiagonalCheck
	    for (int c = 4; c < board.getColumns(); c++){
	        for (int r = 4; r < board.getRows(); r++){
	            if (board.getPosition(c,r) == player.getTile() 
	            		&& board.getPosition(c-1,r-1) == player.getTile() 
	            		&& board.getPosition(c-2,r-2) == player.getTile() 
	            		&& board.getPosition(c-3,r-3) == player.getTile()
	            		&& board.getPosition(c-4,r-4) == player.getTile())
	            	return MatchType.ACENDING_DIAGONAL;
	        }
	    }
	    
	   
//	    
//	    // ascendingDiagonalCheck 
//	    for (int i=3; i < board.getWidth(); i++){
//	        for (int j=0; j < board.getHeight()-3; j++){
//	            if (board.getPosition(i,j) == player.getTile() 
//	            		&& board.getPosition(i-1,j+1) == player.getTile() 
//	            		&& board.getPosition(i-2,j+2) == player.getTile() 
//	            		&& board.getPosition(i-3,j+3) == player.getTile())
//	                return true;
//	        }
//	    }
//	    
//	    // descendingDiagonalCheck
//	    for (int i=3; i < board.getWidth(); i++){
//	        for (int j=3; j < board.getHeight(); j++){
//	            if (board.getPosition(i,j) == player.getTile() 
//	            		&& board.getPosition(i-1,j-1) == player.getTile() 
//	            		&& board.getPosition(i-2,j-2) == player.getTile() 
//	            		&& board.getPosition(i-3,j-3) == player.getTile())
//	                return true;
//	        }
//	    }
	    
	    return MatchType.NONE;
	}
}
