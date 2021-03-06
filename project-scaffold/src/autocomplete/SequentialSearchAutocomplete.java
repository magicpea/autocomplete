package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    /** @param terms collection containing elements to be added
     * summary : this method adds all of the terms within the parameter into the
     * the final list of terms
     * pre : this.terms is empty
     * post : this.terms is full of CharSequences from the parameter
    */
    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // iterating over N items, Big Theta(N)
        this.terms.addAll(terms);
        System.out.print(this.terms);
    }

    /**
     * @param prefix search query
     * @return the list of CharSequences in terms that match the parameter
     * summary : this method finds all matches of the prefix sequentially
     * pre : no matches of the given prefix have been found
     * post : all of the matches containing the given prefix are added to a List
     */
    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matches = new ArrayList<CharSequence>();
        // Runtime here would be Big Theta(N) because iterating over N elements
        for(CharSequence e : this.terms) {
            // find only the prefix of a given word to compare
            //CharSequence word = e.subSequence(0, prefix.length());
            // check to see if it matches

            boolean length_check = e.length() >= prefix.length();
            double difference = CharSequence.compare(e.subSequence(0, prefix.length()), prefix);
            
            if(length_check && difference == 0) {
                matches.add(e);
            }
        }
        return matches;
    }
}
