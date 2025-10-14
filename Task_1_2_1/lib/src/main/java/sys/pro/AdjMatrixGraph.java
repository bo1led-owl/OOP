package sys.pro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

class AdjMatrixGraph implements Graph {
    private HashMap<Integer, HashSet<Integer>> repr;

    private AdjMatrixGraph(AdjMatrixGraph g) {
        repr = new HashMap<Integer, HashSet<Integer>>(g.repr);
    }

    public AdjMatrixGraph() {
        repr = new HashMap<Integer, HashSet<Integer>>();
    }

    @Override
    public void addNode(Integer i) {
        repr.put(i, new HashSet<Integer>());
    }

    @Override
    public void removeNode(Integer i) {
        repr.remove(i);
        for (HashSet<Integer> nodes : repr.values()) {
            nodes.remove(i);
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
        repr.get(e.from).remove(e.to);
    }

    @Override
    public Graph deepCopy() {
        return new AdjMatrixGraph(this);
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
