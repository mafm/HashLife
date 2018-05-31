/**
 *   This class contains the actual Life next generation function for
 *   a TreeNode structure.  TreeNodeBase is extended only so
 *   these generation functions can be split from the maintenance
 *   functions.
 */
public class TreeNode extends TreeNodeBase {
   /**
    *   Given an integer with a bitmask indicating which bits are
    *   set in the neighborhood, calculate whether this cell is
    *   alive or dead in the next generation.  The bottom three
    *   bits are the south neighbors; bits 4..6 are the current
    *   row with bit 5 being the cell itself, and bits 8..10
    *   are the north neighbors.
    */
   TreeNode oneGen(int bitmask) {
      if (bitmask == 0)
         return create(false) ;
      int self = (bitmask >> 5) & 1 ;
      bitmask &= 0x757 ; // mask out bits we don't care about
      int neighborCount = 0 ;
      while (bitmask != 0) {
         neighborCount++ ;
         bitmask &= bitmask - 1 ; // clear least significant bit
      }
      if (neighborCount == 3 || (neighborCount == 2 && self != 0))
         return create(true) ;
      else
         return create(false) ;
   }
   /**
    *   At level 2, we can use slow simulation to compute the next
    *   generation.  We use bitmask tricks.
    */
   TreeNode slowSimulation() {
      int allbits = 0 ;
      for (int y=-2; y<2; y++)
         for (int x=-2; x<2; x++)
            allbits = (allbits << 1) + getBit(x, y) ;
      return create(oneGen(allbits>>5), oneGen(allbits>>4),
                    oneGen(allbits>>1), oneGen(allbits)) ;
   }
   /**
    *   Return a new node one level down containing only the
    *   center elements.
    */
   TreeNode centeredSubnode() {
      return create(nw.se, ne.sw, sw.ne, se.nw) ;
   }
   /**
    *   Return a new node one level down from two given nodes
    *   that contains the east centered two sub sub nodes from
    *   the west node and the west centered two sub sub nodes
    *   from the east node.
    */
   TreeNode centeredHorizontal(TreeNode w, TreeNode e) {
      return create(w.ne.se, e.nw.sw, w.se.ne, e.sw.nw) ;
   }
   /**
    *   Similar, but this does it north/south instead of east/west.
    */
   TreeNode centeredVertical(TreeNode n, TreeNode s) {
      return create(n.sw.se, n.se.sw, s.nw.ne, s.ne.nw) ;
   }
   /**
    *   Return a new node two levels down containing only the
    *   centered elements.
    */
   TreeNode centeredSubSubnode() {
      return create(nw.se.se, ne.sw.sw, sw.ne.ne, se.nw.nw) ;
   }
   /**
    *   The recursive call that computes the next generation.  It works
    *   by constructing nine subnodes that are each a quarter the size
    *   of the current node in each dimension, and combining these in
    *   groups of four, building subnodes from these, and then
    *   recursively invoking the nextGeneration function and combining
    *   those final results into a single return value that is one
    *   half the size of the current node and advanced one generation in
    *   time.
    */
   TreeNode nextGeneration() {
      // skip empty regions quickly
      if (population == 0)
         return nw ;
      if (level == 2)
         return slowSimulation() ;
      TreeNode n00 = nw.centeredSubnode(),
               n01 = centeredHorizontal(nw, ne),
               n02 = ne.centeredSubnode(),
               n10 = centeredVertical(nw, sw),
               n11 = centeredSubSubnode(),
               n12 = centeredVertical(ne, se),
               n20 = sw.centeredSubnode(),
               n21 = centeredHorizontal(sw, se),
               n22 = se.centeredSubnode() ;
      return create(create(n00, n01, n10, n11).nextGeneration(),
                    create(n01, n02, n11, n12).nextGeneration(),
                    create(n10, n11, n20, n21).nextGeneration(),
                    create(n11, n12, n21, n22).nextGeneration()) ;
   }
   /**
    *   We need to pass construction down to the base class.
    */
   TreeNode(boolean living) {
      super(living) ;
   }
   /**
    *   Construct a node given four children.
    */
   TreeNode(TreeNode nw, TreeNode ne, TreeNode sw, TreeNode se) {
      super(nw, ne, sw, se) ;
   }
}
