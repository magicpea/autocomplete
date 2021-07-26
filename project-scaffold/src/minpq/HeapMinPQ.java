package minpq;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

//A standard binary heap priority queue that delegates all method calls
//        to java.util.PriorityQueue. This class contains only one field
//        assigned to an instance of PriorityQueue. Each operation is
//        implemented by calling the underlying PriorityQueue.

/**
 * {@link PriorityQueue} implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class HeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each item-priority pair.
     */
    private final PriorityQueue<PriorityNode<T>> pq;

    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
    }

    /**
     * HeapMinPQ add:
     * @param item node value
     * @param priority node priority
     * summary : this method adds a new priority node to the priority queue
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
        pq.add(new PriorityNode<>(item, priority));
    }


    /** 
     * HeapMinPQ contains:
     * @param item node value
     * summary : this method checks if our given item
     *           is in our priority queue.
     * pre : given our item value, we check to see if we can find it in the priority queue
     * post : Returns True or False if our item is in the priority queue.
     * 
     * Worst Case Runtime:  BigTheta(N) opperation 
    */
    @Override
    public boolean contains(T item) {
        return pq.contains(new PriorityNode<>(item, -1));
    }

    /** 
     * HeapMinPQ peekMin:
     * summary : this method returns the lowest priority item value from our priority queue
     *           without modifying the priority queue structure
     * pre : given a non-empty priority queue, otherwise throws a exception
     *       if our priority queue is empty
     * post : returns the item value for the lowest priority node in our priority queue
     * 
     * Worst Case Runtime:  BigTheta(1) opperation
    */
    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return pq.peek().item();  
    }
    
    /** 
     * HeapMinPQ removeMin:
     * summary : this method removes and returns the lowest priority item from our priority queue
     * pre : given a non-empty priority queue, otherwise throws an empty exception
     * post : removes and returns the lowest priority item value from the priority queue
     * 
     * Worst Case Runtime: BigTheta(N * log(N)) *according to the internet? 
    */
    @Override
    public T removeMin() {
        if (pq.isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        
        T min = peekMin();
        
        // removes the min
        pq.poll(); 
        return min;
    }

    /** 
     * HeapMinPQ changePriority
     * @param item node value
     * @param priority node priority
     * summary : given the item exists in the queue,
     *           updates an item's priority in the priority queue
     *           with the new priority value.
     *           
     * pre : if a node of a certain item value exists in the priority queue, 
     *       otherwise throws an does not contain exception
     * post : the node of iterest's priority is changed in the priority queue.
     * 
     * Worst Case Runtime: BigTheta(N) *iterates over whole priority queue.
    */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        for(PriorityNode<T> c : pq) {
            if(c.item() == item) {
                c.setPriority(priority);
            }
        }
    }

    /** 
     * HeapMinPQ size: 
     * summary : this method returns the number of elements in the priority queue
     * pre : given a priority queue
     * post : returns the number of priority nodes in the priority queue.
     * 
     * Worst Case Runtime: BigTheta(1) or BigTheta(N) ?
    */
    @Override
    public int size() {
        return pq.size();
    }
}
