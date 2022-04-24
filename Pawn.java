import java.util.*;

/*
The Pawn class:
	- inherites all of Piece's class
		- modifies double jump
	- returns the allowed move for a pawn in a givin posision on a givin board
*/

public class Pawn extends Piece {
	private Boolean doublejump;
	public Pawn(String side,Boolean doublejump) {
		super("pawn",side);
		this.doublejump=doublejump;
	}

	//retruns if the last move was a doublejump. only used for en passant
	public Boolean doublejump(){
		return this.doublejump;
	}

	//returns the possible places it can move based on the current pos and board state
	//pawn has many odd litte rules, so many if statments
	public int[] getAllowed(Piece[] pieces, int clickedPieceIndex){
		ArrayList<Integer> allowed = new ArrayList<Integer>();
		int row = rowOf(clickedPieceIndex);
		int col = colOf(clickedPieceIndex);
		int dirc = -1;
		if(pieces[clickedPieceIndex].getSide()=="b"){
			dirc=1;
		}
		if(inRange(row+dirc)){
			//allowed to move one forward if nothing is there
			if(pieces[posToNum((row+dirc),col)]==null){
				allowed.add(posToNum((row+dirc),col));
				this.doublejump=false;
			}

			//allowed to take to either diagnol if piece of opposite side is there
			if((inRange(row+dirc)&&inRange(col+1))&&(pieces[posToNum((row+dirc),col+1)]!=null&&(pieces[posToNum((row+dirc),col+1)].getSide()!=pieces[clickedPieceIndex].getSide()))){
				allowed.add(0,posToNum((row+dirc),col+1));
				this.doublejump=false;
			}
			if((inRange(row+dirc)&&inRange(col-1))&&(pieces[posToNum((row+dirc),col-1)]!=null&&(pieces[posToNum((row+dirc),col-1)].getSide()!=pieces[clickedPieceIndex].getSide()))){
				allowed.add(0,posToNum((row+dirc),col-1));
				this.doublejump=false;
			}

			//allowed to move 2 on first move
			if((row==1&&dirc==1)||(row==6&&dirc==-1)){
				if((inRange(row+(2*dirc)))&&(pieces[posToNum((row+dirc),col)]==null)&&(pieces[posToNum((row+(2*dirc)),col)]==null)){
					allowed.add(posToNum((row+(dirc*2)),col));
					this.doublejump=true;
				}
			}
		}

		//en passant
		if(inRange(col+1)&&pieces[posToNum(row,(col+1))]!=null&&pieces[posToNum(row,(col+1))].toString()=="pawn"&&pieces[posToNum(row,(col+1))].getSide()!=pieces[clickedPieceIndex].getSide()&&pieces[posToNum(row,(col+1))].doublejump()==true){
			allowed.add(0,posToNum((row+dirc),(col+1)));
		}
		if(inRange(col-1)&&pieces[posToNum(row,(col-1))]!=null&&pieces[posToNum(row,(col-1))].toString()=="pawn"&&pieces[posToNum(row,(col-1))].getSide()!=pieces[clickedPieceIndex].getSide()&&pieces[posToNum(row,(col-1))].doublejump()==true){
			allowed.add(0,posToNum((row+dirc),(col-1)));
		}

		return super.arrListToArr(allowed);
	}
}
