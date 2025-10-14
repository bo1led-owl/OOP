package sys.pro;

class Edge {
    public final Integer from;
    public final Integer to;

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
        return that instanceof Edge && ((Edge) that).from == from && ((Edge) that).to == to;
    }
}
