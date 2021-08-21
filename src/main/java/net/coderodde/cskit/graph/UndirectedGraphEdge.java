package net.coderodde.cskit.graph;

import java.util.Comparator;

/**
 * This class holds
 * @author Rodion Efremov
 * @version 1.618033 (28.12.2013)
 */
public class UndirectedGraphEdge {

    private UndirectedGraphNode a;
    private UndirectedGraphNode b;
    double w;

    public UndirectedGraphEdge(UndirectedGraphNode a,
                               UndirectedGraphNode b) {
        this.a = a;
        this.b = b;
    }

    public double getWeight() {
        return w;
    }

    public void setWeigh