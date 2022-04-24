import java.util.*;

/*
The Rook class:
	- inherites all of Piece's class
	- returns the allowed move for a Rook in a givin posision on a givin board
*/

public class Rook extends Piece {
	public Rook(String side) {
		super("rook",side);
	}

	//returns the possible places it can move based on the current pos and board state
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		Boolean up=true, down=true, left=true, right=true;
		
		//single loop that goes in each direction till it runs into something
		//I could not find a concise way to write it
		for(int i=1;inRange(col-i)||inRange(col+i)||inRange(row-i)||inRange(row+i);i++){
			if(inRange(col-i)&&left){
				if(pieces[posToNum(row,col-i)]!=null){
					if(pieces[posToNum(row,col-i)].getSide()!=pieces[clickedPieceIndex].getSide()){
						allowed.add(0,posToNum(row,col-i));
					}
					left=false;
				} else {
					allowed.add(posToNum(row,col-i));
				}
			}
			if(inRange(col+i)&&right){
				if(pieces[posToNum(row,col+i)]!=null){
					if(pieces[posToNum(row,col+i)].getSide()!=pieces[clickedPieceIndex].getSide()){
						allowed.add(0,posToNum(row,col+i));
					}
					right=false;
				} else {
					allowed.add(posToNum(row,col+i));
				}
			}
			if(inRange(row-i)&&down){
				if(pieces[posToNum(row-i,col)]!=null){
					if(pieces[posToNum(row-i,col)].getSide()!=pieces[clickedPieceIndex].getSide()){
						allowed.add(0,posToNum(row-i,col));
					}
					down=false;
				} else {
					allowed.add(posToNum(row-i,col));
				}
			}
			if(inRange(row+i)&&up){
				if(pieces[posToNum(row+i,col)]!=null){
					if(pieces[posToNum(row+i,col)].getSide()!=pieces[clickedPieceIndex].getSide()){
						allowed.add(0,posToNum(row+i,col));
					}
					up=false;
				} else {
					allowed.add(posToNum(row+i,col));
				}
			}
		}
		return arrListToArr(allowed);
	}
}
