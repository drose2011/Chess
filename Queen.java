import java.util.*;

/*
The Queen class:
	- inherits from Piece
	- returns the allowed move for a queen in a given position on a given board
*/

public class Queen extends Piece {
	public Queen(String side) {
		super("queen", side);
	}

	// Returns the possible places it can move based on the current pos and board state
	//
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		String side = super.getSide();

		Piece bishop = new Bishop(side);
		Piece rook = new Rook(side);

		allowed.add(bishop.getAllowed());
		allowed.add(rook.getAllowed());

		return arrListToArr(allowed);
	}
}
