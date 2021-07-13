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

    public List<CharSequence> getTerms() {
        return terms;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> words) {
        // TODO: Replace with your code
        // throw new UnsupportedOperationException("Not implemented yet");

        // Big theta (N)
        for ( CharSequence c : words) {
            // add the word to the collection
            terms.add(c);
        }
        // debugging to make sure everything gets added
        System.out.print("hey!");
//        System.out.println(terms.toString());
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // throw new UnsupportedOperationException("Not implemented yet");
        List<CharSequence> temp = new ArrayList<CharSequence>();
        for (CharSequence c : terms){
            if (CharSequence.compare(c.subSequence(0, prefix.length()), prefix) == 0) {
                // add the lexographic match to match
                temp.add(c);
            }
        }
        return temp;
    }
}
