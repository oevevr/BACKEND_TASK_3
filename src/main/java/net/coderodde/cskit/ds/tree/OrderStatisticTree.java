
package net.coderodde.cskit.ds.tree;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class implements an order-statistic tree. Essentially, this is counted
 * AVL-tree.
 *
 * @author Rodion Efremov
 * @version 1.6180 (19.12.2013)
 */
public class OrderStatisticTree<K extends Comparable<? super K>, V>
        implements Iterable<K>, Map<K, V> {

    /**
     * An entry (a node) in this tree.
     *
     * @param <K> the type of keys.
     * @param <V> the type of values.
     */
    public static class Node<K, V> implements Map.Entry<K, V> {

        /**
         * The key of this entry.
         */
        private K key;

        /**
         * The value of this entry.
         */
        private V value;

        /**
         * Amount of all key-value -mappings in the left subtree of this entry.
         */
        private int count;

        /**
         * This field is the height of this entry. Grows upwards.
         */
        private int h;

        /**
         * The parent entry of this entry.
         */
        private Node<K, V> parent;

        /**
         * The left entry (subtree).
         */
        private Node<K, V> left;

        /**
         * The right entry.
         */
        private Node<K, V> right;

        /**
         * Constructs a new tree node with the specified data.
         *
         * @param key the key of a newly created node.
         * @param value the value of a newly created node.
         */
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key of this entry.
         *
         * @return the key of this entry.
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Returns the value of this entry.
         *
         * @return the value of this entry.
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Set the value and returns the old one.
         *
         * @param value the value to set.
         *
         * @return the old value.
         */
        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        /**
         * Tests whether this node equals <code>o</code>.
         *
         * @param o the object to test against.
         *
         * @return <code>true</code> if nodes equal each other,
         * <code>false</code> otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Node)) {
                return false;
            }

            Node<K, V> e1 = this;
            Node<K, V> e2 = (Node<K, V>) o;

            return (e1.key.equals(e2.key) && e1.value.equals(e2.value));
        }

        /**
         * Retrieves the hash code of this node.
         *
         * @return the hash code of this node.
         */
        @Override
        public int hashCode() {
            return key.hashCode() ^ (value == null ? 0 : value.hashCode());
        }

        /**
         * Returns the minimum entry of this (sub-)tree.
         *
         * @return the minimum entry of this entry.
         */
        private Node<K, V> min() {
            Node<K, V> e = this;

            while (e.left != null) {
                e = e.left;
            }

            return e;
        }

        /**
         * Returns the successor entry if one exists, and
         * <code>null</code> if there is no such.
         *
         * @return the successor entry or <code>null</code>.
         */
        private Node<K, V> next() {
            Node<K, V> e = this;

            if (e.right != null) {
                e = e.right;

                while (e.left != null) {
                    e = e.left;
                }

                return e;
            }

            while (e.parent != null && e.parent.right == e) {
                e = e.parent;
            }

            if (e.parent == null) {
                return null;
            }

            return e.parent;
        }
    }

    /**
     * The root node of this tree.
     */
    private Node<K, V> root;

    /**
     * The amount of key/value - mappings (nodes) in this tree.