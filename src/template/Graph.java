package template;
import java.util.LinkedList;
import java.util.List;

public class Graph {
	public final int V;
	int E;
	public List<Edge>[] adj;

	@SuppressWarnings("unchecked")
	public Graph(int N) {
		this.V = N;
		adj = new LinkedList[N];
		for (int v = 0; v < N; v++)
			adj[v] = new LinkedList<Edge>();
	}

	public Graph(int[] from, int[] to, int[] wt, int N) {
		this(N);
		int E = to.length;
		for (int i = 0; i < E; i++) {
			addEdge(new Edge(from[i], to[i], wt[i]));
			addEdge(new Edge(to[i], from[i], wt[i]));
		}
	}

	public Graph(int[] from, int[] to, int N) {
		this(from, to, new int[to.length], N);
	}

	public Graph(Graph G) {
		this(G.V);
		this.E = G.E;
		for (int v = 0; v < G.V; v++)
			for (Edge e : G.adj[v])
				adj[v].add(e);
	}

	public void addEdge(Edge e) {
		int v = e.from;
		adj[v].add(e);
		E++;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " " + E + "\n");
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (Edge e : adj[v])
				s.append(e + "  ");
			s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		int V = 4;
		int[] to = { 1, 2, 1 };
		int[] from = { 2, 3, 3 };
		System.out.println(new Graph(from, to, V).toString());
	}

}