// Filename: NQueens.java
// 
// Contains class NQueens that has recursive solver for the N Queens problem.
// The challenge is to place N queens on an N X N chessboard such that no queen attacks another.
// 
// This needs to be compiled with Queen.java. NQueens contains a main method that
// executes the solver.
//
// Run make with associated Makefile to get NQueens.jar.
// 
// The format to run this is:
//          java -jar NQueens.jar <INPUT_FILE> <OUTPUT_FILE>
//
// Each line of the input file corresponds to a different instance.
// Each line starts with N, the board size. That is followed by an optional list of queen positions,
// provided as a list of "<COLUMN> <ROW>" values.
// For example, a line may be "8 1 1 2 3".
// The instance is an 8 X 8 chessboard, with one queen as (1,1), another queen as (2,3). The problem is to place 6 more queens. 
// Another example is a line "15".
// This is simply the classic 15 X 15 queens problem: start with an empty 15 X 15 chessboard, and the problem is to place all 15 queens.
// 
//  
// The output file has one line for each input line (input instance): 
//     - If input configuration already has a pair of attacking queens, print "Input configuration invalid"
//     - If no solution possible (though input is valid), print "No solution"
//     - If solution exists, print solution as series of queen positions. Each position is given as col, row of a different queen, in increasing order of column. 
//       Finally, the chessboard is printed out to the console.
// 
// C. Seshadhri, Dec 2016. Sept 2017.
// 
import java.io.*;
import java.util.Scanner;

class NQueens 
{
    private static int board_size;  // board size
    private static Queen[] solution; // global variable containing the solution
   
    // Convenient method to print error message and exit
    // Input: String message to be printed
    // Output: void, simply exits program 
    private static void errExit(String message)
    {
        System.err.println(message);
        System.exit(1);
    }

    // A cool method that actually prints a chessboard with queens onto the console. Uses some UNICODE awesomeness
    // Input: 2D boolean array isFilled, where isFilled[i][j] is true iff there is a queen at col i, row j. 
    // Assumes that isFilled is board_size X board_size.
    // Output: void, basically prints the chessboard with the queens onto console

    private static void printSolution(boolean[][] isFilled)
    {
        String line = "  "; // starting strings to be printed. line is the dividing horizontal strip along the board
        String col_nums = "  ", col_str = ""; // these strings are for printing the column numbers below

        for (int i=1; i < board_size+1; i++) // loop over all the columns to create the line and col_nums strings
        {
            line = line + "+--"; // each iteratin add "+--" to line
            if (i < 10) // have to break into cases, since i < 10 is one symbol, but i >= 10 is two
                col_str = " " + Integer.toString(i) + " ";  // add string i to col_str with spacing
            else
                col_str = " " + Integer.toString(i);  // add string i to col_str with less spacing, since i >= 10
            col_nums = col_nums + col_str ; // append col_str to col_nums
        }
        line = line + "+";  // complete line string
        
        for (int i=board_size; i > 0; i--) // loop over all the rows in decreasing order. each iteration will print a row
        {
            System.out.println(line); // start by printing a line
            String pieces, background; // pieces string will actually put the queen symbols into string
            if (i < 10)   // pieces begins with row number, again break into cases if i has 1 vs 2 symbols
                pieces = " "+Integer.toString(i);
            else
                pieces = Integer.toString(i);

            for (int j=1; j < board_size+1; j++) // now loop over columns to create individual squares
            {
                if ((i+j)%2 == 1) // place alternating black or red background for squares
                    background = "\u001B[40m";  // ANSI escape code for black
                else
                    background = "\u001B[41m";  // ANSI escape code for red
                if (isFilled[i][j]) // if (i,j) has a queen
                    pieces = pieces + "|"+background+"\u2655 "+"\u001B[0m"; // put UNICODE symbol for queen with a line. also set background, and then apply ANSI reset code
                else
                    pieces = pieces + "|"+background+"  "+"\u001B[0m";  // put a space and set background, reset as before
            }
            System.out.println(pieces+"|");
        }
        
        System.out.println(line); // print out the final line
        System.out.println(col_nums); // print out the columns below
    }

// The real procedure. It places num_left queens, assuming the remaining queens are in the array solution (defined at the very beginning).
// The procedure is recursive, decrementing num_left in each recursive call.
// Input: num_left is the number of queens left to place.
// Assume that the remaining k = (board_size - num_left) queens are in solutions[0...k-1]. Thus, the input "implicitly" contains the 
//         positions of k queens
// Output: boolean that is true iff there is a possible placement of queens. In addition, solution contains all positions
//         of the queens


    private static boolean PlaceQueens(int num_left)  
    {
        int free = -1;  // tracking a "free" column
        int num_placed = board_size - num_left; // number of queens already placed

        if (num_left == 0)  // base case, all queens were successfully placed, so return true
            return true;

        boolean available = false; // tracking if some column is available
        for (int i=1; i < board_size+1; i++) // iterate over all columns in chessboard
        {
            available = true; // assume column i is available (no queen is in column i)
            for (int j=0; j < num_placed; j++) // iterate over currently placed queens
                if (solution[j].col == i) // if some queen is in column i, then i is not available
                {
                    available = false;
                    break;
                }
            if (available) // if "available" is true, then no queen in column i. so set "free" to i
            {
                free = i;
                break;
            }
        }

        if (free == -1) // for error tracking. free should never be -1
            errExit("Error in PlaceQueens: free is -1"+": "+num_placed);


        // The next part is the real algorithm. We know that no queen is in column "free", so we'll
        // try to place the next queen in this column. We need to find some row to place the queen,
        // and then we can make a recursive call.

        for (int row=1; row < board_size+1; row++)  // iterate over all rows
        {
            Queen next = new Queen(free, row); // create a queen at (free, row)
            boolean isPossible = true; // assume we can place queen here

            for (int i=0; i < num_placed; i++) // iterate over existing queens
                if (solution[i].isAttacking(next)) // check if existing queen attacks next queen
                {
                    isPossible = false; // if some queen attacks next queen, then this position is not possible
                    break;
                }

            if (isPossible)  // we can place the next queen safely
            {
                solution[num_placed] = next; // add next queen to solution
                if (PlaceQueens(num_left-1)) // make recursice call, decrement the number of queens left
                    return true;  // if recursive call was successful, declare success
            }
        }

        // if the code reaches this point, it could not place any queen in column "free". Thus
        // there exists no solution. 
        Queen dummy = new Queen();
        solution[num_placed] = dummy; // place a dummy queen just for bookkeeping purposes
        return false; // no solution exists
    }

    // A convenient method to convert a string into an int, throwing an error message if not possible
    // Input: String num, that is supposed to be an int
    // Output: If num represents an int, return the int. Otherwise, throw error message

    private static int getInt(String num)
    {
        int parsed_num = 0;
        try
        {
            parsed_num = Integer.parseInt(num); // try to parse num as an int
        }
        catch (NumberFormatException e) // failure, so exception is caught
        {
            errExit("Instance can only have integers"); // throw error
        }
        return parsed_num; // return int value of num
    }

    // Parse the problem into appropriate board size and queen positions.
    // Input: String array (will come from command line)
    // Output: Update board_size and solution appropriately. Return the number of queens placed in the arguments

    private static int parseProblem(String instance)
    {
        String[] problem = instance.split(" "); //parse instance string by whitespace

        board_size = getInt(problem[0]); // set board_size
        if (board_size <= 0) // don't give bad board sizes
            errExit("Board size must be positive");

        if (problem.length%2 == 0) // even number of arguments, so there is problem. throw error
            errExit("Instance must have an odd number of integers. Check out following line input: "+problem);
        
        solution = new Queen[board_size]; // allocate references for solution

        for (int i=1; i < problem.length/2 + 1; i++) // loop over remaining arguments in pairs
        {
            solution[i-1] = new Queen(getInt(problem[2*i-1]),getInt(problem[2*i])); // create a new queen with every successive pair of ints
            if (solution[i-1].row < 1 || solution[i-1].row > board_size || solution[i-1].col < 1 || solution[i-1].col > board_size) // you cannot place a queen outside the board
                errExit("Input position outside board");
        }
        return problem.length/2;
    }

    // Just checking if a partial setting of queens is valid.
    // Input: num_placed queens in solution
    // Output: true iff none attack each other

    private static boolean isValid(int num_placed)
    {
        for (int i=0; i<num_placed; i++) // double loop over queens that have been placed
            for (int j=0; j<i; j++)  
                if (solution[i].isAttacking(solution[j])) // if one queen attacks another, setting is not valid
                    return false;
        return true; // queens don't attack each other, so setting is valid
    }

    // The main procedure

    public static void main(String[] args) throws IOException
    {
        if (args.length < 2) // input format invalid
            errExit("Must provide two arguments: input file and output file");

        try{
            BufferedReader reader = new BufferedReader(new FileReader(args[0])); // open the file to reader
            PrintWriter output = new PrintWriter(new FileWriter(args[1])); // setting up file I/O for output
            
            String line; // stores each line in input file
            int line_no = -1; // store number of line
            while ((line = reader.readLine()) != null) { // reading in line, and checking if file has ended

                line_no++; // increment line_no to keep track of problem number
                int num_placed = parseProblem(line); // parse problem, and get number of placed queens (in command line input)
                int num_left = board_size - num_placed; // number of queens left to place

                if (!isValid(num_placed)) // check for validity of input placed
                {
                    output.println("Input configuration invalid"); // print message in output file
                    continue; // move on to next problem
                }

                // problem is valid instance
                boolean isFilled[][] = new boolean[board_size+1][board_size+1]; // empty array for printing solution
                if(PlaceQueens(num_left)) // PlaceQueens is where real work is happening. if there is a valid solution...
                {
                    for (int col=1; col<solution.length+1; col++) // print solution, looping over columns
                    {
                        int queen_row = 0; //variable for row of queen in col
                        for (int index=0; index<solution.length; index++) // loop over queens to find out which one is the right column
                        {
                            if (solution[index].col == col) // found the queen in column col
                                queen_row = solution[index].row;  // set the row
                        }

                        if (queen_row == 0) // there is some problem. queen_row was not set by above loop
                            errExit("Some problem in solution for instance "+line_no);

                        System.out.print(col + " " + queen_row + " "); // print out position of queen
                        output.print(col + " " + queen_row + " ");
                    }
                    System.out.print("\n"); // print out new line
                    output.print("\n");

        
                    for (int i=0; i < board_size+1; i++) // initialized isFilled to false
                        for (int j=0; j < board_size+1; j++)
                            isFilled[i][j] = false;
            
                    for (int i=0; i < solution.length; i++) // for each queen in solution, set corresponding location in isFilled to true
                        isFilled[solution[i].row][solution[i].col] = true;
        
                    printSolution(isFilled); // print solution
                }
                else // PlaceQueens(...) returned false, so no solution
                {
                    System.out.println("No solution");
                    output.println("No solution");
                }
            }
            
            reader.close(); // close files
            output.close(); 
        }
        catch(Exception e) {
            errExit("Error in reading input file. Are you sure file exists?");
        }

    }
}

