package seamcarving;

import graphs.Edge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {
    @Override
    public List<Integer> findSeam(Picture picture, EnergyFunction f) {
        // Generative find seam:
        // Initialization of our data structures:
        // initialize a 2D array for the picture (DONE YAY)
        double pixels[][] = new double[picture.width()][picture.height()];
        // what we return to the user as the key value pairs for the best pixel in each given x coordinate
        // we treated the first starting point from column 0
        // as the pixel with the lowest energy value in column 0.
        // we want to build our first column of energy values
        // also finding the minimum energy from that column and adding it to our result
        Map mf_coordinates = new HashMap<Integer, Integer>();

        // left most pixels: (initial edge case)
        double min_energy = 9;
        // we treated the first starting point from column 0
        // as the pixel with the lowest energy value in column 0.
        // we want to build our first column of energy values
        // also finding the minimum energy from that column and adding it to our result
        double current_energy = 10;
        for (int y = 0; y < picture.height(); y++) {
            // now we need to apply the energy function
            pixels[0][y] = f.apply(picture, 0, y);
            current_energy = f.apply(picture, 0, y);
            if (current_energy < min_energy) {
                min_energy = current_energy;
                mf_coordinates.put(0, y);

            }
        }
        for(int x = 1; x < picture.width(); x++) {
            for(int y = 0; y < picture.height(); y++) {
                pixels[x][y] = f.apply(picture, x, y);
                // iterate over neighbors and figure out the min
            }
        }
        // now we need to start finding neighbors from our best first source node
//        if (x == picture.width() -1) {
//            return List.of( new Edge<>(this, sink, 0));
//        }
        // essentially a for loop till we reach the edge of the picture

        // checking neighbors of our pixel

        // also we need to address how we calculate the best neighbor to go to

        // we treated the first starting point from column 0

        // as the pixel with the lowest energy value in column 0.
        // we want to build our first column of energy values
        // also finding the minimum energy from that column and adding it to our result
        // iterate over middle of picture (large for loop (x to picture width -2))
        // apply energy function
        // look for best neighbor (left up, left middle, left down) (private neighbors methods)

        // right most pixels build our final edge (right most pixel column) (Edge case)
        // stop our iteration once we reach this point
        // figure out how to do this

        // the best seam we want to return back
        ///
        List<Integer> result = new ArrayList<>();
        return result;
    }

    // private helper method, basically just returns the neighbors of a given pixel
    // now, we need to figure out how to return the best neighbor !
    // returns the energy value of min neighbor

    private double neighbors(Picture picture, EnergyFunction f, int x, int y) {
        Map<Integer, Integer> neighborList = new HashMap<>(3);
        double min = -1;
        // if we are out of bounds horizontally
        if (x == picture.width() - 1) {
            return  min;
        }
        for (int z = y - 1; z <= y + 1; z += 1) {
            // Only if the neighbor is in the bounds of the picture.
            if (0 <= z && z < picture.height()) {
                // wtf is this line doing ?
                neighborList.put(x + 1, z);
            }
        }
        for(Integer key : neighborList.keySet()) {
            double curr = f.apply(picture, key, neighborList.get(key));
            if(curr < min || min == -1) {
                min = curr;
            }
        }
        return min;
    }
}
