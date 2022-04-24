import java.util.*;

/*
The Bishop class:
	- inherites all of Piece's class
	- returns the allowed move for a bishop in a givin posision on a givin board
*/

public class Bishop extends Piece {
	public Bishop(String side) {
		super("bishop",side);
	}

	//returns the possible places it can move based on the current pos and board state
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		// loops through each diagonal and adds it to the list. very consice to run
		// and each only runs twice so not as terrible as most triple nested loops
		for(int i=1;i>=-1;i-=2){
			for(int j=1;j>=-1;j-=2){
				for(int a=1;inRange(row+(a*i))&&inRange(col+(a*j));a++){
					if(pieces[posToNum(row+(a*i),col+(a*j))]!=null){
						if(pieces[posToNum(row+(a*i),col+(a*j))].getSide()!=pieces[clickedPieceIndex].getSide()){
							allowed.add(0,posToNum(row+(a*i),col+(a*j)));
						}
						break;
					} else {
						allowed.add(posToNum(row+(a*i),col+(a*j)));
					}
				}
			}
		}
		return arrListToArr(allowed);
	}
}