package sys.pro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/** Incidence matrix implementation of a graph. */
public class IncidenceMatrixGraph implements Graph {
    // `boolean` is `true` iff an edge is going **from** a vertex
    private HashMap<Integer, HashMap<Edge, Boolean>> repr;

    private IncidenceMatrixGraph(IncidenceMatrixGraph g) {
        repr = new HashMap<Integer, HashMap<Edge, Boolean>>(g.repr);
    }

    /** Create an empty graph. */
    public IncidenceMatrixGraph() {
        repr = new HashMap<Integer, HashMap<Edge, Boolean>>();
    }

    @Override
    public void addNode(Integer i) {
        repr.put(i, new HashMap<Edge, Boolean>());
    }

    @Override
    public void removeNode(Integer i) {
        repr.remove(i);
        for (HashMap<Edge, Boolean> edges : repr.values()) {
            edges.entrySet().stream()
                    .filter(e -> e.getValue() && e.getKey().to == i)
                    .forEach(e -> edges.remove(e));
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
        repr.get(e.from).put(e, true);
        repr.get(e.to).put(e, false);
    }

    @Override
    public void removeEdge(Edge e) {
        if (!repr.containsKey(e.from)) {
            throw new NoSuchElementException("node " + e.from + " is not present");
        }
        if (!repr.containsKey(e.to)) {
            throw new NoSuchElementException("node " + e.to + " is not present");
        }
        repr.get(e.from).remove(e);
    }

    @Override
    public Graph deepCopy() {
        return new IncidenceMatrixGraph(this);
    }

    @Override
    public List<Integer> nodes() {
        return new ArrayList<Integer>(repr.keySet());
    }

    @Override
    public List<Integer> getNeighbours(Integer node) {
        return repr.get(node).entrySet().stream()
                .filter(e -> e.getValue())
                .map(e -> e.getKey().to)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object that) {
        return eq(that);
    }

    @Override
    public boolean hasAnIncomingEdge(Integer target) {
        HashMap<Edge, Boolean> edges = repr.get(target);
        return edges.values().stream().anyMatch(b -> !b);
    }

    @Override
    public String toString() {
        return str();
    }
}
