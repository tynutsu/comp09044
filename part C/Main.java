package partC;

import partC.BinarySearchTree.TreeIterator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeManager start = new TreeManager();
		
		start.buildTrees(500);
		System.out.println(start.List[0].value());
		System.out.println(start.toTree.size());
		start.toLeaf.size();
		start.toRoot.size();
		start.performSearches(10000);
		}
}
