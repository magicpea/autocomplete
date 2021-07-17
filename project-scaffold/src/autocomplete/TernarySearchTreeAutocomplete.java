package autocomplete;


import javax.swing.plaf.synth.SynthUI;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data, boolean isTerm) {
            this.data = data;
            this.isTerm = isTerm;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        TernarySearchTreeAutocomplete tst = new TernarySearchTreeAutocomplete();
        for(CharSequence word : terms) {
            tst.helper(word);
        }
    }

    public void helper(CharSequence word) {
        if(word == null) {
            throw new NullPointerException("calls addAllHelper() with null key");
        }
        overallRoot = helper(overallRoot, word, false, 0);
    }

    private Node helper(Node n, CharSequence word, boolean stupidBoolean, int d) {
        char c = word.charAt(d);
        System.out.println("Character currently being added is: " + c);
        if(n == null) {
            n = new Node(c, true);
        }
        System.out.println("n is currently: " + n.data);
        if(c > n.data) {
            System.out.println("New character is > node, so right child");
            n.right = helper(n.right, word, false, d);
        } else if (c < n.data) {
            System.out.println("New character is < node, so left child");
            n.left = helper(n.left, word, false, d);
        } else if (d < word.length() - 1) {
            System.out.println("Still building word");
            n.mid = helper(n.mid, word, false, d + 1);
        }
        return n;
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
