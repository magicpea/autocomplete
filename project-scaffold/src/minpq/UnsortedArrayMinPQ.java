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

    /**
     * UnsortedArrayMinPQ add:
     * @param item- node value
     * @param priority- node priority
     * summary : adds new priority node to the pq
     * pre : given an existing priority item and priority value
     * post : create a new node to be added to the priority queue,
     *        increasing its size by one node.
     *
     * Worst Case Runtime: BigTheta(log(N))
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        PriorityNode<T> temp = new PriorityNode<>(item, priority);
        items.add(temp);
    }

    /**
     * UnsortedArrayMinPQ contains:
     * @param item node value
     * summary : this method checks if our given item
     *           is in our pq
     * pre : given our item value, we check to see if we can find it in the priority queue
     * post : Returns True or False if our item is in the priority queue
     *
     * Worst Case Runtime:  BigTheta(N)
     */
    @Override
    public boolean contains(T item) {
        List<PriorityNode<T>> temp = new ArrayList<>(items);
        for (PriorityNode<T> c : temp){
            if (c.item() == item){
                return true;
            }
        }
        return false;
    }

    /**
     * UnsortedArrayMinPQ peekMin:
     * summary : this method returns the lowest priority item value from our priority queue
     *           without modifying the priority queue structure
     * pre : given a non-empty priority queue, otherwise throws a exception
     *       if our priority queue is empty
     * post : returns the item value for the lowest priority node in our priority queue
     *
     * Worst Case Runtime:  BigTheta(N)
     */
    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double min = -1.0;
        T temp = null;
        for(PriorityNode<T> c : items) {
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

    /**
     * UnsortedArrayMinPQ  removeMin:
     * summary : this method removes and returns the lowest priority item from our priority queue
     * pre : given a non-empty priority queue, otherwise throws an empty exception
     * post : returns the min value
     *
     * Worst Case Runtime:  BigTheta(2 * N)
     */
    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
       T minVal = peekMin();
       for(PriorityNode<T> c : items) {
           if(c.item() == minVal) {
               items.remove(c);
               return minVal;
           }
       }
       return null;
    }

    /** @param item- node value
     * @param priority- node priority
     * summary : changes the priority of the node with the given item
     * pre : the item priority is unchanged
     * post : the item priority is changed to the parameter priority
     *
     * Worst-case runtime: BigTheta(N)
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
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
