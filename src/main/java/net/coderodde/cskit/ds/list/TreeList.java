
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
        /**
         * The parent node.
         */
        Node<E> parent;
        /**
         * The height of this node.
         */
        int height;
        /**
         * The element amount of the left subtree.
         */
        int leftCount;
        /**
         * The array for storing elements.
         */
        Object[] array;
        /**
         * The leftmost index of the element array.
         */
        int first;
        /**
         * The rightmost index of the element array.
         */
        int last;

        Node(final int degree) {
            this.array = new Object[degree];
            this.last = -1;
        }

        int size() {
            return last - first + 1;
        }

        /**
         * Appends an element to this node's array.
         *
         * @param element element to append.
         */
        void add(E element) {
            if (last == array.length - 1) {
                final int left = Math.max((array.length - size()) >> 1, 1);

                for (int i = first; i <= last; ++i) {
                    array[i - left] = array[i];
                }

                for (int i = last - left + 2, j = 1; j < left; ++j, ++i) {
                    array[i] = null;
                }

                first -= left;
                last -= left;
            }

            array[++last] = element;
        }

        /**
         * Inserts an element into this node's array.
         *
         * @param index the index to insert at.
         * @param element the element to insert.
         */
        void add(int index, E element) {
            final int elementsBefore = index;
            final int elementsAfter = last - first + 1 - index; // 16 - 12 = 3

            if (elementsBefore < elementsAfter) {
                if (first > 0) {
                    // Shift left.
                    for (int i = first, j = 0; j < elementsBefore; ++j, ++i) {
                        array[i - 1] = array[i];
                    };

                    --first;
                } else {
                    // Shift right.
                    for (int i = last, j = 0; j < elementsAfter; ++j, --i) {
                        array[i + 1] = array[i];
                    }

                    ++last;
                }
            } else {
                // elementsBefore >= elements
                if (last < array.length - 1) {
                    // Shift right.
                    for (int i = last, j = 0; j < elementsAfter; ++j, --i) {
                        array[i + 1] = array[i];
                    }

                    ++last;
                } else {
                    // Shift left.
                    for (int i = first, j = 0; j < elementsBefore; ++j, ++i) {
                        array[i - 1] = array[i];
                    }

                    --first;
                }
            }

            // Do insert.
            array[index + first] = element;
        }

        E remove(int index) {
            E old = (E) array[index + first];

            final int elementsBefore = index;
            final int elementsAfter = last - index - first;

            if (elementsBefore < elementsAfter) {
                // Shift left part to right.
                for (int i = index + first - 1; i >= first; --i) {
                    array[i + 1] = array[i];
                }

                array[first++] = null;
            } else {
                // elementsBefore >= elementsAfter
                // Shift right part to left.
                for (int i = index + first; i < last; ++i) {
                    array[i] = array[i + 1];
                }

                array[last--] = null;
            }

            return old;
        }

        void clear() {
            for (int i = first; i <= last; ++i) {
                array[i] = null;
            }

            first = array.length >> 1;
            last = first - 1;
        }

        /**
         * Assumes that this node is full.
         *
         * @return the right node.
         */
        Node<E> split() {
            // TODO: make this run faster.
            final int degree = this.array.length;

            Node<E> newNode = new Node<E>(degree);

            final int leftElements = degree >> 1;
            final int leftSkip = (degree - leftElements) >> 1;
            final int rightElements = degree - leftElements;
            final int rightSkip = (degree - rightElements) >> 1;

            this.first = leftSkip;
            this.last = leftSkip + leftElements - 1;
            newNode.first = rightSkip;
            newNode.last = rightSkip + rightElements - 1;

            int limit = rightSkip + rightElements;

            // Load the right block of this to newNode.
            for (int i = rightSkip, j = leftElements;
                    i < limit;
                    ++i, ++j) {
                newNode.array[i] = this.array[j];
                this.array[j] = null;
            }

            if (leftSkip > 0) {
                for (int i = leftElements - 1; i >= 0; --i) {
                    this.array[i + leftSkip] = this.array[i];
                    this.array[i] = null;
                }
            }

            return newNode;
        }

        Node<E> min() {
            Node<E> e = this;

            while (e.left != null) {
                e = e.left;
            }

            return e;
        }

        Node<E> max() {
            Node<E> e = this;

            while (e.right != null) {
                e = e.right;
            }

            return e;
        }

        Node<E> successor() {
            if (right != null) {
                return right.min();
            }

            Node<E> n = this;

            while (n.parent != null && n.parent.right == n) {
                n = n.parent;
            }

            return n.parent;
        }

        Node<E> predecessor() {
            if (left != null) {
                return left.max();
            }

            Node<E> n = this;