
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
        private final E datum;
        private Node<E, W> parent;
        private Node<E, W> left = this;
        private Node<E, W> right = this;
        private Node<E, W> child;
        private int degree;
        private boolean marked;
        private W priority;

        Node(E element, W priority) {
            this.datum = element;
            this.priority = priority;
        }
    }

    private static final double LOG_PHI = Math.log((1 + Math.sqrt(5)) / 2);

    private Map<E, Node<E, W>> map;
    private Node<E, W> minimumNode;
    private int size;

    public FibonacciHeap(int defaultMapCapacity) {
        map = new HashMap<E, Node<E, W>>(defaultMapCapacity);
    }

    public FibonacciHeap() {
        this(DEFAULT_MAP_CAPACITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(E e, W priority) {
        checkNotNull(e, "Null elements not allowed in this Fibonacci heap");
        Node<E, W> node = new Node<E, W>(e, priority);

        if (minimumNode != null) {
            node.left = minimumNode;
            node.right = minimumNode.right;
            minimumNode.right = node;
            node.right.left = node;

            if (priority.compareTo(minimumNode.priority) < 0) {
                minimumNode = node;
            }
        } else {
            minimumNode = node;
        }

        ++size;
        map.put(e, node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreasePriority(E e, W newPriority) {
        Node<E, W> x = map.get(e);

        if (e == null) {
            throw new NoSuchElementException(
                    "Node " + e.toString() + " is not contained in "
                    + "this Fibonacci heap."
                    );
        }

        if (x.priority.compareTo(newPriority) <= 0) {
            // newPriority is worse than the current priority of x.
            return;
        }

        x.priority = newPriority;
        Node<E, W> y = x.parent;

        if (y != null && x.priority.compareTo(y.priority) < 0) {
            cut(x, y);
            cascadingCut(y);
        }