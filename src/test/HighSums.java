import java.util.Arrays;

public abstract class HighSums {
	public abstract int[] findHighSums(int[][] lists, int n);
	
	public static void descendingSort(int[] list) {
		Arrays.sort(list);
		
		int temp;
		for (int i = 0; i < list.length / 2; i++) {
			temp = list[i];
			list[i] = list[list.length - 1 - i];
			list[list.length - 1 - i] = temp;
		}
	}
}
