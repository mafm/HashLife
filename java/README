The Java implementation.

To compile:

   javac *.java

To run:

   java Driver TreeUniverse < <pattern>.rle

   java Driver CanonicalTreeUniverse < <pattern>.rle

   java Driver MemoizedTreeUniverse < <pattern>.rle

   java Driver HashLifeTreeUniverse < <pattern>.rle

You may need to add after the 'java' command a parameter such as

   -mx256m

to allow it to use 256MB of memory, for instance; all four of these
programs can be very memory hungry.

As the program runs it will print out the generation counts and
the population.  It will not display the actual generations
themselves; you can add that functionality.

The classes are as follows:

   UniverseInterface is an interface that all the other Universe
      classes implements; it contains just three basic functions.

   Driver contains the code to instantiate a Universe of a
      particular type, read in and parse a life form in the RLE
      format, and run that pattern using the given Universe
      object printing out statistics as it goes.

   TreeNodeBase contains the code to support a quadtree structure
      representation of a Life universe.  It also includes the
      basic factory mechanism used for nodes to generate other
      nodes of the same type.

   TreeNode contains the basic Life code to run a single generation
      on a TreeNode structure.

   TreeNodeUniverse glues a TreeNode to the Universe interface.

   CanonicalTreeNode extends TreeNode to support the canonicalization
      (sharing) of TreeNode instances, saving a lot of memory.

   CanonicalTreeNodeUniverse glues a CanonicalTreeNode to the
      Universe interface.

   MemoizedTreeNode extends CanonicalTreeNode to support the caching
      of the results of the life calculation.

   MemoizedTreeNodeUniverse glues a MemoizedTreeNode to the
      Universe interface.

   HashLifeTreeNode extends MemoizedTreeNode to take larger steps
      at a time, and thus complete the HashLife algorithm.

   HashLifeTreeNodeUniverse glues a HashLifeTreeNode to the
      Universe interface.

Note that this implementation is not production quality; it does not
garbage collection the memoized hash; it uses doubles rather than
bigints for generation and population counts; it does not allow you
to control the step size; it uses an unnecessarily large Node structure;
it only reads basic RLE formats; it has no display or other controls;
it is not particularly fast.  For a more comprehensive implementation
(in C), see

   http://tomas.rokicki.com/hashlife/

Suggestions on things to explore:

   * The "slowGeneration" routine is very, very slow.  How can you
     speed this up?  Is the use of leaf nodes all the way down to
     the bits the right design?

   * How small can you make the Node structure?  What fields can be
     removed?  Is there a way to eliminate the per-object overhead?

   * Try using WeakHashMap instead of HashMap in CanonicalTreeNode.
     Does this behave as you expect?  How can you control the
     garbage collector's aggressive removal of the objects?

   * Combine the generation procedure in TreeNode and HashLifeTreeNode
     so you can control the step size in HashLife.  What do you need
     to do when you might want to change that step size for a given
     pattern?

   * Can you build a UI of any sort for this program?  Is there a
     reasonable way to explore life Universes that can have
     trillions of living cells?
