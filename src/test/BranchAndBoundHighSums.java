import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

//runtime = n * m * log(m), but not bigger than n * k; m - arrays count, n - length of the result array, k - length of single array
public class BranchAndBoundHighSums implements HighSums {
	
	@Override
	public List<Integer> findHighSums(int[][] lists, int n) {
		List<Integer> resultList = new ArrayList<>();
		
		//contains max sum's candidates
		//at the start contains sum of the first elements
		PriorityQueue<Node> queue = new PriorityQueue<>(10, Node.nodeComparator);
		//we need Set to prevent adding duplicates to PriorityQueue
		Set<Node> alreadyCounted = new HashSet<>();
		
		//add first element
		int firstSum = 0;
		int[] firstIndexes = new int[lists.length];
		
		for (int i = 0; i < lists.length; i++) {
			firstSum += lists[i][0];
			firstIndexes[i] = 0;
		}
		
		Node e = new Node(firstSum, firstIndexes);
		queue.add(e);
		alreadyCounted.add(e);

		//max possible length of the result array
		int size = 1;
		for (int i = 0; i < lists.length; i++) {
			size *= lists[i].length;
		}
		
		n = Math.min(n, size);
		
		while (resultList.size() < n) {
			//Get element with max sum
			Node maxElement = queue.poll();
			
			//retrieving child nodes
			Set<Node> nextNodes = generateNextNodes(lists, maxElement);
			
			//skip nodes if they already counted
			for (Node node : nextNodes) {
				if (!alreadyCounted.contains(node)) {
					alreadyCounted.add(node);
					queue.add(node);
				}
			}
			
			resultList.add(maxElement.sum);
		}
		
		return resultList;
	}
	
	//generates all child elements for parent
	private Set<Node> generateNextNodes(int[][] lists, Node node) {
		Set<Node> result = new HashSet<>();
		
		for (int i = 0; i < node.indexes.length; i++) {
			//check if it is the last element
			if (node.indexes[i] + 1 < lists[i].length) {
				int sum = node.sum - (lists[i][node.indexes[i]] - lists[i][node.indexes[i] + 1]);
				int[] indexes = Arrays.copyOf(node.indexes, node.indexes.length);
				indexes[i] = node.indexes[i] + 1;
				Node e = new Node(sum, indexes);
				
				result.add(e);
			}
		}
		
		return result;
	}
	
	//Node contains single indexes of arrays and correspondent sum of elements
	private static class Node implements Comparable<Node> {
		public static Comparator<Node> nodeComparator = new Comparator<Node>() {

			public int compare(Node node1, Node node2) {
				return node1.compareTo(node2);
			}

		};
		
		private int sum;
		private int[] indexes;

		public Node(int sum, int[] indexes) {
			this.sum = sum;
			this.indexes = Arrays.copyOf(indexes, indexes.length);
			sum = 0;
		}

		@Override
		public int compareTo(Node compareNode) {
			return compareNode.sum - sum;
		}
		
		@Override
		public boolean equals(Object other) {
		    if (this == other) return true;
		    if (!(other instanceof Node)) return false;
		    
		    Node otherNode = (Node) other;
		    
		    if (sum != otherNode.sum) return false;
		    if (indexes.length != otherNode.indexes.length) return false;
		    
		    for (int i = 0; i < indexes.length; i++) {
		    	if (indexes[i] != otherNode.indexes[i]) return false;
		    }
		    
		    return true;
		}
		
		@Override
		public int hashCode() { 
		    int hash = 1;
		    
		    hash = hash * 31 + sum;
		    hash = hash * 31 + Arrays.hashCode(indexes);
		    
		    return hash;
		  }
		
		@Override
		public String toString() {
			return "sum: " + sum + "\nindexes: " + Arrays.toString(indexes);
		}
	}
}
