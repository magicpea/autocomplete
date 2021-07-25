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


    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        pq.add(new PriorityNode<>(item, priority));
        System.out.println(pq);
    }

    @Override
    public boolean contains(T item) {
        PriorityQueue<PriorityNode<T>> temp = new PriorityQueue<>(pq);
        // for (PriorityNode<T> c : temp){
        //     if (c.item() == item){
        //         return true;
        //     }
        // }
        // return false;
        
        
        return pq.contains(new PriorityNode<>(item, -1));
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return pq.peek().item();  
    }

    @Override
    public T removeMin() {
        if (pq.isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        T minVal = peekMin();
       for(PriorityNode<T> c : pq) {
           if(c.item() == minVal) {
               pq.remove(c);
               return minVal;
           }
       }
       return null;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        for(PriorityNode<T> c : pq) {
            System.out.println(c.item());
            if(c.item() == item) {
                c.setPriority(priority);
            }
        }
    }

    @Override
    public int size() {
        return pq.size();
    }
}
