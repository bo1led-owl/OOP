package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EqualityTest {
    @Test
    void equalityTest() {
        var input = "1-2\n2-3\n3-4\n4-1";

        var adjList = Graph.<AdjListGraph>fromString(AdjListGraph::new, input);
        var adjMatrix = Graph.<AdjMatrixGraph>fromString(AdjMatrixGraph::new, input);
        var incidenceMatrix =
                Graph.<IncidenceMatrixGraph>fromString(IncidenceMatrixGraph::new, input);

        assertEquals(adjList, adjMatrix);
        assertEquals(adjList, incidenceMatrix);
        assertEquals(adjMatrix, incidenceMatrix);
    }
}
