package sys.pro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/** Adjacency matrix implementation of a graph. */
public class AdjMatrixGraph implements Graph {
    private ArrayList<Integer> nodes;
    private ArrayList<ArrayList<Boolean>> repr;

    /** Create an empty graph. */
    public AdjMatrixGraph() {
        nodes = new ArrayList<Integer>();
        repr = new ArrayList<ArrayList<Boolean>>();
    }

    /** Copy a graph to adjacency matrix representaion. */
    public AdjMatrixGraph(Graph g) {
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

        nodes.add(i);
        repr.forEach(row -> row.add(false));
        repr.add(new ArrayList<Boolean>(Collections.nCopies(nodes.size(), false)));
    }

    @Override
    public void removeNode(Integer i) {
        if (!hasNode(i)) {
            throw new NoSuchElementException();
        }

        int idx = nodeIndex(i);
        nodes.remove(idx);
        repr.forEach(row -> row.remove(idx));
    }

    @Override
    public void addEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to)) {
            throw new NoSuchElementException();
        }

        repr.get(nodeIndex(e.from)).set(nodeIndex(e.to), true);
    }

    @Override
    public void removeEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to)) {
            throw new NoSuchElementException();
        }

        int fromIdx = nodeIndex(e.from);
        int toIdx = nodeIndex(e.to);
        if (!repr.get(fromIdx).get(toIdx)) {
            throw new NoSuchElementException();
        }

        repr.get(fromIdx).set(toIdx, false);
    }

    @Override
    public Set<Integer> nodes() {
        return new HashSet<Integer>(nodes);
    }

    @Override
    public Set<Integer> getNeighbours(Integer node) {
        if (!hasNode(node)) {
            throw new NoSuchElementException();
        }

        var row = repr.get(nodeIndex(node));
        var res = new HashSet<Integer>();

        for (int i = 0; i < row.size(); ++i) {
            if (row.get(i)) {
                res.add(nodes.get(i));
            }
        }

        return res;
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
