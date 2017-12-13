// Name: Bingxue Ouyang
// CruzID: bouyang


import java.io.*;
import java.util.*;
import java.lang.*;

public class ChessMoves {

	static int size;
	//static ChessNode first = null;
	public static LinkedChess list;
	
	public static void main(String[] args) throws IOException{
		if(args.length<2){
            System.out.println("Usage: java -jar ChessMoves.jar <input file> <output file>");
            System.exit(1);
        }
        // open files
        Scanner in = new Scanner(new File(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));
		
		while(in.hasNextLine()) {
			list = new LinkedChess();
			
			String line = in.nextLine().trim()+" ";
            // split by colon
            String[] colon = line.split(":");
            // coordinate
            String[] pieces = colon[0].split("\\s+");
            // chesspieces
            String[] moves = colon[1].split("\\s+");
			size = 100;
			//System.out.println(size);
			ChessPiece whiteKing = new King(1, 1, true); //Initialize the white king
			ChessPiece blackKing = new King(1, 1, false); //Initialize the black king
			
			for(int i = 0; i + 2 < pieces.length; i += 3) {
				char c = pieces[i].charAt(0);
				int col = Integer.parseInt(pieces[i + 1]);
				int row = Integer.parseInt(pieces[i + 2]);

				boolean side;
				if (Character.isUpperCase(c)) {
					side = false;
				} else {
					side = true;
				}
				//Creates the chess pieces
				ChessNode chess = null;
				if (c == 'k') {
					 chess = new ChessNode(new King(col, row, side));
					 whiteKing = chess.chess;
				} else if (c == 'K') {
					chess = new ChessNode(new King(col, row, side));
					blackKing = chess.chess;
				} else if (Character.toLowerCase(c) == 'q') {
					chess = new ChessNode(new Queen(col, row, side));
				} else if (Character.toLowerCase(c) == 'b') {
					chess = new ChessNode(new Bishop(col, row, side));
				} else if (Character.toLowerCase(c) == 'n') {
					chess = new ChessNode(new Knight(col, row, side));
				} else if (Character.toLowerCase(c) == 'r') {
					chess = new ChessNode(new Rook(col, row, side));
				} else if (Character.toLowerCase(c) == 'p') {
					chess = new ChessNode(new Pawn(col, row, side));
				}
				list.addNode(chess);
			}
			ChessNode current = list.first;
			while (current != null) {
				current.chess.moveSet(size);
				validateMoves(current.chess, list);
				current = current.next;
			}
			String status = "";
			boolean wcheck = false;
			boolean bcheck = false;
			if (isCheckmate(whiteKing)) {
				status = "White in checkmate ";
			} else if (isCheckmate(blackKing)) {
				status = "Black in checkmate ";
			} else if (isCheck(whiteKing, list)) {
				wcheck=true;
			} else if (isCheck(blackKing, list)) {
				status = "Black in check ";
			} else {
				status = "All kings safe";
			}
			boolean result = true;
			for (int i = 1; i + 3 < moves.length; i += 4) {
				int col1 = Integer.parseInt(moves[i]);
				int row1 = Integer.parseInt(moves[i + 1]);
				int col2 = Integer.parseInt(moves[i + 2]);
				int row2 = Integer.parseInt(moves[i + 3]);
				ChessPiece piece = findPiece(col1, row1, list);
				if (piece == null) {
					result = false;
				}
				else {
					validateMoves(piece, list);	
					if (piece.moves.hasPiece(col2, row2)) {
						result = true;
						piece.move(col2, row2);
						piece.moveSet(size);
					} else {
						result = false;
					}
				}
			}
			if(result){
				p.print("legal");
			}
			else{
				for(int a=moves.length-4; a<moves.length; a++){
               		 p.print(moves[a]+" ");
           		}
				p.print("illegal ");
			}
			p.println();
			p.println(status);
		}
		in.close();
		p.close();
	}
	
	public static ChessPiece findPiece(int col, int row, LinkedChess list) {
		ChessNode current = list.first;
		while (current != null) {
			//Check for valid position
			if (col <= 0 || col > size || row <= 0 || row > size) {
				return null;
			}
			
			if (current.chess.col == col && current.chess.row == row) { //Check if this piece is in the correct coordinates
				return current.chess;
			} else {
				current = current.next;
			}
		}
		
		return null;
	}
	
	//Calculates if king is in check
	public static boolean isCheck(ChessPiece king, LinkedChess list) { 
		ChessNode current = list.first;
		while (current.next != null) { //Look through every piece on the board
			if (current.chess.side != king.side) { //Ignore allied pieces
				SquareNode s = current.chess.moves.top;
				current.chess.moves.printList();
				while (s != null) { //Loop through all possible moves the piece can make
					if (s.col == king.col && s.row == king.row) { //Check if piece can move to the king's location
						return true;
					}
					s = s.next; //Continue if not same square
				}
			}
			current = current.next;
		}
		return false;
	}
	
	//Calculates weak checkmate
	public static boolean isCheckmate(ChessPiece king) {
		//System.out.println("Checking Checkmate for " + king.toFullString());
		int oCol = king.col;
		int oRow = king.row;
		if (isCheck(king, list)) { //If king has to move
			//System.out.print("King can move to ");
			//king.moves.printList();
			while (king.moves.size != 0) { //Repeat until stack of possible moves is empty
				king.move(king.moves.top.col, king.moves.top.row); //Moves the king
				if (isCheck(king, list)) { //If the king is still in check
					king.moves.pop(); 
					king.move(oCol, oRow);
					//System.out.println("King is not safe at " + king.moves.pop());
				} else { //Found a safe space
					king.move(oCol, oRow);
					return false; //Not checkmate, you're safe you little snow flake
				}
			}
			return true;
		} else { //No need to move if the king isn't in check
			return false;
		}
	}
	
	//Remove possible moves that are blocked
	public static void validateMoves(ChessPiece c, LinkedChess list) {
		char chess = c.pieceType(); 
		c.moveSet(size); //Create the set of moves assuming empty board
		if (Character.toLowerCase(chess) == 'p') { //Check if its a pawn
			//Determine direction the pawn moves
			int direction;
			if (c.side) {
				direction = 1;
			} else {
				direction = -1;
			}
			
			//Check if forward movement is blocked
			if (findPiece(c.col, c.row + direction, list) != null) {
				c.moves.pop(); //Move set should be empty at the moment
			}
			
			//Check for attackable pieces
			ChessPiece d = findPiece(c.col - 1, c.row + direction, list); //Attack to the left
			ChessPiece e = findPiece(c.col + 1, c.row + direction, list); //Attack to the right
			if (d != null) {
				if (d.side != c.side) {
					c.moves.push(new SquareNode(d.col, d.row)); //Add this as a possible move
				} else if (e.side != c.side) {
					c.moves.push(new SquareNode(d.col, d.row)); //Add this as a possible move
				}
			}
			
		} else {
			SquareStack newMoves = new SquareStack(); //Will replace the old stack
			SquareNode current = c.moves.top; //Looking at the piece's move set
			while (current != null) { //Begin filtering the possible moves
				newMoves.push(current); //Push this move into the new stack
				ChessPiece d = findPiece(current.col, current.row, list);
				
				if (d != null) { //If there is a piece at the destination
					if (d.side == c.side) { //If it's an ally piece
						newMoves.pop(); //Remove from the new list
					}
				} else { //If destination is clear
					//Check for blocking
					int col1, col2, row1, row2;
					
					if (c.col < current.col) {
						col1 = c.col;
						col2 = current.col;
					} else {
						col1 = current.col;
						col2 = c.col;
					}
					
					if (c.row < current.row) {
						row1 = c.row;
						row2 = current.row;
					} else {
						row1 = current.row;
						row2 = c.row;
					}
					
					if (c.col == current.col) { //vertical
						for (int r = row1 + 1; r < row2; r++) {
							if (findPiece(c.col, r, list) != null) {
								newMoves.pop();
								break;
							}
						}
					} else if (c.row == current.row) { //horizontal
						for (int h = col1 + 1; h < col2; h++) {
							if (findPiece(h, c.row, list) != null) {
								newMoves.pop();
								break;
							}
						}
					} else if (Math.abs(c.col - current.col) == Math.abs(c.row - current.row)) { //diagonal
						for (int e = 1; e < col2 - col1; e++) {
							//Determine which direction to look at
							int colDir = 1;
							int rowDir = 1;
							if (c.col > current.col) {
								colDir = -1;
							}
							if (c.row > current.row) {
								rowDir = -1;
							}
							if (findPiece(c.col + e * colDir, c.row + e * rowDir, list) != null) {
								newMoves.pop();
								break;
							}
						}	
					}
				}
				//Pop from the old list 
				c.moves.pop();
				current = current.next;
			}
			//Update the list of moves in the object itself
			c.moves = newMoves;
		}
	}
	
}

//Represents a square on the board that may or may not be occupied
class SquareNode {
	int col;
	int row;
	SquareNode next = null;
	
	public SquareNode(int c, int r) {
		col = c;
		row = r;
	}
	
	public String toString() {
		return col + ", " + row;
	}
}

class SquareStack {
	SquareNode top;
	int size = 0;
	
	public SquareStack() {
		top = null;
	}
	
	public void push(SquareNode s) {
		SquareNode temp = new SquareNode(s.col, s.row);
		temp.next = top; //Make the new node reference the first node as next
		top = temp; //top is now the new node
		size++;	
	}
	
	public SquareNode pop() {
		SquareNode temp = new SquareNode(top.col, top.row);
		top = top.next;
		size--;
		return temp;
	}
	
	public SquareNode peek() {
		return top;
	}
	
	public boolean hasPiece(int col, int row) {
		SquareNode s = top;
		while (s != null) {
			if (s.col == col && s.row == row) {
				return true;
			}
			s = s.next;
		}
		
		return false;
	}
	
	public void printList() {
		SquareNode current = top;
		while (current != null) {
			current = current.next;
		}
	}
}

class ChessNode {
	ChessPiece chess;
	ChessNode next;
	
	public ChessNode(ChessPiece c) {
		chess = c;
		next = null;
	}
	
	public String toString() {
		return chess.toString();
	}

	public void setNext(ChessNode n) {
		next = n;
	}
	
}

class LinkedChess {
	ChessNode first;
	
	public LinkedChess() {
		first = null;
	}
	
	public void addNode(ChessNode n) {
		ChessNode newNode = n;
		newNode.next = first;
		first = newNode;
	}
	
	public ChessNode findIthNode(int pos) {
		ChessNode c = first;
		for (int i = 0; i < pos; i++) {
			c = c.next;
		}
		return c;
	}
	public void print() {
		ChessNode current = first;
		while (current != null) {
			current = current.next;
		}
	}
	public int getLength() {
		int i = 0;
		ChessNode current = first;
		while (current != null) {
			current = current.next;
			i++;
		}
		return i;
	}
}

abstract class ChessPiece {
	
	int col;
	int row;
	boolean side; //True for white, false for black
	SquareStack moves = new SquareStack();
	
	public ChessPiece() {
		
	}
	
	public ChessPiece(int c, int r, boolean b) {
		col = c;
		row = r;
		side = b;
	}
	
	boolean canAttack(ChessPiece c) {
		return true;
	}
	
	void move(int c, int r) {
		col = c;
		row = r;
	}
	
	//Finds all possible moves assuming empty board
	void moveSet(int size) {
		
	}
	
	public int[] getVals() {
		return (new int[] {col, row});
	}
	
	public String toString() { 
		return col + " " + row;
	}
	public String toFullString() {
		if (this.side) {
			return (this.getClass().getName().toLowerCase().charAt(0) + " " + this.col + " " + this.row);
		} else {
			return (this.getClass().getName().charAt(0) + " " + this.col + " " + this.row);
		}		
	}
	public char pieceType() {
		if (this.side) {
			return this.getClass().getName().toLowerCase().charAt(0);
		} else {
			return this.getClass().getName().charAt(0);
		}
	}
}

class Queen extends ChessPiece {
	
	public Queen(int c, int r, boolean b) {
		col = c;
		row = r;
		side = b;
	}
	
	public boolean canAttack(ChessPiece c) {
		//Check if other piece is on the same row or column as this piece
		if ((c.col == this.col) || (c.row == this.row)) {
			return true;
		}
		//Check if other piece is diagonal to this piece
		else if (Math.abs(c.col - col) == Math.abs(c.row - row)) {
			return true;
		}
		return false;
	}
	
	void moveSet(int size) {
		moves = new SquareStack();
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				if ((col == i) || (row == j)) {
					moves.push(new SquareNode(i, j));
				} else if (Math.abs(col - i) == Math.abs(row - j)) {
					moves.push(new SquareNode(i, j));
				}
			}
		}
	}
	
	public char pieceType() { //Prints the piece's position in the format "col row"
		if (side) {
			return 'q';
		} else {
			return 'Q';
		}
	}	
}

class King extends ChessPiece {
	
	public King(int c, int r, boolean b) {
		col = c;
		row = r;
		side = b;
	}
	
	public boolean canAttack(ChessPiece c) {
		int dCol = Math.abs(c.col - this.col);
		int dRow = Math.abs(c.row - this.row);
		//Check if other piece is on the same row or column as this piece
		if ((dCol == 1 && dRow == 0) || (dCol == 0 && dRow == 1)) {
			return true;
		}
		//Check if other piece is diagonal to this piece
		else if (dCol == 1 && dRow == 1) {
			return true;
		}
		return false;
	}
	
	void moveSet(int size) {
		moves = new SquareStack();
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				int dCol = Math.abs(col - i);
				int dRow = Math.abs(row - j);
				if (dCol == 1 && dRow == 1 || dCol == 0 && dRow == 1 || dCol == 1 && dRow == 0) {
					SquareNode s = new SquareNode(i, j);
					
					moves.push(s);
					//System.out.println(i + " " + j);
				}
			}
		}
	}
	
	public char pieceType() { //Prints the piece's position in the format "col row"
		if (side) {
			return 'k';
		} else {
			return 'K';
		}	
	}	
}

class Rook extends ChessPiece {
	
	public Rook(int c, int r, boolean b) {
		col = c;
		row = r;
		side = b;
	}
	
	public boolean canAttack(ChessPiece c) {
		//Check if other piece is on the same row or column as this piece
		if ((c.col == this.col) || (c.row == this.row)) {
			return true;
		}
		return false;
	}
	
	void moveSet(int size) {
		moves = new SquareStack();
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				if ((col == i) || (row == j)) {
					moves.push(new SquareNode(i, j));
				}
			}
		}
	}
	
	public char pieceType() { //Prints the piece's position in the format "col row"
		if (side) {
			return 'r';
		} else {
			return 'R';
		}	
	}	
}

class Bishop extends ChessPiece {
	
	public Bishop(int c, int r, boolean b) {
		col = c;
		row = r;
		side = b;
	}
	
	public boolean canAttack(ChessPiece c) {
		//Check if other piece is diagonal to this piece
		if (Math.abs(c.col - col) == Math.abs(c.row - row)) {
			return true;
		}
		return false;
	}
	
	void moveSet(int size) {
		moves = new SquareStack();
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				if (Math.abs(col - i) == Math.abs(row - j)) {
					moves.push(new SquareNode(i, j));
				}
			}
		}
	}
	
	public char pieceType() { //Prints the piece's position in the format "col row"
		if (side) {
			return 'b';
		} else {
			return 'B';
		}	
	}	
}

class Knight extends ChessPiece {
	
	public Knight(int c, int r, boolean b) {
		col = c;
		row = r;
		side = b;
	}
	
	public boolean canAttack(ChessPiece c) {
		int dCol = Math.abs(c.col - this.col);
		int dRow = Math.abs(c.row - this.row);
		if (dCol * dRow == 2) {
			return true;
		}
		return false;
	}
	
	void moveSet(int size) {
		moves = new SquareStack();
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				int dCol = Math.abs(col - i);
				int dRow = Math.abs(row - j);
				if (dCol * dRow == 2) {
					moves.push(new SquareNode(i, j));
				}
			}
		}
	}
	
	public String toFullString() {
		if (side) {
			return "n " + this.col + " " + this.row;
		} else {
			return "N " + this.col + " " + this.row;
		}
	}
	public char pieceType() {
		if (side) {
			return 'n';
		} else {
			return 'N';
		}
	}
}

class Pawn extends ChessPiece {
	
	public Pawn(int c, int r, boolean b) {
		col = c;
		row = r;
		side = b;
	}
	
	public boolean canAttack(ChessPiece c) {
		int direction;
		int dCol = c.col - this.col;
		int dRow = c.row - this.row;
		
		if (side) {
			direction = 1;
		} else {
			direction = -1;
		}
		
		if (Math.abs(dRow) == 1) {
			if (dCol == direction) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	void moveSet(int size) {
		moves = new SquareStack();
		int direction;
		if (side) {
			direction = 1;
		} else {
			direction = -1;
		}
		
		moves.push(new SquareNode(col, row + direction));
	}
	
	public char pieceType() { //Prints the piece's position in the format "col row"
		if (side) {
			return 'p';
		} else {
			return 'P';
		}
	}
}	