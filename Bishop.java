import java.util.*;

/*
The Bishop class:
	- inherits from Piece
	- returns the allowed move for a bishop in a given position on a given board
*/

public class Bishop extends Piece {
	public Bishop(String side) {
		super("bishop", side);
	}

	//returns the possible places it can move based on the current pos and board state
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		// loops through each diagonal and adds it to the list.
		for(int i = 1;i >= -1;i -= 2){
			for(int j = 1;j >= -1;j -= 2){
				for(int a = 1; inRange(row + (a * i)) && inRange(col + (a * j)); a++) {
				    // Once it runs into a piece on a diagonal,
				    // it adds it to allowed if it is on the opposite side,
				    // and then stops looking along that diagonal
				    int checkRow = row + (a * i);
				    int checkCol = col + (a * j);
				    int checkNum = posToNum(checkRow, checkCol);
					if(pieces[checkNum] != null) {
						if(pieces[checkNum].getSide() != pieces[clickedPieceIndex].getSide()){
							allowed.add(checkNum);
						}
						break;
					} else {
						allowed.add(checkNum);
					}
				}
			}
		}
		return arrListToArr(allowed);
	}
}