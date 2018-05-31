import java.io.* ;
/**
 *   Invoke one of the Life implementations against a particular
 *   input pattern.  The input pattern is read from standard input.
 *
 *   Usage:  java Driver <classname> <pattern.rle
 *
 *   Warning:  runs forever; it may put your machine into swap
 *   or generate huge output files, so be prepared to stop it with
 *   a control-C.
 */
public class Driver {
   /**
    *   If something is wrong with the command line we complain here.
    */
   static void usage(String s) {
      System.out.println(s) ;
      System.out.println("Usage:  java Driver <classname> <pattern.rle") ;
      System.exit(10) ;
   }
   /**
    *   Read a pattern in RLE format.  This format uses 'b' for a blank,
    *   'o' for a live cell, '$' for a new line, and integer repeat
    *   counts before any of this.  It also contains a leading line
    *   containing the x and y location of the upper right.  This is
    *   not a full-fledged reader but it will read the patterns we
    *   include.  Any ! outside a comment will terminate the pattern.
    */
   static void readPattern() throws Exception {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
      String inputLine = null ;
      int x = 0, y = 0 ;              // current location
      int paramArgument = 0 ;         // our parameter argument so far
      while ((inputLine = br.readLine()) != null) {
         // ignore lines that start with 'x' or '#'
         if (inputLine.startsWith("x") || inputLine.startsWith("#"))
            continue ;
         inputLine = inputLine.trim() ;
         for (int i=0; i<inputLine.length(); i++) {
            char c = inputLine.charAt(i) ;
            int param = (paramArgument == 0 ? 1 : paramArgument) ;
            if (c == 'b') {
               x += param ;
               paramArgument = 0 ;
            } else if (c == 'o') {
               while (param-- > 0)
                  univ.setBit(x++, y) ;
               paramArgument = 0 ;
            } else if (c == '$') {
               y += param ;
               x = 0 ;
               paramArgument = 0 ;
            } else if ('0' <= c && c <= '9') {
               paramArgument = 10 * paramArgument + c - '0' ;
            } else if (c == '!') {
               return ;
            } else {
               usage("In the input, I saw the character " + c +
                " which is illegal in the RLE subset I know how to handle.") ;
            }
         }
      }
   }
   /**
    *   Main picks up the class to use for the UniverseImplementation
    *   from the command line, loads and instantiates that class,
    *   parses the RLE from the standard input, setting bits as it
    *   goes, and then runs the Universe forever.
    */
   public static void main(String[] args) throws Exception {
      if (args.length != 1)
         usage("Please give me an argument containing the class name to use.") ;
      String classToUse = args[0] ;
      System.out.println("Instantiating class " + classToUse + ".") ;
      univ = (UniverseInterface)Class.forName(classToUse).newInstance() ;
      System.out.println("Reading pattern from standard input.") ;
      readPattern() ;
      System.out.println("Simulating.") ;
      while (true) {
         System.out.println(univ.stats()) ;
         univ.runStep() ;
      }
   }
   static UniverseInterface univ ;
}
