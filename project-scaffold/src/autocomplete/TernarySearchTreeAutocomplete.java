package autocomplete;

import java.util.ArrayList;
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
        this.overallRoot = new Node('0', false);
    }

    @Override
    /**
     * @param terms collection of CharSequences
     * summary : adds all the words in our terms collection to
     *              build our Ternary Search Tree
     * pre : given a collection of terms
     * post : build a Ternary search tree consisting:
     *              of all the letters of all the words in our terms set
     */
    public void addAll(Collection<? extends CharSequence> terms) {
        // Big theta(N) for for each loop
        for(CharSequence word : terms) {
            //helper recursion big theta(N)
            helper(word);
        }
        // big theta = N * log3(N)
    }

    /**
     * @param word CharSequence to be added to the TST
     * summary : adds a word from our terms collection to
     *                 the Ternary Search Tree
     * pre : given a collection of characters in a word
     *               throws exception if word is empty
     * post : adds Ternary search Tree nodes for all
     *              the letters of the given word
     */
    public void helper(CharSequence word) {
        // recursively runs through N characters in word (bigTheta(N))
        if(word == null) {
            throw new NullPointerException("calls addAllHelper() with null key");
        }
        this.overallRoot = helper(this.overallRoot, word, false, 0);
    }

    /**
     * @param n node that are going to add new nodes to
     * @param word CharSequence to be added to the TST
     * @param stupidBoolean placeholder boolean for adding new nodes
     * @param d the current index within word of interest
     * @return tree Node that matches the letter of the given word
     * summary : adds a word from our terms collection to
     *                 the Ternary Search Tree, uses recursion to generate new
     *                 nodes and decide where to put them based on the current
     *                 character value in relation to the node's data value
     * pre: given a non empty node n, runs comparisons to determine where next node should go
     *             following TST invariant
     * post: adds Ternary search Tree nodes for the letters of the given word
     */
    private Node helper(Node n, CharSequence word, boolean stupidBoolean, int d) {
        char c = word.charAt(d);
        // System.out.println("Character currently being added is: " + c);
        if(n == null) {
            n = new Node(c, true);
        }
        // System.out.println("n is currently: " + n.data);
        if(c > n.data) {
            // System.out.println("New character is > node, so right child");
            n.right = helper(n.right, word, false, d);
        } else if (c < n.data) {
            // System.out.println("New character is < node, so left child");
            n.left = helper(n.left, word, false, d);
        } else if (d < word.length() - 1) {
            // System.out.println("Still building word");
            n.mid = helper(n.mid, word, false, d + 1);
        }
        return n;
    }

    @Override

    /**
     * @param prefix charSequence search query
     * @return list of all charSequences containing the given prefix
     * @throws exception if prefix is not entered
     * @throws exception if prefix has length of zero
     * summary : allMatches finds all terms in our Data Structure
     *               that match a given Search
     * pre : given a charSequence prefix query, and that a local Ternary
     *               search tree exists
     * post : list of matches containing prefix are returned
     */
    public List<CharSequence> allMatches(CharSequence prefix) {
        // big theta(2*log3(N))
        
        // System.out.println("inside keysWithPrefix");
        
        // Handling our base cases
        if (prefix == null) {
            throw new NullPointerException("calls keysWithPrefix() with null argument");
        } else if (prefix.length() == 0) {
            throw new IllegalArgumentException("prefix must have length >= 1");
        }
        
        // initializing a linked list for word building
        List<CharSequence> list = new ArrayList<CharSequence>();
        
        // grabs sub tree address that contains prefix
        Node x = get(overallRoot, prefix, 0);
         // Log3(N) runitime opperation, recursively explore three children nodes
        
        // exits linkedlist if no matches are found in tree
        if (x == null) return list;
        
        // adding new letter to word linked list if node is not empty
        if (x.data != 0) list.add(prefix);
        // Condenses the branches of our TST that match the prefix into strings 
        collect(x.mid, new StringBuilder(prefix), list);
        // log3(N), recursively building strings from each threeway branch
        return list;
    }

    /**
     * @param x Node Address of where the prefix children start
     * @param prefix Appends the specified character sequence to our search query
     * @param list A collection that holds all of our matches as they are added
     * summary : collects all keys in subtree rooted at x with given prefix and
     *             collapses letters into word, uses recursion
     * pre : given a prefix address node x, Stringbuilder prefix, and a collection list
     * post : returns the list of words that hang off of a given node address
     */
    private void collect(Node x, StringBuilder prefix, List<CharSequence> list) {
        if (x == null) {
            return;
        }
        collect(x.left,  prefix, list);
        if (x.data != 0) {
            list.add(prefix.toString() + x.data);
        }
        prefix.append(x.data);
        collect(x.mid, prefix, list);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, list);
    }

    /**
     * @param word the CharSequence we are trying to find in the TST
     * @return a character representing the node of the given word within the TST
     * @throws exception if the word is empty or it's is zero
     * summary : find the character element of a Node using the given prefix
     * pre : given a word of interest
     * post : returns the node address of a given word in the TST
     */
    public char get(CharSequence word) {
        if (word == null) {
            throw new NullPointerException("calls get() with null argument");
        } else if (word.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        Node x = get(overallRoot, word, 0);
        if (x == null) {
            return 0;
        }
        return x.data;
    }

    /**
     * @param x the current node we are situated at
     * @param word the CharSequence we are trying to find in the TST
     * @param d the current index within word of interest
     * @return the node at the end of the given word in the TST
     * summary : decides which side of the node to add to based on
     *             the value ifi the current character in relation to
     *             the data value of the Node
     * pre : given a node, a word, and an index
     * post : a Node is returned, recursion happens
     */
    private Node get(Node x, CharSequence word, int d) {
        if (x == null) {
            return null;
        }
        char c = word.charAt(d);
        if (c < x.data) {
            return get(x.left,  word, d);
        }
        else if (c > x.data) {
            return get(x.right, word, d);
        }
        else if (d < word.length() - 1) {
            return get(x.mid, word, d + 1);
        }
        else {
            return x;
        }
    }
}
