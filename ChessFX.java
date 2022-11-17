import javafx.application.Application;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.*;
import java.io.*;

/*
The ChessFX class:
	- uses javaFX to display the board as three identically sizes flowpanes of a stackpane
		- flowpane 1 has the checkered grid, along with the coloring of the clicked piece
		- flowpane 2 has the images of the pieces
		- flowpane 3 has an array of clickable buttons used to interact
	- makes the window with the stackpane on it
*/

public class ChessFX extends Application {

	StackPane disp;
	@Override
	public void start(Stage stage){
		stage.setMinWidth(600);
		stage.setMaxWidth(600);
		stage.setMinHeight(623);
		stage.setMaxHeight(623);
		Scene scene = new Scene(new Group());

		Board board = new Board();
		ChessAI chessAI = new ChessAI();

		// makes clickable buttons array for input using a flowPane that is placed on top of the board
		FlowPane buttonflow = new FlowPane();
		Button[] buttons = new Button[64];
		// because the button click handler has to made dynamically, it can not affect anything
		// outside directly, so to track which button is which, I store the memory addresses and
		// later search it
		ArrayList<String> address = new ArrayList<String>();
		Boolean hasPiece = false;
		int index;
		for(int i = 0; i < 64; i++){
			buttons[i] = new Button();
			buttons[i].setMinSize(75,75);
			buttons[i].setStyle("-fx-background-color:transparent;");
			buttonflow.getChildren().add(buttons[i]);
			address.add(String.valueOf(buttons[i]));
			buttons[i].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Gives the same function to every button, but eachone will give back a uniqe
			// input using stored memory
			public void handle(ActionEvent e) {
				board.clicked(address.indexOf(e.getSource().toString()), false);
				if(Math.abs(board.gameStatus()) == 2){
					System.out.print("GAME OVER, ");
					if(board.gameStatus()<0){
						System.out.print("Black");
					} else {
						System.out.print("White");
					}
					System.out.println(" Wins");
				}
				// if the next turn in the computers, it calculates and moves
				// the next move before updating the board
				if(board.getTurn().equals("b")){
					long startTime = System.nanoTime();
					int[] compmv = chessAI.getMove(board, "b");
					long endTime = System.nanoTime();
					System.out.println("time: "+(endTime-startTime)/Math.pow(10,9));
					if(compmv==null){
						return;
					}
					board.clicked(compmv[0], false);
					board.clicked(compmv[1], false);
					
				} 

				//update display and window
				updatedisp(board,buttonflow);
				stage.setTitle("Chess, "+board.getTurn()+" turn");
				scene.setRoot(disp);
				stage.setScene(scene);
				stage.show();
				
			}
		});
		}

		updatedisp(board,buttonflow);
		scene.setRoot(disp);
		stage.setScene(scene);
		stage.show();
	}
 	
 	//used to update the display while in click handler
 	public void updatedisp(Board board, FlowPane buttonflow){
 		if(disp!=null){
 			disp.getChildren().clear();
 		}
 		disp = board.updatedBoard();
 		disp.getChildren().add(buttonflow);
 	}

	public static void main(String[] args) {
		launch(args);
	}
}