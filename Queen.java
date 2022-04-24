import java.util.*;

/*
The Queen class:
	- inherites all of Piece's class
	- returns the allowed move for a queen in a givin posision on a givin board
*/

public class Queen extends Piece {
	public Queen(String side) {
		super("queen",side);
	}

	//returns the possible places it can move based on the current pos and board state
	//just a copy/paste combo of the rook and bishop getAllowed()
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		Boolean up=true, down=true, left=true, right=true;
		
		//rook getAllowed code
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

		//bishop allowed spot code
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
