import java.text.NumberFormat ;
/**
 *   This Universe uses a quadtree datastructure to hold our cells.
 */
public class TreeUniverse implements UniverseInterface {
   /**
    *   Set a single bit; can only do this before running, and once
    *   we've started running cannot change.
    */
   public void setBit(int x, int y) {
      /*
       *   We need to make sure the current universe is large enough
       *   to handle these parameters.  A root node at level n supports
       *   coordinates from -2^(n-1) .. 2^(n-1)-1.
       */
      while (true) {
         int maxCoordinate = 1 << (root.level - 1) ;
         if (-maxCoordinate <= x && x <= maxCoordinate-1 &&
             -maxCoordinate <= y && y <= maxCoordinate-1)
            break ;
         root = root.expandUniverse() ;
      }
      /*
       *   Call our recursive routine to set the bit.
       */
      root = root.setBit(x, y) ;
   }
   /**
    *   Run a step.  First, we make sure the root is large enough to
    *   include the entire next generation by checking that all border
    *   nodes in the 4x4 square three levels down are empty.  Then we
    *   simply invoke the next generation method of the node.
    */
   public void runStep() {
      while (root.level < 3 ||
             root.nw.population != root.nw.se.se.population ||
             root.ne.population != root.ne.sw.sw.population ||
             root.sw.population != root.sw.ne.ne.population ||
             root.se.population != root.se.nw.nw.population)
         root = root.expandUniverse() ;
      root = root.nextGeneration() ;
      generationCount++ ;
   }
   /**
    *   Display the current statistics about the current generation.
    *   This should include the generation count and the population.
    */
   public String stats() {
      return "Generation " + nf.format(generationCount) +
             " population " + nf.format(root.population) ;
   }
   /*
    *   The data we use.
    */
   double generationCount = 0 ;
   TreeNode root = TreeNode.create() ;
   NumberFormat nf = NumberFormat.getIntegerInstance() ;
   {
      nf.setMaximumFractionDigits(0) ;
   }
}
