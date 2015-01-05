package partB;
/**
 * Changes performed to default binary search tree, since last version of the BST (part A)
 * 
 * 
 * - added private field insertAtRoot
 * - initialised insertAtRoot to false in the default BST constructor
 * - added another BST constructor that can change at creation how the
 *		entries will be inserted: at leaf or at root 
 * 
 * 
 * - renamed old method add to insertAtLeaf
 * - prepared empty method insertAtRoot
 * - added new method add that decides which type of insertion to perform
 * - added if statement in getEntry() to check if the root is null before
 * 		the rest of method and in case is true, will return null
 * 
 * 
 * - added method updateParent() that repairs the links between a node and
 * 		his children
 * - added methods rotateRight() and rotateLeft() to perform rotations that
 *		allow the newest inserted element to be at the root
 *
 *
 * - added insertAtRoot() method
 * 
 * 
 * - modified displayBreadthFirst:
 *		now looping while queue is not empty instead of looping from i to
 *		sizeWithNull;
 * 
 **/
import java.util.*;


public class BinarySearchTree<E> extends AbstractSet<E> 
{
    protected Entry<E> root;
    private boolean insertAtRoot;
    protected int size;        
    
    /**
     *  Initialises this BinarySearchTree object to be empty, to contain only elements
     *  of type E, to be ordered by the Comparable interface, and to contain no 
     *  duplicate elements.
     *
     */ 
    public BinarySearchTree() 
    {
        root = null;
        size = 0;  
        insertAtRoot = false;
    } // default constructor

    /**
     * This constructor will change the way the elements are added into 
     * the Binary Search Tree. If insertAtRoot is true, the most recent
     * inserted elements will be closer to the root
     * If insertAtRoot is false, it will have the same behaviour like
     * using the default constructor, and will insert using the same way
     * like it did in partA
     * @param insertAtRoot - toggle that decides if the newly declared tree
     * 		will insert elements at root or at leaf
     */
    public BinarySearchTree(boolean insertAtRoot)
	{
		root = null;
		size = 0;
		this.insertAtRoot = insertAtRoot;
	} // constructor that inserts at root if true

    /**
     * Initialises this BinarySearchTree object to contain a shallow copy of
     * a specified BinarySearchTree object.
     * The worstTime(n) is O(n), where n is the number of elements in the
     * specified BinarySearchTree object.
     *
     * @param otherTree - the specified BinarySearchTree object that this
     *                BinarySearchTree object will be assigned a shallow copy of.
     *
     */
    public BinarySearchTree (BinarySearchTree<? extends E> otherTree)
    {
         root = copy (otherTree.root, null);
         size = otherTree.size;  
    } // copy constructor


    protected Entry<E> copy (Entry<? extends E> p, Entry<E> parent)
    {
        if (p != null)
        {
            Entry<E> q = new Entry<E> (p.element, parent);
            q.left = copy (p.left, q);
            q.right = copy (p.right, q);
            return q;
        } // if
        return null;
    } // method copy
        
    public boolean equals (Object obj)
    {
        if (!(obj instanceof BinarySearchTree))
            return false;
        return equals (root, ((BinarySearchTree<? extends E>)obj).root);
    } // method 1-parameter equals
    
    public boolean equals (Entry<E> p, Entry<? extends E> q)
    {
       if (p == null || q == null)
           return p == q;      
       if (!p.element.equals (q.element))
           return false;
       if (equals (p.left, q.left) && equals (p.right, q.right) )
           return true;            
       return false;     
    } // method 2-parameter equals
    
    /**
     *  Returns the size of this BinarySearchTree object.
     *
     * @return the size of this BinarySearchTree object.
     *
     */
    public int size( )
    {
    	System.out.println("Current size of the tree is: "+ size);
        return size;
    } // method size()
    
    /**
	 * method that returns the level of the tree will return 0 if tree is empty
	 * needed for the TreePrinter class to display a viewable tree
	 */
	public int level(Entry<E> entry) {
		if (entry == null)
			return 0;
		else 
		{
			return 1 + Math.max(level(entry.left), level(entry.right));
		}
	} // method level()
  
	/**
	 * recursive method that returns the height of a (sub)tree 
	 * @param entry - is the (sub)tree that will have its height calculated
	 * @returns 0 if the node is external
	 */
	private int calculateHeight(Entry<E> entry) 
	{
		if (entry.isExternal()) 
		{
			return 0;
		}
		else 
		{
			return 1 + Math.max(calculateHeight(entry.left), calculateHeight(entry.right));
		}
	} // method calculateHeight()
	
	/**
	 * 
	 * @return 0 if the tree is empty or calls the calculateHeight method which
	 * returns the height of the tree
	 */
	public int height()
	{
		int result;
		if (root == null) 
		{
			result = 0;
		}
		else 
		{
			result = calculateHeight(root);
		}
		System.out.println("Height of the tree is: "+ result);
		return result;
	} // method height()
	
	/**
	 * method that displays the tree in breadth first order
	 * using the algorithm from chapter 9 Binary Trees page 392
	 * from the Data Structures and the Java Collections Framework book
	 * Algorithm:
	 * if tree is not empty)
	 * 		queue.enqueue(tree)
	 * 		while haven`t processed all nodes
	 * 			temporary tree = queue.dequeue
	 * 			process the root (store the element in the array)
	 * 			if left subtree ot temporary tree is not empty
	 * 				queue.enqueue(temporary`s left sub tree)
	 * 			endif
	 * 			if right subtree of temporary tree is not empty
	 * 				queue.enqueue(temporary`s right sub tree)
	 * 			endif
	 * 		end while
	 * 		display the array of elements
	 * 	else
	 * 		tree is empty
	 *  endif
	 *  			
	 */
	@SuppressWarnings("rawtypes")
	public void displayBreadthFirst() {
		Entry entry = root;
		if (entry != null) {
			int sizeWithNull = 2*size + 1;	// including null nodes
			Queue<Entry> queue = new LinkedList<Entry>();			
			String[] listOfElements = new String[size];		// array that holds only nodes with values
			String[] listOfElementsWithNull = new String[sizeWithNull]; 	// array including null nodes
			queue.add(entry);

			int i = 0;
			int j = 0;
			while (!queue.isEmpty()) {
				Entry temporary = queue.remove();
				if (temporary.element == null)
					listOfElementsWithNull[i] = "null";
				else {
					listOfElementsWithNull[i] = temporary.element.toString();
					listOfElements[j] =  temporary.element.toString();
					j++;
				}
				i++;
				

				if (temporary.left != null) {
					queue.add(temporary.left);
				}
				if (temporary.right != null) {
					queue.add(temporary.right);
				}
			}
			System.out.println("\nBreadth first display including null entries: ");
			for (int k = 0; k < sizeWithNull; k++)
			{
				System.out.print(listOfElementsWithNull[k] + "\t");
			}
			System.out.println("\nBreadth first display without null entries: ");
			for (int k = 0; k < size; k++)
			{
				System.out.print(listOfElements[k] + "\t");
			}
			System.out.println();
		} 
		
		else {
			System.out.println("Tree is empty...");
		}
	} // method displayBreadthFirst()
	
    /**
     *  Returns an iterator positioned at the smallest element in this 
     *  BinarySearchTree object.
     *
     *  @return an iterator positioned at the smallest element in this
     *                BinarySearchTree object.
     *
     */
    public Iterator<E> iterator()
    {
         return new TreeIterator();
    } // method iterator

    /**
     *  Determines if there is at least one element in this BinarySearchTree object that
     *  equals a specified element.
     *  The worstTime(n) is O(n) and averageTime(n) is O(log n).  
     *
     *  @param obj - the element sought in this BinarySearchTree object.
     *
     *  @return true - if there is an element in this BinarySearchTree object that
     *                equals obj; otherwise, return false.
     *
     *  @throws ClassCastException - if obj cannot be compared to the 
     *           elements in this BinarySearchTree object. 
     *  @throws NullPointerException - if obj is null.
     *  
     */ 
    public boolean contains (Object obj) 
    {
        return getEntry (obj) != null;
    } // method contains

    /**
     * This method eliminates the possibility to add duplicates and 
     * will return false when attempting to add an element that already exists
     * Once we are sure that the element can be added, we decide its 
     * destination: insertAtRoot or insertAtLeaf
     * @param element - the element to be checked for duplicates and to be added
     * 		if unique
     */
    public boolean add(E element) {
		if (contains(element))
		{
			return false;	// do not allow adding duplicates
		}
		if (insertAtRoot)
		{ 
			root = insertAtRoot(root,element);
			return true;
		}
		else 
		{
			return insertAtLeaf(element);
		}
	} // method add()
    
    /**
     * simple method that updates the parent of the
     * node`s children
     * @param node - node who`s children to adjust
     */
    private void updateParent(Entry<E> node)
	{
		node.left.parent = node;
		node.right.parent = node;
	} // method updateParent()
	
    /**
     * using the algorithm from the coursework specifications and 
     * the helper method updateParent() to perform a rightRotation
     * @param oldRoot - root that will become a child after rotation
     * @return the new root
     */
	private Entry<E> rotateRight(Entry<E> oldRoot) {
		Entry<E> newRoot = oldRoot.left;
		oldRoot.left = newRoot.right;
		newRoot.right = oldRoot;
		newRoot.parent = null;
		updateParent(newRoot);
		updateParent(oldRoot);
		oldRoot.parent = newRoot;
		
		return newRoot;
	} // method rotateRight()
	
	/**
     * using the algorithm from the coursework specifications and 
     * the helper method updateParent() to perform a leftRotation
     * @param oldRoot - root that will become a child after rotation
     * @return the new root
     */
	private Entry<E> rotateLeft(Entry<E> oldRoot) {
		Entry<E> newRoot = oldRoot.right;
		oldRoot.right = newRoot.left;
		newRoot.left = oldRoot;
		newRoot.parent = null;
		newRoot.left.parent = newRoot;
		oldRoot.parent = newRoot;
		
		return newRoot;
	} // method rotateLeft()
    
    /**
     * Adds a new elemenet at the root performing the right rotations
     * where necessary to maintain the most recent element at 
     * the BinarySearchTree`s root
     * 
     * Algorithm:
     * 	if the root is null 
     * 		add new internal node
     * 		return the node
     * 	// endif
     *  if the current node is an external node
     *  	add new internal node
     *  	return the node
     *  else
     *  	if the element is smaller than the root
     *  		perform insertAtRoot the element on the left child of the node
     *  		perform a right rotation around the resulted node
     *  		the result is the new root, return it
     *   	else (the element is greater than the root)
     *   		perform insertAtRoot the element on the right child of the node
     *  		perform a left rotation around the resulted node
     *  		the result is the new root, return it
     * 		// endif
     *  //endif
     * @param node - the entry where the element will be added
     * @param element - value to be inserted
     * @return the new ordered root (which is basically the new tree)
     */
    private Entry<E> insertAtRoot(Entry<E> node, E element)
    {
		if (node == null) 
		{
			if (element == null)
			{
				throw new NullPointerException();
			}
			node = new Entry<E>(element, null, true);
			size++;
			return node;
		} // empty tree
		if (node.isExternal()) 
		{
			node = new Entry<E>(element, null, true);
			size++;
			return node;
		} // used when calling insertAtRoot
		else 
		{
			int comp = ((Comparable) element).compareTo(node.element);
			
			if (comp < 0) 
			{
				node.left = insertAtRoot(node.left, element);
				node = rotateRight(node);
				return node;	
			}
			else 
			{
				node.right = insertAtRoot(node.right, element);
				node = rotateLeft(node);
				return node;	
			}
		}
	} // method insertAtRoot

    /**
     *  Ensures that this BinarySearchTree object contains a specified element.
     *  The worstTime(n) is O(n) and averageTime(n) is O(log n).
     *
     *  @param element - the element whose presence is ensured in this 
     *                 BinarySearchTree object.
     *
     *  @return true - if this BinarySearchTree object changed as a result of this
     *                method call (that is, if element was actually inserted); otherwise,
     *                return false.
     *
     *  @throws ClassCastException - if element cannot be compared to the 
     *                  elements already in this BinarySearchTree object.
     *  @throws NullPointerException - if element is null.
    
     * Algorithm:
	 * 	if the tree is empty
	 * 		create new node at the root
	 * 		increment size
	 *  else
	 *  	temp is the element to compare against (starting at the root)
	 *  	if the added element is equal to the root
	 *  		return unsuccessful insertion , element would be duplicate
	 *  	else
	 *  		if it`s less than the root
	 *  			if the left subtree is an external node
	 *  				create an internal node
	 *  				update size
	 *  				return successful insertion
	 *  			else
	 *  				go deeper to the left
	 *  			endif
	 *  		if it`s greater than the root
	 *  			if the right subtree is an external node
	 *  				create an internal node
	 *  				update size
	 *  				return successful insertion
	 *  			else
	 *  				go deeper to the right
	 *  			endif
	 *  		endif
	 *  	endif
	 *  endif
	 *  		
	 */	
	private boolean insertAtLeaf (E element)
	{
		if (root == null)
		{
			if (element == null)
				throw new NullPointerException();
			root = new Entry<E> (element, null, true);
			size++;
			return true;
		} // empty tree
		else
		{
			Entry <E> temp = root;
			
			int comp;
			
			while (true)
			{
				comp = ((Comparable)element).compareTo(temp.element);
				if (comp == 0) 
				{
					return false;
				}
				if (comp < 0)
				{
					if (temp.left.isExternal())
					{
						temp.left.makeInternal(element);;
						size++;
						return true;
					} // temp.left == null
					else
					{
						temp = temp.left;
					}
				}
				else
				{
					if (temp.right.isExternal()) 
					{
						temp.right.makeInternal(element);
						size++;
						return true;
					} // temp.right == null
					else
					{
						temp = temp.right;
					} 
				} 
			} // while
		} // root not null
	} // method insertAtLeaf()


    /**
     *  Ensures that this BinarySearchTree object does not contain a specified 
     *  element.
     *  The worstTime(n) is O(n) and averageTime(n) is O(log n).
     *
     *  @param obj - the object whose absence is ensured in this 
     *                 BinarySearchTree object.
     *
     *  @return true - if this BinarySearchTree object changed as a result of this
     *                method call (that is, if obj was actually removed); otherwise,
     *                return false.
     *
     *  @throws ClassCastException - if obj cannot be compared to the 
     *                  elements already in this BinarySearchTree object. 
     *  @throws NullPointerException - if obj is null.
     *
     */
    public boolean remove (Object obj)
    {
        Entry<E> e = getEntry (obj);
        if (e == null)
            return false;
        deleteEntry (e);       
        return true;
    } // method remove


    /**
     *  Finds the Entry object that houses a specified element, if there is such an Entry.
     *  The worstTime(n) is O(n), and averageTime(n) is O(log n).
     *
     *  @param obj - the element whose Entry is sought.
     *
     *  @return the Entry object that houses obj - if there is such an Entry;
     *                otherwise, return null.  
     *
     *  @throws ClassCastException - if obj is not comparable to the elements
     *                  already in this BinarySearchTree object.
     *  @throws NullPointerException - if obj is null.
     *
     */
    protected Entry<E> getEntry (Object obj) 
    {
        int comp;

        if (obj == null)
           throw new NullPointerException();
        if (root == null)
        	return null;
        Entry<E> e = root;
        while (!e.isExternal()) 
        {
            comp = ((Comparable)obj).compareTo (e.element);
            if (comp == 0)
                return e;
            else if (comp < 0)
                e = e.left;
            else
                e = e.right;
        } // while
        return null;
    } // method getEntry
    
  

     /**
      *  Deletes the element in a specified Entry object from this BinarySearchTree.
      *  
      *  @param p – the Entry object whose element is to be deleted from this
      *                 BinarySearchTree object.
      *
      *  @return the Entry object that was actually deleted from this BinarySearchTree
      *                object. 
      *
      */
    protected Entry<E> deleteEntry (Entry<E> p) 
    {
        size--;

        // If p has two children, replace p's element with p's successor's
        // element, then make p reference that successor.
        if (p.left != null && p.right != null) 
        {
            Entry<E> s = successor (p);
            p.element = s.element;
            p = s;
        } // p had two children


        // At this point, p has either no children or one child.

        Entry<E> replacement;
         
        if (p.left != null)
            replacement = p.left;
        else
            replacement = p.right;

        // If p has at least one child, link replacement to p.parent.
        if (replacement != null) 
        {
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left  = replacement;
            else
                p.parent.right = replacement;
        } // p has at least one child  
        else if (p.parent == null)
            root = null;
        else 
        {
            if (p == p.parent.left)
                p.parent.left = null;
            else
                p.parent.right = null;        
        } // p has a parent but no children
        return p;
    } // method deleteEntry


    /**
     *  Finds the successor of a specified Entry object in this BinarySearchTree.
     *  The worstTime(n) is O(n) and averageTime(n) is constant.
     *
     *  @param e - the Entry object whose successor is to be found.
     *
     *  @return the successor of e, if e has a successor; otherwise, return null.
     *
     */
    protected Entry<E> successor (Entry<E> e) 
    {
        if (e == null || e.isExternal())
            return null;
        else if (e.right.element != null) 
        {
            // successor is leftmost Entry in right subtree of e
            Entry<E> p = e.right;
            while (p.left.element != null)
                p = p.left;
            return p;

        } // e has a right child
        else 
        {

            // go up the tree to the left as far as possible, then go up
            // to the right.
            Entry<E> p = e.parent;
            Entry<E> ch = e;
            while (p != null && ch == p.right) 
            {
                ch = p;
                p = p.parent;
            } // while
            return p;
        } // e has no right child
    } // method successor
    
    protected class TreeIterator implements Iterator<E>
    {

        protected Entry<E> lastReturned = null,
                           next;               

        /**
         *  Positions this TreeIterator to the smallest element, according to the Comparable
         *  interface, in the BinarySearchTree object.
         *  The worstTime(n) is O(n) and averageTime(n) is O(log n).
         *
         */
        protected TreeIterator() 
        {             
            next = root;
            if (next != null)
                while (!next.left.isExternal())
                    next = next.left;
        } // default constructor


        /**
         *  Determines if there are still some elements, in the BinarySearchTree object this
         *  TreeIterator object is iterating over, that have not been accessed by this
         *  TreeIterator object.
         *
         *  @return true - if there are still some elements that have not been accessed by
         *                this TreeIterator object; otherwise, return false.
         *
         */ 
        public boolean hasNext() 
        {
            return next != null;
        } // method hasNext


        /**
         *  Returns the element in the Entry this TreeIterator object was positioned at 
         *  before this call, and advances this TreeIterator object.
         *  The worstTime(n) is O(n) and averageTime(n) is constant.
         *
         *  @return the element this TreeIterator object was positioned at before this call.
         *
         *  @throws NoSuchElementException - if this TreeIterator object was not 
         *                 positioned at an Entry before this call.
         *
         */
        public E next() 
        {
            if (next == null)
                throw new NoSuchElementException();
            lastReturned = next;
            next = successor (next);             
            return lastReturned.element;
        } // method next

        /**
         *  Removes the element returned by the most recent call to this TreeIterator
         *  object’s next() method.
         *  The worstTime(n) is O(n) and averageTime(n) is constant.
         *
         *  @throws IllegalStateException - if this TreeIterator’s next() method was not
         *                called before this call, or if this TreeIterator’s remove() method was
         *                called between the call to the next() method and this call.
         *
         */ 
        public void remove() 
        {
            if (lastReturned == null)
                throw new IllegalStateException();
 
            if (lastReturned.left != null && lastReturned.right != null)
                next = lastReturned;
            deleteEntry(lastReturned);
            lastReturned = null; 
        } // method remove     

    } // class TreeIterator

    protected static class Entry<E> 
    {
        protected E element;

        protected Entry<E> left = null,
                           right = null,
                           parent;
 
        /**
         *  Initializes this Entry object.
         *
         *  This default constructor is defined for the sake of subclasses of
         *  the BinarySearchTree class. 
         */
        public Entry() { }


        /**
         *  Initializes this Entry object from element and parent.
         *
         */ 
         public Entry (E element, Entry<E> parent) 
         {
             this.element = element;
             this.parent = parent;
         } // constructor
         
         /**
          * Initializes this Entry object and specifies if it is internal
          * @param element - value of the element
          * @param parent - updates link to parent
          * @param shouldBeInternal - will decide if node is internal or not
          */
     	public Entry(E element, Entry<E> parent, boolean shouldBeInternal) 
     	{
     		this.parent = parent;
     		
     		if (shouldBeInternal)
     		{
     			makeInternal(element);
     		}
     		else
     		{
     			makeExternal();
     		}
     	} // constructor that can create a specific node (internal or external)

     	/**
         * method to check if the node is external or not
         * a node is external if the element and left and right 
         * children are null
         * @return true if is external
         */
     	public boolean isExternal()
     	{
     		boolean leftLinkIsNull 	= (this.left == null);
     		boolean rightLinkIsNull = (this.right == null);
     		boolean elementIsNull 	= (this.element == null);
     		boolean isExternal = (leftLinkIsNull && rightLinkIsNull && elementIsNull);
     		
     		return isExternal;
     	} // method isExternal()
     	
     	/**
         * converts this internal node to an external node and returns the element 
         * that the internal node contained (used when deleting an internal node
         * that has no other internal nodes as children)  
         * @return the contained element before conversion
         */
        public E makeExternal() 
        {
        	E value = this.element;
     		this.left = null;
     		this.right = null;
     		this.element = null;
     		return value;
     	} // method makeExternal()
     	
        /**
         * converts this external node to an internal node containing the given 
         * element and adds two new external nodes as the left and right children of
         * the node (used when inserting an element into the tree)
         * an internal node does not have any null fields
         * so the element cannot be null, and neither the children
         * @param element is the element to be added
         */
        public void makeInternal(E element) 
        {
    		this.element = element;
    		this.left = new Entry<E>(null, this);
    		this.right = new Entry<E>(null, this);
    	} // method makeInternal()
        
    } // class Entry

} // class BinarySearchTree

