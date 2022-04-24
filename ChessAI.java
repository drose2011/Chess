import java.util.*;

/*
The ChessAI class:
	- huisticlly evaluates a board based on rows away from start, piece value, and check status
	- uses minmax to determine the best move
		- recursivly goes 5 layers deep
		- uses alpha beta pruning to signifigantly decrese number of boards that need to be evaled

*/

public class ChessAI {
	//get the AI's next move, given the current board state. returns array with starting and ending click
	public int[] getMove(Board board, String turn){
		int[][] allpossible = board.getAllPossible(turn);
		if(allpossible.length!=0){
			//minnmax returns the index (withinin all available moves) of the move it 
			//wishs to take. it currently goes 5 lvls deep.
			int mv = minnmax(0,4,board,-1000000,1000000)[0];
			return allpossible[mv];
		} else {
			return null;
		}
	}

	//huristically evaluates the board based on row, piece values, and check status
	public int boardeval(Board board){
		Piece[] pieces = board.getPieces();
		int tot=0;
		for(int i=0;i<pieces.length;i++){
			if(pieces[i]!=null){
				//adds piece value
				tot+=pieces[i].getValue()*20;
				//adds value based on how fare from starting boarder to give position some value
				if(board.pieceAt(i).getSide().equals("b")){
					tot+=rowOf(i)*8;
				} else {
					tot-=(7-rowOf(i))*8;
				}
				//adds value to check and checkmate
				int status=board.gameStatus();
				if(Math.abs(status)==2){
					tot+=status*50;
				} else if(Math.abs(status)==1){
					tot+=status*1;
				}
			}
		}
		return tot;
	}


	// minnmax made to be recurssivle called, uses α/β pruning, 
	// used https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning as a source
	public int[] minnmax(int lvl, int maxlvl, Board board, int alpha, int beta){
		
		// base case
		if(lvl==maxlvl){
			int[] ret = {0,boardeval(board)};
			return ret;
		} 

		int mvchosen=0;
		int[][] tempallpossible = board.getAllPossible(board.getTurn());
		int record;
		//no moves=checkmate and so it returns a very negative value for the previous player
		if(tempallpossible.length==0){
			int[] ret = {0,-100};
			if(lvl%2==1){
				ret[1] = 100;
			} 
			return ret;
		}

		//starting record=oposite of what you want
		if(lvl%2==0){
			record=-1000000;
		} else {
			record=1000000;
		}
		//makes every possible move
		for(int i=0;i<tempallpossible.length;i++){
			Board temp = new Board(board.getPieces(), board.getTurnNum());
			temp.clicked(tempallpossible[i][0],false);
			temp.clicked(tempallpossible[i][1],false);
			int[] minmaxval=minnmax(lvl+1,maxlvl,temp,alpha,beta);
			int val = minmaxval[1];
			//maximizing
			if(lvl%2==0){	
				if(val>record){
					record=val;
					mvchosen=i;
				}
				//α/β pruning
				alpha=Math.max(alpha,val);
				if(alpha>=beta){
					break;
				}
			} 
			//minimizing
			else {
				if(val<record){
					record=val;
					mvchosen=i;
				}
				//α/β pruning
				beta=Math.min(beta,val);
				if(alpha>=beta){
					break;
				}
			}
		}
		int[] rec = {mvchosen,record};
		return rec;
	}

	//returns the row of a certin number
	public int rowOf(int num){
		if(num<0){
			return -1;
		}
		return num/8;
	}
}
