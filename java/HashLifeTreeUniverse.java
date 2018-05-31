/**
 *   This Universe uses the full HashLife algorithm.
 *   Since this algorithm takes large steps, we have to
 *   change how we increment the generation counter, as
 *   well as how we construct the root object.
 */
public class HashLifeTreeUniverse extends TreeUniverse {
   public void runStep() {
      while (root.level < 3 ||
             root.nw.population != root.nw.se.se.population ||
             root.ne.population != root.ne.sw.sw.population ||
             root.sw.population != root.sw.ne.ne.population ||
             root.se.population != root.se.nw.nw.population)
         root = root.expandUniverse() ;
      double stepSize = Math.pow(2.0, root.level-2) ;
      System.out.println("Taking a step of " + nf.format(stepSize)) ;
      root = root.nextGeneration() ;
      generationCount += stepSize ;
   }
   {
      root = HashLifeTreeNode.create() ;
   }
}
