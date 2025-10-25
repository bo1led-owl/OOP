package sys.pro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/** Incidence matrix implementation of a graph. */
public class IncidenceMatrixGraph implements Graph {
    private ArrayList<Integer> nodes;
    private ArrayList<Edge> edges;

    // `null` is for edge that is not connected with the node, `true` if the edge enters the node,
    // `false` if leaves
    //
    // first dimension is nodes, second is edges
    private ArrayList<ArrayList<Boolean>> repr;

    /** Create an empty graph. */
    public IncidenceMatrixGraph() {
        nodes = new ArrayList<Integer>();
        edges = new ArrayList<Edge>();
        repr = new ArrayList<ArrayList<Boolean>>();
    }

    /** Copy a graph to incidence matrix representaion. */
    public IncidenceMatrixGraph(Graph g) {
        this();

        g.nodes().forEach(n -> addNode(n));
        g.edges().forEach(e -> addEdge(e));
    }

    private int nodeIndex(Integer node) {
        return nodes.indexOf(node);
    }

    @Override
    public boolean hasNode(Integer i) {
        return nodes.contains(i);
    }

    @Override
    public void addNode(Integer i) {
        if (hasNode(i)) {
            return;
        }

        nodes.add(i);
        repr.add(new ArrayList<Boolean>(Collections.nCopies(edges.size(), null)));
    }

    @Override
    public void removeNode(Integer i) {
        if (!hasNode(i)) {
            throw new NoSuchElementException();
        }

        int idx = nodeIndex(i);
        nodes.remove(idx);
        repr.remove(idx);

        int j = 0;
        while (j < edges.size()) {
            var edge = edges.get(j);
            if (edge.to.equals(i) || edge.from.equals(i)) {
                edges.remove(j);
                for (var row : repr) {
                    row.remove(j);
                }
            } else {
                j++;
            }
        }
    }

    @Override
    public void addEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to)) {
            throw new NoSuchElementException();
        }

        if (edges.contains(e)) {
            return;
        }

        edges.add(e);
        repr.forEach(row -> row.add(null));
        int edgeIndex = edges.size() - 1;

        repr.get(nodeIndex(e.from)).set(edgeIndex, false);
        repr.get(nodeIndex(e.to)).set(edgeIndex, true);
    }

    @Override
    public void removeEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to) || !edges.contains(e)) {
            throw new NoSuchElementException();
        }

        var i = edges.indexOf(e);
        edges.remove(i);
        repr.forEach(row -> row.remove(i));
    }

    @Override
    public Set<Integer> nodes() {
        return new HashSet<Integer>(nodes);
    }

    @Override
    public Set<Edge> edges() {
        return new HashSet<Edge>(edges);
    }

    @Override
    public Set<Integer> getNeighbours(Integer node) {
        if (!hasNode(node)) {
            throw new NoSuchElementException();
        }

        return edges.stream()
                .filter(e -> e.from.equals(node))
                .map(e -> e.to)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object that) {
        return eq(that);
    }

    @Override
    public boolean hasAnIncomingEdge(Integer target) {
        if (!hasNode(target)) {
            throw new NoSuchElementException();
        }

        return edges.stream().anyMatch(e -> e.to.equals(target));
    }

    @Override
    public String toString() {
        return str();
    }
}
