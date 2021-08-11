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
            public List<Edge<Node>> neighbors(Picture picture, EnergyFunction f) {
                List<Edge<Node>> result = new ArrayList<>(picture.height());
                for (int j = 0; j < picture.height(); j += 1) {
                    Pixel to = new Pixel(0, j);
                    result.add(new Edge<>(source, to, f.apply(picture, 0, j)));
                }
                System.out.println("Result is this big : " + result.size());
                return result;
            }
        };
        /**
         * Sink {@link Node} for the adjacency list graph.
         */
        // YES WE NEED TO DO THIS
        private final Node sink = new Node() {
            @Override
            public List<Edge<Node>> neighbors(Picture picture, EnergyFunction f) {
                System.out.println("Here we go!");
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

        // DO NOT OVERRIDE THIS METHOD
        @Override
        public List<Edge<Node>> neighbors(Node node) {
            List<Edge<Node>> result = new ArrayList<>();
            // essentially  this one is looking up, looking forward, looking down from a given node which contains this info
            System.out.println(node);

//            for (int z = y - 1; z <= y + 1; z += 1)
//                // Only if the neighbor is in the bounds of the picture.
//                if (0 <= z && z < picture.height()) {
//                    result.add(new Edge<>(from, to, f.apply(picture, x + 1, z)));
//                }
//            }
            return result;
            // return node.neighbors(picture, f);
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


            // YES DO THIS ADD STUFF HERE
            // So this is the scanning neighbors for middle pixels (inspired from pixel graph)
            // Does this method decompose into our sink and source, neighbor methods?

            // sink - no neighbors (right most pixels)

            // source- deals with pixel edges as list

            // general neighbors -

            // three neighbors methods all in pixelgraph?

            // or only scans middle?
            @Override
            public List<Edge<Node>> neighbors(Picture picture, EnergyFunction f) {
                // Pixel[][] pixels = new Pixel[picture.width()][picture.height()];
                List<Edge<Node>> result = new ArrayList<>(picture.height());
                // Starting from the rightmost column, each pixel has only a single edge to the sink (with 0 weight).
//                for (int y = 0; y < picture.height(); y += 1) {
//                    Pixel from = new Pixel(picture.width() - 1, y);
//                    // pixels[picture.width() - 1][y] = from;
//                    result.add(new Edge<>(from, sink, 0));
//                }
//                // Starting from the next-rightmost column...
//                for (int x = picture.width() - 2; x >= 0; x -= 1) {
//                    // Consider each pixel in the column...
//                    for (int y = 0; y < picture.height(); y += 1) {
//                        Pixel from = new Pixel(x, y);
//                        // pixels[x][y] = from;
//                        // Connect the pixel to its right-up, right-middle, and right-down neighbors...
//                        for (int z = y - 1; z <= y + 1; z += 1) {
//                            // Only if the neighbor is in the bounds of the picture.
//                            if (0 <= z && z < picture.height()) {
//                                Pixel to = pixels[x + 1][z];
//                                result.add(new Edge<>(from, to, f.apply(picture, x + 1, z)));
//                            }
//                        }
//                    }
//                }
//                return result;


                // seam finder gives the list of pixels you want to find neighbors for

                // we need to call neighbors / implement

                // to add the neighbors of each pixel in that original seamfinder

                // iterate over seamfinder call

                // if statement from pixel

                // add them

//                for (int z = y - 1; z <= y + 1; z += 1) {
//                    // Only if the neighbor is in the bounds of the picture.
//                    if (0 <= z && z < picture.height()) {
//                        AdjacencyListSeamFinder.PixelGraph.Pixel to = pixels[x + 1][z];
//                        from.neighbors.add(new Edge<>(from, to, f.apply(picture, x + 1, z)));
//                        System.out.println("Neighbors : " + from.neighbors);
//                    }
//                }


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
