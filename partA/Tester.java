package partA;

import java.util.Iterator;

import partA.BinarySearchTree.TreeIterator;


/**
 * This class performs a series of methods to check the functionality
 * of a Binary Search Tree and displays a report (on request)
 * There are several individual tests that can be performed or 
 * a set of tests can be performed on a default Binary Search Tree 
 * @author Tinu
 *
 * @param <E>
 * passed - number of successful tests
 * failed - number of failed tests
 * failedTests - will record in a string the exact method call that failed
 */

public class Tester <E> {

	private BinarySearchTree test;
	
	private int passed;
	private int failed;
	private String failedTests ;
	
	/**
	 * Default constructor that creates a reference for a 
	 * binary search tree and initialises the private fields
	 */
	public Tester () 
	{
		test = new BinarySearchTree();
		passed = 0;
		failed = 0;
		failedTests = "Methods that failed: \t";
	} // default constructor
	
	/**
	 * constructor that specifies which tree to use for testing
	 * @param tree - the input tree to be tested
	 */
	public Tester(BinarySearchTree tree)
	{
		test = tree;
		passed = 0;
		failed = 0;
		failedTests = "Methods that failed: \t";
	} // constructor that specifies what tree to test
	
	/**
	 * simple method to draw a line
	 * used for report
	 */
	private void line()
	{
		System.out.println("---------------------------------");
	} // line method
	
	/**
	 * Tests the add function of the tree
	 * The function takes an element and ads it to the internal tree that 
	 * needs to be tested, then displays the tree
	 * @param element - the element to add
	 */
	public void add(E element)
	{
		line();
		test.add(element);
		TreePrinter.printNode(test);
	} // method add - 1 parameter
	
	/**
	 * same like previous method but this method also tests 
	 * the height, size, hashCode functions of the tree and compares
	 * the result against the input parameters 
	 * @param element - element to add
	 * @param size - expected value for size, to have a passed size test
	 * @param height - expected value for height, to have a passed height test
	 * @param code - expected value for hashCode, to have a passed hashCode test
	 */
	public void add(E element, int size, int height, int code)
	{
		line();
		test.add(element);
		testHeight(height);
		testSize(size);
		testHashCode(code);		
		TreePrinter.printNode(test);
	} // method add - 4 parameters
	
	/**
	 * This method tests the functionality of the remove function
	 * of the Binary Search tree and displays the size, height, hashCode
	 * and tree layout after the removal
	 * The test also counts any function call as a successful test
	 * @param element - item to be removed
	 */
	public void remove(E element)
	{
		boolean successfullyRemoved;
		line();
		System.out.println("Removing element: "+ element);
		successfullyRemoved = test.remove(element);
		if (successfullyRemoved)
		{
			System.out.println("Succesfully removed "+ element);
			passed++;
		}
		else
		{
			System.out.println("Could not remove "+ element +" not found...");
			passed++;
		}
		test.size();
		test.height();
		test.hashCode();
		TreePrinter.printNode(test);
	} // method remove
	
	/**
	 * This test checks if an element exists and compares against
	 * the expected outcome given by the shouldExist variable
	 * For example in a tree that has element 1, when checking the scenario:
	 * 		- testContain(1, true) the test will pass
	 * 		- testContain(1, false) the test will fail
	 * 		- testContain(5, true) the test will fail
	 * 		- testContain(5, false) the test will pass
	 * The function also counts the successful/failed tests and records
	 * the exact failed call to be stored for the report
	 * @param element - item that needs to be checked
	 * @param shouldExist -  the expected result for a test to pass
	 */
	public void testContain(E element, boolean shouldExist)
	{
		System.out.println("Testing if element " + element + " exists in the BST. Result should be: " + shouldExist);
		if (test.contains(element) == shouldExist) 
		{
			System.out.println("Test OK! contains("+element+"): Result is: " + test.contains(element));
			passed++;
		}
		else
		{
			System.out.println("FAILED!... contains("+element+"): Result is: " + test.contains(element));
			failed++;
			failedTests += "\n testContain("+element+","+shouldExist+")";
		}
	} // method testContain - 2 parameters
	
	/**
	 * This will test the height of the tree against
	 * an expected value
	 * The function also counts the successful/failed tests and records
	 * the exact failed call to be stored for the report  
	 * @param h - the exepected value of the height for the test to pass
	 */
	public void testHeight(int h)
	{
		// testing height
		if (test.height() == h)
		{
			System.out.print("Test Passed! " );
			passed++;
		}
		else 
		{
			System.out.print("FAILED!... ");
			failed++;
			failedTests += "\n testHeight("+h+")";
		}
		System.out.println(" Height should be: "+h );		
	} // method testHeight()
	
	/**
	 * This will test the size of the tree against
	 * an expected value 
	 * The function also counts the successful/failed tests and records
	 * the exact failed call to be stored for the report
	 * @param s - the exepected value of the size for the test to pass
	 */
	public void testSize(int s)
	{
		// testing size
		if (test.size() == s)
		{
			System.out.print("Test Passed! " );
			passed++;
		}
		else 
		{
			System.out.print("FAILED!... ");
			failed++;
			failedTests += "\n testSize("+s+")";
		}
		System.out.println(" Size should be: "+s );
	} // method testSize()
	
	/**
	 * This will test the hashCode of the tree against
	 * an expected value 
	 * The function also counts the successful/failed tests and records
	 * the exact failed call to be stored for the report
	 * @param c - the exepected value of the hashCode for the test to pass
	 */
	public void testHashCode(int c)
	{
		// testing hashCode
		if (test.hashCode() == c)
		{
			System.out.print("Test Passed! " );
			passed++;
		}
		else 
		{
			System.out.print("FAILED!... ");
			failed++;
			failedTests += "\n testHashCode("+c+")";
		}
		System.out.println(" HashCode should be: "+c );
	} // method testHashCode() 
	
	/**
	 * This will test if the iterator is placed at leftmost node in the tree
	 * (with the smallest value) and compares the element against an expected value
	 * The function also counts the successful/failed tests and records
	 * the exact failed call to be stored for the report
	 * @param value - the value to test against
	 */
	public void testIterator(int value)
	{
		int result = (int) test.iterator().next();
		System.out.println("Checking if iterator is equal to value: "+ value);
		System.out.println("The iterator returned "+ result);
		if (value == result)
		{
			System.out.println("Test passed!");
			passed++;
		}
		else
		{
			System.out.println("FAILED!...");
			failed++;
			failedTests += "\n testIterator("+value+")";
		}
	} // method testIterator()
	
	/**
	 * This function tests the successor method of the tree and checks 
	 * if the successor of a given node is equal to an expected value
	 * The function also counts the successful/failed tests and records
	 * the exact failed call to be stored for the report
	 * @param toFindSuccessor - node who`s successor we need to test
	 * @param value - value to test against
	 */
	public void testSuccessor(E toFindSuccessor, E value)
	{
		BinarySearchTree.Entry node = test.getEntry(toFindSuccessor);
		BinarySearchTree.Entry successor = test.successor(node);
		E result;
		System.out.println("Checking if the successor of "+toFindSuccessor+" is "+value);
		if (successor == null) 
		{
			result = null;
		}
		else
		{
			result = (E) successor.element;
		}
		
		// testing successor against null
		if (result == value)
		{
			System.out.print("Test Passed! " );
			passed++;
		}
		else 
		{
			System.out.print("FAILED!... ");
			failed++;
			failedTests += "\n testSuccessor("+toFindSuccessor+","+value+")";
		}
		System.out.println(" Successor should be: "+result );		
	} // method testSuccessor()
	
	/**
	 * This method will show how many tests have passed, how many
	 * failed, and shows which calls exactly have failed and for which value
	 */
	public void errorReport()
	{
		line();
		System.out.println("Passed tests: "+ passed);
		if (failed != 0) 
		{
			System.out.println(failedTests);
			System.out.println("Failed tests: "+ failed);
		}
		else
		{
			failedTests += " none! ";
			System.out.println(failedTests);
		}
		failedTests = "Methods that failed: \t";
		failed = 0;
		passed = 0;
		line();
	} // method errorReport
	
	/**
	 * This function allows to change the tree to which we want to 
	 * perform tests
	 * Also resets any previous values of the passed/failed tests 
	 * @param newTree - the new tree to be tested
	 */
	public void toTest(BinarySearchTree newTree)
	{
		test = newTree;
		failedTests = "Methods that failed: \t";
		failed = 0;
		passed = 0;
	}
	
	/**
	 * This function will show the diagram of the tree togheter
	 * with the height and hashcode
	 */
	public void display()
	{
		if (test.isEmpty())
		{
			System.out.println("Tree is empty...");
			test.height();
		}
		else
		{
			test.height();
			test.hashCode();
			TreePrinter.printNode(test);
		}
	} // method display()
	
	/**
	 * This test will iterate through all the nodes in order
	 */
	public void testIterate()
	{
		System.out.println("Testing the iterator... iterating the tree");
		int counter = 0;
		TreeIterator x = (TreeIterator) test.iterator();
		while (x.hasNext()) 
		{
			System.out.println(x.next());
			counter++;
		}
		if (counter == test.size) 
		{
			System.out.println("Test Passed!");
			passed++;
		}
		else
		{
			System.out.println("FAILED!...");
			failed++;
			failedTests += "\n testIterate()";
		}
	}
	
	/**
	 * This method will build a tree and perform a series of tests 
	 * on it, displaying at the end a report 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void performDefaultTests()
	{
		Tester x = new Tester();
		x.display();
		x.testIterate();
		x.add(21, 1, 1, 21);	// item 21, size 1, height 1, hash 21, iterator 21
		x.add(25, 2, 2, 46);	// item 25, size 2, height 2, hash 46, iterator 21
		x.add(17, 3, 2, 63);	// item 17, size 3, height 2, hash 63, iterator 17
		x.add(12, 4, 3, 75);	// item 12, size 4, height 3, hash 75, iterator 12
		x.add(9, 5, 4, 84);		// item  9, size 5, height 4, hash 84, iterator 9
		x.testContain(17, true);
		x.testContain(19,  false);
		x.remove(21);			// size 4, height 4, hash 63, iterator 9
		x.remove(9);			// size 3, height 3, hash 54, iterator 12
		x.testHashCode(54);		
		x.testIterator(12);
		x.testHeight(3);
		x.testSize(3);
		
		x.add(10);				// item 10, size 4, height 4, hash 64, iterator 10
		x.testIterator(10);
		x.testHeight(4);
		x.testHashCode(64);
		x.errorReport();
		x.testIterate();
		x.testHashCode(53);		// should fail
		x.errorReport();
	} // method performDefaultTests()
	
}
