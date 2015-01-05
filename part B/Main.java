package partB;

import partB.BinarySearchTree.TreeIterator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tester go = new Tester();
		go.performDefaultTests();
		
		BinarySearchTree <Integer> x = new BinarySearchTree <Integer>();
		BinarySearchTree.TreeIterator loop = (TreeIterator) x.iterator();
		
		x.add(72); x.add(31); x.add(44); x.add(87); x.add(37); x.add(75);x.add(60);
		x.size();
		System.out.println("hash: " +x.hashCode() + "level: "+ x.level(x.root));
		x.height();
		x.level(x.root);
		x.add(24); x.add(24);
		x.size();
		System.out.println("hash: " +x.hashCode() + " level: "+ x.level(x.root));
		x.height();	
		
		while (loop.hasNext())
		{
			System.out.print(loop.next+" ");
		}
		System.out.println("\n"+x.contains(31)+"\n");
		x.displayBreadthFirst();
		TreePrinter.printNode(x);
		x.remove(31);
		x.displayBreadthFirst();
		TreePrinter.printNode(x);
		x.remove(31);
		
		System.out.println(((x.successor(x.root.left)).element));
		System.out.println(((x.successor(x.root)).element));
		
	}

}
