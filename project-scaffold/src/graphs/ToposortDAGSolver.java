package graphs;
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
//        System.out.println("Scalloped Potatoes");
        List <V> result = new ArrayList<>();
        Set<V> visited = new HashSet<>();
        dfsPostOrder(graph, start, visited, result);
        // initializing our starting nodes
        this.edgeTo.put(start, null);
        this.distTo.put(start, 0.0);
        V from = start;
        // TA Q:
        // Potential for loop we thought about using
        // not sure how to iterate our for loop what is the mood?
//        for(V vertex : result) {
            // for every node, we want to update its connections with that postorder
            // maybe this operates on a single node ?

        // we did this to experiment
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
                    edgeTo.put(to, null);
                    distTo.put(to, oldDist);
                } else {
                    double newDist = distTo.get(from) + edge.weight;
                    // updating the shortest path
                    if(newDist < oldDist) {
                        edgeTo.put(to, edge);
                        distTo.put(to, newDist);
                    }
                }
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
                    // TA Q:
                    // how do we update these structures so they make sense
//                    result.add(to);
//                    visited.add(from);
//                    result.add(from);
                }
                visited.add(from);
                result.add(from);
            }
        }

        // TA Q:
        // building DFSpost order Questions:
        // What's the deal ok?
        // Index out of bounds? IndexFrom(1) > IndexTo(0)
        // what the heck is going on?
        // for real tho we are trying to visualize this and it seems
        // once we reach the end of our graph we can't access edge and vertice information
        // for the last column of pixels?

        // whats a good strategy

        // When do we call this key command:
        // Is it after we've ran and built or DFSpostOrder list?
        // when does that happen in the method?
//        Collections.reverse(result);

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
