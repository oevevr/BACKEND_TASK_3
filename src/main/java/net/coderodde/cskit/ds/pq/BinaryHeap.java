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

    @O