import javafx.application.Application;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.*;
import javafx.scene.image.*;
import java.util.*;

/*
The Board class:
	- Stores the positioning of the pieces in an array
	- checks for check and checkmate
	- receives the input from ChessFX of the clicked spots and decides if it is allowed
	- returns a StackPane for ChessFX to use to display the current board
	- gives the ChessAI a 2D array of all the possible moves
*/

public class Board {
	private Piece[] pieces = new Piece[64];
	private Boolean hasPiece = false;
	private int clickedPieceIndex;
	private int turnnum;
	private String turn = "w";
	private int selectedSpot = -1;
	private int[] openSpots = new int[0];
	private int[][] checkallowed;
	//2 = checkmate, 1 = check, - = black, + = white
	private int gameStatus;

	// initializes the starting board
	public Board(){
		String side = "w";
		this.gameStatus = 0;
		this.turnnum = turnnum;
		for(int i = 0; i < 64; i++){
			if(i / 8 > 5){
				side = "w";
			} else if(i / 8 < 2){
				side = "b";
			}
			if(i / 8 < 1 || i / 8 > 6){
				switch (i % 8) {
					case 0: case 7:
						this.pieces[i] = new Rook(side);
						break;
					case 1: case 6:
						this.pieces[i] = new Knight(side);
						break;
					case 2: case 5:
						this.pieces[i] = new Bishop(side);
						break;
					case 3:
						this.pieces[i] = new Queen(side);
						break;
					case 4:
						this.pieces[i] = new King(side, false);
						break;
				}
			} else if(i / 8 == 1 || i / 8 == 6){
				this.pieces[i] = new Pawn(side, false);	
			} 
		}
	}

	//intiallizes a board in progress
	public Board(Piece[] pieces, int turnnum){
		// I had a slight issue making a new board because instead of duplicating it
		// would just keep the same memory pointer, so I am duplicating an identical 
		// piece[] here. I also need to clone the king and pawn for the same reason
		this.gameStatus = 0;
		this.pieces = new Piece[64];
		for(int i = 0; i < pieces.length; i++){
			if(pieces[i] != null){
				if(pieces[i].getName().equals("king")){
					this.pieces[i] = new King(pieces[i].getSide(),pieces[i].hasMoved());
				} else if(pieces[i].getName().equals("pawn")){
					this.pieces[i] = new Pawn(pieces[i].getSide(),pieces[i].doublejump());
				} else {
					this.pieces[i] = pieces[i];
				}
			} else {

				this.pieces[i] = null;
			}
		}
		this.turnnum = turnnum;
	}

	//returns a StackPane for the ChessFX class to use in order to display the board
	public StackPane updatedBoard() {
		StackPane stack = new StackPane();

		//makes background board with board colors to signify clicked piece and possible moves
		FlowPane board = new FlowPane();
		board.setPrefWrapLength(300);
		Button test;
		for(int i = 0; i < 64; i++){
			test = new Button();
			test.setStyle("-fx-background-radius: 0;");
			if ((i + (i / 8)) % 2 == 1){
				test.setStyle("-fx-background-color: Brown;");
			} else {
				test.setStyle("-fx-background-color: White;");
			}
			if(i == this.selectedSpot){
				test.setStyle("-fx-background-color: Green;");
			}
			for(int j = 0; j < this.openSpots.length; j++){
				if(this.openSpots[j] == i){
					test.setStyle("-fx-background-color: grey;");
				}
			}
			test.setPrefSize(75, 75);
			board.getChildren().add(test);
		}
		stack.getChildren().add(board);

		// Overlays the chess pieces on top of board in an identical button array, except with
		// transparent backgrounds
		FlowPane pieces1 = new FlowPane();
		Image img;
		for(int i = 0; i < 64; i++){
			test = new Button();
			if(this.pieces[i] != null){
				img = this.pieces[i].getImage();
				test.setGraphic(new ImageView(img));
			}
			test.setStyle("-fx-background-color:transparent;");
			test.setMaxSize(75,75);
			test.setMinSize(75,75);
			pieces1.getChildren().add(test);
		}
		int check = checkCheck(0, false);
			
		stack.getChildren().add(pieces1);

		return stack;
	}

	//used by ChessFX to signify a clicked square
	public void clicked(int index, Boolean checking){
		if(this.turnnum % 2 == 0){
			this.turn = "w";
		} else {
			this.turn = "b";
		}
		// if it is the second click then move
		if(this.pieces[index] == null && this.hasPiece == true){
			move(index, checking);
		} 
		// first click on null, do nothing
		else if(this.pieces[index] == null){
			return;
		}
		// not the second click, so it selected the piece 
		else if(this.pieces[index].getSide() == this.turn){
			this.clickedPieceIndex = index;
			this.selectedSpot = index;
			this.hasPiece = true;
			if(!checking){
				this.openSpots = this.pieces[index].getAllowed(this.pieces, index);
			}
		}
		// taking an opposing piece
		else if(this.pieces[index].getSide() != turn && this.hasPiece == true){
			move(index, checking);
		}
		if(this.turnnum % 2 == 0){
			this.turn = "w";
		} else {
			this.turn = "b";
		}
	}

	// actually moves the piece
	public void move(int index, Boolean checking){
		Boolean isAllowed = false;
		//checks to make sure it is a valid move
		isAllowed = isAllowed(this.clickedPieceIndex, index, checking);
		if(isAllowed){
			if(this.pieces[this.clickedPieceIndex].getName().equals("king") &&
			   !this.pieces[this.clickedPieceIndex].hasMoved()){
				this.pieces[this.clickedPieceIndex].setHasMoved(true);
				if(index == this.clickedPieceIndex - 2){
					moveOverRide(this.clickedPieceIndex - 4, this.clickedPieceIndex - 1);
				} else if(index == this.clickedPieceIndex + 2){
					moveOverRide(this.clickedPieceIndex + 3,this.clickedPieceIndex + 1);
				}
			}

			// Pawn specific move cases
			if (this.pieces[this.clickedPieceIndex].getName().equals("pawn")) {
			    // Setting doublejump
			    if (Math.abs(index - this.clickedPieceIndex) == 16) {
			        this.pieces[this.clickedPieceIndex].doublejump = true;
			    }
			    // Promoting
			    if(index < 8 || index > 55){
                    this.pieces[this.clickedPieceIndex] = new Queen(this.pieces[this.clickedPieceIndex].getSide());
                }
			}

			this.pieces[index] = this.pieces[this.clickedPieceIndex];
			this.pieces[this.clickedPieceIndex] = null;
			this.hasPiece = false;
			this.turnnum++;
			this.selectedSpot = -1;
			this.openSpots = new int[0];
			
		}
	}

	// simply move the piece(used in checking check)
	public void moveOverRide(int start,int end){
		this.pieces[end] = this.pieces[start];
		this.pieces[start] = null;
	}


	// checks for a check. Goes through every piece's possible moves and checks for a king in there
	// public int checkCheck(int round, Boolean kingtest){
	// 	for(int i = 0; i < 64; i++){
	// 		if(this.pieces[i] != null &&
	// 		   !(kingtest&&this.pieces[i].getName().equals("king"))){
				
	// 			int[] allow = this.pieces[i].getAllowed(this.pieces,i);
	// 			for(int j = 0; j < allow.length; j++){
	// 				if(this.pieces[allow[j]] != null && this.pieces[allow[j]].getName().equals("king")){
	// 					int mod=1;
	// 					if(this.pieces[allow[j]].getSide() == "b"){
	// 						mod=-1;
	// 					}
	// 					//there is check, so now check for checkmate
	// 					if(round == 0){
	// 						this.checkallowed = checkCheckMate(this.pieces[i].getSide());
	// 						if(this.checkallowed.length == 0){
	// 							this.gameStatus = -2 * mod;
	// 							return 2 * mod;
	// 						} 
	// 					}
	// 					this.gameStatus = -1 * mod;
	// 					return  1 * mod;
	// 				} else {
	// 					break;
	// 				}
	// 			}
	// 		}
	// 	}
	// 	this.gameStatus = 0;
	// 	return 0;
	// }

	// checks every possible move to see if one results in the person no longer being in check
	// it returns a 2D array with every possible move out of check(empty if check mate)
	// public int[][] checkCheckMate(String side){
	// 	ArrayList<int[]> list = new ArrayList<int[]>();
	// 	for(int i = 0; i < this.pieces.length; i++){
	// 		if(this.pieces[i] != null && this.pieces[i].getSide() != side){
	// 			int[] allow = this.pieces[i].getAllowed(this.pieces,i);
	// 			Board temp;
	// 			for(int j = 0; j < allow.length; j++){
	// 				int test;
	// 				if(side == "b"){
	// 					test = 0;
	// 				} else {
	// 					test = 1;
	// 				}

	// 				//if this move does that, it stores it
	// 				temp=new Board(this.pieces, test);
	// 				temp.clicked(i, true);
	// 				temp.clicked(allow[j], true);
	
	// 				//kinda recursively calls checkCheck
	// 				if(temp.checkCheck(1,false)==0){
	// 					int[] arr = {i,allow[j]};
	// 					list.add(arr);
	// 				}
	// 			}
	// 		}
	// 	}
	// 	int[][] arr = new int[list.size()][2];
	// 	for(int i = 0; i < list.size(); i++){
	// 		arr[i] = list.get(i);
	// 	}
	// 	return arr;
	// }


	//returns every single possible move with the current board state in a 2D array.
	public int[][] getAllPossible(String side){
		int[][] arr;
		if(checkCheck(0,false)==0){
			ArrayList<int[]> allpossiblemvs = new ArrayList<int[]>();
			for(int i = 0; i < this.pieces.length; i++){
				if(this.pieces[i] != null && this.pieces[i].getSide().equals(side)){
					int[] test = pieces[i].getAllowed(this.pieces,i);
					for(int j = 0; j < test.length; j++){
						if(isAllowed(i,test[j],false) == true){
							int[] test2 = {i, test[j]};
							allpossiblemvs.add(test2);
						}
					}
				}
			}
			arr = new int[allpossiblemvs.size()][2];
			for(int i = 0; i < allpossiblemvs.size(); i++){
				arr[i][0]=allpossiblemvs.get(i)[0];
				arr[i][1]=allpossiblemvs.get(i)[1];
			}
		} else {
			String test;
			if(side.equals("b")){
				test = "w";
			} else {
				test = "b";
			}
			arr=checkCheckMate(test);
		}
		return arr;
	}

	// // Adds special moves (Castle)
	// public int[] getAllowedSpecial(int clickedPieceIndex){
	// 	int[] normalAllowedSpots = this.pieces[start].getAllowed(this.pieces, clickedPieceIndex);
	// 	// Castle

	// 	// Queen side (left)
	// 	Piece clickedPiece = this.pieces[clickedPieceIndex];
	// 	int lRookPos = clickedPieceIndex - (clickedPieceIndex % 8);
	// 	int rRookPos = lRookPos + 7;
	// 	boolean rookAllowed = this.pieces[lRookPos] != null &&
	// 		this.pieces[lRookPos].getN
	// 		this.pieces[lRookPos].getSide() == this.pieces[clickedPieceIndex].getSide() &&
	// 		this.pieces[lRookPos].hasMoved() == false &&

	// 	if(!clickedPiece.hasMoved() && )
	// }

	// checks for the pieces allowed moves and check status to ensure it is a valid move
	public Boolean isAllowed(int start, int end, Boolean checking){
		int[] allowedSpots = this.pieces[start].getAllowed(this.pieces, start);
		int[] mv = {start, end};
		int check;
		if(checking == true){
			check = 0;
		} else {
			check = checkCheck(0,false);
		}
		for(int i = 0; i < allowedSpots.length; i++){
			//if the move is in the generically allowed spots
			if(end == allowedSpots[i]){
				if(checking == true){
					return true;
				} else 
				if(check == 2){
					return false;
				} else if(Math.abs(check) == 1 || check == 0){
					// makes a temp board and moves the move then checks for check against
					// itself. if so, it is not a valid move.
					Board temp1=new Board(this.pieces,this.turnnum);
					temp1.clicked(start, true);
					temp1.clicked(end, true);
					if(temp1.checkCheck(1,false) == 0){
						return true;
					} else if(temp1.checkCheck(1,false) == -1 && this.pieces[start].getSide().equals("w")){
						return true;
					} else if(temp1.checkCheck(1,false) == 1 && this.pieces[start].getSide().equals("b")){
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}



	// Getters
	public int gameStatus(){
		return this.gameStatus;
	}
	public String getTurn(){
		return this.turn;
	}
	public int getTurnNum(){
		return this.turnnum;
	}
	public Boolean getHasPiece(){
		return this.hasPiece;
	}
	public Piece[] getPieces(){
		return this.pieces;
	}
	public Piece pieceAt(int i){
		return this.pieces[i];
	}
	public int[][] getCheckAllowed(){
		return checkallowed;
	}
}