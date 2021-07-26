package minpq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import UnsortedArrayMinPQ.java;

public class EasyExample  {
    public static void main(String[] args) {
//        ExtrinsicMinPQ<String> pq = new DoubleMapMinPQ<>();
        ExtrinsicMinPQ<String> pq = new OptimizedHeapMinPQ<>();
        pq.add("11", 70.0);
        pq.add("1", 1.0);
        pq.add("2", 2.0);
        pq.add("4", 4.0);
        pq.add("6", 6.0);
        pq.add("3", 3.0);
        pq.add("5", 5.0);
        System.out.println(pq.contains("35"));
        System.out.println(pq.contains("2"));
        
        System.out.println(pq.peekMin());
        // Call methods to evaluate behavior.

        pq.changePriority("3", 0.0);
        pq.changePriority("1", 7.0);
        
        System.out.println("Min is : " + pq.peekMin());
         while (!pq.isEmpty() && pq.size() != 1) {
             System.out.println(pq.removeMin());
         }
    }
}
