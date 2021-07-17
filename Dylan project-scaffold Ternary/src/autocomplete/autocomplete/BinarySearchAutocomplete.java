package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    public List<CharSequence> getTerms() {
        return terms;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> words) {
        // TODO: Replace with your code
        // throw new UnsupportedOperationException("Not implemented yet");
        
        // making a temp list to allow for comparable use
        // Maybe unecessary temp structure
        List<CharSequence> temp = new ArrayList<CharSequence>();
        
        // Big theta(_N_)
        // take in an arraylist of words  
        for (CharSequence c : words){
            // add the lexographic match to match
            temp.add(c);
        }

        // Big theta(_N_)
        // sort them alphabetically
        Collections.sort(temp,  CharSequence::compare);
        
        // Big Theta(_N_)
        for (CharSequence d: temp){
            terms.add(d);
        }
        // debugging terms list
        // System.out.println(terms);
        
        
        // build a binary search tree adding nodes to the tree
        
        // add the nodes such that the left node is lexographically less than the parent

        // right node is lexographically greater than the parent

        // maybe implement recursion to help in this feature
    }


    @Override
     public List<CharSequence> allMatches(CharSequence prefix) {
        // this is only a modified sequential search
        // having trouble fully implementing a true binary search
        // might have to look at Collections.binary search

        // main goal is just to write a new pseudo code to fully understand binary search and what we need to do:

        // everytime you make a comparison
        // low to mid or mid to high // spliting the tree each time 
        // 

        List<CharSequence> temp = new ArrayList<CharSequence>();
         // setting up splitting variables
         int low = 0;
         int high = this.terms.size() - 1;
         int mid = (low + high) / 2;
         // debug statement to see current reference frame

         System.out.println("Mid" + mid );
        
         // Matches that are to the right in the alphabet
         for (int i = 0; i <= mid; i++) {
             System.out.println("right:" + i);
             if(CharSequence.compare(prefix, terms.get(mid + i).subSequence(0, prefix.length())) == 0 && !(temp.contains(terms.get(mid + i)))) {
                 temp.add(this.terms.get(mid + i));
             }    
         }
         // matches are to the left in the alphabet
         for (int i = mid; i >= 0; i--) {
             System.out.println("left:" + i);
             if(CharSequence.compare(prefix, terms.get(mid + i).subSequence(0, prefix.length())) == 0 && !(temp.contains(terms.get(mid + i)))) {
                 temp.add(this.terms.get(mid + i));
             }    
         }
         return temp;
     }
    } 


