package seamcarving;

import graphs.Edge;

import java.lang.reflect.Array;
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

        // dummy thick mf coordinates I'm going crazy
//        Map mf_coordinates = new HashMap<Integer, Integer>();
        ArrayList seam = new ArrayList<Integer>();

        ArrayList lobotomy = new ArrayList<ArrayList<Double>>();

        // left most pixels: (initial edge case)

        // we treated the first starting point from column 0
        // as the pixel with the lowest energy value in column 0.
        // we want to build our first column of energy values
        // also finding the minimum energy from that column and adding it to our result



        int x_i = picture.width() - 1;

        for (int x = x_i; x >= 0; x--) {
            // x is zero test case
            if (x == 0){
                for (int y = 0; y < picture.height(); y++) {
                    // left most pixels equal energy value
                    pixels[x][y] = f.apply(picture, x, y);
                }
            }
            // width to 1
            List appendix = new ArrayList<Double>();
            for (int y = 0; y < picture.height(); y++) {
                appendix = neighbors(picture, f, x, y);
//                System.out.println(appendix.toString());
                lobotomy.add(appendix);
                // now we need to apply the energy function
                // R -> L
                // start from right then add outer for loop till we reach left
                // call neighbors
            }
            seam.add(updateEnergy(x,  pixels, lobotomy));
        }

        return seam;
//        // iterate through the lobotomy (remember that size doesn't matter ;P )
//        // min value
//        double minVal = -1.0;
//        // y-value
//        int newY;
//        // looking for min energy function in the list
//        for(int i = 0; i < lobotomy.size(); i++) {
//            ArrayList temp = (ArrayList<Double>) lobotomy.get(i);
//            if((double)temp.get(0) < minVal) {
//                minVal = (double)temp.get(0);
//                newY = (int)temp.get(1);
//                // gotta figure out where to get the x from
//                pixels[x][newY] = minVal;
//            }
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

//        return mf_coordinates;
    }

    // private helper method, basically just returns the neighbors of a given pixel
    // now, we need to figure out how to return the best neighbor !
    // returns the energy value of min neighbor

    private List<Double> neighbors(Picture picture, EnergyFunction f, int x, int y) {
        Map<Integer, Integer> neighborList = new HashMap<>(3);
        List concussion = new ArrayList<Double>(2);
        double min = - 1;
        int best_y = -1;

        // if we are out of bounds horizontally
        if (x <= 0) {
            return concussion;
        }
        for (int z = y - 1; z <= y + 1; z += 1) {
            // Only if the neighbor is in the bounds of the picture.
            if (0 <= z && z < picture.height()) {
                // wtf is this line doing ?
                neighborList.put(x - 1, z);
            }
        }
        double parent_e_val = f.apply(picture, x, y);
        for(Integer key : neighborList.keySet()) {
            // current energy value
            double e_sum = parent_e_val;
            if (key - 1 >= 0) {
                double neighbor_path_e_value = f.apply(picture, key, neighborList.get(key));
                e_sum = neighbor_path_e_value + parent_e_val;
            } else {
                if (min == -1) {
                    min = e_sum;
                    best_y = neighborList.get(key);
                }
            }
//            double neighbor_path_e_value = f.apply(picture, key, neighborList.get(key));
//            double e_sum = neighbor_path_e_value + parent_e_val;
//            double e_sum = parent_e_val;



            if(e_sum < min) {
                min = e_sum;
                best_y = neighborList.get(key);
            }
        }
        // after finding best finally add them to be sent out
        concussion.add(min);
        concussion.add((double)best_y);

        return concussion;
    }

    private int updateEnergy(int x, double pixels[][], ArrayList<ArrayList<Double>> lobotomy) {
        // iterate through the lobotomy (remember that size doesn't matter ;P )
        // min value
        if (x == 0) {
            System.out.println("dammit we made a goof");
            return -1;
        }


        double minVal = -1.0;
        // y-value
        int newY = 0;
        // looking for min energy function in the list

        ArrayList temp = new ArrayList<Double>(2);
        for(int i = 0; i < lobotomy.size(); i++) {

            temp = (ArrayList) lobotomy.get(i).clone();
            if (temp.isEmpty()) {
                System.out.println("we are empty at " + i + " index");
                System.out.println("x " + x);
            }

            double curr_e = (double) temp.get(0);
            double curr_y = (double) temp.get(1);
            if(minVal == -1) {
                minVal = curr_e;
            }

            if (curr_e < minVal) {
                minVal = (double) curr_e;
                newY = (int) curr_y;
                pixels[x][newY] = minVal;
            }
        }
        return newY;
    }
}
