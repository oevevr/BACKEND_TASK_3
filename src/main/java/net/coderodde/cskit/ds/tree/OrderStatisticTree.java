
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
     */
    private int size;

    /**
     * The modification count.
     */
    private long modCount;

    /**
     * Constructs an empty tree.
     */
    public OrderStatisticTree() {}

    /**
     * Constructs a tree populated with the contents of <code>m</code>.
     *
     * @param m the map to populate this tree with.
     */
    public OrderStatisticTree(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            this.put(e.getKey(), e.getValue());
        }
    }

    /**
     * Returns <code>true</code> if this tree is empty; <code>false</code>
     * otherwise.
     *
     * @return <code>true</code> if this tree is empty; <code>false</code>
     * otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retrieves the size of this tree.
     *
     * @return the size of this tree.
     */
    public int size() {
        return size;
    }

    /**
     * Clears this tree.
     */
    public void clear() {
        modCount++;
        root = null;
        size = 0;
    }

    /**
     * Returns <code>true</code> if this tree contains the specified key,
     * <code>false</code> otherwise.
     *
     * @param key the key to query.
     *
     * @return <code>true</code> if this tree contains the key,
     * <code>false</code>.
     */
    public boolean containsKey(Object o) {
        K key = (K) o;
        Node<K, V> e = root;
        int cmp;

        while (e != null) {
            if ((cmp = key.compareTo(e.key)) < 0) {
                e = e.left;
            } else if (cmp > 0) {
                e = e.right;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns <code>true</code> if this tree contains the specified value,
     * <code>false</code> otherwise.
     *
     * @param value the value to query.
     *
     * @return <code>true</code> if this tree contains the specified value,
     * <code>false</code> otherwise.
     */
    @Override
    public boolean containsValue(Object value) {
        if (root == null) {
            return false;
        }

        Node<K, V> e = root;
        e = e.min();

        while (e != null) {
            if (e.value.equals(value)) {
                return true;
            }

            e = e.next();
        }

        return false;
    }

    /**
     * Associates <tt>key</tt> with <tt>value</tt>. If <tt>key</tt> is already
     * present in the tree, its old value is overwritten by <tt>value</tt>. Runs
     * in logarithmic time.
     *
     * @param key the key.
     * @param value the value.
     * @return the old value for <tt>key</tt>, or <tt>null</tt>, if no such.
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("'key' is null.");
        }

        modCount++;

        Node<K, V> e = new Node<K, V>(key, value);

        if (root == null) {
            root = e;
            size = 1;
            return null;
        }

        Node<K, V> x = root;
        Node<K, V> p = null;
        int cmp;

        while (x != null) {
            p = x;

            if ((cmp = e.key.compareTo(x.key)) < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                V old = x.value;
                x.value = value;
                return old;
            }
        }

        e.parent = p;

        if (e.key.compareTo(p.key) < 0) {
            p.left = e;
            p.count = 1;
        } else {
            p.right = e;
        }
        Node<K, V> tmp = p.parent;
        Node<K, V> tmpLo = p;

        // Update the counters.
        while (tmp != null) {
            if (tmp.left == tmpLo) {
                tmp.count++;
            }

            tmpLo = tmp;
            tmp = tmp.parent;
        }

        size++;

        while (p != null) {
            if (h(p.left) == h(p.right) + 2) {
                Node<K, V> pp = p.parent;
                Node<K, V> subroot =
                        (h(p.left.left) >= h(p.left.right)
                        ? rightRotate(p)
                        : leftRightRotate(p));

                if (pp == null) {
                    root = subroot;
                } else if (pp.left == p) {
                    pp.left = subroot;
                } else {
                    pp.right = subroot;
                }

                if (pp != null) {
                    pp.h = Math.max(h(pp.left), h(pp.right)) + 1;
                }

                return null;
            } else if (h(p.left) + 2 == h(p.right)) {
                Node<K, V> pp = p.parent;
                Node<K, V> subroot =
                        (h(p.right.right) >= h(p.right.left)
                        ? leftRotate(p)
                        : rightLeftRotate(p));

                if (pp == null) {
                    root = subroot;
                } else if (pp.left == p) {
                    pp.left = subroot;
                } else {
                    pp.right = subroot;
                }


                if (pp != null) {
                    pp.h = Math.max(h(pp.left), h(pp.right)) + 1;
                }

                return null;
            }

            p.h = Math.max(h(p.left), h(p.right)) + 1;
            p = p.parent;
        }

        return null;
    }

    /**
     * Puts every key/value - mapping from <code>m</code> to this tree.
     *
     * @param m the map to add.
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            this.put(e.getKey(), e.getValue());
        }
    }

    /**
     * Removes a mapping with key <tt>key</tt> from this tree. Runs in
     * logarithmic time.
     *
     * @param key the key of a node.
     *
     * @return the value associated with <tt>key</tt> or null, if there was no
     * entries in the tree with key <tt>key</tt>.
     */
    @Override
    public V remove(Object o) {
        if (size == 0) {
            return null;
        }

        K key = (K) o;
        modCount++;
        Node<K, V> e = root;
        int cmp;

        while (e != null) {
            if ((cmp = e.key.compareTo(key)) > 0) {
                e = e.left;
            } else if (cmp < 0) {
                e = e.right;
            } else {
                V old = e.value;
                e = removeImpl(e);
                balanceAfterRemoval(e);
                return old;
            }
        }

        return null;
    }

    /**
     * Gets the value associated with <tt>key</tt>. Runs in logarithmic time.
     *
     * @param key the key.
     *
     * @return the value associated with <tt>key</tt>.
     */
    @Override
    public V get(Object o) {
        K key = (K) o;
        Node<K, V> e = root;
        int cmp;

        while (e != null) {
            if ((cmp = key.compareTo(e.key)) < 0) {
                e = e.left;
            } else if (cmp > 0) {
                e = e.right;
            } else {
                return e.value;
            }
        }

        return null;
    }

    /**
     * Gets the entry with ith smallest key. Runs in logarithmic time.
     *
     * @param i the index of the node, by in-order.
     *
     * @return an entry if i is within range, or <tt>null</tt> otherwise.
     */
    public Node<K, V> entryAt(int i) {
        if (i < 0 || i >= size) {
            return null;
        }

        int save = i;
        Node<K, V> e = root;

        for (;;) {
            if (i < e.count) {
                e = e.left;
            } else if (i > e.count) {
                i -= e.count + 1;
                e = e.right;
            } else {
                return e;
            }
        }
    }

    /**
     * Returns the rank of a key, or <tt>-1</tt> if there is no such key in the
     * tree.
     *
     * @param key the key to rank.
     *
     * @return the rank of key.
     */
    public int rankOf(K key) {
        int cmp;
        int counter = 0;
        Node<K, V> e = root;

        for (;;) {
            if ((cmp = e.key.compareTo(key)) > 0) {
                e = e.left;
            } else if (cmp < 0) {
                counter += e.count + 1;
                e = e.right;
            } else if (e == null) {
                return -1;
            } else {
                return e.count + counter;
            }
        }
    }

    /**
     * Retrieves the hash code of this <code>OrderedStatisticTree</code>.
     *
     * @return the hash code of this tree.
     */
    @Override
    public int hashCode() {
        if (root == null) {
            return 0;
        }

        Node<K, V> e = root.min();
        int sum = 0;

        while (e != null) {
            sum += e.hashCode();
            e = e.next();
        }

        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderStatisticTree)) {
            return false;
        }

        return true;
    }

    @Override
    public Set<K> keySet() {
        return new KeySet();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException(
                "Value view not yet implemented");
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }
