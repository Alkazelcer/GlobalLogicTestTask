import java.util.Arrays;
import java.util.Random;


public class Tets {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		int[][] lists = new int[][]{{6, 4, 1}, {5, 2}, {1, 1}};
		long time = System.currentTimeMillis();
		int[][] lists = new int[5][];
		
		Random rand = new Random();
		
		for (int i = 0; i < lists.length; i++) {
			lists[i] = new int[(int) rand.nextInt(5) + 1];
			for (int j = 0; j < lists[i].length; j ++) {
				lists[i][j] = (int) rand.nextInt(100);
			}
			
			HighSums.descendingSort(lists[i]);
		}
		for (int i = 0; i < lists.length; i++) {
			System.out.println(Arrays.toString(lists[i]));
		}
		
		HighSums bruteForceSum = new BruteForceHighSums();
		
		int[] bruteForceArray = bruteForceSum.findHighSums(lists, 10000);
		System.out.println(Arrays.toString(bruteForceArray));
//		System.out.println(System.currentTimeMillis() - time);
		
		
		HighSums branchSums = new BranchAndBoundHighSums();
		
		int[] branchArray = branchSums.findHighSums(lists, 10000);
		System.out.println(Arrays.toString(branchArray));
		System.out.println(BranchAndBoundHighSums.ittCount);
		System.out.println(BranchAndBoundHighSums.whileCount);
		
		assert(Arrays.equals(bruteForceArray, branchArray));
		
		//big arrays
		lists = new int[3][];
		
		for (int i = 0; i < lists.length; i++) {
			lists[i] = new int[(int) 500];
			for (int j = 0; j < lists[i].length; j ++) {
				lists[i][j] = (int) rand.nextInt(100);
			}
			
			HighSums.descendingSort(lists[i]);
		}
		for (int i = 0; i < lists.length; i++) {
			System.out.println(Arrays.toString(lists[i]));
		}
	
	bruteForceSum = new BruteForceHighSums();
	
	bruteForceArray = bruteForceSum.findHighSums(lists, 10);
//	System.out.println(Arrays.toString(bruteForceArray));
	System.out.println("done");
//	System.out.println(System.currentTimeMillis() - time);
	
	
	branchSums = new BranchAndBoundHighSums();
	
	branchArray = branchSums.findHighSums(lists, 10);
//	System.out.println(Arrays.toString(branchArray));
	System.out.println(BranchAndBoundHighSums.ittCount);
	System.out.println(BranchAndBoundHighSums.whileCount);
	
	assert(Arrays.equals(bruteForceArray, branchArray));
	}
}
