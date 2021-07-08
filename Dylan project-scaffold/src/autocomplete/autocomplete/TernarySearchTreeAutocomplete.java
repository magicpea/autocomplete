package autocomplete;

import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        setOverallRoot(null);
    }

    public Node getOverallRoot() {
        return overallRoot;
    }

    public void setOverallRoot(Node overallRoot) {
        this.overallRoot = overallRoot;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data, boolean isTerm) {
            this.setData(data);
            this.setTerm(isTerm);
            this.setLeft(null);
            this.setMid(null);
            this.setRight(null);
        }

        private void setRight(Object object) {
        }

        private void setMid(Object object) {
        }

        private void setLeft(Object object) {
        }

        private void setTerm(boolean isTerm2) {
        }

        private void setData(char data2) {
        }     
    }
}
