import java.util.*;

/*
The King class:
	- inherits from Piece
		- modifies hasMoved and setHasMoved to decide castle
	- returns the allowed move for a King in a given position on a given board
*/

public class King extends Piece {
	public King(String side, Boolean hasMoved) {
		super("king",side);
		this.hasMoved=hasMoved;
	}

	//methods used to decide if the king can castle
	public Boolean hasMoved(){
		return this.hasMoved;
	}
	public void setHasMoved(Boolean bool){
		this.hasMoved=bool;
		return;
	}

	//returns the possible places it can move based on the current pos and board state
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		// starts to the top left of the king and works its way across and then down, adding all empty/enemy-occupied spots
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(inRange(row + i) && inRange(col + j)) {
					if(pieces[posToNum(row + i, col + j)] == null ||
					   pieces[posToNum(row + i, col + j)].getSide() != pieces[clickedPieceIndex].getSide()) {
						allowed.add(posToNum(row + i, col + j));
					}
				}
			}
		}

		// Allows for castling
		if(!this.hasMoved){
			Boolean allo;
			// allows it on the left
			if(pieces[posToNum(row,0)] != null &&
			   pieces[posToNum(row,0)].getName().equals("rook") &&
			   pieces[posToNum(row,1)] == null && pieces[posToNum(row,2)] == null &&
			   pieces[posToNum(row,3)] == null){
				allo=true;
				for(int i = 1; i < 4; i++){
					Board temp = new Board(pieces,1);
					temp.moveOverRide(clickedPieceIndex,posToNum(row,i));
					if(temp.checkCheck(1,true) != 0){
						allo = false;
						break;
					}
				}
				if(allo){
					allowed.add(clickedPieceIndex - 2);
				}
			}
			//allows it on the right
			if(pieces[posToNum(row,7)] != null && pieces[posToNum(row,7)].getName().equals("rook") && pieces[posToNum(row,6)] == null && pieces[posToNum(row,5)] == null){
				allo = true;
				for(int i = 6; i > 4; i--){
					Board temp = new Board(pieces,0);
					temp.moveOverRide(clickedPieceIndex,posToNum(row,i));
					if(temp.checkCheck(1,true) != 0){
						allo = false;
						break;
					}
				}
				if(allo){
					allowed.add(clickedPieceIndex + 2);
				}
			}
		}
		return arrListToArr(allowed);
	}	
}
