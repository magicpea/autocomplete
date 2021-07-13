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
        overallRoot = null;
    }
    // addALL():
    // build a TST recursively adding words
    // this is essentially going over steps we did on the TST lesson in class
    // Action items:
    // read TST class documentation
    // Public/Private method paring it seems like
    // Questions:
    // What check are we going to need to make to add words to the same branch
    // how do we break out of our recursion? What check will that take
    // list [4,6,2,8]
    // Overall root: 4
    // check < or >
    // most likely we will pass in the currentroot to the private method as a VAR
    // Recursive step:
    // go one word at a time
    // do this by using substrings
    // if list[i] < currentletter
    // Left
    // else if list[i] = currentletter
    // go straight down
    // node.mid (goes straight down)
    // else list[i] > currentRoot
    // Right
    // node.right
    // when do you stop for a given word?
    // probably at the end of a charsequenec
    // public
    // for (char c : words){
    // run recursion
    // run associated TST add method from the example code
    // method that traverses TsT
    // }
    // till out of letters in the words of term

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
            this.data = data;
            this.isTerm = isTerm;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
