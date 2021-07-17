package seamcarving;

import graphs.DijkstraSolver;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Seam carving, an approach for content-aware image resizing. Given a {@link Picture}, an {@link EnergyFunction}, and a
 * {@link SeamFinder} algorithm, {@link #removeHorizontal()} or {@link #removeVertical()} seams from the picture.
 *
 * @see Picture
 * @see EnergyFunction
 * @see SeamFinder
 */
public class SeamCarver {
    /**
     * Path to the input image.
     */
    private static final String INPUT_PATH = "data/seamcarving/HJoceanSmall.png";
    /**
     * Path to the output image.
     */
    private static final String OUTPUT_PATH = "result.png";
    /**
     * The {@link EnergyFunction} for determining the minimum-cost seam.
     */
    private final EnergyFunction f;
    /**
     * The {@link SeamFinder} implementation.
     */
    private final SeamFinder seamFinder;
    /**
     * The {@link Picture}.
     */
    private Picture picture;

    /**
     * Constructs a seam carver by reading the {@link Picture} from the file, using the given {@link EnergyFunction} and
     * {@link SeamFinder} implementations.
     *
     * @param file       the file path to the image.
     * @param f          the {@link EnergyFunction}.
     * @param seamFinder the {@link SeamFinder}.
     * @throws IOException if an error occurs during reading.
     */
    public SeamCarver(File file, EnergyFunction f, SeamFinder seamFinder) throws IOException {
        if (file == null || f == null || seamFinder == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        this.picture = new Picture(file);
        this.f = f;
        this.seamFinder = seamFinder;
    }

    public static void main(String[] args) throws IOException {
        EnergyFunction f = new DualGradientEnergyFunction();
        SeamFinder seamFinder = new AdjacencyListSeamFinder(DijkstraSolver::new);
        SeamCarver seamCarver = new SeamCarver(new File(INPUT_PATH), f, seamFinder);
        System.out.println(seamCarver.removeVertical());
        seamCarver.picture().save(new File(OUTPUT_PATH));
    }

    /**
     * Checks that the seam is a valid horizontal seam in the picture.
     *
     * @param picture the input picture.
     * @param seam    the seam to remove.
     */
    private static void validate(Picture picture, List<Integer> seam) {
        if (seam == null) {
            throw new NullPointerException("Seam cannot be null");
        } else if (seam.size() == 1) {
            throw new IllegalArgumentException("Cannot remove seam of size 1");
        } else if (seam.size() != picture.width()) {
            throw new IllegalArgumentException("Seam length does not match image size");
        }
        for (int i = 0; i < seam.size() - 2; i++) {
            if (Math.abs(seam.get(i) - seam.get(i + 1)) > 1) {
                throw new IllegalArgumentException("Seam values too far from neighbors at index " + i);
            }
        }
    }

    /**
     * Returns a copy of the current picture.
     *
     * @return a copy of the current picture.
     */
    private Picture picture() {
        return new Picture(picture);
    }

    /**
     * Removes and returns a minimum-cost horizontal seam from the picture.
     *
     * @return a minimum-cost horizontal seam.
     */
    public List<Integer> removeHorizontal() {
        List<Integer> seam = seamFinder.findSeam(picture, f);
        validate(picture, seam);
        Picture result = new Picture(picture.width(), picture.height() - 1);
        for (int x = 0; x < picture.width(); x += 1) {
            for (int y = 0; y < seam.get(x); y += 1) {
                result.set(x, y, picture.get(x, y));
            }
            for (int y = seam.get(x); y < picture.height() - 1; y += 1) {
                result.set(x, y, picture.get(x, y + 1));
            }
        }
        picture = result;
        return seam;
    }

    /**
     * Removes and returns a minimum-cost vertical seam from the picture.
     *
     * @return a minimum-cost vertical seam.
     */
    public List<Integer> removeVertical() {
        // Transpose the picture by flipping the x/y and width/height access.
        Picture transposed = new Picture() {
            @Override
            public int get(int x, int y) {
                return picture.image.getRGB(y, x); // (x, y) -> (y, x)
            }

            @Override
            public void set(int x, int y, int rgb) {
                throw new UnsupportedOperationException("Transposed picture is immutable");
            }

            @Override
            public int width() {
                return picture.image.getHeight();
            }

            @Override
            public int height() {
                return picture.image.getWidth();
            }

            @Override
            public void save(File file) {
                throw new UnsupportedOperationException("Transposed picture cannot be saved");
            }
        };
        List<Integer> seam = seamFinder.findSeam(transposed, f);
        validate(transposed, seam);
        Picture result = new Picture(picture.width() - 1, picture.height());
        for (int y = 0; y < picture.height(); y += 1) {
            for (int x = 0; x < seam.get(y); x += 1) {
                result.set(x, y, picture.get(x, y));
            }
            for (int x = seam.get(y); x < picture.width() - 1; x += 1) {
                result.set(x, y, picture.get(x + 1, y));
            }
        }
        picture = result;
        return seam;
    }
}
