/**
 *   MemoizedTreeNode extends CanonicalTreeNode by adding a member
 *   to hold the cached result node, and modifying the nextGeneration
 *   function to check this new field for a cached value.
 */
public class MemoizedTreeNode extends CanonicalTreeNode {
   /**
    *   This is the only change; we make nextGeneration use the result
    *   cache.
    */
   TreeNode nextGeneration() {
      if (result == null)
         result = super.nextGeneration() ;
      return result ;
   }
   /**
    *   Here is the result cache itself.
    */
   TreeNode result = null ;
   /**
    *   Provide constructors.  Everything below here just supports the
    *   factory method of construction.
    */
   MemoizedTreeNode(boolean alive) {
      super(alive) ;
   }
   MemoizedTreeNode(TreeNode nw, TreeNode ne, TreeNode sw, TreeNode se) {
      super(nw, ne, sw, se) ;
   }
   /**
    *   We override the three create functions.
    */
   TreeNode create(boolean living) {
      return new MemoizedTreeNode(living).intern() ;
   }
   TreeNode create(TreeNode nw, TreeNode ne, TreeNode sw, TreeNode se) {
      return new MemoizedTreeNode(nw, ne, sw, se).intern() ;
   }
   static TreeNode create() {
      return new MemoizedTreeNode(false).emptyTree(3) ;
   }
}
