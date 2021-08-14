package graphs;

import minpq.DoubleMapMinPQ;
import minpq.ExtrinsicMinPQ;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();

        System.out.println("Scalloped Potatoes");
        List <V> result = new ArrayList<>();
        Set<V> visited = new HashSet<>();

        dfsPostOrder(graph, start, visited, result);
        // initializing our starting nodes
        this.edgeTo.put(start, null);
        this.distTo.put(start, 0.0);
        V from = start;
//        for(V vertex : result) {
            // for every node, we want to update its connections with that postorder
            // maybe this operates on a single node ?
        int maxIndex = graph.neighbors(from).size();
//        for (Edge<V> edge : graph.neighbors(from)) {
        for (int i = 0; i <= graph.neighbors(from).size() - 1; i++) {
            Edge<V> edge = graph.neighbors(from).get(i);
                V to = edge.to;
                System.out.println("P1");
                double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
                System.out.println("P2");
                if(i == graph.neighbors(from).size() - 1) {
                    // we need to initialize distTo and edgeTo here
                    System.out.println("P3");
                    edgeTo.put(to, null);
                    System.out.println("P4");
                    distTo.put(to, oldDist);
                    System.out.println("P5");
                } else {
                    System.out.println("P6");
                    double newDist = distTo.get(from) + edge.weight;
                    System.out.println("P7");
                    // updating the shortest path
                    if(newDist < oldDist) {
                        System.out.println("P8");
                        edgeTo.put(to, edge);
                        System.out.println("P9");
                        distTo.put(to, newDist);
                        System.out.println("P10");
                    }
                }
                System.out.println("does this break?");
        }
        System.out.println("does THIS break?");
    }

    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, List<V> result) {
        // comment marked for deletion later
//        System.out.println("we in this joint");

        // we know we need to call collections.reverse at some point

        // 1. Run DFS (build a collection of verticies)
        // stores the remaining vertices to visit in the DFS
        Stack<V> perimeter = new Stack<V>();

        // stores the set of discovered vertices so we don't revisit them multiple times
//        Set<V> discovered = new Set<>();
        // kicking off our starting point by adding it to the perimeter
        perimeter.add(start);
        while (!perimeter.isEmpty()) {
            V from = perimeter.remove(0);
//            System.out.println(from.toString());
            if (!visited.contains(from)) {
                for (Edge<V> bro : graph.neighbors(from)) {
                    V to = bro.to;
                    perimeter.add(to);
                    // we added this try it out
//                    result.add(to);
//                    visited.add(from);
//                    result.add(from);
                }
                visited.add(from);
                result.add(from);
            }
        }
//        System.out.println("Visited nodes: " + visited);
//        System.out.println("Result nodes: " + result);
//        System.out.println("Before: " + result);
        Collections.reverse(result);
//        System.out.println("After: " + result);
        // Debug code for deletion later
//        System.out.println("Visited: " + visited.toString());
        // 2. Return verticies in reverse DFS post-order
        // essentially take DFS and reverse at the end
        // Questions:
        // How do we implement toposort objective 2 and 3
        // Is it just by building our DFS list of results?
        // then calling Collections.reverse
    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
