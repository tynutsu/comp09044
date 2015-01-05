package partA;
/**
 * Changes performed to default binary search tree, have been made in different steps
 * Some steps might modify a previous change to update the functionality of the methods
 * 
 * 
 * - added new constructor for Entry that creates a specific type of node
 * 		(internal or external)
 * - added method isExternal that checks if a node is external or not
 * - added method makeExternal() converts current node into an external node
 * - added method makeInternal(E element) converts current node into an internal node
 * 
 * 
 * - added level() to be used with an imported class (TreePrinter) that
 *		prints the diagram of the tree. It requires the level of the tree to
 *		perform a decent representation of the binary search tree
 * 
 * 
 * - method size() now displays the size of the tree at every call
 * - added recursive method calculateHeight() that returns the height of
 *		the tree passed in as parameter
 * - added method height that displays the height of the tree at every call
 * 		or 0 if the tree is empty
 * 
 * 
 * - added displayBreadthFirst() method that shows all elements of the tree
 * 		top to bottom
 * 
 * 
 * -modified add() method:
 * 		when the root is empty, after the insertion becomes internal by creating
 *		an entry with empty nodes for the left/right children
 * 		in the while loop, now we are checking if the each of the children is an
 *		external node, and makes it internal instead of checking if they are
 * 		null
 * - modified displayBreadthFirst():
 * 		= added an int that holds the total size of the tree including the null
 *		external nodes
 * 		= added another string that holds the array of elements including the null
 *		elements (listOfElementsWithNull)
 *		= now the while loop goes from 0 to the total number of nodes instead of
 *		going from 0 to the size of the tree (given by the number of internal
 *		nodes)
 *		= added an if statement that, when the element is null adds "null" string
 * 		in the List of elements with null
 *		= added a for loop to display the listOfElementsWithoutNull too
 *
 *
 * - modified getEntry() method:
 *		now checking if e is not external instead of checking if is null
 *
 *
 * - modified successor() method
 *		instead of checking if the right child of the entry is null, to move in
 *		the right subtree, we check if the right child`s element is null
 * 		also to avoid going too much on the left and ending in an external node,
 * 		we now check if the left child`s element is not null instead of checking
 *		if the child is null.
 *
 *
 * - modified successor()
 *		now we also return null when e is external node;
 * - modified TreeIterator`s default constructor:
 *		now the loop goes to the left until the node is external instead of
 *		going until the node is null
 *
 * 
 * -modified calculateHeight()
 *		the method should return 0 when the entry is external instead of
 *		returning 0 when null; this prevents the recursive method going too deep
 */
import java.util.*;

public class BinarySearchTree<E> extends AbstractSet<E> 
{
    protected Entry<E> root;

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
    } // default constructor


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
			while (i < sizeWithNull) {
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
	public boolean add (E element)
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
	} // method add


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

