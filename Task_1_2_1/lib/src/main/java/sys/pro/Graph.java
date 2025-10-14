package sys.pro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

interface Graph {
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

    Graph deepCopy();

    void addNode(Integer i);

    void removeNode(Integer i);

    void addEdge(Edge e);

    void removeEdge(Edge e);

    List<Integer> nodes();

    List<Integer> getNeighbours(Integer node);

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

    default boolean hasEdges() {
        return !edges().isEmpty();
    }

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

    default boolean eq(Object that) {
        return that instanceof Graph && ((Graph) edges()).equals(((Graph) that).edges());
    }

    default String str() {
        Set<Edge> edges = edges();
        String s = "";

        for (Edge e : edges) {
            s += e.toString() + "\n";
        }

        return s;
    }
}
