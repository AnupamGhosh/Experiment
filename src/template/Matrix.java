package template;

import java.util.Arrays;

public class Matrix {
	public static int[][] pow(int[][] A, long n, int mod) {
		int N = A.length;
		int[][] ret = new int[N][N];
		for (int i = 0; i < N; i++) {
			ret[i][i] = 1;
		}
		while (n > 0) {
			if (n % 2 == 1) {
				ret = multiply(ret, A, mod);
			}
			A = multiply(A, A, mod);
			n /= 2;
		}
		return ret;
	}
	
	public static int[][] multiply(int[][] A, int[][] B, int mod) {
		int N = A.length;
		int M = B[0].length;
		int[][] C = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				long sum = 0;
				for (int k = 0; k < B.length; k++) {
					sum = (sum + (long) A[i][k] * B[k][j]) % mod;
				}
				C[i][j] = (int) sum;
			}
		}
		return C;
	}

	public static void main(String[] args) {
		int[][] A = {{ 1, 2, 8 },
					 { 5, 6, 8 },
					 { 7, 0, 3 }};
		int[][] B = {{ 5, 9, 8 },
					 { 6, 4, 2 },
					 { 3, 0, 1 }};
		System.out.println(Arrays.deepToString(multiply(A, B, 100000007)));
	}

}
