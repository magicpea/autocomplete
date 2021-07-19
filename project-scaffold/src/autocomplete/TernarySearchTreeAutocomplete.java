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
        // Big theta(N) for for each loop
        for(CharSequence word : terms) {
            //helper recursion big theta(N)
            helper(word);
        }
        // big theta = N * log3(N)
    }
    // helper: 
    //   summary: adds a word from our terms collection to 
    //            the Ternary Search Tree 
    //   parameter: 
    //          word - CharSequence to be added to the TST 
    //   pre: given a collection of characters in a word
    //          throws exception if word is empty 
    //   post: adds Ternary search Tree nodes for all 
  //           the letters of the given word
    public void helper(CharSequence word) {
        // recursively runs through N characters in word (bigTheta(N))
        if(word == null) {
            throw new NullPointerException("calls addAllHelper() with null key");
        }
        this.overallRoot = helper(this.overallRoot, word, false, 0);
    }

    // helper(private): 
    //   summary: adds a word from our terms collection to 
    //            the Ternary Search Tree 
    //   parameter: 
    //             n - previous node that are going to add new nodes to      
    //             word - CharSequence to be added to the TST
    //             stupidBoolean - placeholder boolean for adding new nodes
    //             d- the current index within word of interest 
    //   pre: given a non empty node n, runs comparisons to determine where next node should go 
    //        following TST invariant 
    //   post: adds Ternary search Tree nodes for the letters of the given word
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
    
    //  allMatches:
    //  summary: allMatches finds all terms in our Data Structure 
    //          that match a given Search
    //  parameters: prefix- charSequence search query
    //  pre: given a charSequence prefix query, 
    //       and that a local Ternary search tree exists
    //       throws exception if prefix is not entered
    //       throws exception if prefix has length of zero
    //  post: returns a list of all charSequences that contain the given prefix
    public List<CharSequence> allMatches(CharSequence prefix) {
        System.out.println("inside keysWithPrefix");
        
        // Handling our base cases
        if (prefix == null) {
            throw new NullPointerException("calls keysWithPrefix() with null argument");
        } else if (prefix.length() == 0) {
            throw new IllegalArgumentException("prefix must have length >= 1");
        }
        
        // initializing a linked list for word building
        List<CharSequence> list = new ArrayList<CharSequence>();
        
        // grabs sub tree address that contains prefix
        // Log3(N) runitime opperation
        Node x = get(overallRoot, prefix, 0);
        
        // exits linkedlist if no matches are found in tree
        if (x == null) return list;
        
        // adding new letter to word linked list if node is not empty
        if (x.data != 0) list.add(prefix);
        // Condenses the branches of our TST that match the prefix into strings 
        
        // log3(N)
        collect(x.mid, new StringBuilder(prefix), list);
        return list;
        // big theta(2*log3(N))
    }

    
    // collect:
    //  summary: collapse letters in a tree into words
    //  parameters: 
    //             x- Node Address of where the prefix children start
    //             prefix- Appends the specified character sequence to our search query
    //             list- A collection that holds all of our matches as they are added 
    //  pre: given a prefix address node x, stringbuilder prefix, and a collection list   
    //  post: returns the list of words that hang off of a given node address. 

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
    
    // get:
    //  summary: collapse letters in a tree into words
    //  parameters: 
    //             word- the charsequence we are trying to find in the TST
                
    //  pre: given a word of interest
    //       throws an exception if the word is empty or it's is zero
    //  post: returns the node address of a given word in the TST 
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
    
    // get(private):
    //  summary: collapse letters in a tree into words
    //  parameters: 
    //             x- the current node we are situated at
    //             word- the charsequence we are trying to find in the TST
    //             d- the current index within word of interest
    //  pre: given a node address, word of interest and the character index of interest
    //       throws exception if the node address x does not exist
    //  post: returns the node address at the end of given word in the TST 
    private Node get(Node x, CharSequence word, int d) {
        if (x == null) return null;
        char c = word.charAt(d);
        if      (c < x.data)              return get(x.left,  word, d);
        else if (c > x.data)              return get(x.right, word, d);
        else if (d < word.length() - 1) return get(x.mid, word, d + 1);
        else                           return x;
    }
}
