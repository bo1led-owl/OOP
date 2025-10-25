package sys.pro;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/** Adjacency list implementation of a graph. */
public class AdjListGraph implements Graph {
    private ArrayList<Integer> nodes;
    private ArrayList<ArrayList<Integer>> repr;

    /** Create an empty graph. */
    public AdjListGraph() {
        nodes = new ArrayList<Integer>();
        repr = new ArrayList<ArrayList<Integer>>();
    }

    /** Copy a graph to adjacency list representaion. */
    public AdjListGraph(Graph g) {
        this();

        g.nodes().forEach(n -> addNode(n));
        g.edges().forEach(e -> addEdge(e));
    }

    private int nodeIndex(Integer node) {
        return nodes.indexOf(node);
    }

    @Override
    public boolean hasNode(Integer node) {
        return nodes.contains(node);
    }

    @Override
    public void addNode(Integer i) {
        if (hasNode(i)) {
            return;
        }

        int firstEmpty = nodes.indexOf(-1);
        if (firstEmpty != -1) {
            nodes.set(firstEmpty, i);
            repr.set(firstEmpty, new ArrayList<Integer>());
        } else {
            nodes.add(i);
            repr.add(new ArrayList<Integer>());
        }
    }

    @Override
    public void removeNode(Integer i) {
        if (!hasNode(i)) {
            throw new NoSuchElementException();
        }

        int idx = nodeIndex(i);
        if (idx == nodes.size()) {
            nodes.remove(nodes.size() - 1);
            repr.remove(repr.size() - 1);
        } else {
            nodes.set(idx, -1);
            repr.set(idx, null);
        }

        repr.stream().filter(row -> row != null).forEach(row -> row.remove((Object) idx));
    }

    @Override
    public void addEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to)) {
            throw new NoSuchElementException();
        }

        int fromIdx = nodeIndex(e.from);
        int toIdx = nodeIndex(e.to);
        if (repr.get(fromIdx).contains(toIdx)) {
            return;
        }

        repr.get(fromIdx).add(toIdx);
    }

    @Override
    public void removeEdge(Edge e) {
        if (!hasNode(e.from)) {
            throw new NoSuchElementException();
        }

        int fromIdx = nodeIndex(e.from);
        int toIdx = nodeIndex(e.to);
        boolean deleted = repr.get(fromIdx).remove((Object) toIdx);
        if (!deleted) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Set<Integer> nodes() {
        return nodes.stream().filter(n -> n != -1).collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getNeighbours(Integer node) {
        if (!hasNode(node)) {
            throw new NoSuchElementException();
        }

        return repr.get(nodeIndex(node)).stream()
                .map(n -> nodes.get(n))
                .collect(Collectors.toSet());
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
