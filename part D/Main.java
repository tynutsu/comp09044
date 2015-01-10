package partD;

import java.io.*;

import partD.BinarySearchTree.TreeIterator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinarySearchTree <Integer> b1 = new BinarySearchTree();
		b1.add(21);b1.add(54);b1.add(29); b1.add(99); b1.add(421);
		System.out.println("This is the original tree");
		TreePrinter.printNode(b1);
		
		try {
			FileOutputStream fos = new FileOutputStream ("binary.ser");
			ObjectOutputStream oos = new ObjectOutputStream (fos);
			oos.writeObject(b1);
			oos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileInputStream fis = new FileInputStream ("binary.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			BinarySearchTree reloaded = (BinarySearchTree) ois.readObject();
			ois.close();
			System.out.println("This is the deserialized tree:");
			TreePrinter.printNode(reloaded);
				
			if (reloaded.equals(b1)) System.out.println("Identical");
			else System.out.println("Different");
			
			reloaded.add(1);
			reloaded.remove(99);
			TreePrinter.printNode(reloaded);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		 
	}

}
