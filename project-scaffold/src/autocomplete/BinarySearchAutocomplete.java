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

    /**
     * @param terms collection containing elements to be added
     * summary : this method adds all of the terms within the parameter into the
     * the final list of terms
     * pre : this.terms is empty
     * post : this.terms is full of CharSequences from the parameter & alphabetized
     */
    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        List<CharSequence> temp = new ArrayList<CharSequence>();
        temp.addAll(terms);
        Collections.sort(temp, CharSequence::compare);
        this.terms.addAll(temp);
        // System.out.println("Alphabetized list: " + this.terms);
    }

    /**
     * @param prefix search query
     * @return a list of terms that match the given prefix
     * summary : this method finds all matches of the prefix using binary search
     * pre: no matches are found
     * post: all instances of the prefix are found and added to a list
     */
    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> termsCopy = new ArrayList<CharSequence>();
        List<CharSequence> matches = new ArrayList<CharSequence>();
        termsCopy.addAll(this.terms);
        // System.out.println("Index: " + Collections.binarySearch(termsCopy, prefix, null));
        int superiorIndex = Collections.binarySearch(termsCopy, prefix, null);
        // Here, the runtime will be O(N) because there is a for loop
        for(int i = superiorIndex; i < termsCopy.size(); i++) {
            if(CharSequence.compare(termsCopy.get(i).subSequence(0, prefix.length()), prefix) == 0) {
                matches.add(termsCopy.get(i));
            }
        }
        int curr = superiorIndex;
        double truth = CharSequence.compare(prefix, terms.get(curr).subSequence(0, prefix.length()));
        // Doing the actual binary search here, the worst case (and most likely) runtime is O(log n)
        // The best case runtime here would be O(1)
        while(truth == 0 && !(matches.contains(terms.get(curr)))) {
            if(CharSequence.compare(termsCopy.get(curr).subSequence(0, prefix.length()), prefix) == 0) {
                matches.add(termsCopy.get(curr));
            }
            curr++;
        }
        // System.out.println("Here are the matches" + matches);
        // Total runtime would be O(N) + O(log n)
        return matches;
    }
}
