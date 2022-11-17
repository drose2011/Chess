import java.util.*;

/*
The Pawn class:
	- inherits from Piece
		- modifies double jump
	- returns the allowed move for a pawn in a given position on a given board
*/

public class Pawn extends Piece {
	public Boolean doublejump;
	public Pawn(String side, Boolean doublejump) {
		super("pawn", side);
		this.doublejump = doublejump;
	}

	// Returns if the last move was a doublejump. Only used for en passant
	public Boolean doublejump(){
		return this.doublejump;
	}

	// Returns the possible places it can move based on the current pos and board state
	// Pawn has many odd little rules
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		String side = super.getSide();
		int dirc = super.getSideMod();

		if(inRange(row + dirc)){
			//allowed to move one forward if nothing is there
			if(pieces[posToNum((row + dirc), col)]==null){
				allowed.add(posToNum((row + dirc), col));
			}

			//allowed to take to either diagonal if piece of opposite side is there
			if(inRange(col+1) &&
			   pieces[posToNum((row + dirc), col + 1)] != null &&
			   pieces[posToNum((row + dirc), col + 1)].getSide() != side) {
				allowed.add(posToNum((row + dirc), col + 1));
			}
			if(inRange(col-1) &&
			   pieces[posToNum((row + dirc), col - 1)] != null &&
			   pieces[posToNum((row + dirc), col - 1)].getSide() != side){
				allowed.add(posToNum((row + dirc), col - 1));
			}

			//allowed to move 2 on first move
			if((row == 1 && dirc == 1) || (row == 6 && dirc == -1)){
				if(inRange(row + (2 * dirc)) &&
				   pieces[posToNum((row + dirc), col)] == null &&
				   pieces[posToNum((row + (2 * dirc)), col)] == null){
					allowed.add(posToNum(row + (dirc * 2), col));
				}
			}
		}

		//en passant
		if(inRange(col + 1) &&
		   pieces[posToNum(row, (col + 1))] != null &&
		   pieces[posToNum(row,(col+1))].toString() == "pawn" &&
		   pieces[posToNum(row,(col+1))].getSide() != side &&
		   pieces[posToNum(row,(col+1))].doublejump()==true){
			allowed.add(posToNum(row + dirc, col + 1));
		}
		if(inRange(col-1) &&
		   pieces[posToNum(row,(col-1))] != null &&
		   pieces[posToNum(row,(col-1))].toString() == "pawn" &&
		   pieces[posToNum(row,(col-1))].getSide() != side &&
		   pieces[posToNum(row,(col-1))].doublejump() == true){
			allowed.add(posToNum(row + dirc, col - 1));
		}

		return super.arrListToArr(allowed);
	}
}
