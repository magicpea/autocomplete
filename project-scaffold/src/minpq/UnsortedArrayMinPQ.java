package minpq;

import org.w3c.dom.Node;

import java.util.*;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T>  {
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
        PriorityNode<T> temp = new PriorityNode<T>(item, priority);
        System.out.println(temp);
        items.add(temp);
    }

    @Override
    public boolean contains(T item) {
        for(PriorityNode<T> c : items) {
            if(c.item() == item) {
                return true;
            }
        }
        return false;
//        return items.contains(item);
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return peekMin();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return removeMin();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        // Let's day we don't know where value is in the array
//        for(PriorityNode<T> c : items) {
//            if(c.item() == item) {
//                System.out.println("Before: " + items);
//                items.remove(c);
//                System.out.println("After: " + items);
//            }
//        }
//        PriorityNode<T> temp = new PriorityNode<T>(item, priority);
//        items.add(temp);
        System.out.println("Item: " + item + " & Priority: " + priority);
    }
//
//    double oldPriority = itemToPriority.get(item);
//        if (priority != oldPriority) {
//        Set<T> itemsWithOldPriority = priorityToItem.get(oldPriority);
//        itemsWithOldPriority.remove(item);
//        if (itemsWithOldPriority.isEmpty()) {
//            priorityToItem.remove(oldPriority);
//        }
//        itemToPriority.remove(item);
//        add(item, priority);
//    }

    @Override
    public int size() {
        return items.size();
    }
}
