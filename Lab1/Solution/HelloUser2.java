//-----------------------------------------------------------------------------
// HelloUser2.java
// Gets username and prints greeting to stdout.
//
// Matt Bryson, October 2016. C. Seshadhri, Sept 2017
//-----------------------------------------------------------------------------
class HelloUser2{
   public static void main( String[] args ){
      String userName = System.getProperty("user.name");
      System.out.println("Hello "+userName);
   }
}

