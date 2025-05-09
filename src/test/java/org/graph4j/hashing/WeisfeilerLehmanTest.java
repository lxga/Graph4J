package org.graph4j.hashing;

import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeisfeilerLehmanTest {
    private String computeHash(Graph g, int iterations) {
        WeisfeilerLehmanHash wl = new WeisfeilerLehmanHash(g, iterations);
        return wl.hash();
    }

    @Test
    void testEmptyGraphHashNotNull() {
        Graph emptyGraph = GraphBuilder.empty().buildGraph();
        String h0 = computeHash(emptyGraph, 3);
        assertNotNull(h0, "Hash of an empty graph should not be null");
        assertFalse(h0.isEmpty(), "Hash of an empty graph should not be empty");
    }

    @Test
    void testSingleNodeGraph() {
        Graph singleNodeGraph = GraphBuilder.vertices(0).buildGraph();
        String h1a = computeHash(singleNodeGraph, 5);
        String h1b = computeHash(singleNodeGraph, 5);
        assertEquals(h1a, h1b, "Repeated hashing of the same single-node graph must be identical");
    }

    @Test
    void testIsomorphicGraphs() {
        Graph graphA = GraphBuilder.vertices(0,1,2,3,4).addEdges("0-1,0-3,0-2,1-2,2-4,3-4").buildGraph();
        Graph graphB = GraphBuilder.vertices(0,1,2,3,4).addEdges("0-2,0-4,1-3,1-4,2-3,2-4").buildGraph();
        String hA = computeHash(graphA, 5);
        String hB = computeHash(graphB, 5);
        assertEquals(hA, hB, "Isomorphic graphs should yield the same WL hash");
    }

    @Test
    void testNonIsomorphicGraphs() {
        Graph graphA = GraphBuilder.vertices(0,1,2,3,4).addEdges("0-1,0-3,0-2,1-2,2-4,3-4").buildGraph();
        Graph graphB = GraphBuilder.vertices(0,1,2,3,4).addEdges("0-2,0-4,1-3,1-4,2-3,2-4,0-1").buildGraph();
        String hTriangle = computeHash(graphA, 5);
        String hSquare   = computeHash(graphB, 5);
        assertNotEquals(hTriangle, hSquare, "Non isomorphic graphs should have different WL hashes");
    }
}
