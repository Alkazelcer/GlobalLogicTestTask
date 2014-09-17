import java.util.Arrays;

//runtime = n ^ k; n - arrays count, k - length of single array
public class BruteForceHighSums extends HighSums {
	private ThreadLocal<Integer> index;

	@Override
	public int[] findHighSums(int[][] lists, int n) {
		index = new ThreadLocal<Integer>() {
			@Override
			protected Integer initialValue() {
				return 0;
			}
		};

		// max length of the result array
		int size = 1;
		for (int i = 0; i < lists.length; i++) {
			size *= lists[i].length;
		}

		int[] resultSum = new int[size];

		bruteForce(resultSum, lists, 0, 0);

		descendingSort(resultSum);

		return resultSum.length > n ? Arrays.copyOfRange(resultSum, 0, n) : resultSum;
	}

	// recursively count sum of elements
	private void bruteForce(int[] sums, int[][] lists, int i, int sum) {
		if (i > lists.length - 1) {
			sums[index.get()] = sum;
			index.set(index.get() + 1);
			return;
		}

		for (int k = 0; k < lists[i].length; k++) {
			bruteForce(sums, lists, i + 1, sum + lists[i][k]);
		}
	}
}
