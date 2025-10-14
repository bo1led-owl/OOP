package sys.pro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/** Interface for graph operations. */
public interface Graph {
    /**
     * Parse a graph from string.
     *
     * @param <G> - target graph type.
     * @param supp - a supplier for graph type to parse.
     * @param input - input string.
     * @return parsed graph.
     */
    static <G extends Graph> Graph fromString(Supplier<G> supp, String input) {
        G res = supp.get();

        for (String[] edge :
                (Iterable<String[]>) input.lines().map(line -> line.split("-"))::iterator) {
            if (edge.length != 2) {
                throw new IllegalArgumentException("Expected an edge description");
            }
            Integer from = Integer.parseInt(edge[0].trim());
            Integer to = Integer.parseInt(edge[1].trim());
            res.addNode(from);
            res.addNode(to);
            res.addEdge(new Edge(from, to));
        }

        return res;
    }

    /**
     * Create a deep copy of this graph.
     *
     * @return the copied graph.
     */
    Graph deepCopy();

    /**
     * Add a new node to the graph.
     *
     * @param i - node index
     */
    void addNode(Integer i);

    /**
     * Remove a node from the graph.
     *
     * @param i - node to remove.
     */
    void removeNode(Integer i);

    /**
     * Add an edge to the graph. Throws `NoSuchElementException` if either source or destination
     * node was not in the graph.
     *
     * @param e - edge to add.
     */
    void addEdge(Edge e);

    /**
     * Remove an edge from the graph.
     *
     * @param e - edge to remove
     */
    void removeEdge(Edge e);

    /**
     * Get a list of all nodes of a graph.
     *
     * @return list of nodes of the graph
     */
    List<Integer> nodes();

    /**
     * Get neighbours of a node in the graph.
     *
     * @param node to find the neighbourhood for.
     * @return neighbourhood of a node.
     */
    List<Integer> getNeighbours(Integer node);

    /**
     * Get a list of all edges of a graph.
     *
     * @return edges of the graph.
     */
    default Set<Edge> edges() {
        Set<Edge> edges = new HashSet<>();
        var nodes = nodes();

        for (Integer node : nodes) {
            for (Integer neighbour : getNeighbours(node)) {
                edges.add(new Edge(node, neighbour));
            }
        }

        return edges;
    }

    /**
     * Check whether the graph has at least one edge.
     *
     * @return whether the predicate is true or not.
     */
    default boolean hasEdges() {
        return !edges().isEmpty();
    }

    /**
     * Check whether the node has an edge coming into it in the graph.
     *
     * @param target - node that edges should come into.
     * @return whether the predicate is true or not.
     */
    default boolean hasAnIncomingEdge(Integer target) {
        var nodes = nodes();

        for (Integer node : nodes) {
            for (Integer neighbour : getNeighbours(node)) {
                if (neighbour == target) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Topologically sort a graph.
     *
     * @return topologically ordered nodes of the graph.
     */
    default List<Integer> topologicalSort() {
        ArrayList<Integer> res = new ArrayList<Integer>();
        Graph g = deepCopy();

        List<Integer> nodes = g.nodes();
        Set<Integer> nodesWithoutAnIncomingEdge =
                nodes.stream().filter(n -> !g.hasAnIncomingEdge(n)).collect(Collectors.toSet());
        while (!nodesWithoutAnIncomingEdge.isEmpty()) {
            Integer n = nodesWithoutAnIncomingEdge.iterator().next();
            nodesWithoutAnIncomingEdge.remove(n);
            res.add(n);

            for (Integer m : g.getNeighbours(n)) {
                g.removeEdge(new Edge(n, m));
                if (!g.hasAnIncomingEdge(m)) {
                    nodesWithoutAnIncomingEdge.add(m);
                }
            }
        }

        if (g.hasEdges()) {
            throw new IllegalArgumentException("Graph had cycles");
        }

        return res;
    }

    /**
     * A helper for `equals`.
     *
     * @param that - object to compare to.
     * @return whether graph and `that` are equal.
     */
    default boolean eq(Object that) {
        return that instanceof Graph && ((Graph) edges()).equals(((Graph) that).edges());
    }

    /**
     * A helper for `toString`.
     *
     * @return a basic string representation of a graph.
     */
    default String str() {
        Set<Edge> edges = edges();
        String s = "";

        for (Edge e : edges) {
            s += e.toString() + "\n";
        }

        return s;
    }
}
