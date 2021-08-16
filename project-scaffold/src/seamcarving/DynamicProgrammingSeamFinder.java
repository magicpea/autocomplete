package seamcarving;

import graphs.Edge;

import java.lang.module.FindException;
import java.lang.reflect.Array;
import java.util.*;

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

        // dummy thick mf coordinates I'm going crazy
//        Map mf_coordinates = new HashMap<Integer, Integer>();
        ArrayList seam = new ArrayList<Integer>(picture.width());


        // left most pixels: (initial edge case)

        // we treated the first starting point from column 0
        // as the pixel with the lowest energy value in column 0.
        // we want to build our first column of energy values
        // also finding the minimum energy from that column and adding it to our result



        int x_f = picture.width();

        // filled out from left most column to the right for total energy for each pixel
        // left most pixel energy
        for (int y = 0; y < picture.height(); y++) {
            pixels[0][y] = f.apply(picture, 0, y);
        }

        // everything in middle to right
        for (int x = 1; x < x_f; x++) {
            // x is zero test case
            for (int y = 0; y < picture.height(); y++) {
                double prev_e = neighbors(pixels[x - 1], y - 1, y + 2).getValue();
                pixels[x][y] = f.apply(picture, x, y) + prev_e;
            }
        }

        // populate seam list
        int y_prev = neighbors(pixels[picture.width() -1], 0, pixels[0].length).getKey();
        seam.add(y_prev);

        for (int x = x_f - 2; x >= 0; x --) {
            y_prev = neighbors(pixels[x], y_prev - 1, y_prev + 2).getKey();
            int y_to_add = y_prev;
            seam.add(y_to_add);
        }

        // reverse the seam
        Collections.reverse(seam);
        return seam;
    }

    // private helper method, basically just returns the neighbors of a given pixel
    // now, we need to figure out how to return the best neighbor !
    // returns the energy value of min neighbor

    private AbstractMap.SimpleEntry<Integer, Double> neighbors(double[] pixels, int y, int y_2) {
        // checking bounds:
        int low = Math.max(y, 0);
        int high = Math.min (y_2, pixels.length);

        // indices initialized
        double min = Double.MAX_VALUE;
        int best_y = -1;

        // real stuff
        for (int i = low; i < high; i ++ ) {
            // check if pixels y is lower than min
            if (pixels[i] < min) {
                min = pixels[i];
                best_y = i;
            }
        }
        // making a simple entry for our best case option
        return new AbstractMap.SimpleEntry<>(best_y, min);
    }

}
