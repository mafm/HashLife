/**
 *   This class contains the tree maintenance functions for quadtrees.
 */
public class TreeNodeBase {
   /**
    *   Construct a leaf cell.
    */
   TreeNodeBase(boolean living) {
      nw = ne = sw = se = null ;
      level = 0 ;
      alive = living ;
      population = alive ? 1 : 0 ;
   }
   /**
    *   Construct a node given four children.
    */
   TreeNodeBase(TreeNode nw_, TreeNode ne_, TreeNode sw_, TreeNode se_) {
      nw = nw_ ;
      ne = ne_ ;
      sw = sw_ ;
      se = se_ ;
      level = nw_.level + 1 ;
      population = nw.population + ne.population +
                   sw.population + se.population ;
      alive = population > 0 ;
   }
   /**
    *   Factory methods to allow us to "override" the constructors.
    *   These two calls are the only places that the constructors
    *   should ever be called.  The first two are nonstatic member
    *   functions only so they can be overriden; they do not actually use
    *   the self class at all.  The third provides a mechanism for us
    *   to bootstrap the root.
    */
   TreeNode create(boolean living) {
      return new TreeNode(living) ;
   }
   TreeNode create(TreeNode nw, TreeNode ne, TreeNode sw, TreeNode se) {
      return new TreeNode(nw, ne, sw, se) ;
   }
   static TreeNode create() {
      return new TreeNode(false).emptyTree(3) ;
   }
   /**
    *   Set a bit in this node in its relative coordinate system;
    *   returns a whole new node since our nodes are immutable.
    *
    *   In the recursive call, we simply adjust the coordinate system
    *   and call down a level.
    */
   TreeNode setBit(int x, int y) {
      if (level == 0)
         return new TreeNode(true) ;
      // distance from center of this node to center of subnode is
      // one fourth the size of this node.
      int offset = 1 << (level - 2) ;
      if (x < 0)
         if (y < 0)
            return create(nw.setBit(x+offset, y+offset), ne, sw, se) ;
         else
            return create(nw, ne, sw.setBit(x+offset, y-offset), se) ;
      else
         if (y < 0)
            return create(nw, ne.setBit(x-offset, y+offset), sw, se) ;
         else
            return create(nw, ne, sw, se.setBit(x-offset, y-offset)) ;
   }
   /**
    *   If we ever really need to get a bit one at a time, we can
    *   use this subroutine.  For convenience it returns 0/1 rather
    *   than false/true.
    */
   int getBit(int x, int y) {
      if (level == 0)
         return alive ? 1 : 0 ;
      int offset = 1 << (level - 2) ;
      if (x < 0)
         if (y < 0)
            return nw.getBit(x+offset, y+offset) ;
         else
            return sw.getBit(x+offset, y-offset) ;
      else
         if (y < 0)
            return ne.getBit(x-offset, y+offset) ;
         else
            return se.getBit(x-offset, y-offset) ;
   }
   /**
    *   Build an empty tree at the given level.
    */
   TreeNode emptyTree(int lev) {
      if (lev == 0)
         return create(false) ;
      TreeNode n = emptyTree(lev-1) ;
      return create(n, n, n, n) ;
   }
   /**
    *   Expand the universe; return a new node up one level with the
    *   current node in the center.  Requires us to disassemble the
    *   current node.
    */
   TreeNode expandUniverse() {
      TreeNode border = emptyTree(level-1) ;
      return create(create(border, border, border, nw),
                    create(border, border, ne, border),
                    create(border, sw, border, border),
                    create(se, border, border, border)) ;
   }
   /*
    *   Our data; the class is immutable so all of these are final.
    */
   final TreeNode nw, ne, sw, se ; // our children
   final int level ;           // distance to root
   final boolean alive ;       // if leaf node, are we alive or dead?
   final double population ;   // we cache the population here
}
