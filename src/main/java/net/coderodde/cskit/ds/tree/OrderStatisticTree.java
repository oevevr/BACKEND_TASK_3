
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