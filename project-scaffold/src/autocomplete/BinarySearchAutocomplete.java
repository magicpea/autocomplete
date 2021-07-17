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

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // Temp ArrayList
        List<CharSequence> temp = new ArrayList<CharSequence>();
        temp.addAll(terms);
        // Sorting the list alphabetically
        Collections.sort(temp, CharSequence::compare);
        // Adding the alphabetized list into this.terms
        this.terms.addAll(temp);
        System.out.println("Alphabetized list: " + this.terms);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> termsCopy = new ArrayList<CharSequence>();
        List<CharSequence> matches = new ArrayList<CharSequence>();
        termsCopy.addAll(this.terms);
        System.out.println("Index? " + Collections.binarySearch(termsCopy, prefix, null));
        int superiorIndex = Collections.binarySearch(termsCopy, prefix, null);
        for(int i = superiorIndex; i < termsCopy.size(); i++) {
            if(CharSequence.compare(termsCopy.get(i).subSequence(0, prefix.length()), prefix) == 0) {
                matches.add(termsCopy.get(i));
            }
        }
        int curr = superiorIndex;
        double truth = CharSequence.compare(prefix, terms.get(curr).subSequence(0, prefix.length()));
        while(truth == 0 && !(matches.contains(terms.get(curr)))) {
            if(CharSequence.compare(termsCopy.get(curr).subSequence(0, prefix.length()), prefix) == 0) {
                matches.add(termsCopy.get(curr));
            }
            curr++;
        }
        System.out.println("Here are the matches" + matches);
        return matches;
    }
}
