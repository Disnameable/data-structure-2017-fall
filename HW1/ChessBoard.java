/* Name: Bingxue Ouyang
 * CruzID: bouyang
 * solve N Queen problem
 */

import java.io.*;
import java.util.Scanner;
import java.lang.*;

public class ChessBoard{
    
    public static void main (String[] args) throws IOException{
        
        if(args.length<2){
            System.out.println("Usage: java -jar NQueens.jar <input file> <output file>");
            System.exit(1);
        }
        // open files
        Scanner in = new Scanner(new File(args[0]));
        PrintWriter out = new PrintWriter(new FileWriter(args[1]));
        
        // start reading
        while(in.hasNextLine()){
            String line = in.nextLine().trim()+" ";
            String[] split = line.split("\\s+");
            
            // assign value
            int n = Integer.parseInt(split[0]);
            int qx = Integer.parseInt(split[1])-1;
            int qy = Integer.parseInt(split[2])-1;
            
            // base case
            if(n==0) return;
            
            // initialize chessboard and first queen
            Queen[] solution = new Queen[n];
            solution[qx] = new Queen(qx,qy);
            
            // start to solve
            if(solution[qx].placeQueen(solution, qx+1, qx)){
                for(int i=0; i<solution.length; i++){
                    int x = solution[i].qx+1;
                    int y = solution[i].qy+1;
                    out.print(x +" "+ y + " ");
                }
            }
            else out.print("No solution");
            out.print("\n");
        }
        
        // close files
        in.close();
        out.close();
    }
    
}
