package template;
public class LCA {
	private int N, lgN, maxLevel;
	public  int[] level, parent;
	private int[][] ancestor;
	private Graph G;
	
	public LCA(Graph G, int N) {
		this.G = G;
		this.N = N;
		level = new int[N];
		parent = new int[N];
		bfs();
		lgN = 32 - Integer.numberOfLeadingZeros(maxLevel) + 2;
		ancestor = new int[lgN][N];
		ancestor[0] = parent.clone();
		for (int lgStep = 1; lgStep < lgN; lgStep++) {
			for (int i = 0; i < N; i++) {
				ancestor[lgStep][i] = ancestor[lgStep - 1][ancestor[lgStep - 1][i]];
			}
		}
	}
	
	private void bfs() {
		int head = 0, tail = 1;
		int[] q = new int[N];
		level[0] = 1;
		while (head < N) {
			int i = q[head++];
			maxLevel = level[i] + 1;
			for (Edge adj : G.adj[i]) {
				if (parent[i] != adj.to) {
					parent[adj.to] = i;
					level[adj.to] = maxLevel;
					q[tail++] = adj.to;
				}
			}
		}
	}
	
	public int lca(int p, int q) {
		if (level[p] < level[q]) {
			int temp = p;
			p = q;
			q = temp;
		}
		for (int i = lgN - 1; i >= 0; i--) {
			if (level[p] - (1 << i) >= level[q]) {
				p = ancestor[i][p];
			}
		}
		if (level[p] != level[q]) throw new IllegalStateException();
		for (int i = lgN - 1; i >= 0; i--) {
			if (ancestor[i][p] != ancestor[i][q]) {
				p = ancestor[i][p];
				q = ancestor[i][q];
			}
		}
		return p != q ? parent[p] : p;
	}
	
	public static void main(String[] args) {
		int[] from = {0, 1, 1, 1, 3, 3, 3, 6, 6,  7,  7, 10, 10, 12};
		int[] to = 	 {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
		int N = 15;
		Graph G = new Graph(from, to, N);
		LCA lca = new LCA(G, N);
		for (int i = 0; i < lca.lgN; i++)
			System.out.println(java.util.Arrays.toString(lca.ancestor[i]));
		System.out.println(lca.lca(8, 11));
		System.out.println(lca.lca(2, 14));
		System.out.println(lca.lca(5, 14));
	}

}
