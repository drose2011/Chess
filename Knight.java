import java.util.*;

/*
The Knight class:
	- inherites all of Piece's class
	- returns the allowed move for a Knight in a givin posision on a givin board
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
		int rinc=2;
		int cinc=1;
		// I thought this was a very consise way to write the kind of strange movement of a rook
		// however it does use 3 nested for loops so it isn't perfect. Each one only runs twice
		// though so its not terrible. 
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				for(int a=0;a<2;a++){
					if(inRange(row+rinc)&&inRange(col+cinc)){
						if(pieces[posToNum((row+rinc),(col+cinc))]!=null){
							if(pieces[posToNum((row+rinc),(col+cinc))].getSide()!=pieces[clickedPieceIndex].getSide()){
								allowed.add(0,posToNum(row+rinc,col+cinc));
							}
						} else {
							allowed.add(posToNum(row+rinc,col+cinc));
						}
					}
					cinc=-cinc;
				}
				rinc=-rinc;
			}
			cinc=2;
			rinc=1;
		}

		return arrListToArr(allowed);
	}
}
