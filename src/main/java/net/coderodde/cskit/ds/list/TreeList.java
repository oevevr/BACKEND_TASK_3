
package net.coderodde.cskit.ds.list;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * This class implements an AVL-tree based list, providing efficient access to
 * elements.
 *
 * @author Rodion Efremov
 * @version 1.618033 (11.1.2014)
 */
public class TreeList<E>
        extends AbstractList<E>
        implements Deque<E>, Serializable, Cloneable {

    public static boolean DEBUG_MSG = true;
    private static final int DEFAULT_DEGREE = 512;

    /**
     * This class implements a node in the AVL-tree containing a contiguous
     * sublist with at most <tt>degree</tt> elements.
     *
     * @param <E> the element type.
     */
    static class Node<E> {

        /**
         * The left subtree.
         */
        Node<E> left;
        /**
         * The right subtree.
         */
        Node<E> right;