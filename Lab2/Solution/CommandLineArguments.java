// CommandLineArguments.java
//
// Prints out the number of command line arguments, and then each command line arguments in a separate line
//
// Matt Bryson, Oct 2016. C. Seshadhri, Sept 2017
class CommandLineArguments{
   public static void main(String[] args){
      int n = args.length;
      System.out.println("args.length = " + n);
      for(int i=0; i<n; i++) System.out.println(args[i]);
   }
}

