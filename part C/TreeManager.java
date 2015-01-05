package partC;

import java.util.AbstractSet;

public class TreeManager {
	
	public BinarySearchTree<Item> toLeaf = new BinarySearchTree<Item>();		// tree that inserts at leaf 
	public BinarySearchTree<Item> toRoot = new BinarySearchTree<Item>(true);	// tree that inserts at root
	public java.util.TreeSet<Item>toTree = new java.util.TreeSet<Item>();		// tree that is a tree set
	
	// defines the percentage of last inserted values considered rencent
	private int recentPercentage = 20;	
	// defines the size of the array needed to store the most recent items
	private int recentSize;
	// holds the value of the loop counter from which the generated numbers will be stored in the array 
	private int lowerLimit;
	// will define the interval for the generated numbers from 0 to this value
	private long randomRange = 1000;	
	
	public Item[] List;
	
	/**
	 * this method will generate a new item
	 * @return
	 */
	private Item generate() 
	{
		Integer value = (int) (Math.random() * randomRange);
		return new Item(value);
	} // method generate()
	
	/**
	 * this method will be used to generate an integer
	 * between 0 and the upperlimit
	 * @param upperLimit - maximum random range
	 * @return the generated number
	 */
	protected int generateID(int upperLimit)
	{
		return (int) (Math.random() * upperLimit);
	} // method generateID
	
	
	/**
	 * This method is used to initialise the trees
	 * with the same values based on an inputed size
	 * Also this method stores an array of the most recent 
	 * recentPercentage inserted Items
	 * @param size - the number of elements each tree will have
	 */
	public void buildTrees(int size)
	{
		recentSize = size * recentPercentage / 100;
		lowerLimit = size - recentSize;
		Item[] recentList = new Item[recentSize];
		
		Item temp = new Item(0);
		
		long start = System.nanoTime();
		for (int i = 0; i < size; i++)
		{
			
			temp = generate();
			toLeaf.add(temp);
			toRoot.add(temp);
			toTree.add(temp);
			
			if (i>lowerLimit)
				recentList[size-i-1] = temp;
		}
		System.out.println("Created 3 trees of size "+size+" in "+(System.nanoTime()-start)+" nanoseconds \n");
		List = recentList;
	} // method buildTrees()

	// class needed for stats
	public class Comparisons
	{
		int minimumToFail;
		int maximumToFail;
		int averageToFail;
		int numberOfFailed;
		
		int minimumToFind;
		int maximumToFind;
		int averageToFind;
		int numberOfFound;
		
		public Comparisons() 
		{
			minimumToFail = 999999;
			maximumToFail = 0;
			averageToFail = 0;
			numberOfFailed = 0;
			
			minimumToFind = 999999;
			maximumToFind = 0;
			averageToFind = 0;
			numberOfFound = 0;
		}
	} // class Comparisons
	
	private Comparisons Leaf = new Comparisons(), Root = new Comparisons(), Tree = new Comparisons();
	
	/**
	 * generic function that takes a type of tree as a parameter,
	 * searches the item x in it and fill in the stats
	 * @param tree - the data structure to search
	 * @param x - the value to find
	 * @param stats - the record of the results
	 */
	private <T> void searchInTree(AbstractSet<T> tree, Item x, Comparisons stats) 
	{
		x.resetCompCount();
		boolean found = tree.contains(x);
		int temp = (int) x.getCompCount();
		if (found)
		{
			stats.numberOfFound++;
			stats.averageToFind += temp;
			if (temp < stats.minimumToFind) stats.minimumToFind = temp;
			if (temp > stats.maximumToFind) stats.maximumToFind = temp;
		}
		else
		{
			stats.numberOfFailed++;
			stats.averageToFail += temp;
			if (temp < stats.minimumToFail) stats.minimumToFail = temp;
			if (temp > stats.maximumToFail) stats.maximumToFail = temp;
		}
	} // method searchInTree()
	
	
	public void performSearches(int numberOfSearches)
	{
		Item temp = new Item(0);
		temp.resetCompCount();
		long start = System.nanoTime();
		int successful = 0;
		
		for (int i = 0; i < numberOfSearches; i++)
		{
			temp = generate();
			
			searchInTree(toLeaf, temp, Leaf);
			searchInTree(toRoot, temp, Root);
			searchInTree(toTree, temp, Tree);
			
			successful = Leaf.numberOfFound;
		}
		System.out.println("Performed "+numberOfSearches+" in "+(System.nanoTime()-start)+" nanoseconds \n");
		System.out.println("From "+numberOfSearches+" generated Items, only "+successful+" were found in the tree, which is about "+String.format("%.2f",((double)successful/(double)numberOfSearches)*100)+"% success rate");
		System.out.println("To find these "+successful+" Items, the trees needed the following number of comparisons: ");
		System.out.println("toLeaf: \taverage: "+Leaf.averageToFind/successful+"\t total: "+Leaf.averageToFind+"\tfastest: "+Leaf.minimumToFind+"\tslowest: "+Leaf.maximumToFind);
		System.out.println("toRoot: \taverage: "+Root.averageToFind/successful+"\t total: "+Root.averageToFind+"\tfastest: "+Root.minimumToFind+"\tslowest: "+Root.maximumToFind);
		System.out.println("toTree: \taverage: "+Tree.averageToFind/successful+"\t total: "+Tree.averageToFind+"\tfastest: "+Tree.minimumToFind+"\tslowest: "+Tree.maximumToFind);
		System.out.println("");
		System.out.println("To perform the other "+(numberOfSearches - successful)+" searches, the trees needed the following number of comparisons: ");
		System.out.println("toLeaf: \taverage: "+Leaf.averageToFail/(numberOfSearches - successful)+"\t total: "+Leaf.averageToFail+"\tfastest: "+Leaf.minimumToFail+"\tslowest: "+Leaf.maximumToFail);
		System.out.println("toRoot: \taverage: "+Root.averageToFail/(numberOfSearches - successful)+"\t total: "+Root.averageToFail+"\tfastest: "+Root.minimumToFail+"\tslowest: "+Root.maximumToFail);
		System.out.println("toTree: \taverage: "+Tree.averageToFail/(numberOfSearches - successful)+"\t total: "+Tree.averageToFail+"\tfastest: "+Tree.minimumToFail+"\tslowest: "+Tree.maximumToFail);
		
	} // method performSearches
}
