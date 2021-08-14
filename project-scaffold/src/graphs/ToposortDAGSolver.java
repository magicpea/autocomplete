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

        // we took alot from DJ solver
        // We wonder if we need to make any other key changes?

        // gross unecessary structure
        ExtrinsicMinPQ<V> pq = new DoubleMapMinPQ<>();

        // Christmas tree topper node (The very first node):
        // add the start node (with its given edgeTo ordering, and distance to this node)
        pq.add(start, 0.0);
        edgeTo.put(start, null);
        distTo.put(start, 0.0);


//        while (!pq.isEmpty()) {
//            V from = pq.removeMin();
//            for (Edge<V> e : graph.neighbors(from)) {
//                V to = e.to;
//                double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
//                double newDist = distTo.get(from) + e.weight;
//                if (newDist < oldDist) {
//                    edgeTo.put(to, e);
//                    distTo.put(to, newDist);
//                    if (pq.contains(to)) {
//                        pq.changePriority(to, newDist);
//                    } else {
//                        pq.add(to, newDist);
//                    }
//                }
//            }
//            // comments marked for deletion later on
////            System.out.println("edge to: " + this.edgeTo.toString());
////            System.out.println("dist to: " + this.distTo.toString());
//             List <V> result = new ArrayList<>();
//             Set<V> visited = new HashSet<V>();
//
//             dfsPostOrder(graph, start, visited, result);
//            System.out.println("DFS post  result: " + result);


//        Initialize DFS post order params
//
//
        }


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
        System.out.println("we in this joint");

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
            if (!visited.contains(from)) {
                for (Edge<V> bro : graph.neighbors(from)) {
                    V to = bro.to;
                    perimeter.add(to);
                    // we added this try it out
                    result.add(to);
                }
                visited.add(from);
            }
        }

        // Debug code for deletion later
//        System.out.println("Visited: " + visited.toString());


        // 2. Return verticies in reverse DFS post-order

        // essentially take DFS and reverse at the end

        // Questions:

        // Why/Where are we calling DFS post order?

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
