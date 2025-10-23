package sys.pro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/** Adjacency matrix implementation of a graph. */
public class AdjMatrixGraph implements Graph {
    private boolean[][] repr;

    private AdjMatrixGraph(AdjMatrixGraph g) {
        repr = new boolean[g.repr.length][];
        for (int i = 0; i < g.repr.length; ++i) {
            repr[i] = (g.repr[i] == null) ? null : g.repr[i].clone();
        }
    }

    /** Create an empty graph. */
    public AdjMatrixGraph() {
        repr = new boolean[][] {};
    }

    @Override
    public boolean hasNode(Integer node) {
        return node < repr.length && repr[node] != null;
    }

    @Override
    public void addNode(Integer i) {
        if (hasNode(i)) {
            return;
        }

        if (i >= repr.length) {
            repr = Arrays.copyOf(repr, i + 1);
            for (int j = 0; j < repr.length; ++j) {
                if (repr[j] == null) {
                    continue;
                }
                repr[j] = Arrays.copyOf(repr[j], i + 1);
            }
        }

        repr[i] = new boolean[repr.length];
    }

    @Override
    public void removeNode(Integer i) {
        if (!hasNode(i)) {
            throw new NoSuchElementException();
        }

        repr[i] = null;
        for (int j = 0; j < repr.length; ++j) {
            if (repr[j] == null) {
                continue;
            }
            repr[j][i] = false;
        }
    }

    @Override
    public void addEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to)) {
            throw new NoSuchElementException();
        }

        repr[e.from][e.to] = true;
    }

    @Override
    public void removeEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to)) {
            throw new NoSuchElementException();
        }

        repr[e.from][e.to] = false;
    }

    @Override
    public Graph deepCopy() {
        return new AdjMatrixGraph(this);
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
    public Set<Integer> getNeighbours(Integer node) {
        if (!hasNode(node)) {
            throw new NoSuchElementException();
        }

        var row = repr[node];
        var res = new HashSet<Integer>();

        for (int i = 0; i < row.length; ++i) {
            if (row[i]) {
                res.add(i);
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
