/**
 *   This class contains the HashLife code that makes the recursive
 *   function take huge steps, rather than computing a single
 *   generation at a time.
 */
public class HashLifeTreeNode extends MemoizedTreeNode {
   /**
    *   HorizontalForward() takes two horizontally adjacent nodes,
    *   builds a new node from the east half of the west node and
    *   the west half of the east node, and computes the next
    *   step for that new node.
    */
   TreeNode horizontalForward(TreeNode w, TreeNode e) {
      return create(w.ne, e.nw, w.se, e.sw).nextGeneration() ;
   }
   /**
    *   VerticalForward() takes two vertically adjacent nodes,
    *   builds a new node from the south half of the north node
    *   and the north half of the south node, and computes the
    *   next step for that node.
    */
   TreeNode verticalForward(TreeNode n, TreeNode s) {
      return create(n.sw, n.se, s.nw, s.ne).nextGeneration() ;
   }
   /**
    *   CenterForward() builds a new subnode that is half the
    *   size of the this node out of the center portions.  It
    *   then does a generation step and returns that result.
    */
   TreeNode centerForward() {
      return create(nw.se, ne.sw, sw.ne, se.nw).nextGeneration() ;
   }
   /**
    *   NextGeneration() is the core HashLife recursive algorithm.
    *   It builds 9 subnodes that are one quarter the size of the
    *   current node and advanced in time one eighth the size of
    *   the current node.  It then takes these 9 subnodes in groups
    *   of four and builds 4 subnodes, each one quarter the size
    *   of the current node and advanced in time one fourth the size
    *   of the current node.  It combines these four subnodes into
    *   a new node and returns that as its result.
    */
   TreeNode nextGeneration() {
      if (result != null)
         return result ;
      if (population == 0)
         return result = nw ;
      if (level == 2)
         return result = slowSimulation() ;
      TreeNode n00 = nw.nextGeneration(),
           n01 = horizontalForward(nw, ne),
           n02 = ne.nextGeneration(),
           n10 = verticalForward(nw, sw),
           n11 = centerForward(),
           n12 = verticalForward(ne, se),
           n20 = sw.nextGeneration(),
           n21 = horizontalForward(sw, se),
           n22 = se.nextGeneration() ;
      return result = create(create(n00, n01, n10, n11).nextGeneration(),
                             create(n01, n02, n11, n12).nextGeneration(),
                             create(n10, n11, n20, n21).nextGeneration(),
                             create(n11, n12, n21, n22).nextGeneration()) ;
   }
   /**
    *   Provide constructors to support the factory interface.
    */
   HashLifeTreeNode(boolean alive) {
      super(alive) ;
   }
   HashLifeTreeNode(TreeNode nw, TreeNode ne, TreeNode sw, TreeNode se) {
      super(nw, ne, sw, se) ;
   }
   /**
    *   We override the three create functions.
    */
   TreeNode create(boolean living) {
      return new HashLifeTreeNode(living).intern() ;
   }
   TreeNode create(TreeNode nw, TreeNode ne, TreeNode sw, TreeNode se) {
      return new HashLifeTreeNode(nw, ne, sw, se).intern() ;
   }
   static TreeNode create() {
      return new HashLifeTreeNode(false).emptyTree(3) ;
   }
}
