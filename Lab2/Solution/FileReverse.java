//-----------------------------------------------------------------------------
// FileReverse.java
// Reverses all tokens in a file and outputs them to another file
// Solution to Lab2 in CMPS 12B/M 
// Code reuse from other lab 2 documents. 
//
//
// Usage: ideally, this should be compiled into a jar file. 
// The command "java -jar FileReverse.jar <in> <out>" will produce a file <out> that, in a separate line, has the reverse of each word of <in>
//
// Matt Bryson, Oct 2016. C. Seshadhri, Sept 2017.
//-----------------------------------------------------------------------------
import java.io.*;
import java.util.Scanner;

class FileReverse{
   public static void main(String[] args) throws IOException{

      // check number of command line arguments is at least 2
      if(args.length < 2){
         System.out.println("Usage: java -jar FileReverse.jar <input file> <output file>");
         System.exit(1);
      }

      // open files
      Scanner in = new Scanner(new File(args[0]));
      PrintWriter out = new PrintWriter(new FileWriter(args[1]));

      // read lines from in, extract and print tokens from each line
      while( in.hasNextLine() ){
         // trim leading and trailing spaces, then add one trailing space so 
         // split works on blank lines
         String line = in.nextLine().trim() + " "; 

         // split line around white space 
         String[] token = line.split("\\s+");  

         // print out tokens       
         int n = token.length;
         for(int i=0; i<n; i++){
            out.println(stringReverse(token[i]));
         }
      }

      // close files
      in.close();
      out.close();
   }
   
// function to reverse a string
   public static String stringReverse(String s)
   {
	   int length = s.length(); 
	   char [] reversed = new char [length]; // set up output array of correct length
	   
	   int y = length - 1;  // initialize to last position in s
	   for(int i = 0; i < length; i++) // loop to fill in reversed
	   {
		   reversed [i] = s.charAt(y); // copy into reversed, and decrement y. Basically, loop is going backwards in s
		   y--; 
	   }
	   
	   return String.valueOf(reversed);
   }
   
}
