package template;
public class Permutation {
	private static int K, qi, N, kpi;
	private static int[] A, queue;
	private static int[][] permutations;

	private static void kPermutations(int mask) {
		if (qi == K) {
			permutations[kpi++] = queue.clone();
		} else {
			int m = mask;
			while (m != (1 << N) - 1) {
				int pos = ~m & (m + 1); // rightmost zero bit
				m |= pos; // sets rightmost zero bit
				queue[qi++] = A[31 - Integer.numberOfLeadingZeros(pos)]; // lg(pos)
				kPermutations(mask | pos);
				qi--;
			}
		}
	}

	static int[][] getPermutations(int[] a, int k) {
		K = k;
		A = a;
		N = A.length;
		queue = new int[K];
		int totalPerm = 1;
		for (int i = N - K + 1; i <= N; i++) {
			totalPerm *= i;
		}
		permutations = new int[totalPerm][];
		kPermutations(0);
		return permutations;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int[][] perms = Permutation
				.getPermutations(new int[] { 1, 2, 3, 4 }, 3);
		for (int[] p : perms) {
			System.out.println(java.util.Arrays.toString(p));
		}
		System.out.println(System.currentTimeMillis() - start + "ms");
	}
}
