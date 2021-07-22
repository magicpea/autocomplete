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
        // in the current version we are calling the method we are trying to write
        
        // Figuring out how to avoid this our main issue and how to reference the extrinsicPQ methods 
        

        // build a node

        // then call items.add

        // <T>- all different data types

        PriorityNode<T> temp = new PriorityNode<>(item, priority);
        items.add(temp);
        System.out.println(items);
    }

    @Override
    public boolean contains(T item) {
        // copy 
        // look at the copy
        List<PriorityNode<T>> temp = new ArrayList<>(items);
        
        for ( PriorityNode<T> c : temp){
            System.out.println(c + " " + item);
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
        return peekMin();
        // copy the list's first node?
        // copying is a waste if not necessary
        
        // look at priority node class for help 
        // then grab the first node?

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
        System.out.println("changing priority chief");

        System.out.println("item: " + item);
        System.out.println("priority: " + priority);
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        

        // PriorityNode<T> tem = new PriorityNode<T>(item, priority);
        // System.out.println(tem);
        // items.add(tem);
        // System.out.println(items);

        // for loop to iterate the queue to find the matching item in the que to modify

        // then once we find that we remove it from the priority que with "remove"

        // then we iterate the queue again, checking priority queue (<, > =)
 
    
        
        // Object[] temp = items.toArray();

        System.out.println("Yo so I'm trying to make an Array, can we like do that plz?  " + items.toString());

        // for ( PriorityNode<T> c : items){
        //     System.out.println(c + " " + item);
        //     if (c.item() == item){
        //         c.priority(priority) = priority;
        //     }
        // }        
    }

    @Override
    public int size() {
        return items.size();
    }
}
