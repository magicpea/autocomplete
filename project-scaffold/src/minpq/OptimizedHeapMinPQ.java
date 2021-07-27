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

    private Comparator<Double> comparator;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
    }

    /**
     * OptimizedHeapMinPQ add:
     * @param item node value
     * @param priority node priority
     * summary : this method adds a new priority node to the priority queue
     *           also updating its index in our index Hashmap
     * pre : given an existing priority item and priority value
     * post : create a new node to be added to the priority queue, 
     *        increasing its size by one node and 
     *        reordering all nodes to 
     * 
     * Worst Case Runtime: BigTheta(log(N))
     */
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
        size++;
        sink(0);
        swim(items.size() - 1);  
    }

    /** 
     * OptimiziedHeapMinPQ swim:
     * @param k index of node we want to run swim comparisons on 
     * summary : this method maintains the minPQ invariant
     *           for priority node at index k 
     *           checking if its priority value is less than those to the left of it
     *           it recursively swaps nodes until all nodes starting from k
     *           are sorted from least to greatest.  
     *              
     * pre : given k index for a node in the pq.
     * post : Asserts minpQ invariant for nodes with a smaller priority than neighbors
     * 
     * Worst Case Runtime:  BigTheta(N * log(N)) *(;-;) recursion be like
    */ 
    private void swim(int k) {
        while (k > 1 && greater(k - 1, k)) {
            exchange(k, k - 1);
            k = k - 1;
        }
    }

    /** 
     * OptimiziedHeapMinPQ sink:
     * @param k index of node we want to run sink comparisons on 
     * summary : this method maintains the minPQ invariant
     *           for priority node at index k 
     *           checking if its priority value is greater than those to the right of it
     *           it recursively swaps nodes until all nodes starting from k
     *           are sorted from least to greatest.  
     *              
     * pre : given k index for a node in the pq.
     * post : Asserts minpQ invariant for nodes with a greater priority than neighbors
     * 
     * Worst Case Runtime:  BigTheta(N * log(N)) *(;-;) recursion be like
    */ 
    private void sink(int k) {
        while (k < items.size() &&  greater(k, k + 1)) {
            if (!greater(k, k + 1)) break;
            exchange(k, k + 1);
            k++;
        }
    }

    /** 
     * OptimiziedHeapMinPQ greater:
     * @param i the first node's index
     * @param j the second node's index
     * summary : this method checks 
     *           if the priority at i'th node is greater than
     *           the j'th priority
     *              
     * pre : given i and j indexes are valid in the pq
     * post : Returns true if i'th priority is greater than j'th priority
     * 
     * Worst Case Runtime:  BigTheta(1) opperation *using our data structures
    */
     private boolean greater(int i, int j) {
         return (items.get(i).priority() > items.get(j).priority());
     }

     private void exchange(int i, int j) {
         PriorityNode<T> tempI = new PriorityNode<>(items.get(i).item(), items.get(i).priority());
         PriorityNode<T> tempJ = new PriorityNode<>(items.get(j).item(), items.get(j).priority());
         // updating the pq
         items.set(i, tempJ);
         items.set(j, tempI);
         // updating the map
         itemToIndex.put(tempI.item(), items.indexOf(tempI));
         itemToIndex.put(tempJ.item(), items.indexOf(tempJ));
     }

    /** 
     * OptimiziedHeapMinPQ contains:
     * @param item node value
     * summary : this method checks if our given item
     *           is in our priority queue.
     * pre : given our item value, we check to see if we can find it in the priority queue
     * post : Returns True or False if our item is in the priority queue.
     * 
     * Worst Case Runtime:  BigTheta(1) opperation *maps very good
    */
    @Override
    public boolean contains(T item) {
        return itemToIndex.containsKey(item);
    }

    @Override
    /** 
     * OptimiziedHeapMinPQ peekMin:
     * summary : this method returns the lowest priority item value from our priority queue
     *           without modifying the priority queue structure
     * pre : given a non-empty priority queue, otherwise throws a exception
     *       if our priority queue is empty
     * post : returns the item value for the lowest priority node in our priority queue
     * 
     * Worst Case Runtime:  BigTheta(1) opperation pq indexing at the head of pq
    */
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        
        return items.get(1).item();

    }
    /** 
     * OptimizedHeapMinPQ removeMin:
     * summary : this method removes and returns the lowest priority item from our priority queue
     * pre : given a non-empty priority queue, otherwise throws an empty exception
     * post : removes and returns the lowest priority item value from the priority queue
     *        Also removing the corresponding itemToIndex key,value.
     * 
     * Worst Case Runtime: BigTheta(1) b/c map indexing  >;/  (>.<)
    */
    @Override
    public T removeMin() {
        if (isEmpty() || items.size() == 1) {
            throw new NoSuchElementException("PQ is empty");
        }
        T homeSlice = items.get(1).item();
        itemToIndex.remove(1);
        items.remove(1);
        return homeSlice;
    }
    /** 
     * OptimizedHeapMinPQ changePriority:
     * @param item node value
     * @param priority node priority
     * summary : given the item exists in the queue,
     *           updates an item's priority in the priority queue
     *           with the new priority value. 
     *           Also updating the corresponding itemToIndex key,value. 
     *           
     * pre : if a node of a certain item value exists in the priority queue, 
     *       otherwise throws an does not contain exception
     * post : the node of iterest's priority is changed in the priority queue.
     *        as well as corresponding index in itemToIndex.
     * 
     * Worst Case Runtime: BigTheta(N * log(N)) recursively sinking/swiming
    */
    @Override
    public void changePriority(T item, double priority) {
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
    }

    @Override
    /** 
     * OptimizedHeapMinPQ size: 
     * summary : this method returns the number of elements in the priority queue
     * pre : given a priority queue exists
     * post : returns the number of priority nodes in the priority queue.
     * 
     * Worst Case Runtime: BigTheta(1)
    */
    public int size() {
        return size;
    }
}
