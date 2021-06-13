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
    public void decreasePriority(E e, W newPriority);

    /**
     * Returns the size of this queue.
     *
     * @return the size of this queue.
     */
    public int size();

    /**
     * Returns <code>true</code> if this queue is empty, <code>false</code>
     * otherwise.
     */
    public boolean isEmpty();

    /**
     * Retrieves but does not remove a top element.
     *
     * @return the top element (with the least priority token).
     */
    public E min();

    /**
     * Removes the top element from this que