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
        List<CharSequence> matches = new ArrayList<CharSequence>();
        // Same here we assume that the terms are already sorted
        int superiorIndex = Collections.binarySearch(this.terms, prefix, null);
        int curr = superiorIndex;
        // Doing the actual binary search here, the worst case (and most likely) runtime is theta(n)
        if (curr >= 0){
            double difference = CharSequence.compare(prefix, terms.get(curr).subSequence(0, prefix.length()));
            boolean new_match = !(matches.contains(this.terms.get(curr)));
            while(difference == 0) {
                if (new_match){
                    matches.add(this.terms.get(curr));    
                }
                curr++;
            }
        }
        // System.out.println("Here are the matches" + matches);
        return matches;
    }
}
