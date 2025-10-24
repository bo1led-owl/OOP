package sys.pro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/** Adjacency list implementation of a graph. */
public class AdjListGraph implements Graph {
    private ArrayList<Integer>[] repr;

    private AdjListGraph(AdjListGraph g) {
        repr = Arrays.<ArrayList<Integer>>copyOf(g.repr, g.repr.length);
    }

    /** Create an empty graph. */
    public AdjListGraph() {
        repr = new ArrayList[0];
    }

    @Override
    public boolean hasNode(Integer node) {
        return node < repr.length && repr[node] != null;
    }

    @Override
    public void addNode(Integer i) {
        if (i < repr.length && repr[i] != null) {
            return;
        }

        if (i >= repr.length) {
            repr = Arrays.copyOf(repr, i + 1);
        }
        repr[i] = new ArrayList<Integer>();
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
            repr[j].removeIf(n -> n.equals(i));
        }
    }

    @Override
    public void addEdge(Edge e) {
        if (!hasNode(e.from) || !hasNode(e.to)) {
            throw new NoSuchElementException();
        }

        if (repr[e.from].contains(e.to)) {
            return;
        }

        repr[e.from].add(e.to);
    }

    @Override
    public void removeEdge(Edge e) {
        if (!hasNode(e.from)) {
            throw new NoSuchElementException();
        }

        boolean deleted = repr[e.from].remove((Object) e.to);
        if (!deleted) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Graph deepCopy() {
        return new AdjListGraph(this);
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
        return new HashSet<Integer>(repr[node]);
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
