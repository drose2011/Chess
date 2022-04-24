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

//the generaric piece class that all the pieces are stored as. has very little functinoallity
// except to make an array of it and also to provide general functinos used in many pieces.
public class Piece {
	private String side;
	private String name;
	private int value;

	public Piece(String name, String side) {
		this.side=side;
		this.name=name;
		switch (name) {
			case "pawn":
				this.value=1*getSideMod();
				break;
			case "knight": case "bishop":
				this.value=3*getSideMod();
				break;
			case "rook":
				this.value=5*getSideMod();
				break;
			case "queen":
				this.value=9*getSideMod();
				break;
			case "king":
				this.value=1000*getSideMod();
				break;
		}
	}


	//returns the image of the givin piece based on
	public Image getImage(){
		try{
			URL url = getClass().getResource("Pics/"+this.side+"_"+this.name+".png");
			Image img = new Image(url.openStream());
			return img;
		}catch(Exception e){
			return null;
		}
	}



	//common methods used in all the piece classes
	public int[] arrListToArr(ArrayList<Integer> list){
		int[] arr = new int[list.size()];
		for(int i=0;i<list.size();i++){
			arr[i]=list.get(i);
		}
		return arr;
	}
	public int colOf(int num){
		return num%8;
	}
	public int rowOf(int num){
		if(num<0){
			return -1;
		}
		return num/8;
	}
	//inputs row and col, outputs number of square
	public int posToNum(int row, int col){
		if(inRange(row)&&inRange(col)){
			return row*8+col;
		} else {
			return 64;
		}
	}
	//if row and col are on the board
	public boolean inRange(int num){
		if(num>=0&&num<8){
			return true;
		}
		return false;
	}




	//methods defined in (some) later classes, but needed here to be able to call in Piece[]
	public int[] getAllowed(Piece[] board, int clickedPieceIndex){
		return null;
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


	//getters
	public int getSideMod(){
		if(this.side=="w"){
			return -1;
		}
		return 1;
	}
	public String getSide(){
		return this.side;
	}
	public String toString(){
		return this.name;
	}
	public int getValue(){
		return this.value;
	}
}