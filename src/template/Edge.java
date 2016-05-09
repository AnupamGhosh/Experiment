package template;
public class Edge implements Comparable<Edge> { 
    public final int from;
	public final int to;
	public final int weight;

    public Edge(int v, int w, int weight) {
        this.from = v;
        this.to = w;
        this.weight = weight;
    }

    public int compareTo(Edge that) {
        if      (this.weight < that.weight) return -1;
        else if (this.weight > that.weight) return +1;
        else                                return  0;
    }

    public String toString() {
        return String.format("%d->%d %d", from, to, weight);
    }

    public static void main(String[] args) {
        Edge e = new Edge(12, 23, 3);
        System.out.println(e);
    }
}