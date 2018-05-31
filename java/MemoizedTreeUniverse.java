/**
 *   This Universe uses a memoized canonicalized quadtree datastructure
 *   to hold our cells.  The only difference is how we initialize root.
 */
public class MemoizedTreeUniverse extends TreeUniverse {
   {
      root = MemoizedTreeNode.create() ;
   }
}
