import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class IndexGenerator extends HighSums {
	public static int ittCount = 0;
	public static int whileCount = 0;
	@Override
	public int[] findHighSums(int[][] lists, int n) {
		ittCount = 0;
		whileCount = 0;
		PriorityQueue<Node> queue = new NoDuplicates<>(10, Node.nodeComparator);
		int iterations = 1;
		int size = 1;
		int firstSum = 0;
		int[] firstIndexes = new int[lists.length];
		
		for (int i = 0; i < lists.length; i++) {
			firstSum += lists[i][0];
			firstIndexes[i] = 0;
		}
		
		Node e = new Node(firstSum, firstIndexes, 0);
		queue.add(e);

		for (int i = 0; i < lists.length; i++) {
			size *= lists[i].length;
		}
		
		
		while (iterations < n && queue.size() < size) {
			queue.addAll(fromArrayToNode(lists, queue));
			
			whileCount++;
			iterations++;
			
			if (iterations % 5 == 0) System.out.println(iterations); 
		}
		
		int[] result = new int[queue.size() > n ? n : queue.size()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = queue.poll().sum;
		}
				
		descendingSort(result);
		
		return result;
	}
	
	private List<Node> fromArrayToNode(int[][] lists, Collection<Node> nodes) {
		List<Node> result = new ArrayList<>();
		
		for (Node node : nodes) {
			for (int i = 0; i < node.indexes.length; i++) {
//				if (i != node.currentListLevel && node.indexes[i] + 1 < lists[i].length) {
				if (node.indexes[i] + 1 < lists[i].length) {
					int sum = node.sum - (lists[i][node.indexes[i]] - lists[i][node.indexes[i] + 1]);
					int[] indexes = Arrays.copyOf(node.indexes, node.indexes.length);
					indexes[i] = node.indexes[i] + 1;
					Node e = new Node(sum, indexes, i);
					result.add(e);
					ittCount++;
				}
			}
		}
		
		return result;
	}
	
	private static class Node implements Comparable<Node> {
		private int sum;
		private int[] indexes;
		private int currentListLevel;

		public Node(int sum, int[] indexes, int currentList) {
			this.sum = sum;
			this.currentListLevel = currentList;
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
		    hash = hash * 31 + indexes.hashCode();
		    
		    return hash;
		  }
		
		@Override
		public String toString() {
			return "sum: " + sum + "\nindexes: " + Arrays.toString(indexes);
		}
		
		public static Comparator<Node> nodeComparator = new Comparator<Node>() {

			public int compare(Node node1, Node node2) {
				return node1.compareTo(node2);
			}

		};
	}
	
	private int[] nextIndex() {
		int[] result = new int[10];
		
		return result;
	}
	
	public static void main(String[] args) {
		Node e1 = new Node(3, new int[]{1,1,0},3);
		Node e2 = new Node(3, new int[]{1,0,1},3);
		Node e3 = new Node(3, new int[]{1,0,1},3);
		System.out.println(e1.equals(e2));
		System.out.println(e1.equals(e3));
		System.out.println(e2.equals(e3));
		System.out.println(e2.equals(e2));
		System.out.println(e1.equals(e1));
		System.out.println(e3.equals(e3));
	}
}
