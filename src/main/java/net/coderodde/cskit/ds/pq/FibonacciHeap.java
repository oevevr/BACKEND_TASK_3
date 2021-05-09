
package net.coderodde.cskit.ds.pq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import static net.coderodde.cskit.Utilities.checkNotNull;

/**
 * This class implements Fibonacci heap.
 *
 * @author Rodion Efremov
 * @version 1.618033 (28.12.2013)
 */
public class FibonacciHeap<E, W extends Comparable<? super W>>
implements PriorityQueue<E, W>{

    private static final int DEFAULT_MAP_CAPACITY = 256;

    private static class Node<E, W extends Comparable<? super W>> {