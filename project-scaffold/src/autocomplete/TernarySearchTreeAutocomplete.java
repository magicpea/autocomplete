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
    // addAll:

    //   summary: adds all the words in our terms collection to 
    //            build our Ternary Search Tree 
    //   parameters: terms - collection of CharSequences 
    //   pre: given a collection of terms
            
    //   post: build a Ternary search tree consisting:
    //         of all the letters of all the words in our terms set
    public void addAll(Collection<? extends CharSequence> terms) {
        for(CharSequence word : terms) {
            helper(word);
        }
    }

    // need to be done 
    public void helper(CharSequence word) {
        if(word == null) {
            throw new NullPointerException("calls addAllHelper() with null key");
        }
        this.overallRoot = helper(this.overallRoot, word, false, 0);
    }
    // need to be done
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
    
    //  allMatches:

    //  summary: allMatches finds all terms in our Data Structure 
    //          that match a Given Search
    //  parameters: given a charSequence prefix search query
    //  pre: given a charSequence prefix query, 
    //       and that a local Ternary search tree exists
    //       throws exception if prefix is not entered
    //       throws exception if prefix has length of zero
    //  post: return a list of all charSequences that contain the given prefix
    public List<CharSequence> allMatches(CharSequence prefix) {
        System.out.println("inside keysWithPrefix");
        
        // Handling our base cases
        if (prefix == null) {
            throw new NullPointerException("calls keysWithPrefix() with null argument");
        } else if (prefix.length() == 0) {
            throw new IllegalArgumentException("prefix must have length >= 1");
        }
        
        // initializing our list
        List<CharSequence> list = new LinkedList<CharSequence>();
        
        // grabs sub tree that contains prefix
        Node x = get(overallRoot, prefix, 0);
        
        // exits list if no matches are found in tree
        if (x == null) return list;
        
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
    
    // need to be done 
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
    //needs to be done
    // Returns subtree corresponding to given key
    private Node get(Node x, CharSequence word, int d) {
        if (x == null) return null;
        char c = word.charAt(d);
        if      (c < x.data)              return get(x.left,  word, d);
        else if (c > x.data)              return get(x.right, word, d);
        else if (d < word.length() - 1) return get(x.mid, word, d + 1);
        else                           return x;
    }
}
