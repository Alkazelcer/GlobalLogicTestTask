import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

//runtime = n * m, but not bigger than n * k; n - arrays count, m - length of the result array, k - length of single array
public class BranchAndBoundHighSums extends HighSums {
	//contains already counted elements, 
	//ThreadLocal for save multyThread sharing 
	private ThreadLocal<Set<Node>> alreadyCounted;
	
	@Override
	public int[] findHighSums(int[][] lists, int n) {
		alreadyCounted = new ThreadLocal<Set<Node>>() {
			@Override
			protected Set<Node> initialValue() {
				return new HashSet<>();
			}
		};
		
		//contains max sum's candidates
		//at the start contains sum of the first elements
		PriorityQueue<Node> queue = new PriorityQueue<>(10, Node.nodeComparator);
		int firstSum = 0;
		int[] firstIndexes = new int[lists.length];
		
		for (int i = 0; i < lists.length; i++) {
			firstSum += lists[i][0];
			firstIndexes[i] = 0;
		}
		
		Node e = new Node(firstSum, firstIndexes);
		queue.add(e);

		//max length of the result array
		int size = 1;
		for (int i = 0; i < lists.length; i++) {
			size *= lists[i].length;
		}
		
		//element's child nodes
		Set<Node> currentLayer = new HashSet<>();
		currentLayer.add(e);

		List<Integer> resultList = new ArrayList<>();
		
		alreadyCounted.get().add(e);
		
		int iterations = 0;
		while (iterations < n && iterations < size) {
			//bounding
			Node ee = queue.poll();
			
			//branching
			currentLayer = generateNextLayer(lists, ee);
			
			queue.addAll(currentLayer);
			resultList.add(ee.sum);
			iterations++;
		}
		
		int[] result = new int[resultList.size()];
		
		//from list to array
		for (int i = 0; i < resultList.size(); i++) {
			result[i] = resultList.get(i);
		}
				
		return result;
	}
	
	//generates all child elements for parent
	private Set<Node> generateNextLayer(int[][] lists, Node node) {
		Set<Node> result = new HashSet<>();
		
		for (int i = 0; i < node.indexes.length; i++) {
			//is there right elements in the current list
			if (node.indexes[i] + 1 < lists[i].length) {
				int sum = node.sum - (lists[i][node.indexes[i]] - lists[i][node.indexes[i] + 1]);
				int[] indexes = Arrays.copyOf(node.indexes, node.indexes.length);
				indexes[i] = node.indexes[i] + 1;
				Node e = new Node(sum, indexes);

				if (!alreadyCounted.get().contains(e)) {
					alreadyCounted.get().add(e);
					result.add(e);
				}
				
			}
		}
		
		return result;
	}
	
	//represents tree elements
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
