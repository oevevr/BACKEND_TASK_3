
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

            while (n.parent != null && n.parent.left == n) {
                n = n.parent;
            }

            return n.parent;
        }
    }
    private final int degree;
    private int size;
    private Node<E> root;
    private Node<E> firstNode;
    private Node<E> lastNode;
    private long modCount;

    public TreeList(final int degree) {
        checkDegree(degree);
        this.degree = degree;
        Node<E> n = new Node<E>(degree);
        firstNode = n;
        lastNode = n;
        root = n;
    }

    public TreeList() {
        this(DEFAULT_DEGREE);
    }

    @Override
    public boolean add(E e) {
        if (lastNode.size() == degree) {
            Node<E> newNode = new Node<E>(degree);
            newNode.add(e);
            newNode.parent = lastNode;
            lastNode.right = newNode;
            lastNode = newNode;
            fixAfterInsertion(newNode.parent);
        } else {
            lastNode.add(e);
        }

        ++size;
        ++modCount;
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (firstNode.size() == degree) {
            Node<E> newNode = new Node<E>(degree);
            newNode.add(e);
            newNode.parent = firstNode;
            firstNode.left = newNode;
            firstNode = newNode;
            fixAfterInsertion(newNode.parent);
        } else {
            firstNode.add(0, e);
        }

        updateLeftCounters(firstNode, 1);
        ++modCount;
        ++size;
    }

    @Override
    public void addLast(E e) {
        if (lastNode.size() == degree) {
            Node<E> newNode = new Node<E>(degree);
            newNode.add(e);
            newNode.parent = lastNode;
            lastNode.right = newNode;
            lastNode = newNode;
            fixAfterInsertion(newNode.parent);
        } else {
            lastNode.add(e);
        }

        ++modCount;
        ++size;
    }

    @Override
    public void add(int index, E element) {
        if (size == 0) {
            add(element);
            return;
        }

        Node<E> n = root;

        for (;;) {
            if (index < n.leftCount) {
                n = n.left;
            } else if (index > n.leftCount + n.size()) {
                index -= n.leftCount + n.size();
                n = n.right;
            } else {
                index -= n.leftCount;
                break;
            }
        }

        if (n.size() == degree) {
            // Split node 'n'.
            Node<E> newNode = n.split();

            if (index < n.size()) {
                n.add(index, element);
            } else {
                newNode.add(index - n.size(), element);
            }

            if (n.right == null) {
                n.right = newNode;
                newNode.parent = n;

                if (lastNode == n) {
                    lastNode = newNode;
                }

                updateLeftCounters(n, 1);
                fixAfterInsertion(n);
            } else {
                Node<E> successor = n.right.min();
                successor.left = newNode;
                newNode.parent = successor;
                updateLeftCounters(newNode, newNode.size(), n);
                updateLeftCounters(n, 1);
                fixAfterInsertion(successor);
            }
        } else {
            n.add(index, element);
            updateLeftCounters(n, 1);
        }

        ++modCount;
        ++size;
    }

    @Override
    public E get(int index) {
        Node<E> n = root;

        for (;;) {
            if (index < n.leftCount) {
                n = n.left;
            } else if (index >= n.leftCount + n.size()) {
                index -= n.leftCount + n.size();
                n = n.right;
            } else {
                return (E) n.array[index - n.leftCount];
            }
        }
    }

    @Override
    public E set(int index, E element) {
        Node<E> n = root;

        for (;;) {
            if (index < n.leftCount) {
                n = n.left;
            } else if (index >= n.leftCount + n.size()) {
                index -= n.leftCount + n.size();
                n = n.right;
            } else {
                break;
            }
        }

        final int indx = index + n.first - n.leftCount;
        E ret = (E) n.array[indx];
        n.array[indx] = element;
        return ret;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root.left = null;
        root.right = null;
        root.leftCount = 0;
        root.clear();
        firstNode = root;
        lastNode = root;
        ++modCount;
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Removing from empty TreeList.");
        }

        if (firstNode.size() == 0) {
            System.out.println("Fail!");
            System.out.println("first: " + firstNode.first);
            System.out.println("last:  " + firstNode.last);
        }

        E element = firstNode.remove(0);
        updateLeftCounters(firstNode, -1);

        if (firstNode.size() == 0) {
            Node<E> removedNode = removeImpl(firstNode);
            fixAfterDeletion(removedNode);
            firstNode = (removedNode.parent == null
                    ? root
                    : removedNode.parent.min());
        }

        --size;
        ++modCount;
        return element;
    }

    @Override
    public E removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Removing from empty TreeList.");
        }

        E element = lastNode.remove(lastNode.size() - 1);

        if (lastNode.size() == 0) {
            Node<E> removedNode = removeImpl(lastNode);
            fixAfterDeletion(removedNode);
            lastNode = removedNode.parent;
        }

        --size;
        ++modCount;
        return element;
    }

    @Override
    public E remove(int index) {
        Node<E> n = root;

        for (;;) {
            if (index < n.leftCount) {
                n = n.left;
            } else if (index >= n.leftCount + n.size()) {
                index -= n.leftCount + n.size();
                n = n.right;
            } else {
                break;
            }
        }

        E removedElement = n.remove(index - n.leftCount);

        if (n.size() == 0) {
            Node<E> removedNode = removeImpl(n);
            fixAfterDeletion(removedNode);
            updateLeftCounters(removedNode, -1);
        } else {
            updateLeftCounters(n, -1);
        }

        --size;
        ++modCount;
        return removedElement;
    }

    @Override
    public E pollFirst() {
        if (size == 0) {
            return null;
        }

        return (E) firstNode.array[firstNode.first];
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }

        return (E) lastNode.array[lastNode.last];
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException(
                    "Reading the head of an empty list.");
        }

        return (E) firstNode.array[firstNode.first];
    }

    @Override
    public E getLast() {
        if (size == 0) {
            throw new NoSuchElementException(
                    "Reading the tail of an empty list.");
        }

        return (E) lastNode.array[lastNode.last];
    }

    @Override
    public E peekFirst() {
        if (size == 0) {
            return null;
        }

        return (E) firstNode.array[firstNode.first];
    }

    @Override
    public E peekLast() {
        if (size == 0) {
            return null;
        }

        return (E) lastNode.array[lastNode.last];
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean offer(E e) {
        add(e);
        return true;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override