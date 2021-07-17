package autocomplete;


import javax.swing.plaf.synth.SynthUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
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
        this.overallRoot = new Node('0', false);
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        //TernarySearchTreeAutocomplete tst = new TernarySearchTreeAutocomplete();

        for(CharSequence word : terms) {
            helper(word);
        }
        //return tst;
    }

    public void helper(CharSequence word) {
        if(word == null) {
            throw new NullPointerException("calls addAllHelper() with null key");
        }
        this.overallRoot = helper(this.overallRoot, word, false, 0);
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
        System.out.println("Houston we are inside allMatches, Over... *beep*");
        return keysWithPrefix(prefix);
         // we are going to finish this tonight 100%  current bet is 5$
    }


    /**
     * Returns all of the keys in the set that start with prefix.
     * @param prefix the prefix
     * @return all of the keys in the set that start with prefix as a list
     * @throws NullPointerException if prefix is null
     * @throws IllegalArgumentException if prefix is empty
     */
    public List<CharSequence> keysWithPrefix(CharSequence prefix) {
        System.out.println("inside keysWithPrefix -alpha squad over *beeps repeatedly*");
        if (prefix == null) {
            throw new NullPointerException("calls keysWithPrefix() with null argument");
        } else if (prefix.length() == 0) {
            throw new IllegalArgumentException("prefix must have length >= 1");
        }
        // keep linked list
        List<CharSequence> list = new LinkedList<CharSequence>();
        Node x = get(overallRoot, prefix, 0);
        if (x == null) return list;
        // need to modify this 
        if (x.data != 0) list.add(prefix);
        collect(x.mid, new StringBuilder(prefix), list);
        return list;
    }

    // Collects all keys in subtree rooted at x with the given prefix
    private void collect(Node x, StringBuilder prefix, List<CharSequence> list) {
        if (x == null) return;
        collect(x.left,  prefix, list);
        if (x.data != 0) list.add(prefix.toString() + x.data);
        prefix.append(x.data);
        collect(x.mid,   prefix, list);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, list);
    }

    public char get(CharSequence word) {
        if (word == null) {
            throw new NullPointerException("calls get() with null argument");
        } else if (word.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        Node x = get(overallRoot, word, 0);
        if (x == null) return 0;
        return x.data;
    }

    // Returns subtree corresponding to given key
    private Node get(Node x, CharSequence word, int d) {
        if (x == null) return null;
        char c = word.charAt(d);
        if      (c < x.data)              return get(x.left,  word, d);
        else if (c > x.data)              return get(x.right, word, d);
        else if (d < word.length() - 1) return get(x.mid, word, d + 1);
        else                           return x;
    }

    public String toString() {
        return "" + this.overallRoot.data;
    }
}
