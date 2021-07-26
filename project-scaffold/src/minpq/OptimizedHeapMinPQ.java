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

    // Not sure wtf this is but we shall see
    private Comparator<Double> comparator;

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
        // Let's start with this unsorted
        PriorityNode<T> temp = new PriorityNode<>(item, priority);
        PriorityNode<T> tempFirst = new PriorityNode<T>(null, 0);
        if(items.isEmpty() && itemToIndex.isEmpty()) {
            items.add(tempFirst);
            itemToIndex.put(null, null);
        }
        items.add(temp);
        System.out.println("Here's the list beforehand : " + items);
        // UPDATE THE MAP DO NOT FORGET TO DO THIS !!!!!!!!!!!!
        itemToIndex.put(temp.item(), (items.size() - 1));
        swim(items.size() - 1);
        System.out.println("Here's the map: " + itemToIndex);
        System.out.println("Here's the list afterward : " + items);
        // basically we have two structures to help us out

        // 1. the priority queue which is an Arraylist (hard)

            // our goal is to make sure that we update these two structures everytime we call add 

            // additionally, we must make sure that the order of the priority queue is maintained
            // basically make sure everything is still lowest to highest after we add a new node.
        
            // how do we do this you might ask?

            // basically sinking and swimmming

        // 2. the map of <item, index> (easy once 1. is done)
    }

     private void swim(int k) {
        System.out.println("K is: " + k + " && k - 1 is: " + (k - 1));
         while (k > 1 && greater(k - 1, k)) {
             exchange(k, k - 1);
             k = k - 1;
         }
         System.out.println("");
     }

     private void sink(int k) {
         while (2*k <= size) {
             int j = 2*k;
             if (j < size && greater(j, j+1)) j++;
             if (!greater(k, j)) break;
             exchange(k, j);
             k = j;
         }
     }

    // Comparing the priorities as a means to reset the index
     private boolean greater(int i, int j) {
         return (items.get(i).priority() > items.get(j).priority());
     }

     private void exchange(int i, int j) {
         System.out.println("Parent trap to follow");
         System.out.println("item i is: " + items.get(i));
         System.out.println("item j is: " + items.get(j));
         PriorityNode<T> tempI = new PriorityNode<>(items.get(i).item(), items.get(i).priority());
         PriorityNode<T> tempJ = new PriorityNode<>(items.get(j).item(), items.get(j).priority());
         items.set(i, tempJ);
         items.set(j, tempI);
         System.out.println("item i is: " + items.get(i));
         System.out.println("item j is: " + items.get(j));
     }

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
        return itemToIndex.containsKey(item);
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
