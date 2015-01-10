package partA;

import partA.BinarySearchTree.TreeIterator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tester test = new Tester();
		test.reset(); 
		test.add(72); test.add(31);
		test.testHashCode(103);
		test.add(44); test.add(87); test.add(37); test.add(75); test.add(60); test.add(24); test.add(24);
		test.testIterate();
		test.testIterator(24);
		test.testContain(31, true);
		test.display();
		test.remove(37);		// remove an external node
		test.remove(44);		// remove an entry with right internal node only
		test.remove(31);		// remove an entry with two internal nodes
		test.remove(87);		// remove an entry with left internal node only 
		test.remove(44);		// remove inexistent item
		test.errorReport();
		
	}

}
