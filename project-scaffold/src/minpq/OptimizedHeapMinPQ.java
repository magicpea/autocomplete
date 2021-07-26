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
        // Dummy variables here for now (might delete l8r) :
        if(items.isEmpty() && itemToIndex.isEmpty()) {
            items.add(tempFirst);
            itemToIndex.put(null, null);
        }
        items.add(temp);
        // UPDATE THE MAP DO NOT FORGET TO DO THIS !!!!!!!!!!!!
        swim(items.size() - 1);
        System.out.println("Priority node list: " + items);
//        itemToIndex.put(temp.item() , items.indexOf(temp));
//        for(PriorityNode<T> c : items) {
//            itemToIndex.put(c.item() , items.indexOf(c));
//        }
        mapQuest(items);
        System.out.println("Here's the map ! : " + itemToIndex);

        // Use the list to create a new map
        // But everytime we call add(), delete the map and rebuild it
        
        // we have a map
        // right now we are adding the new node/priority queue index values sequentially

        // we need it to be in order from least to greatest

        // key: item , value: index in our priority queue of the given item

        // modify the map so that indexes as keys

        // 
    }

     private void swim(int k) {
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
        System.out.println("Changing priority of: " + item + " to: " + priority);
        System.out.println("ITEMS BEFORE CHANGING PRIORITY: " + items);
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        // so we assume structure is already built
        // what we want to add
        PriorityNode<T> temp = new PriorityNode<>(item, priority); 
        
        // get the item index from the map somehow

        // remove at the index the node using array list
//        int index_ismo = itemToIndex.get(item);
//        this.items.remove(index_ismo);
        for(PriorityNode<T> c : items) {
            if(c.item() == item) {
                c.setPriority(priority);
                // update the list bc we will swim using c
                // we need to get the index of c in items
//                swim(itemToIndex.get(c.item()));
//                sink(itemToIndex.get(c.item()));
            }
            // need to update the map !
        }
        System.out.println("ITEMS AFTER CHANGING PRIORITY: " + items);
        mapQuest(items);
        // update the item of interest
        // swim
        // update map index as well
    }

    private void mapQuest(List<PriorityNode<T>> temp) {
        System.out.println("On the quest!");
//        itemToIndex.clear();
        for(PriorityNode<T> c : temp) {
            itemToIndex.put(c.item() , temp.indexOf(c));
        }
        System.out.println("The update map post-quest is : " + itemToIndex);
    }

    @Override
    // Done!
    public int size() {
        return items.size();
    }
}
