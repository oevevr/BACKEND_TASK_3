package net.coderodde.cskit.ds.disjointset;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the disjoint-set structure.
 *
 * @author Rodion Efremov
 * @version 1.618033 (28.12.2013)
 */
public class DisjointSet<E> {

    public static class Node<E> {
        E datum;
        Node<E> parent;
        int rank;

        Node(E rootElement) {
            this.datum = rootElement;
            this.parent = this;
        }
    }

    private Map<E, Node<E>> map = new HashMap<E, Node<E>>();

    public E find(E e) {
        Node<E> node = find(getNode(e));

        if (node == node.parent) {
            return node.datum;
        }

        node.parent = find(node.parent);
        return node.parent.datum;
    }

    public void union(E e1, E e2) {
        Node<E> n1 = find(getNode(e1));
        Node<E> n2 = find(get