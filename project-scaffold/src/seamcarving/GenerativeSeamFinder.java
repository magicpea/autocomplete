package seamcarving;

import graphs.Edge;
import graphs.Graph;
import graphs.ShortestPathSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Generative adjacency list graph single-source {@link ShortestPathSolver} implementation of the {@link SeamFinder}
 * interface.
 *
 * @see Graph
 * @see ShortestPathSolver
 * @see SeamFinder
 * @see SeamCarver
 */
public class GenerativeSeamFinder implements SeamFinder {
    /**
     * The constructor for the {@link ShortestPathSolver} implementation.
     */
    private final ShortestPathSolver.Constructor<Node> sps;

    /**
     * Constructs an instance with the given {@link ShortestPathSolver} implementation.
     *
     * @param sps the {@link ShortestPathSolver} implementation.
     */
    public GenerativeSeamFinder(ShortestPathSolver.Constructor<Node> sps) {
        this.sps = sps;
    }

    @Override
    public List<Integer> findSeam(Picture picture, EnergyFunction f) {
        PixelGraph graph = new PixelGraph(picture, f);
        List<Node> seam = sps.run(graph, graph.source).solution(graph.sink);
        seam = seam.subList(1, seam.size() - 1); // Skip the source and sink nodes
        List<Integer> result = new ArrayList<>(seam.size());
        for (Node pixel : seam) {
            result.add(((PixelGraph.Pixel) pixel).y);
        }
        return result;
    }

    /**
     * Generative adjacency list graph of {@link Pixel} vertices and {@link EnergyFunction}-weighted edges. Rather than
     * materialize all vertices and edges upfront in the constructor, generates vertices and edges as needed when
     * {@link #neighbors(Node)} is called by a client.
     *
     * @see Pixel
     * @see EnergyFunction
     */
    private static class PixelGraph implements Graph<Node> {
        /**
         * The {@link Picture} for {@link #neighbors(Node)}.
         */
        private final Picture picture;
        /**
         * The {@link EnergyFunction} for {@link #neighbors(Node)}.
         */
        private final EnergyFunction f;
        /**
         * Source {@link Node} for the adjacency list graph.
         */
        private final Node source = new Node() {
            @Override
            // YES WE ALSO NEED TO DO THIS METHOD
            // Giga chad whole grain grind set quinoa warrior
            // source neighbors are the surrounding nodes of the source nodes
            public List<Edge<Node>> neighbors(Picture picture, EnergyFunction f) {
                List<Edge<Node>> result = new ArrayList<>(picture.height());
                for (int j = 0; j < picture.height(); j += 1) {
                    Pixel to = new Pixel(0, j);
                    result.add(new Edge<Node>(this, to, f.apply(picture, 0, j)));
                }
                return result;
            }
        };
        /**
         * Sink {@link Node} for the adjacency list graph.
         */
        // YES WE NEED TO DO THIS
        // sink neighbors, gives the list of neighbors at the end of the line
        // there to fill the empty void of being lonely and having no neighbors
        // this is what happens when you live off the grid
        // returns list.of b/c we are at the end of the picture (sink nodes)
        private final Node sink = new Node() {
            @Override
            public List<Edge<Node>> neighbors(Picture picture, EnergyFunction f) {
//                System.out.println("Here we go!");
                return List.of(); // Sink has no neighbors
                // yup!
            }
        };

        /**
         * Constructs a generative adjacency list graph. All work is deferred to implementations of
         * {@link Node#neighbors(Picture, EnergyFunction)}.
         *
         * @param picture the input picture.
         * @param f       the input energy function.
         */
        private PixelGraph(Picture picture, EnergyFunction f) {
            this.picture = picture;
            this.f = f;
        }

        // part of the PixelGraph class
        // we are finding the neighbors of a given pixel node
        @Override
        public List<Edge<Node>> neighbors(Node node) {
            // we are calling the method that appears later down the line in PixelGraph
            // the parameters are the instance variables of picture and f
            // we are applying the neighbors method to the passed-in node
            // expecting to receive a list of neighbors of the given node
             return node.neighbors(picture, f);
        }

        /**
         * A pixel in the {@link PixelGraph} representation of the {@link Picture} with {@link EnergyFunction}-weighted
         * edges to neighbors.
         *
         * @see PixelGraph
         * @see Picture
         * @see EnergyFunction
         */
        public class Pixel implements Node {
            private final int x;
            private final int y;

            /**
             * Constructs a pixel representing the (<i>x</i>, <i>y</i>) indices in the picture.
             *
             * @param x horizontal index into the picture.
             * @param y vertical index into the picture.
             */
            public Pixel(int x, int y) {
                this.x = x;
                this.y = y;
            }
            // these were our initial ideas :
            // YES DO THIS ADD STUFF HERE
            // So this is the scanning neighbors for middle pixels (inspired from pixel graph)
            // Does this method decompose into our sink and source, neighbor methods?
            // sink - no neighbors (right most pixels)
            // source- deals with pixel edges as list
            // general neighbors -
            // three neighbors methods all in pixelgraph?
            // or only scans middle?

            // this method takes in the parameters picture and f
            // this method is called above in the PixelGraph neighbors constructor method
            // the instance variables picture and f are passed in
            // the method finds neighbors given just the picture info
            // the method should return a given node's neighbors
            // contains the large data structure : a list of all the nodes' neighbors

            // Questions :
            // So...we aren't sure if this is what we should be returning...all up in the air still
            // Do we want neighbors for everything, or just one node ?
            // How do we access the node's information from inside of the Pixel class ?

            // Our primary concerns :
            // 1. What exactly are we returning ? All neighbors ? Node's neighbors ? How do we reference node ?
                // is the index of (x, y) the only way we can access a node's info ?
            // 2. At what point in the building of our neighborhood do we reach the fence ?
                // (when do we break our index ?)

            // Pixel Neighbors
            @Override
            public List<Edge<Node>> neighbors(Picture picture, EnergyFunction f) {
                List<Edge<Node>> result = new ArrayList<>(3);

                    // if we are out of bounds horizontally
                if (x == picture.width() -1) {
                    return List.of( new Edge<>(this, sink, 0));
                }
                for (int z = y - 1; z <= y + 1; z += 1) {
                    // Only if the neighbor is in the bounds of the picture.
                    if (0 <= z && z < picture.height()) {
                        Pixel to = new Pixel(x + 1, z);
                        result.add(new Edge<>(this, to, f.apply(picture, x + 1, z)));
//                                System.out.println("Neighbors : " + result);
                     }

                }
                return result;
            }

            @Override
            public String toString() {
                return "(" + x + ", " + y + ")";
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                } else if (!(o instanceof Pixel)) {
                    return false;
                }
                Pixel other = (Pixel) o;
                return this.x == other.x && this.y == other.y;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }
        }
    }
}
