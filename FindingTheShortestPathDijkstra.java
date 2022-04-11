/**
 *Implemented using Sedgewick and Waynes and William Fiset version found here https://algs4.cs.princeton.edu/52trie/TST.java.html
 *For the Priority Queue & DijkstraSP
 */

import static java.lang.Math.*;
import java.util.*;

public class FindingTheShortestPathDijkstra {

    // An edge class to represent a directed edge
    // between two nodes with a certain cost.
    public static class Edge {
        int to;
        double cost;

        public Edge(int to, double cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    //Instance Variables
    private final int n;
    private int edgeCount;
    private double[] dist;
    private Integer[] prev;
    private List < List < Edge >> graph;

    //Constructor Declaration of Class
    public FindingTheShortestPathDijkstra(int n) {
        this.n = n;
        graph = new ArrayList < > (n);
        for (int i = 0; i < n; i++) graph.add(new ArrayList < > ());
    }

    public void addEdge(int from, int to, int cost) {
        edgeCount++;
        graph.get(from).add(new Edge(to, cost));
    }

    public double dijkstra(int start, int end) {
        int degree = edgeCount / n;
        IndexMinPQ < Double > ipq = new IndexMinPQ < > (degree, n);
        ipq.insert(start, 0.0);

        dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0.0;

        boolean[] visited = new boolean[n];
        prev = new Integer[n];

        while (!ipq.isEmpty()) {
            int nodeId = ipq.peekMinKeyIndex();

            visited[nodeId] = true;
            double minValue = ipq.pollMinValue();

            if (minValue > dist[nodeId]) continue;

            for (Edge edge: graph.get(nodeId)) {

                if (visited[edge.to]) continue;

                double newDist = dist[nodeId] + edge.cost;
                if (newDist < dist[edge.to]) {
                    prev[edge.to] = nodeId;
                    dist[edge.to] = newDist;
                    if (!ipq.contains(edge.to)) ipq.insert(edge.to, newDist);
                    else ipq.decrease(edge.to, newDist);
                }
            }
            if (nodeId == end) return dist[end];
        }
        return Double.POSITIVE_INFINITY;
    }

    // Provides list of vertices from the shortest path
    public List < Integer > reconstructPath(int start, int end) {
        if (end < 0 || end >= n) throw new IllegalArgumentException("Invalid node index");
        if (start < 0 || start >= n) throw new IllegalArgumentException("Invalid node index");
        List < Integer > path = new ArrayList < > ();
        double dist = dijkstra(start, end);
        if (dist == Double.POSITIVE_INFINITY) return path;
        for (Integer at = end; at != null; at = prev[at]) path.add(at);
        Collections.reverse(path);
        return path;
    }

    // IndexMinPQ
    private static class IndexMinPQ < T extends Comparable < T >> {
        private int current;
        private final int maxN;
        private final int degreeOfNode;
        private final int[] child,
        parent;
        public final int[] pq;
        public final int[] qp;
        public final Object[] keys;


        public IndexMinPQ(int degree, int maxSize) {
            if (maxSize <= 0) throw new IllegalArgumentException("maxSize <= 0");

            degreeOfNode = max(2, degree);
            maxN = max(degreeOfNode + 1, maxSize);

            qp = new int[maxN];
            pq = new int[maxN];
            child = new int[maxN];
            parent = new int[maxN];
            keys = new Object[maxN];

            for (int i = 0; i < maxN; i++) {
                parent[i] = (i - 1) / degreeOfNode;
                child[i] = i * degreeOfNode + 1;
                pq[i] = qp[i] = -1;
            }
        }

        public boolean isEmpty() {
            return current == 0;
        }

        public boolean contains(int i) {
            if (i < 0 || i >= maxN) {
                throw new IllegalArgumentException("Key index out of bounds; received: " + i);
            }
            return pq[i] != -1;
        }

        public int peekMinKeyIndex() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            return qp[0];
        }

        @SuppressWarnings("unchecked")
        public T peekMinValue() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            return (T) keys[qp[0]];
        }

        public T pollMinValue() {
            T minValue = peekMinValue();
            delete(peekMinKeyIndex());
            return minValue;
        }

        public void insert(int i, T value) {
            if (contains(i)) throw new IllegalArgumentException("index already exists; received: " + i);
            if (value == null) throw new IllegalArgumentException("value cannot be null");
            pq[i] = current;
            qp[current] = i;
            keys[i] = value;
            swim(current++);
        }

        @SuppressWarnings("unchecked")
		public T delete(int index) {
            if (!contains(index)) throw new NoSuchElementException("Index does not exist; received: " + index);
            final int i = pq[index];
            swap(i, --current);
            sink(i);
            swim(i);
            T value = (T) keys[index];
            keys[index] = null;
            pq[index] = -1;
            qp[current] = -1;
            return value;
        }

        public void decrease(int index, T value) {
            if (!contains(index)) throw new NoSuchElementException("Index does not exist; received: " + index);
            if (value == null) throw new IllegalArgumentException("value cannot be null");
            if (less(value, keys[index])) {
                keys[index] = value;
                swim(pq[index]);
            }
        }

        private void sink(int i) {
            for (int j = minium(i); j != -1;) {
                swap(i, j);
                i = j;
                j = minium(i);
            }
        }

        private void swim(int i) {
            while (less(i, parent[i])) {
                swap(i, parent[i]);
                i = parent[i];
            }
        }

        private int minium(int i) {
            int index = -1, from = child[i], to = min(current, from + degreeOfNode);
            for (int j = from; j < to; j++)
                if (less(j, i)) index = i = j;
            return index;
        }

        private void swap(int i, int j) {
            pq[qp[j]] = i;
            pq[qp[i]] = j;
            int tmp = qp[i];
            qp[i] = qp[j];
            qp[j] = tmp;
        }

        // Comparable tests
        @SuppressWarnings("unchecked")
        private boolean less(int i, int j) {
            return ((Comparable <? super T > ) keys[qp[i]]).compareTo((T) keys[qp[j]]) < 0;
        }

        @SuppressWarnings("unchecked")
        private boolean less(Object obj1, Object obj2) {
            return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
        }

        @Override
        public String toString() {
            List < Integer > lst = new ArrayList < > (current);
            for (int i = 0; i < current; i++) lst.add(qp[i]);
            return lst.toString();
        }
    }
}