package partB;
/**
 * To get a better view of the tree, I have added a TreePrinter class, 
 * courtesy of Michal Kreuzman from Stackoverflow, original code example
 * can be found at the following link 
 * http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram 
 * To adapt it to our Binary Search Tree to this TreePrinter has suffered minor changes
 */
import java.util.*;

class TreePrinter {

	public static <T extends Comparable<?>> void printNode(BinarySearchTree<T> tree) {
		int maxLevel = tree.level(tree.root);
		System.out.println("");
		printNodeInternal(Collections.singletonList(tree.root), 1, maxLevel);
	}

	private static <T extends Comparable<?>> void printNodeInternal(
			List<BinarySearchTree.Entry<T>> nodes, int level, int maxLevel) {
		if (nodes.isEmpty() || TreePrinter.isAllElementsNull(nodes))
			return;

		int floor = maxLevel - level;
		int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

		// values spacing
		TreePrinter.printWhitespaces(firstSpaces);

		List<BinarySearchTree.Entry<T>> newNodes = new ArrayList<BinarySearchTree.Entry<T>>();
		for (BinarySearchTree.Entry<T> node : nodes) {
			if (node != null) {
				if (node.element == null)
					System.out.print("-");
				else
					System.out.print(node.element);
				newNodes.add(node.left);
				newNodes.add(node.right);
			} else {
				newNodes.add(null);
				newNodes.add(null);
				System.out.print(" ");
			}

			// seems like right side values separator
			TreePrinter.printWhitespaces(betweenSpaces);
		}
		System.out.println("");

		for (int i = 1; i <= endgeLines; i++) {
			for (int j = 0; j < nodes.size(); j++) {
				// spacing for the /\ (basically like the tab)
				TreePrinter.printWhitespaces(firstSpaces - i +1 );
				if (nodes.get(j) == null) {
					TreePrinter.printWhitespaces(endgeLines + endgeLines + i);
					continue;
				}

				if (nodes.get(j).left != null)
					System.out.print("/");
				else
					TreePrinter.printWhitespaces(1);

				// distance between /\
				TreePrinter.printWhitespaces(i + i - 2);

				if (nodes.get(j).right != null)
					System.out.print("\\");
				else
					TreePrinter.printWhitespaces(1);

				TreePrinter.printWhitespaces(endgeLines + endgeLines - i);
			}

			System.out.println(" ");
		}

		printNodeInternal(newNodes, level + 1, maxLevel);
	}

	private static void printWhitespaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" ");
	}

	private static <T extends Comparable<?>> int maxLevel(BinarySearchTree.Entry<T> node) {
		if (node == null)
			return 0;

		return Math.max(TreePrinter.maxLevel(node.left),
				TreePrinter.maxLevel(node.right)) + 1;
	}

	private static <T> boolean isAllElementsNull(List<T> list) {
		for (Object object : list) {
			if (object != null)
				return false;
		}

		return true;
	}

}