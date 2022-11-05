
package net.coderodde.cskit.sorting;

import java.util.Comparator;
import java.util.Iterator;

/**
 * This class implements a tree traversal sort using unbalanced binary trees.
 * Stability guaranteed.
 *
 * @author Rodion Efremov
 * @version 1.618
 */
public class TreeSort<E extends Comparable<? super E>>
implements ObjectSortingAlgorithm<E> {

    @Override
    public void sort(E[] array) {
        sort(array, new Range(0, array.length - 1));
    }

    @Override
    public void sort(E[] array, Range r) {
        if (r.from <= r.to) {
            ascendingSort(array, r.from, r.to);
        } else {
            descendingSort(array, r.to, r.from);
        }
    }

    static class Node<E> {
        E value;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        Node(E value) {
            this.value = value;
        }
    }

    static class AscendingTree<E extends Comparable<? super E>>
    implements Iterable<E> {

        private Node<E> root;
        private int size;

        AscendingTree(E first) {
            this.root = new Node<E>(first);
            this.size = 1;
        }

        @Override
        public Iterator<E> iterator() {
            return new TreeIterator();
        }

        void insert(E value) {
            Node<E> newNode = new Node<E>(value);
            Node<E> current = root;
            int c;

            for (;;) {
                if (value.compareTo(current.value) < 0) {
                    if (current.left != null) {
                        current = current.left;
                    } else {
                        current.left = newNode;
                        break;
                    }
                } else {