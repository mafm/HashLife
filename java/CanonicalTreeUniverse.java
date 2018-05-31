/**
 *   This Universe uses a canonicalized quadtree datastructure to
 *   hold our cells.  The only difference is how we initialize root.
 */
public class CanonicalTreeUniverse extends TreeUniverse {
   {
      root = CanonicalTreeNode.create() ;
   }
}
