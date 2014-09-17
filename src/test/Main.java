import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Main {
	
	public static void main(String[] args) {
		long time;
		HighSums bruteForceSum = new BruteForceHighSums();
		HighSums branchSums = new BranchAndBoundHighSums();
		
		//specific lists
		System.out.println("Specific lists");
		int[][] lists = new int[][]{{6, 4, 1}, {5, 2}, {1, 1}};
		
		printArrays(lists);
		
		time = System.currentTimeMillis();
		List<Integer> bruteForceArray = bruteForceSum.findHighSums(lists, 1000);
		System.out.println("Brute force time = " + (System.currentTimeMillis() - time) + "ms");
		
		time = System.currentTimeMillis();
		List<Integer> branchArray = branchSums.findHighSums(lists, 1000);
		System.out.println("Branch and Bounds time = " + (System.currentTimeMillis() - time) + "ms");
		System.out.println(branchArray);
		
		assert(bruteForceArray.equals(branchArray));
		
		//random lists
		System.out.println("\nRandom lists");
		Random rand = new Random();

		lists = new int[rand.nextInt(5) + 1][];
		int size = 1;
		
		for (int i = 0; i < lists.length; i++) {
			int arraySize = rand.nextInt(5) + 1;
			size *= arraySize;
			
			lists[i] = new int[arraySize];
			for (int j = 0; j < lists[i].length; j ++) {
				lists[i][j] = (int) rand.nextInt(100);
			}
			
			descendingSort(lists[i]);
		}
		
		printArrays(lists);
		
		int resultCount = rand.nextInt(size) + 1;
		
		time = System.currentTimeMillis();
		bruteForceArray = bruteForceSum.findHighSums(lists, resultCount);
		System.out.println("Brute force time = " + (System.currentTimeMillis() - time) + "ms");
		
		time = System.currentTimeMillis();
		branchArray = branchSums.findHighSums(lists, resultCount);
		System.out.println("Branch and Bounds time = " + (System.currentTimeMillis() - time) + "ms");
		System.out.println(branchArray);
		
		assert(bruteForceArray.equals(branchArray));
		
		//big lists
		System.out.println("\nBig lists");
		lists = new int[3][];
		
		for (int i = 0; i < lists.length; i++) {
			lists[i] = new int[(int) 200];
			for (int j = 0; j < lists[i].length; j ++) {
				lists[i][j] = (int) rand.nextInt(100);
			}
			
			descendingSort(lists[i]);
		}
		
		printArrays(lists);
		
		time = System.currentTimeMillis();
		bruteForceArray = bruteForceSum.findHighSums(lists, 20);
		System.out.println("Brute force time = " + (System.currentTimeMillis() - time) + "ms");
		
		time = System.currentTimeMillis();
		branchArray = branchSums.findHighSums(lists, 20);
		System.out.println("Branch and Bounds time = " + (System.currentTimeMillis() - time) + "ms");
		System.out.println(branchArray);
	
		assert(bruteForceArray.equals(branchArray));
	}
	
	private static void printArrays(int[][] lists) {
		for (int i = 0; i < lists.length; i++) {
			System.out.println(Arrays.toString(lists[i]));
		}
	}
	
	private static void descendingSort(int[] list) {
		Arrays.sort(list);
		
		int temp;
		for (int i = 0; i < list.length / 2; i++) {
			temp = list[i];
			list[i] = list[list.length - 1 - i];
			list[list.length - 1 - i] = temp;
		}
	}

}
