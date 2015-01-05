package partC;

import partC.BinarySearchTree.TreeIterator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeManager start = new TreeManager();
		
		start.buildTrees(2000);
		System.out.println(start.List[0].value());
		}
}
