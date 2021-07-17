package autocomplete;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 * @param <Value>
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete<Value> implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node<Value> overallRoot;
    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        setOverallRoot(null);
    }

    private void setOverallRoot(Node<Value> root) {
        Node<Value> overallRoot = root;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        
        for(CharSequence word : terms){
            System.out.println("now adding:" + word);
            for (int i = 0; i <= word.length() - 1; i++){
                setOverallRoot(put(overallRoot, word, i));
                System.out.println("current letter added: " + word.charAt(i));
            }
            
            

        }


    }

    private Node<Value> put(Node<Value> x, CharSequence key, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node<Value>();
            x.c = c;
        }
        if      (c < x.c)               x.left  = put(x.left,  key, d);
        else if (c > x.c)               x.right = put(x.right, key, d);
        else if (d < key.length() - 1)  x.mid   = put(x.mid,   key, d + 1);
        return x;    
    }

    public boolean containsKey(CharSequence key) {
        if (key == null) {
            throw new NullPointerException("calls containsKey() with null argument");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the map
     *     and null if the key is not in the map
     * @throws NullPointerException if key is null
     * @throws IllegalArgumentException if key is empty
     */
    public Value get(CharSequence key) {
        if (key == null) {
            throw new NullPointerException("calls get() with null argument");
        } else if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        Node<Value> x = get(overallRoot, key, 0);
        if (x == null) return null;
        return x.val;
    }

    // Returns subtree corresponding to given key
    private Node<Value> get(Node<Value> x, CharSequence key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d + 1);
        else                           return x;
    }



    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        return keysWithPrefix(prefix);
    }

    /**
     * Returns all of the keys in the set that start with prefix.
     * @param prefix the prefix
     * @return all of the keys in the set that start with prefix as a list
     * @throws NullPointerException if prefix is null
     * @throws IllegalArgumentException if prefix is empty
     */
    public List<CharSequence> keysWithPrefix(CharSequence prefix) {
        if (prefix == null) {
            throw new NullPointerException("calls keysWithPrefix() with null argument");
        } else if (prefix.length() == 0) {
            throw new IllegalArgumentException("prefix must have length >= 1");
        }
        List<CharSequence> list = new LinkedList<CharSequence>();
        Node<Value> x = get(overallRoot, prefix, 0);
        if (x == null) return list;
        if (x.val != null) list.add(prefix);
        collect(x.mid, new StringBuilder(prefix), list);
        return list;
    }

    // Collects all keys in subtree rooted at x with the given prefix
    private void collect(Node<Value> x, StringBuilder prefix, List<CharSequence> list) {
        if (x == null) return;
        collect(x.left,  prefix, list);
        if (x.val != null) list.add(prefix.toString() + x.c);
        prefix.append(x.c);
        collect(x.mid,   prefix, list);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, list);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node<Value> {
        private char c;                        // character
        private Node<Value> left, mid, right;  // left, middle, and right subtrees
        private Value val;                     // value associated with string
    }
}


