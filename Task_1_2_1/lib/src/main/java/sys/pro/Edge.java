package sys.pro;

/** Edge in a graph. */
public class Edge {
    /** Node that this edge is coming from. */
    public final Integer from;

    /** Node that this edge is coming to. */
    public final Integer to;

    /**
     * Create a new edge.
     *
     * @param from - source node.
     * @param to - destination node.
     */
    public Edge(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public int hashCode() {
        return (from << 7) ^ (to << 5);
    }

    @Override
    public String toString() {
        return from.toString() + " -> " + to.toString();
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Edge
                && ((Edge) that).from.equals(from)
                && ((Edge) that).to.equals(to);
    }
}
