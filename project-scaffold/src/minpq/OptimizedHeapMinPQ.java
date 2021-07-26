package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of item-priority pairs.
     */
    private final List<PriorityNode<T>> items;
    /**
     * {@link Map} of each item to its associated index in the {@code items} heap.
     */
    private final Map<T, Integer> itemToIndex;
    /**
     * The number of elements in the heap.
     */
    private int size;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        // We need to add to the map the <item, index>
        // PriorityNode<T> temp = new PriorityNode<>(item, priority);
        // int i = items.indexOf(temp);
        // // Resizing situation
        // // Now, let's add the key-val pair to the map
        // itemToIndex.put(temp.item(), i);
        // swim(size);
        // assert isMinHeap();

        // basically we have two structures to help us out

        // 1. the priority queue which is an Arraylist (hard)

            // our goal is to make sure that we update these two structures everytime we call add 

            // additionally, we must make sure that the order of the priority queue is maintained
            // basically make sure everything is still lowest to highest after we add a new node.
        
            // how do we do this you might ask?

            // basically sinking and swimmming

        // 2. the map of <item, index> (easy once 1. is done)

        

        // 

    }
//    public void insert(Key x) {
//        // double size of array if necessary
//        if (n == pq.length - 1) resize(2 * pq.length);
//
//        // add x, and percolate it up to maintain heap invariant
//        pq[++n] = x;
//        swim(n);
//        assert isMinHeap();

    // private void swim(int k) {
    //     while (k > 1 && greater(k/2, k)) {
    //         exch(k, k/2);
    //         k = k/2;
    //     }
    // }

    // private void sink(int k) {
    //     while (2*k <= size) {
    //         int j = 2*k;
    //         if (j < size && greater(j, j+1)) j++;
    //         if (!greater(k, j)) break;
    //         exch(k, j);
    //         k = j;
    //     }
    // }

    // private boolean greater(int i, int j) {
    //     if (comparator == null) {
    //         return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
    //     }
    //     else {
    //         return comparator.compare(pq[i], pq[j]) > 0;
    //     }
    // }

    // private void exch(int i, int j) {
    //     Key swap = pq[i];
    //     pq[i] = pq[j];
    //     pq[j] = swap;
    // }

    // // is pq[1..n] a min heap?
    // private boolean isMinHeap() {
    //     for (int i = 1; i <= n; i++) {
    //         if (pq[i] == null) return false;
    //     }
    //     for (int i = n+1; i < pq.length; i++) {
    //         if (pq[i] != null) return false;
    //     }
    //     if (pq[0] != null) return false;
    //     return isMinHeapOrdered(1);
    // }

    @Override
    public boolean contains(T item) {
        // iterator method I think?
        boolean cake = false;
        return cake;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // taking advantage of unsorted ArrayMinPQ :)
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

    @Override
    // DoNE ?

    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // this one is chill as we remove the first element
        // also Sarah recomended starting the array indexing at a[1] instead of a[0]

        // save that min value
        T homeSlice = items.get(1).item();

        // basically update the following
    
        // map- hash map with keys being indexes
        itemToIndex.remove(1);

        // priorityqueue - arraylist my dudes, drop an index in the chat
        items.remove(1);

        return homeSlice;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        // TODO: Replace with your code
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    // Done!
    public int size() {
        return size;
    }
}
