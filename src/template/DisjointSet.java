package template;
import java.util.Arrays;

class DisjointSet {
	public int setCount;
	private int[] p;
	public DisjointSet(int n) {
		p = new int[n];
		Arrays.fill(p, -1);
		setCount = n;
	}
	public void union(int u, int v) {
		int uroot = root(u);
		int vroot = root(v);
		if (uroot == vroot) return;
		if (p[uroot] < p[vroot]) {
			p[uroot] += p[vroot];
			p[vroot] = uroot;
		}
		else {
			p[vroot] += p[uroot];
			p[uroot] = vroot;
		}
		setCount--;
	}
	public int root(int v) {
		if (p[v] < 0) return v;
		return p[v] = root(p[v]);
	}
	public int size(int v) {// size of set v
		return -p[root(v)];
	}
}