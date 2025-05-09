package org.graph4j.hashing;

import org.graph4j.Graph;
import org.graph4j.NeighborIterator;
import org.graph4j.SimpleGraphAlgorithm;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WeisfeilerLehmanHash extends SimpleGraphAlgorithm implements SimpleGraphHashingAlgorithm {
    private final int iterations;
    private final MessageDigest digest;

    public WeisfeilerLehmanHash(Graph graph, int iterations) {
        super(graph);
        this.iterations = iterations;

        try {
            this.digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    @Override
    public String hash() {
        int n = graph.numVertices();
        int[] labels = new int[n];

        // run WL relabeling
        for (int it = 0; it < iterations; it++) {
            labels = calculateNewLabels(n, labels);
            Arrays.sort(labels);
        }

        // convert int[] -> byte[] for hashing
        byte[] labelBytes = toByteArray(labels);
        byte[] hashBytes = digest.digest(labelBytes);

        return bytesToHex(hashBytes);
    }

    private static byte[] toByteArray(int[] array) {
        ByteBuffer bb = ByteBuffer.allocate(array.length * Integer.BYTES);
        for (int x : array) {
            bb.putInt(x);
        }
        return bb.array();
    }

    private static String bytesToHex(byte[] bytes) {
        // simple hex encoding
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    private int[] calculateNewLabels(int n, int[] oldLabels) {
        int[] newLabels = new int[n];
        Map<String, Integer> labelMap = new HashMap<>();
        AtomicInteger nextLabel = new AtomicInteger();

        for (int v = 0; v < n; v++) {
            // collect neighbor labels
            List<Integer> neighLabels = new ArrayList<>();
            NeighborIterator vi = graph.neighborIterator(v);
            while (vi.hasNext()) {
                int u = vi.next();
                neighLabels.add(oldLabels[u]);
            }
            Collections.sort(neighLabels);

            // build the WL “string”: own label + sorted neighbor labels
            StringBuilder sb = new StringBuilder();
            sb.append(oldLabels[v]);
            for (int l : neighLabels) {
                sb.append("|").append(l);
            }
            String key = sb.toString();

            // assign or reuse a small integer
            newLabels[v] = labelMap.computeIfAbsent(key, k -> nextLabel.getAndIncrement());
        }

        return newLabels;
    }
}
