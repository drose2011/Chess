import java.util.*;

/*
The Knight class:
	- inherits from Piece
	- returns the allowed move for a Knight in a given position on a given board
*/

public class Knight extends Piece {
	public Knight(String side) {
		super("knight",side);
	}

	//returns the possible places it can move based on the current pos and board state
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		String side = super.getSide();
		int[][] directions = [[1,2],[2,1],[-1,2],[2,-1],[1,-2],[-2,1],[-1,-2],[-2,-1]]

		for (int idx = 0; idx < directions.length(); idx++) {
		    int checkRow = row + directions[idx][0];
		    int checkCol = col + directions[idx][1];
		    if (inRange(checkRow, checkCol) &&
		       (pieces[posToNum(checkRow, checkCol)] == null ||
		       pieces[posToNum(checkRow, checkCol)].getSide() != side)) {
		        allowed.add(posToNum(checkRow, checkCol));
		       }
		}

		return arrListToArr(allowed);
	}
}
