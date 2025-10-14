package sys.pro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/** Adjacency list implementation of a graph. */
public class AdjListGraph implements Graph {
    private HashMap<Integer, ArrayList<Integer>> repr;

    private AdjListGraph(AdjListGraph g) {
        repr = new HashMap<Integer, ArrayList<Integer>>(g.repr);
    }

    /** Create an empty graph. */
    public AdjListGraph() {
        repr = new HashMap<Integer, ArrayList<Integer>>();
    }

    @Override
    public void addNode(Integer i) {
        repr.put(i, new ArrayList<Integer>());
    }

    @Override
    public void removeNode(Integer i) {
        repr.remove(i);
        for (ArrayList<Integer> nodes : repr.values()) {
            nodes.removeIf(n -> n == i);
        }
    }

    @Override
    public void addEdge(Edge e) {
        if (!repr.containsKey(e.from)) {
            throw new NoSuchElementException("node " + e.from + " is not present");
        }
        if (!repr.containsKey(e.to)) {
            throw new NoSuchElementException("node " + e.to + " is not present");
        }
        repr.get(e.from).add(e.to);
    }

    @Override
    public void removeEdge(Edge e) {
        if (!repr.containsKey(e.from)) {
            throw new NoSuchElementException("node " + e.from + " is not present");
        }
        if (!repr.containsKey(e.to)) {
            throw new NoSuchElementException("node " + e.to + " is not present");
        }
        repr.get(e.from).removeIf(n -> n == e.to);
    }

    @Override
    public Graph deepCopy() {
        return new AdjListGraph(this);
    }

    @Override
    public List<Integer> nodes() {
        return new ArrayList<Integer>(repr.keySet());
    }

    @Override
    public List<Integer> getNeighbours(Integer node) {
        return new ArrayList<Integer>(repr.get(node));
    }

    @Override
    public boolean equals(Object that) {
        return eq(that);
    }

    @Override
    public String toString() {
        return str();
    }
}
