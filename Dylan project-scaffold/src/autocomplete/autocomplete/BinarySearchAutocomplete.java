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
        // TODO: Replace with your code
        // throw new UnsupportedOperationException("Not implemented yet");
        // List<CharSequence> temp = new ArrayList<CharSequence>();
        
        // initialize 
        int low = 0;
        int high = terms.size() -1;
        int mid = low + high /2;

        // Explore
        low = allMatchesHelper(low, mid, prefix);

        high = allMatchesHelper(mid, high, prefix);
        System.out.println(low + " " + high);
        // CharSequence.compare(c.subSequence(0, prefix.length()), prefix) == 0)
        if (low < high){
            System.out.println(terms.subList(low, high));
            return terms.subList(low, high);
        } else if (low == high){
            System.out.println("anything");
            List<CharSequence> temp = new ArrayList<CharSequence>();
            temp.add(terms.get(low));
            return temp;
        }
         
        System.out.println("empty list vrother");
        return new ArrayList<CharSequence>();
    }

    private int allMatchesHelper(int low, int high, CharSequence prefix) {
        // if low bound and upper bound have compare to equal zer0
        // return subset of array from low to high bound that contains prefix
        
        // splits list in half
        int mid = (low + high)/ 2;

        // if(mid >= 0){
            // This segment checks to return lower bound if found as a match
            // if (CharSequence.compare(terms.get(low).subSequence(0, prefix.length()), prefix) == 0){
            //    return low;
            // }

            // This segment checks to return lower bound if found as a match
            // if (CharSequence.compare(terms.get(high).subSequence(0, prefix.length()), prefix) == 0){
            //    return high;
            // }
            
            // when the sequence equals exactly 0 its a 100% match
            // when less than zero that means it happens before in the alphabet (which for prefix is bad)
            // when more than zero that means it happens after in the alphabet
        if (CharSequence.compare(terms.get(low), prefix) >= 0){
            return low;
        }

        if (CharSequence.compare(terms.get(high), prefix) >= 0){
            return high;
        }

        // explore lower portion
        allMatchesHelper(low, mid, prefix);
            
        // explore upper portion
        allMatchesHelper(mid, high, prefix);




        //}
        return 0;

        // This segment checks to return lower bound if found as a match
        // if (CharSequence.compare(terms.get(low).subSequence(0, prefix.length()), prefix) == 0){
        //    return low;
        // }

        // This segment checks to return lower bound if found as a match
        // if (CharSequence.compare(terms.get(high).subSequence(0, prefix.length()), prefix) == 0){
        //    return high;
        // }
        /*
        if (CharSequence.compare(terms.get(low).subSequence(0, prefix.length()), prefix) == 0){
            return low;
        }

        if (CharSequence.compare(terms.get(high).subSequence(0, prefix.length()), prefix) == 0){
            return high;
        }

        // explore lower portion
        allMatchesHelper(low, mid, prefix);
        
        // explore upper portion
        allMatchesHelper(mid, high, prefix);
        */






        
        /*if (CharSequence.compare(terms.get(mid).subSequence(0, prefix.length()), prefix) == 0){
            return mid;    
        } else if (CharSequence.compare(terms.get(high).subSequence(0, prefix.length()), prefix) < 0){
            if (CharSequence.compare(terms.get(low).subSequence(0, prefix.length()), prefix) == 0){
                return low;
            }
            allMatchesHelper(low, mid, prefix);
        } else {
            // when our prefix sequence is further along the alphabet
            
            if (CharSequence.compare(terms.get(high).subSequence(0, prefix.length()), prefix) == 0){
                return high;
            }
            allMatchesHelper(mid, high, prefix);
        }

        return mid;
        */
    }
}
