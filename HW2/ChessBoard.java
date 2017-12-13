/*
 * Name: Bingxue Ouyang
 * CruzID: bouyang
 * create chessboard using linked list
 * check if chessboard is valid
 * and find chesspiece at specified coordinate
 */
import java.io.*;
import java.util.Scanner;
import java.lang.*;

// Linked list class
public class ChessBoard{
    public static int boardSize;
    public static ChessPiece head;
    public static LinkedList list;
    public static void main(String[] args) throws IOException{
        if(args.length<2){
            System.out.println("Usage: java -jar ChessBoard.jar <input file> <output file>");
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
            String[] coor = colon[0].split("\\s+");
            // chesspieces
            String[] pieces = colon[1].split("\\s+");
            makeList(pieces);
            if(isValid(list)){
                int findX = Integer.parseInt(coor[0]);
                int findY = Integer.parseInt(coor[1]);
                ChessPiece findPiece = list.find(findX,findY);
                if(findPiece!=null){
                    out.print(findPiece.name);
                    if(list.findAttack(findPiece)){
                        out.print(" y");
                    }
                    else out.print(" n");
                }
                else out.print("-");
            }
            else out.print("Invalid");
            out.println();
        }
        in.close();
        out.close();
    }
    
    public static void makeList(String[] s){
        list = new LinkedList();
        // assign chesspiece
        for(int i = 1; i < s.length; i+=3){
            String name = s[i];
            int x = Integer.parseInt(s[i+1]);
            int y = Integer.parseInt(s[i+2]);
            if(name.toLowerCase().equals("k")){
                list.insert(new King(name, x, y));
            }
            else if(name.toLowerCase().equals("q")){
                list.insert(new Queen(name, x, y));
            }
            else if(name.toLowerCase().equals("r")){
                list.insert(new Rook(name, x, y));
            }
            else if(name.toLowerCase().equals("n")){
                list.insert(new Knight(name, x, y));
            }
            else if(name.toLowerCase().equals("b")){
                list.insert(new Bishop(name, x, y));
            }
            else if(name.toLowerCase().equals("p")){
                list.insert(new Pawn(name, x, y));
            }
        }
    }

    // count the pieces in a coordinate
    public static int counter(int x, int y, LinkedList l){
        Node curr = l.head;
        int counter = 0;
        while(curr != null){
            if(curr.piece.x == x && curr.piece.y == y){
                counter ++;
            }
            curr = curr.next;
        }
        return counter;
    }

    // double occupy check
    public static boolean doubleOccupy(LinkedList l){
        Node curr = l.head;
        while(curr != null){
            if(counter(curr.piece.x, curr.piece.y, l)>1){
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public static boolean isValid(LinkedList l){
        if(doubleOccupy(l)){
            return false;
        }
        else return true;
    }

}