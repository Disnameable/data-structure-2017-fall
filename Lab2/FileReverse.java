//---------------------------------------------------
// Name: Bingxue Ouyang
// CruzID: bouyang
// FileReverse.java
// Reverse input file text
//---------------------------------------------------
import java.io.*;
import java.util.Scanner;

class FileReverse{
    public static void main(String[] args) throws IOException{
        // check that command contains input file and output file
        if(args.length<2){
            System.out.println("Usage: java -jar FileReverse.jar <input file> <output file>");
            System.exit(1);
        }


        // open input and output files
        Scanner in = new Scanner(new File(args[0]));
        PrintWriter out = new PrintWriter(new FileWriter(args[1]));
        String result="";

        // read input file
        while(in.hasNextLine()){
        // trim spaces and add trailing space for split()
            String line = in.nextLine().trim()+" ";
			
            // split line around white space
            String[] split = line.split("\\s+");
			
			// call reverse function and print out
			for(int j=0; j<split.length; j++){
				out.println(stringReverse(split[j]));
			}		
		}
		
		// close files
		in.close();
		out.close();	
	}
		
	public static String stringReverse(String s){
		String reverse = "";
		
		// split characters and reverse
		for(int i=s.length(); i>0; i--){
			reverse += s.charAt(i-1);
		}
		return reverse;
	}
}
