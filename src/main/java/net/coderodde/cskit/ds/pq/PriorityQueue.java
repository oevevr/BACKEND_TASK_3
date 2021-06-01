package net.coderodde.cskit.ds.pq;

/**
 * This interface specifies the minimum priority queue API.
 *
 * @author Rodion Efremov
 * @version 1.618 (11.12.2013)
 */
public interface PriorityQueue<E, W extends Comparable<? super W>> {

    /**
     * Inserts a new element into this priority queue.
     *
     * @param e the element to insert.
     * @param priority the initial priority of the element.
     */
    public void insert(E e, W priority);

    /**
     * Decreases the priority of the specified element.
     *
     * @param e the element to decrease.
     * @param newPriority the new priority of the element.
     */
    public void decreasePriority(E