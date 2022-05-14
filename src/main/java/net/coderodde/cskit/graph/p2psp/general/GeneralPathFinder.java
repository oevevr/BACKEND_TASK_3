package net.coderodde.cskit.graph.p2psp.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.coderodde.cskit.ds.pq.PriorityQueue;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;

/**
 * This interface defines the common API for general shortest path algorithms.
 *
 * @author Rodion Efremov
 * @version 1.618
 */
public abstract class GeneralPathFinder {

    protected PriorityQueue<DirectedGraphNode, Double> OPEN;
    protected Set<DirectedGraphNode> CLOSED;
    protected Map<Di