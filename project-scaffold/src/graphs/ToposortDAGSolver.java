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
    private final V start;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        this.start = start;
        List <V> result = new ArrayList<>();
        Set<V> visited = new HashSet<>();
        dfsPostOrder(graph, start, visited, result);
        Collections.reverse(result);
        edgeTo.put(start, null);
        distTo.put(start, 0.0);

        for(V vertex : result) {
            // each neighbor of a given vertex
            for (int i = 0; i <= graph.neighbors(vertex).size() - 1; i++) {
                Edge<V> edge = graph.neighbors(vertex).get(i);
                // figure out where the edge points
                V to = edge.to;
                // the old distance
                double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
                double newDist = distTo.get(vertex) + edge.weight;
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
        // check to make sure it isn't visited twice
        if(visited.contains(start)) {
            return;
        }
        List<Edge<V>> temp = graph.neighbors(start);
        visited.add(start);
        if (!temp.isEmpty()){
            for(Edge<V> e : temp) {
                V to = e.to;
                dfsPostOrder(graph, to, visited, result);
            }
            result.add(start);
        }
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
