package sys.pro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/** Incidence matrix implementation of a graph. */
public class IncidenceMatrixGraph implements Graph {
    private ArrayList<Edge> edges;

    // `null` is for edge that is not connected with the node, `true` if the edge enters the node,
    // `false` if leaves
    //
    // first dimension is nodes, second is edges
    private Boolean[][] repr;

    private IncidenceMatrixGraph(IncidenceMatrixGraph g) {
        edges = new ArrayList<Edge>(g.edges);
        repr = new Boolean[g.repr.length][];
        for (int i = 0; i < g.repr.length; ++i) {
            repr[i] = (g.repr[i] == null) ? null : g.repr[i].clone();
        }
    }

    /** Create an empty graph. */
    public IncidenceMatrixGraph() {
        edges = new ArrayList<Edge>();
        repr = new Boolean[0][0];
    }

    @Override
    public boolean hasNode(Integer i) {
        return i < repr.length && repr[i] != null;
    }

    @Override
    public void addNode(Integer i) {
        if (hasNode(i)) {
            return;
        }

        if (i >= repr.length) {
            repr = Arrays.copyOf(repr, i + 1);
        }

        repr[i] = new Boolean[edges.size()];
    }

    @Override
    public void removeNode(Integer i) {
        if (!hasNode(i)) {
            throw new NoSuchElementException();
        }

        repr[i] = null;
        for (int j = 0; j < edges.size(); ++j) {
            var edge = edges.get(j);
            if (edge == null) {
                continue;
            }

            if (edge.from == i || edge.to == i) {
                edges.set(j, null);
            }
        }
    }

    @Override
    public void addEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to)) {
            throw new NoSuchElementException();
        }

        var edgeIndex = edges.indexOf(e);
        if (edgeIndex == -1) {
            edgeIndex = edges.indexOf(null);
            if (edgeIndex == -1) {
                edges.add(e);
                edgeIndex = edges.size() - 1;

                for (int i = 0; i < repr.length; ++i) {
                    if (repr[i] == null) {
                        continue;
                    }
                    repr[i] = Arrays.copyOf(repr[i], edges.size());
                }
            } else {
                edges.set(edgeIndex, e);
            }
        }

        repr[e.from][edgeIndex] = false;
        repr[e.to][edgeIndex] = true;
    }

    @Override
    public void removeEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to) || !edges.contains(e)) {
            throw new NoSuchElementException();
        }

        var i = edges.indexOf(e);
        edges.remove(i);
        repr[e.from][i] = null;
        repr[e.to][i] = null;
    }

    @Override
    public Graph deepCopy() {
        return new IncidenceMatrixGraph(this);
    }

    @Override
    public Set<Integer> nodes() {
        var res = new HashSet<Integer>();

        for (int i = 0; i < repr.length; ++i) {
            if (hasNode(i)) {
                res.add(i);
            }
        }

        return res;
    }

    @Override
    public Set<Edge> edges() {
        return edges.stream().filter(e -> e != null).collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getNeighbours(Integer node) {
        if (!hasNode(node)) {
            throw new NoSuchElementException();
        }

        return edges.stream()
                .filter(e -> e.from == node)
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

        return edges.stream().anyMatch(e -> e.to == target);
    }

    @Override
    public String toString() {
        return str();
    }
}
