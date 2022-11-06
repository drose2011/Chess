import java.util.*;

/*
The Rook class:
	- inherits from Piece
	- returns the allowed moves for a Rook in a given position on a given board
*/

public class Rook extends Piece {
	public Rook(String side) {
		super("rook", side);
	}

	//returns the possible places it can move based on the current pos and board state
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		String side = this.getSide();
		Boolean up=true, down=true, left=true, right=true;

		// TODO: Improve this?		
		// Add all vertical spots available
		int start = 0, end = 7;
		for (int checkRow = 0; inRange(checkRow); checkRow++) {
		    if (pieces[posToNum(checkRow, col)] != null) {
		        if (checkRow < row) {
		            start = checkRow;
		            if (pieces[posToNum(checkRow, col)].getSide() == side) {
		                start++;
		            }
		        } else if (checkRow > row) {
		            end = checkRow;
		            if (pieces[posToNum(checkRow, col)].getSide() == side) {
		                end--;
		            }
		            break;
		        }
		    }
		}
		for (int i = start; i <= end; i++) {
		    if (i != row) {
		        allowed.add(posToNum(i, col));
		    }
		}

		// Add all horizontal spots available
		start = 0;
		end = 7;
		for (int checkCol = 0; inRange(checkCol); checkCol++) {
		    if (pieces[posToNum(row, checkCol)] != null) {
		        if (checkCol < col) {
		            start = checkCol;
		            if (pieces[posToNum(row, checkCol)].getSide() == side) {
		                start++;
		            }
		        } else if (checkCol > col) {
		            end = checkCol;
		            if (pieces[posToNum(row, checkCol)].getSide() == side) {
		                end--;
		            }
		            break;
		        }
		    }
		}
		for (int i = start; i <= end; i++) {
		    if (i != col) {
		        allowed.add(posToNum(row, i));
		    }
		}

		return arrListToArr(allowed);
	}
}
