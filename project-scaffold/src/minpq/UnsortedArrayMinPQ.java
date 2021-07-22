package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        PriorityNode<T> temp = new PriorityNode<>(item, priority);
        items.add(temp);
    }

    @Override
    public boolean contains(T item) {
        List<PriorityNode<T>> temp = new ArrayList<>(items);
        for ( PriorityNode<T> c : temp){
            if (c.item() == item){
                return true;
            }
        }
        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double min = -1.0;
        T temp = null;
        for(PriorityNode<T> c : items) {
            System.out.println(c);
            if(min < 0) {
                min = c.priority();
                temp = c.item();
            }
            if(c.priority() < min) {
                min = c.priority();
                temp = c.item();
            }
        }
         return temp;
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return null;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        List<PriorityNode<T>> temp = new ArrayList<>();
        PriorityNode<T> n = new PriorityNode<>(item, priority);
        for(PriorityNode<T> c : items) {
            if(c.item() == item) {
                c.setPriority(priority);
            }
        }
    }

    @Override
    public int size() {
        return items.size();
    }
}
