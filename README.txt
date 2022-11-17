To run, simply run ‘java -jar Chess.jar’ in this folder.

This project began as a class exercise in using JavaFX and building a game with a computer controlled opponent. It implements a basic version of chess, along with a simple min-max AI to play against.

- Board.java = overall movement and board state logic 
- Piece.java = interface for pieces
- <piece>.java = implementation of specific piece, mainly used for retrieving valid moves
- ChessFX.java = GUI to display Board.java
- ChessAI.java = min-max implementation using alpha beta pruning. The brains of the AI opponent