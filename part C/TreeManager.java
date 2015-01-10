package partC;

import java.util.AbstractSet;

public class TreeManager {
	
	public BinarySearchTree<Item> toLeaf = new BinarySearchTree<Item>();		// tree that inserts at leaf 
	public BinarySearchTree<Item> toRoot = new BinarySearchTree<Item>(true);	// tree that inserts at root
	public java.util.TreeSet<Item>toTree = new java.util.TreeSet<Item>();		// tree that is a tree set
	
	// defines the percentage of last inserted values considered recent
	private int recentPercentage = 10;	
	// defines the size of the array needed to store the most recent items
	private int recentSize;
	// holds the value of the loop counter from which the generated numbers will be stored in the array 
	private int lowerLimit;
	// will define the interval for the generated numbers from 0 to this value
	private long randomRange = 5000;	
	// will hold a list with last 10% (recentPercentage) added Items
	public Item[] List;
	
	/*
	 * method to update the random range
	 */
	public void setRandomRange(long newRange) 
	{
		randomRange = newRange;
	}
	
	
	/**
	 * this method will generate a new item
	 * @return the generated item
	 */
	private Item generate() 
	{
		Integer value = (int) (Math.random() * randomRange);
		return new Item(value);
	} // method generate()
	
	
	/**
	 * This method is used to initialise the trees
	 * with the same values based on an inputed size
	 * Also this method stores an array of the most recent 
	 * 10% (recentPercentage) inserted Items
	 * @param size - the number of elements each tree will have
	 */
	public void buildTrees(int size)
	{
		recentSize = size * recentPercentage / 100;
		lowerLimit = size - 1 - recentSize;
		Item[] recentList = new Item[recentSize];
	
		/**
		 * new tree on every build
		 */
		toLeaf = new BinarySearchTree<Item>();		// tree that inserts at leaf 
		toRoot = new BinarySearchTree<Item>(true);	// tree that inserts at root
		toTree = new java.util.TreeSet<Item>();		// tree that is a tree set
		
		
		Item temp = new Item(0);
		
		long start = System.nanoTime();
		for (int i = 0; i < size; i++)
		{
			
			temp = generate();
			boolean exists = toTree.contains(temp);
			while(exists)
			{
				temp = generate();
				exists = toTree.contains(temp);
			}
			toLeaf.add(temp);
			toRoot.add(temp);
			toTree.add(temp);
			
			if (i>lowerLimit)
				recentList[size-i-1] = temp;
		}
		List = recentList;
	} // method buildTrees()

	// class needed for stats
	public class Stats
	{
		int c_m_min;				// minimum comparisons on a failed search
		int c_m_max;				// maximum comparisons on a failed search
		int c_m_average;			// average comparisons on a failed search
		int c_m_total; 				// total comparisons on failed searches
		int s_m_total;				// total failed searches
		long time_m_min;			// fastest missed search
		long time_m_max;			// slowest missed search
		long time_m_total;			// total time wasted on missed search
		
		int c_h_min; 				// minimum comparisons on a hit (successful search)
		int c_h_max; 				// maximum comparisons on a hit (successful search)
		int c_h_average;			// average comparisons on a hit (successful search)
		int c_h_total;				// total comparisons on hits (successful searches)
		int s_h_total;				// total hits (successful searches)
		long time_h_min;			// fastest hit
		long time_h_max;			// slowest hit
		long time_h_total;			// total time wasted on hits (successful searches)
		
		public Stats() 
		{
			time_m_min = 999999;
			time_m_max = 0;
			time_m_total = 0;
			c_m_min = 999999;
			c_m_max = 0;
			c_m_total = 0;
			s_m_total = 0;
			c_m_average = 0;
			
			time_h_min = 999999;
			time_h_max = 0;
			time_h_total = 0;
			c_h_min = 999999;
			c_h_max = 0;
			c_h_total = 0;
			s_h_total = 0;
			c_h_average = 0;
		}
	} // class Comparisons
	
	private Stats Leaf = new Stats(), Root = new Stats(), Tree = new Stats();
	
	/**
	 * generic function that takes a type of tree as a parameter,
	 * searches the item x in it and fills in the stats
	 * @param tree - the data structure to search
	 * @param x - the value to find
	 * @param stats - the record of the results
	 */
	private <T> void searchInTree(AbstractSet<T> tree, Item x, Stats stats) 
	{
		x.resetCompCount();
		long start = System.nanoTime();
		boolean found = tree.contains(x);
		long latency = (System.nanoTime()-start);
		int temp = (int) x.getCompCount();
		if (found)
		{
			stats.time_h_total += latency;
			stats.s_h_total++;
			stats.c_h_total+= temp;
			if (temp < stats.c_h_min) stats.c_h_min = temp;
			if (temp > stats.c_h_max) stats.c_h_max = temp;
			if (latency < stats.time_h_min) stats.time_h_min = latency;
			if (latency > stats.time_h_max) stats.time_h_max = latency;
			stats.c_h_average = stats.c_h_total / stats.s_h_total;
		}
		else
		{
			stats.time_m_total += latency;
			stats.s_m_total++;
			stats.c_m_total+= temp;
			if (temp < stats.c_m_min) stats.c_m_min = temp;
			if (temp > stats.c_m_max) stats.c_m_max = temp;
			if (latency < stats.time_m_min) stats.time_m_min = latency;
			if (latency > stats.time_m_max) stats.time_m_max = latency;
			stats.c_m_average = stats.c_m_total / stats.s_m_total;
		}
	} // method searchInTree()
	
	/**
	 * method that exports the most important stats
	 * separated by a whitespace to easily export to excel
	 * @return a string with the stats
	 */
	private String exportPartCForExcel()
	{
		String result = toLeaf.size+
				"\t"+Leaf.c_h_average + 
				"\t" + Root.c_h_average + 
				"\t" + Tree.c_h_average +
				"\t" +Leaf.c_m_average + 
				"\t" + Root.c_m_average + 
				"\t" + Tree.c_m_average +
				"\t" + toLeaf.height()+
				"\t" + toRoot.height()+
				"\t" + (Leaf.c_h_total+Leaf.c_m_total)+
				"   \t" + Leaf.c_h_total+
				"    \t" + Leaf.c_m_total;
		return result;
	} // method exportPartCForExcel()
	
	/**
	 * generates an item from the tree
	 * @return the existing item
	 */
	public Item ExistingItem()
	{
		Item existing = null;
		while (true)
		{
			existing = generate();
			if (toLeaf.contains(existing)&&toRoot.contains(existing))
				return existing;
		}
	} // method ExistingItem()
	
	/**
	 * generates an item that is not yet in the tree
	 * @return a new fresh item that is not a dupplicate
	 */
	public Item NewItem()
	{
		Item newItem = null;
		while (true)
		{
			newItem = generate();
			if (toLeaf.contains(newItem)&&toRoot.contains(newItem))
				continue;
			else
				return newItem;
		}
	} // method NewItem()
	
	
	/**
	 * method that performs inserts a random item in the trees 
	 * and deletes a random item from the trees
	 */
	public void alterTree()
	{
			Item toInsert = generate();
			toLeaf.add(toInsert);
			toRoot.add(toInsert);
			Item toDelete = generate();
			toLeaf.remove(toDelete);
			toRoot.remove(toDelete);
	} // method alterTree()
	
	/**
	 * public method that performs a required number of searches
	 * @param numberOfSearches - the quantity of searches to be performed
	 */
	public String performSearches(int numberOfSearches)
	{
		Item temp = null;
		Item.resetCompCount();
		
		for (int i = 0; i < numberOfSearches; i++)
		{
			temp = generate();
			
			searchInTree(toLeaf, temp, Leaf);
			searchInTree(toRoot, temp, Root);
			searchInTree(toTree, temp, Tree);		
		}
		return exportPartCForExcel();
	} // method performSearches()
	
	/**
	 * This method searches for an item and calls the 
	 * searchInTree method for each tree, recording stats
	 * for each performed search
	 * Unlike the previous method, this one runs ony one combo
	 * of 3 searches per call
	 * @param x - item to search for
	 * @return a string with recorded data
	 */
	public String performRecentSearches(Item x) 
	{
		Item.resetCompCount();
		searchInTree(toLeaf, x, Leaf);
		searchInTree(toRoot, x, Root);
		searchInTree(toTree, x, Tree);
		
		return exportPartCForExcel();
	} // method performRecentSearches()

}
