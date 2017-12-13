/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * void main class
 * move chesspieces to specific coordinate
 * and check if movement is valid
 */
import java.io.*;
import java.util.Scanner;
import java.lang.*;

// main class
public class ChessMoves{
    public static int boardSize;
    public static ChessBoard board;
    public static void main(String[] args) throws IOException{
        if(args.length<2){
            System.out.println("Usage: java -jar ChessMoves.jar <input file> <output file>");
            System.exit(1);
        }
        // open files
        Scanner in = new Scanner(new File(args[0]));
        PrintWriter out = new PrintWriter(new FileWriter(args[1]));
        // read input file
        while(in.hasNextLine()){
            String line = in.nextLine().trim()+" ";
            // split by colon
            String[] colon = line.split(":");
            // coordinate
            String[] pieces = colon[0].split("\\s+");
            // chesspieces
            String[] moves = colon[1].split("\\s+");

            makeBoard(pieces);
            if(makeMoves(moves, out, board)){
                out.print("legal");
            }
            else{
                out.print("illegal");
            }
            out.println();
        }

        in.close();
        out.close();
    }
    
    public static void makeBoard(String[] pieces){
        board = new ChessBoard();
        // assign chesspiece
        for(int i = 0; i+2 < pieces.length; i+=3){
            String name = pieces[i];
            int x = Integer.parseInt(pieces[i+1]);
            int y = Integer.parseInt(pieces[i+2]);
            if(name.toLowerCase().equals("k")){
                board.insert(new King(name, x, y));
            }
            else if(name.toLowerCase().equals("q")){
                board.insert(new Queen(name, x, y));
            }
            else if(name.toLowerCase().equals("r")){
                board.insert(new Rook(name, x, y));
            }
            else if(name.toLowerCase().equals("n")){
                board.insert(new Knight(name, x, y));
            }
            else if(name.toLowerCase().equals("b")){
                board.insert(new Bishop(name, x, y));
            }
            else if(name.toLowerCase().equals("p")){
                board.insert(new Pawn(name, x, y));
            }
        }
    }
    public static boolean isChecked(ChessPiece king, ChessBoard board, int counter){
        Node curr = board.head;
        while(curr != null){
            if(curr.piece.isWhite != king.isWhite){
                if((curr.piece).isValidMove(king.x, king.y, board, counter)){
                    // System.out.println(king.x+" "+king.y+"King is checked by " +curr.piece.name+curr.piece.x+curr.piece.y);
                    return true;
                }
            }
            curr = curr.next;
        }
        // System.out.println(king.x+" "+king.y+"no piece attack king");
        return false;
    }

    public static boolean makeMoves(String[] moves, PrintWriter out, ChessBoard board){
        int counter = 2;
        for(int i = 1; i+3< moves.length; i+=4){
            int moveX = Integer.parseInt(moves[i]);// original x
            int moveY = Integer.parseInt(moves[i+1]);// original y
            int toX = Integer.parseInt(moves[i+2]);// move to x
            int toY = Integer.parseInt(moves[i+3]);// move to y
            ChessPiece findPiece = board.find(moveX, moveY); // find original piece
            if(findPiece == null){
                out.print(moveX+" "+moveY+" "+toX+" "+toY+" ");
                return false;
            }
            else{
                // check if color is right
                if(findPiece.isWhite && counter%2 != 0){
                    out.print(moveX+" "+moveY+" "+toX+" "+toY+" ");
                    return false;
                }
                else if(!findPiece.isWhite && counter % 2 == 0){
                    out.print(moveX+" "+moveY+" "+toX+" "+toY+" ");
                    return false;
                }
                // check if move is valid and move
                if(findPiece.isValidMove(toX,toY,board,counter)){
                    findPiece.update(toX,toY,board);
                    // check if player's king is checked
                    ChessPiece whiteKing = board.findName("k");
                    ChessPiece blackKing = board.findName("K");
                    if(findPiece.isWhite){
                        if(isChecked(whiteKing, board, counter)){ // white king is still checked
                            out.print(moveX+" "+moveY+" "+toX+" "+toY+" ");
                            return false;
                        }
                    }
                    else{
                        if(isChecked(blackKing, board, counter)){ // black king is still checked
                            out.print(moveX+" "+moveY+" "+toX+" "+toY+" ");
                            return false;
                        }
                    }
                }
                else{
                    out.print(moveX+" "+moveY+" "+toX+" "+toY+" ");
                    return false;
                }
            }
            counter ++;
        }
        return true;
    }

}
