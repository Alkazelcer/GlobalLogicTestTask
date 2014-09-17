import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//runtime = n ^ k; n - arrays count, k - length of single array
public class BruteForceHighSums implements HighSums {

	@Override
	public List<Integer> findHighSums(int[][] lists, int n) {
		List<Integer> resultSum = new ArrayList<>();

		bruteForce(resultSum, lists, 0, 0);

		Collections.sort(resultSum, Collections.reverseOrder());

		return resultSum.size() > n ? resultSum.subList(0, n) : resultSum;
	}

	// recursively count sum of elements
	private void bruteForce(List<Integer> sums, int[][] lists, int i, int sum) {
		if (i > lists.length - 1) {
			sums.add(sum);
			return;
		}

		for (int k = 0; k < lists[i].length; k++) {
			bruteForce(sums, lists, i + 1, sum + lists[i][k]);
		}
	}
}
