package net.coderodde.cskit.ds.pq;

/**
 * This interface specifies the minimum priority queue API.
 *
 * @author Rodion Efremov
 * @version 1.618 (11.12.2013)
 */
public interface PriorityQueue<E, W extends Comparable<? super W>> {

    /**
     * Inserts a new element into this priority q