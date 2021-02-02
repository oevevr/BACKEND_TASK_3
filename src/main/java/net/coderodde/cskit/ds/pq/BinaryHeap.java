package net.coderodde.cskit.ds.pq;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import net.coderodde.cskit.ds.pq.PriorityQueue;

/**
 * This class implements the binary minimum heap (a priority queue type).
 *
 * @author Rodion Efremov
 * @version 1.618 (11.12.2013)
 */
public class BinaryHeap<E, W extends Comparable<? super W>>
implements PriorityQueue<E, W> {

    private static final float LOAD_FACTOR = 1.05f;
    private static final int DEFAULT_CAPACITY = 1024;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public PriorityQueue<E, W> newInstance() {
        return new BinaryHeap<E, W>(nodeArray.length);
    }

    private static class HeapNode<E, W> {
        E element;
        W priority;
        int index;

        HeapNode(E element, W priority) {
            this.element = element;
            this.priority = priority;
        }
    }

    /**
     * The amount of elements in this heap.
     */
    private int size;
    private HeapNode<E, W>[] nodeArray;
    private Map<E, HeapNode<E, W>> map;

    /**
     * Constructs a binary heap with initial capacity <code>capacity</code>.
     *
     * @para