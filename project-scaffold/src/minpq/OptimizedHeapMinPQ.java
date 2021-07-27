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
        PriorityNode<T> temp = new PriorityNode<>(item, priority);
        PriorityNode<T> tempFirst = new PriorityNode<T>(null, 0);
        if(items.isEmpty() && itemToIndex.isEmpty()) {
            items.add(tempFirst);
            itemToIndex.put(null, null);
        }
        items.add(temp);
        sink(0);
        swim(items.size() - 1);

        PriorityNode<T> wtf = new PriorityNode<>(item, temp.priority());
        int index = items.indexOf(wtf);
        // Having trouble deciding what to put in items.indexOf()
        itemToIndex.put(temp.item(), index);
        System.out.println("Here's the map ! : " + itemToIndex);
        System.out.println(items);
        
    }

     private void swim(int k) {
         while (k > 1 && greater(k - 1, k)) {
             exchange(k, k - 1);
             k = k - 1;
         }
         System.out.println("");
     }

     private void sink(int k) {
         while (k < items.size() &&  greater(k, k + 1)) {
             if (!greater(k, k + 1)) break;
             exchange(k, k + 1);
             k++;
         }
     }

    // Comparing the priorities as a means to reset the index
     private boolean greater(int i, int j) {
         return (items.get(i).priority() > items.get(j).priority());
     }

     private void exchange(int i, int j) {
         PriorityNode<T> tempI = new PriorityNode<>(items.get(i).item(), items.get(i).priority());
         PriorityNode<T> tempJ = new PriorityNode<>(items.get(j).item(), items.get(j).priority());
         items.set(i, tempJ);
         items.set(j, tempI);
     }

    

    @Override
    public boolean contains(T item) {
        return itemToIndex.containsKey(item);
    }

    @Override
    // worst case runtime : BigTheta(1)
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        
        return items.get(1).item();

    }

    @Override
    // DoNE ?

    public T removeMin() {
        if (isEmpty() || items.size() == 1) {
            throw new NoSuchElementException("PQ is empty");
        }
        T homeSlice = items.get(1).item();
        itemToIndex.remove(1);
        items.remove(1);
        return homeSlice;
    }

    @Override
    public void changePriority(T item, double priority) {
        System.out.println("MAP B4: " + itemToIndex);
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int index = itemToIndex.get(item);
        double oldPriority = items.get(index).priority();
        items.get(index).setPriority(priority);
        if (oldPriority < priority) {
            sink(index);
        } else if (oldPriority > priority) {
            swim(index);
        }
        itemToIndex.put(item, items.indexOf(new PriorityNode<>(item, priority)));
    }

    @Override
    // Done!
    public int size() {
        return items.size();
    }
}
