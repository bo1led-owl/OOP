package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

abstract class GenericGraphTest<G extends Graph> {
    abstract Supplier<G> getSupplier();

    final Graph newGraph(String from) {
        return Graph.<G>fromString(getSupplier(), from);
    }

    @Test
    final void fromString() {
        var g = newGraph("1-2\n2-6\n3-6\n7-3\n7-2");

        var expectedNodes = new HashSet<Integer>();
        expectedNodes.add(1);
        expectedNodes.add(2);
        expectedNodes.add(3);
        expectedNodes.add(6);
        expectedNodes.add(7);
        assertEquals(expectedNodes, g.nodes());

        var expectedEdges = new HashSet<Edge>();
        expectedEdges.add(new Edge(1, 2));
        expectedEdges.add(new Edge(2, 6));
        expectedEdges.add(new Edge(3, 6));
        expectedEdges.add(new Edge(7, 3));
        expectedEdges.add(new Edge(7, 2));
        assertEquals(expectedEdges, g.edges());
    }

    @Test
    final void addNode() {
        var g = newGraph("1-3\n4-2");

        var nodes = g.nodes();
        var edges = g.edges();
        g.addNode(3);
        assertEquals(nodes, g.nodes());
        assertEquals(edges, g.edges());

        g.addNode(6);
        nodes.add(6);
        assertEquals(nodes, g.nodes());
        assertEquals(edges, g.edges());
    }

    @Test
    final void removeNode() {
        var g = newGraph("1-3\n4-2\n5-2");

        var nodes = g.nodes();
        nodes.remove(2);
        var edges = g.edges();
        edges.remove(new Edge(4, 2));
        edges.remove(new Edge(5, 2));

        g.removeNode(2);
        assertEquals(nodes, g.nodes());
        assertEquals(edges, g.edges());

        assertThrows(NoSuchElementException.class, () -> g.removeNode(100500));
    }

    @Test
    final void replaceNode() {
        var g = newGraph("1-3\n4-2\n5-2");

        var nodes = g.nodes();
        nodes.remove(2);
        var edges = g.edges();
        edges.remove(new Edge(4, 2));
        edges.remove(new Edge(5, 2));

        g.removeNode(2);
        assertEquals(nodes, g.nodes());
        assertEquals(edges, g.edges());

        nodes = g.nodes();
        edges = g.edges();
        g.addNode(6);
        nodes.add(6);
        assertEquals(nodes, g.nodes());
        assertEquals(edges, g.edges());
    }

    @Test
    final void addEdge() {
        var g = newGraph("1-3\n4-2");

        var nodes = g.nodes();
        var edges = g.edges();

        var newEdge = new Edge(3, 2);

        edges.add(newEdge);
        g.addEdge(newEdge);

        assertEquals(nodes, g.nodes());
        assertEquals(edges, g.edges());

        assertThrows(NoSuchElementException.class, () -> g.addEdge(new Edge(1, 5)));
        assertThrows(NoSuchElementException.class, () -> g.addEdge(new Edge(5, 1)));
    }

    @Test
    final void removeEdge() {
        var g = newGraph("1-3\n4-2\n5-2");

        var nodes = g.nodes();
        var edges = g.edges();

        var edgeToRemove = new Edge(4, 2);

        edges.remove(edgeToRemove);
        g.removeEdge(edgeToRemove);

        assertEquals(nodes, g.nodes());
        assertEquals(edges, g.edges());

        assertThrows(NoSuchElementException.class, () -> g.removeEdge(new Edge(2, 4)));
    }

    @Test
    final void getNeighbours() {
        var g = newGraph("1-3\n1-2\n4-1");

        assertThrows(NoSuchElementException.class, () -> g.getNeighbours(5));

        var neighbours = new HashSet<Integer>();
        neighbours.add(3);
        neighbours.add(2);

        assertEquals(neighbours, g.getNeighbours(1));
    }

    @Test
    final void hasAnIncomingEdge() {
        var g = newGraph("1-3\n1-2\n4-1");

        assertTrue(g.hasAnIncomingEdge(1));
        assertFalse(g.hasAnIncomingEdge(4));

        assertThrows(NoSuchElementException.class, () -> g.hasAnIncomingEdge(5));
    }

    @Test
    final void topologicalSort() {
        var g = newGraph("1-2\n1-3\n2-3\n3-4");

        var topsort = g.topologicalSort();

        var correct = Arrays.asList(new Integer[] {1, 2, 3, 4});
        assertIterableEquals(correct, topsort);

        var graphWithLoop = newGraph("1-2\n2-3\n3-1");
        assertThrows(IllegalArgumentException.class, () -> graphWithLoop.topologicalSort());
    }
}
