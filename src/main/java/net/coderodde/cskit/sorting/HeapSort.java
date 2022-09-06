package net.coderodde.cskit.sorting;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.coderodde.cskit.ds.pq.BinaryHeap;
import net.coderodde.cskit.ds.pq.PriorityQueue;

/**
 * Implements the heap sort.
 *
 * @author Rodion Efremov
 * @version 1.618033 (18.1.2014)
 */
public class HeapSort<E extends Comparable<? super E>>
implements ObjectSortingAlgorithm<E> {

    private Map<E, List<E>> MAP;
    private PriorityQueue<E, E> HEAP;

    public HeapSort(PriorityQueue<E, E> heap) {
        this.HEAP = heap.newInstance();
        this.MAP = new HashMap<E, List<E>>();
    }

    public Hea