package fun;

import java.util.Arrays;
import java.util.Scanner;

public class SudokuSolver {
	private int[] fixed;
	private int[][] grid;
	private int[] row;
	private int[] col;
	private int[] box;
	private int N;

	private int[][] solveSudoku(int[][] Grid) {
		N = Grid.length;
		grid = new int[N][N];
		fixed = new int[N];
		row = new int[N];
		col = new int[N];
		box = new int[N];
		Arrays.fill(row, 1);
		Arrays.fill(col, 1);
		Arrays.fill(box, 1);
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				grid[r][c] = Grid[r][c]; 
				if (Grid[r][c] > 0) {
					int x = r / 3 * 3 + c / 3; 
					fixed[r] |= 1 << c;
					row[r] |= 1 << Grid[r][c];
					col[c] |= 1 << Grid[r][c];
					box[x] |= 1 << Grid[r][c];
				}
			}
		}
		if (solveSudoku(0)) {
			return grid;
		} else {
			return null;
		}
	}
	
	private boolean solveSudoku(int r) {
		if (r == N) {
			return true;
		}
		int colBit = ~fixed[r] & fixed[r] + 1; // rightmost 0-bit
		int c = Integer.numberOfTrailingZeros(colBit);
		if (c == N) {
			return solveSudoku(r + 1);
		}
		fixed[r] |= colBit;
		int x = r / 3 * 3 + c / 3;
		for (int mask = row[r] | col[c] | box[x]; mask < (1 << N + 1) - 1; mask |= mask + 1) {
			int value = ~mask & mask + 1;
			grid[r][c] = Integer.numberOfTrailingZeros(value);
			row[r] |= value; // remembers row[r] contains value
			col[c] |= value; // sets the value bit
			box[x] |= value;
			if (solveSudoku(r)) {
				return true;
			}
			row[r] ^= value; // clears the value bit
			col[c] ^= value;
			box[x] ^= value;
//			grid[r][c] = 0; // for debugging
		}
		fixed[r] ^= colBit;
		return false;
	}
	
	public static int[][] solve(int[][] Grid) {
		return new SudokuSolver().solveSudoku(Grid);
	}

	public static void main(String[] args) {
//		int[][] grid = {{2, 0, 0, 5, 8, 0, 0, 0, 7},
//						{0, 3, 0, 0, 0, 0, 9, 0, 8},
//						{0, 6, 0, 0, 0, 4, 0, 0, 0},
//						{0, 0, 3, 0, 5, 1, 7, 0, 0},
//						{0, 0, 0, 0, 7, 0, 0, 0, 0},
//						{0, 0, 8, 3, 9, 0, 2, 0, 0},
//						{0, 0, 0, 9, 0, 0, 0, 7, 0},
//						{3, 0, 4, 0, 0, 0, 0, 6, 0},
//						{9, 0, 0, 0, 4, 5, 0, 0, 2}
//						};
//		int[][] grid = {{2, 0, 0, 0, 0, 5, 1, 0, 8},
//						{0, 0, 8, 3, 0, 0, 4, 0, 0},
//						{4, 1, 0, 2, 0, 0, 0, 7, 0},
//						{0, 0, 0, 0, 0, 7, 0, 2, 0},
//						{1, 0, 0, 0, 0, 0, 0, 0, 9},
//						{0, 7, 0, 6, 0, 0, 0, 0, 0},
//						{0, 5, 0, 0, 0, 0, 0, 3, 7},
//						{0, 0, 6, 0, 0, 4, 2, 0, 0},
//						{7, 0, 0, 8, 0, 0, 0, 0, 4}};
//		int[][] grid = {{0, 0, 7, 0, 0, 3, 4, 8, 6},
//						{0, 0, 0, 1, 0, 0, 0, 9, 0},
//						{2, 0, 0, 0, 0, 0, 0, 0, 0},
//						{6, 0, 2, 0, 0, 0, 3, 0, 0},
//						{0, 1, 9, 3, 0, 7, 6, 2, 0},
//						{0, 0, 8, 0, 0, 0, 5, 0, 9},
//						{0, 0, 0, 0, 0, 0, 0, 0, 4},
//						{0, 8, 0, 0, 0, 2, 0, 0, 0},
//						{9, 4, 6, 5, 0, 0, 8, 0, 0}};
//		int[][] grid = new int[9][9];
		Scanner in = new Scanner(System.in);
		int N = 9;
		int[][] grid = new int[N][N];
		for (int i = 0; i < N; i++) {
			char[] line = in.nextLine().toCharArray();
			for (int j = 0; j < N; j++) {
				grid[i][j] = line[j] - '0';
			}
		}
		for (int[] row : solve(grid)) {
			System.out.println(Arrays.toString(row));
		}
		in.close();
	}
}