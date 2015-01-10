package partC;

import partC.BinarySearchTree.TreeIterator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * Uncomment as necessary
		 * Experiment 1 Returns data necessary to create the plots
		 * Experiment 2 Returns data necessary to investigate over the height of the trees
		 * Experiment 3 Returns data necessary to investigate over the advantage of the root insertion
		 * 				when searching for the most recent items
		 */ 
		// experiment1();
		// experiment2();
		 experiment3();
	}
		
		public static void experiment1() 
		{
			TreeManager start = new TreeManager();
			System.out.println("     "+ 
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\tHeight"+
					"\tHeight"+
					"\tTotal Search"); // print average sign

			System.out.println("Size  "+ 
					"\tLeaf+"+
					"\tRoot+"+
					"\tTree+"+
					"\tLeaf-"+
					"\tRoot-"+
					"\tTree-"+
					"\tLeaf h"+
					"\tRoot h"+
					"\tComparisons"+
					"   \thit    "+
					"   \tmissed");
	
			for ( int i=0, treeSize= 1; i< 20; i++, treeSize = treeSize<<1)
			{
				start.setRandomRange(treeSize<<1);
				start.buildTrees(treeSize);
				String result = start.performSearches(10000);
				System.out.println(result);
			}
		
			System.out.println("Average comparisons per search completed");
		}

		public static void experiment2()

		{
			// TODO Auto-generated method stub
			TreeManager start = new TreeManager();
					
			System.out.println("     "+ 
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\tHeight"+
					"\tHeight"+
					"\tTotal Search"); // print average sign

			System.out.println("Size  "+ 
					"\tLeaf+"+
					"\tRoot+"+
					"\tTree+"+
					"\tLeaf-"+
					"\tRoot-"+
					"\tTree-"+
					"\tLeaf h"+
					"\tRoot h"+
					"\tComparisons"+
					"   \thit    "+
					"   \tmissed");
			/**
			 * To create the other versions of the experiment (that changes also the size of the 
			 * trees that need to be built) we would need to insert the start.buildTrees function
			 * in the for loop and to use another variable that increases with every loop and is passedf
			 * as parameter for the start.buildTrees function
			 * 
			 * Also to create the experiment that increases also the number of alterations based on the size 
			 * the created tree (or trees if created with every loop) we need to place the increasing variable
			 * in the second for loop in the condition statement 
			 * 
			 * Currently in this state, the experiment performs a number of insertion and deletions that exponentially
			 * increases with every loop 
			 */
			String averageComparisonsBefore = "";
			start.setRandomRange(2000);
			start.buildTrees(1000);
			for ( int j=0, numberOfAlterations= 2; j< 19; j++, numberOfAlterations = numberOfAlterations << 1)
			{
				averageComparisonsBefore = start.performSearches(10000);	
				for ( int i=0; i< numberOfAlterations; i++)
				{
					start.alterTree();
				}
				String averageComparisonsAfter = start.performSearches(10000);
				System.out.println(averageComparisonsBefore + "\n" + averageComparisonsAfter+" "+start.toRoot.size());
			}			
			System.out.println("Height experiment completed");
		}
		
		public static void experiment3()
		{
			TreeManager start = new TreeManager();
			System.out.println("     "+ 
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\t_____"+
					"\tHeight"+
					"\tHeight"+
					"\tTotal Search"); // print average sign

			System.out.println("Size  "+ 
					"\tLeaf+"+
					"\tRoot+"+
					"\tTree+"+
					"\tLeaf-"+
					"\tRoot-"+
					"\tTree-"+
					"\tLeaf h"+
					"\tRoot h"+
					"\tComparisons"+
					"   \thit    "+
					"   \tmissed");
			final int sizeOfTree = 100;
			
			start.setRandomRange(2*sizeOfTree);
			start.buildTrees(sizeOfTree);
			
			/**
			 * having already stored in the start.List the most 10% recent items
			 * we can just iterate through the list and search for each item.
			 */
			for (int i= 0; i< start.List.length; i++)
			{
				String result = start.performRecentSearches(start.List[i]);
				System.out.println(result);
			}
			
			System.out.println("Root advantage experiment complete");
		}
}
