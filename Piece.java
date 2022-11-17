import java.util.*;
import java.net.*;
import javafx.scene.image.*;

/*
The Piece class:
	- makes a generic piece for all the other pieces to extend
		- main use is ability to make an array of them
	- assigns a value to a piece
	- returns the image based on side and name
*/

// the generic piece class that all the pieces inherit from
// provides general functions
public class Piece {
	private String side;  	// 'w' = white (-1), 'b' = black (1)
	private String name; 
	private int value; 		// 1 = pawn, 3 = bishop/knight, 5 = rook, 9 = queen, 1000 = king

	public Boolean hasMoved;
	public Boolean doublejump;

	public Piece(String name, String side) {
		this.side = side;
		this.name = name;
		if(side != "w" && side != "b"){
			throw new IllegalArgumentException("Invalid piece side: \"" + side + "\".\n Must be \"b\" or \"w\".");
		}
		// TODO: make this a dict lookup
		switch (name) {
			case "pawn":
				this.value=1 * getSideMod();
				break;
			case "knight": case "bishop":
				this.value=3 * getSideMod();
				break;
			case "rook":
				this.value=5 * getSideMod();
				break;
			case "queen":
				this.value=9 * getSideMod();
				break;
			case "king":
				this.value=1000 * getSideMod();
				break;
			default:
				throw new IllegalArgumentException("Invalid piece name: \"" + 
					name + "\".\n Must be \"pawn\", \"knight\", \"bishop\", \"rook\", \"queen\", or \"king\".");
		}
	}


	// get image of piece to display
	public Image getImage() {
		try{
			URL url = getClass().getResource("Pics/"+this.side+"_"+this.name+".png");
			Image img = new Image(url.openStream());
			return img;
		} catch(Exception e) {
			return null;
		}
	}


	/*
	 *	HELPER FUNCTIONS
	 */

	// convert arrayList<Integer> to int[]
	// TODO: is this already a built in function?
	public int[] arrListToArr(ArrayList<Integer> list) {
		int[] arr = new int[list.size()];
		for(int i = 0; i < list.size(); i++){
			arr[i] = list.get(i);
		}
		return arr;
	}

	// positional helpers
	public int colOf(int num){
		if(num < 0 || num > 63) {
			throw new IllegalArgumentException("num " + num + " not in range. (0-63)");
		}
		return num % 8;
	}
	public int rowOf(int num){
		if(num < 0 || num > 63) {
			throw new IllegalArgumentException("num " + num + " not in range. (0 to 63)");
		}
		return num/8;
	}
	// converts row and col to number of square
	public int posToNum(int row, int col){
		if(!inRange(row) || !inRange(col)){
			throw new IllegalArgumentException("position (" + row + "," + col + ") not in range (0,0) to (7,7)");
		}
		return row * 8 + col;
	}
	//if row and col are on the board
	public boolean inRange(int num){
		if(num >= 0 && num < 8){
			return true;
		}
		return false;
	}


	// methods defined in (some) later classes, but needed here to be able to call in Piece[]
	// TODO: are these necissary?
	public int[] getAllowed(Piece[] board, int clickedPieceIndex){
		ArrayList<Integer> allPossible = getPiecePossible(board, clickedPieceIndex);
		for(move in allPossible) {
			// Remove all moves that put moving team into check
			// For each move, check if any of other team pieces are attacking king
			// if so, remove it
		}
		return null;
	}
	public ArrayList<Integer> getPiecePossible(Piece[] board, int clickedPieceIndex){

	}
	public Boolean doublejump(){
		return null;
	}
	public Boolean hasMoved(){
		return null;
	}
	public void setHasMoved(Boolean bool){
		return;
	}


	/*
	 *  GETTERS
	 */

	public int getSideMod(){
		if(this.side == "w"){
			return -1;
		}
		return 1;
	}
	public String getSide(){
		return this.side;
	}
	public String getName(){
		return this.name;
	}
	@Override
	public String toString(){
		return this.side + " " + this.name;
	}
	public int getValue(){
		return this.value;
	}
}