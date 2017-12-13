/* Name: Bingxue Ouyang
 * CruzID: bouyang
 * solve N Queen problem using stack
 */

import java.io.*;
import java.util.Scanner;
import java.lang.*;

public class NQueens{
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
            int n = Integer.parseInt(split[0]);
            // base case
            if(n==0){
                out.print("\n");
                return;
            }
            // initialize chessboard
            Queen[] solution = new Queen[n];
            Queen[] jump = new Queen[n];
            boolean valid = true;
            for(int i=1; i+1<split.length; i+=2){
                int qx = Integer.parseInt(split[i])-1;
                int qy = Integer.parseInt(split[i+1])-1;
                if(new Queen(0,0).isSafe(jump, qx, qy)){
                    // initialize input queens
                    solution[qx] = new Queen(qx, qy);
                    jump[qx]=new Queen(qx,qy);
                }
                else{
                    // System.out.println(qx +" "+ qy + " ");
                    valid = false;
                }
            }
            
            // start to solve
            if(new Queen(0,0).placeQueen(solution, jump) && valid){
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