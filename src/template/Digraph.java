package template;
import java.util.LinkedList;
import java.util.List;

class Digraph {
	final int V;
	int E;
	List<Edge>[] adj;

	@SuppressWarnings("unchecked")
	public Digraph(int V) {
		this.V = V;
		adj = new LinkedList[V];
		for (int v = 0; v < V; v++)
			adj[v] = new LinkedList<Edge>();
	}

	public Digraph(int[] from, int[] to, int V, int[] wt) {
		this(V);
		int E = to.length;
		for (int i = 0; i < E; i++)
			addEdge(new Edge(to[i], from[i], wt[i]));
	}

	public Digraph(int[] from, int[] to, int V) {
		this(from, to, V, new int[to.length]);
	}

	public Digraph(Digraph G) {
		this(G.V - 1);
		this.E = G.E;
		for (int v = 0; v < G.V; v++)
			for (Edge e : G.adj[v])
				adj[v].add(e);
	}
	
	public Digraph reverse() {
		Digraph G = new Digraph(this.V);
		for (int v = 0; v < this.V; v++)
			for (Edge e : this.adj[v])
				G.addEdge(new Edge(e.to, e.from, e.weight));
		return G;
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
		Digraph g = new Digraph(from, to, V);
		System.out.println(g.toString());
		System.out.println(g.reverse().toString());
	}

}