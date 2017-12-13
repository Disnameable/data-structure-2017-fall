// Filename: ChessBoard.java
//
// Contains class ChessBoard that creates a linkedlist of chessboard where each node holds a chesspiece to be placed
//
// This needs to be compiled with ChessPiece.java, Node.java, Utilities.java, King.java, Queen.java, Knight.java, Rook.java, Bishop.java and Pawn.java.
//
// Run make with associated Makefile to get ChessBoard.jar.
// 
// The format to run this is:
//                 java -jar ChessBoard.jar <INPUT_FILE> <OUTPUT_FILE>
// 
// Each line of the input file is a different chessboard.
// The line starts with two integers, denote (column, row) of an 8X8 chessboard.
// This is followed by a colon, and then a sequence of chesspieces given as <char> <column> <row>.
// The char denotes the chesspiece, and is capitalized if it denotes a black piece.
// Check out the HW2 description for details.
// 
//
// Santrupti Nerli, Jan 2017. C. Seshadhri, Oct 2017.
//
import java.io.*;

class ChessBoard {

  private static Node head; // linkedlist to store chesspieces
  private static int board_size; // board_size
  public static BufferedWriter writer; // write to write to file

  // constructor
  public ChessBoard() {
    head = new Node();
  }

  // Method to perform insertion at the front of the list
  // Input: Node to be inserted
  // Output: void
  public Node insert(Node piece) {
    Node temp = head.getNext();
    head.setNext(piece);
    piece.setNext(temp);
    return head;
  }

  // Method to find Node in a given location
  // Input: integer row and column to look for
  // Output: Node found
  public Node findChessPiece(int row, int col) {
    Node piece = head.getNext();
    while(piece != null) {
      if(piece.getRow() == row && piece.getCol() == col) {
        return piece;
      }
      piece = piece.getNext();
    }
    return null;
  }

  // Method to count the number of chesspieces for a given type
  // This method will helps us check the validity case
  // Input: character color
  // Output: returns the count
  public int countPiecesOfType(char pieceType) {
    Node piece = head.getNext();
    int pieceCtr = 0;
    // loop through to check if the same piece type is found
    while(piece != null) {
      if(Utilities.returnChessPieceType(piece) == pieceType) {
        pieceCtr++;
      }
      piece = piece.getNext();
    }
    return pieceCtr;
  }

  // Method to count the number of chesspieces on a single location
  // This method will helps us check the validity case
  // Input: integer row and column
  // Output: returns the count
  public int countPiecesInLocation(int row, int col) {
    Node piece = head.getNext();
    int pieceCtr = 0;
    // loop through to check if any two pieces overlap
    while(piece != null) {
      if(piece.getRow() == row && piece.getCol() == col) {
        pieceCtr++;
      }
      piece = piece.getNext();
    }
    return pieceCtr;
  }

  // Method to check if two pieces occupy the same place
  // This method utilizes the countPiecesInLocation method to see
  // if there are more than two pieces in a single location
  // Input: none
  // Output: returns true if two pieces occupy same position
  public boolean twoPiecesOccupySamePosition() {
    Node piece = head.getNext();
    // loop through and see if any of the pieces overlap
    while(piece != null) {
      if(countPiecesInLocation(piece.getRow(), piece.getCol()) > 1) {
        return true;
      }
      piece = piece.getNext();
    }
    return false;
  }

  // Method to check validity
  // basically looks if there are not two chesspieces in the same location and
  // one each colored king is present
  // Input: none
  // Output: returns if it is valid or not
  public boolean checkValidity() {
    if(!twoPiecesOccupySamePosition()) {
      return true;
    }
    else {
      return false;
    }
  }

  // Method to check if any piece exists in the query location
  // Input: integer array query
  // Output: returns the piece (in character) if found otherwise just returns '-'
  public char findChessPiece(int[] query) {
    int col = query[0];
    int row = query[1];
    Node foundPiece = findChessPiece(row, col);
    if ( foundPiece != null) {
      return Utilities.returnChessPieceType(foundPiece);
    }
    return '-';
  }

  // Method to check if two nodes given are different or the same ones
  // It serves as a helper when trying to find the attack
  // Input: two nodes
  // Output: returns if they are same or different pieces
  public boolean isDifferent(Node one, Node other) {
    if(one.getRow() == other.getRow() && one.getCol() == other.getCol() && one.getColor() == other.getColor()) {
      return false;
    }
    return true;
  }

  // Method to see if any of the pieces attack
  // as soon as you encounter the first attack, just print it and return
  // Input: integer query array of size 2
  // Output: returns if piece here is attacking another piece
  public boolean determineAttacking(int[] query) {
    Node piece = findChessPiece(query[1], query[0]); // get piece at query position
    if (piece == null) // nothing at query piece
        return false; // vacuously false

    // now loop through all pieces to check for attack
    // get the first valid chesspiece (remember not the head)
    Node other = head.getNext();
    // loop through each of the remaining chesspieces and check for attack
    while(other != null) {
        if(isDifferent(piece, other) && piece.getColor() != other.getColor() && piece.getChessPiece().isAttacking(other.getRow(), other.getCol()))  // found an attack!
            return true;
        other = other.getNext();
    }
    return false; // no attack
  }

  // Method to write to the analysis.txt file
  // Input: String to write
  // Output: void, just write
  public void writeToAnalysisFile(String stringToWrite) {
    try {
        writer.write(stringToWrite);
    }
    catch (Exception e) {
        Utilities.errExit("Exception occurred while trying to write to file: writeToAnalysisFile"); // throw a generic exception if failure to read occurs
    }
  }

  // Method to iterate through the list and update a 2D array for printing the board
  // onto the console
  // Input: integer board number read from input.txt
  // Output: void, jusr print the solution
  public static void convertFromListToMatrixAndPrint(int board_no) {
    // Initialize isFilled board
    char[][] isFilled = new char[board_size+1][board_size+1];
    for(int i = 0; i < board_size; i++) {
      for(int j = 0; j < board_size; j++) {
        isFilled[i][j] = '-';
      }
    }
    // iterate through the list and update isFilled matrix
    Node piece = head.getNext();
    while(piece != null) {
      System.out.println(piece.getRow()+" "+piece.getCol());
      isFilled[piece.getRow()][piece.getCol()] = Utilities.returnChessPieceType(piece);
      piece = piece.getNext();
    }

    System.out.println("Board No: " + board_no);
    Utilities.printSolution(isFilled, board_size);
  }

  // Method to read from input.txt
  // for each chessboard and query, perform all the required operations
  // an then proceed further
  // Input: BufferedReader reader, for input file
  // Output: void, just read and perform requested operations
  public static void readFromInputFile(BufferedReader reader) {

    int lineCtr = 0;
    int[] query = new int[2];
    ChessBoard c = null;

    try {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(":"); // Reader assumes that the input format is as given in the instruction
            // Splitting by colon, first token should have two integers, for column and row
            String[] query_tokens = tokens[0].split(" ");
            if (query_tokens.length != 2) // some problem, there should be two ints before colon
                Utilities.errExit("Input line below has a problem. There should be only two ints before colon.\n"+line); // print error message

            query[0] = Integer.parseInt(query_tokens[0]); // parse tokens to get the query column
            query[1] = Integer.parseInt(query_tokens[1]); // parsing to get query row

            c = new ChessBoard(); //create new chessboard
            board_size = 8; // board size is 8
            String[] board_tokens = tokens[1].trim().split(" "); // split part after colon by space

            if (board_tokens.length%3 == 1) // number of parts after colon not divisible by 3, so some problem
                Utilities.errExit("Input line after colon does not have number of strings that are divisible by 3. Incorrect format.\n"+line);
            // loop over strings in board_tokens, create piece and insert it into board
            for(int i = 0; i < board_tokens.length; i += 3) {
                head = c.insert(new Node(board_tokens[i].charAt(0), Integer.parseInt(board_tokens[i+2]), Integer.parseInt(board_tokens[i+1])));
              }
            // perform the requested operations
            System.out.println(lineCtr);
            performOperations(c, query, lineCtr);
            lineCtr++; // move to the next line
        }
        reader.close();
    }
    catch (NumberFormatException e) {
        Utilities.errExit("All arguments must be integers"); // throw error incase parsing integer fails
    }
    catch (IndexOutOfBoundsException e) {
        Utilities.errExit("Array index is out of bounds"); // throw error when inserting elements into arrays fail
    }
    catch (Exception e) {
        Utilities.errExit("Exception occurred trying to read file"); // throw a generic exception if failure to read occurs
    }
  }

  // Method to perform all the requested operations
  // namely, check validity, perform the search query
  // check for attack
  // Input: ChessBoard and the query
  // Output: returns the count
  public static void performOperations(ChessBoard c, int[] query, int board_no) {
    try {
      // Check for validity here
      convertFromListToMatrixAndPrint(board_no);
      if(c.checkValidity() == false) {
        writer.write("Invalid\n");
        return;
      }
      // Find the chesspiece given in query location
      char result = c.findChessPiece(query);
      writer.write(result); // write the result
      if (result == '-') // no piece at query
      {
          writer.write("\n");
          return;
      }

      // See if anyone attacks anyone else
      if (c.determineAttacking(query)) // query piece is attacking another piece
          writer.write(" y\n");
      else // query piece is not attacking another piece
          writer.write(" n\n");
    }
    catch(Exception e) {
      e.printStackTrace();
      Utilities.errExit("Error while performing operations");
    }

  }

  // main method
  public static void main(String[] args) {

    if (args.length < 2) // input format invalid
        Utilities.errExit("Must provide two arguments: input file and output file");

    try{
        BufferedReader reader = new BufferedReader(new FileReader(args[0])); // open the file to reader
        writer = new BufferedWriter(new FileWriter(args[1])); // setting up file I/O for output
        readFromInputFile(reader); // read from input file and perform operations
        writer.close(); // close the writer
    }
    catch(Exception e) {
      Utilities.errExit("Error in reading input file. Sure it exists?");
    }

  }
}

// End
