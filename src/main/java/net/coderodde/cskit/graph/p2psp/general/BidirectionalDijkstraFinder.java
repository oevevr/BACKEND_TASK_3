
package net.coderodde.cskit.graph.p2psp.general;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static net.coderodde.cskit.Utilities.tracebackPathBidirectional;
import net.coderodde.cskit.ds.pq.PriorityQueue;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;

/**
 * This class implements bidirectional Dijkstra's algorithm.
 *
 * @author Rodion Efremov
 * @version 1.618 (18.12.2013)
 */
public class BidirectionalDijkstraFinder extends GeneralPathFinder {

    private PriorityQueue<DirectedGraphNode, Double> OPENB;
    private Set<DirectedGraphNode> CLOSEDB;
    private Map<DirectedGraphNode, Double> GSCOREB;
    private Map<DirectedGraphNode, DirectedGraphNode> PARENTB;

    public BidirectionalDijkstraFinder(
            PriorityQueue<DirectedGraphNode, Double> OPEN) {
        super(OPEN);
        OPEN.clear();
        // Use the same heap structure.
        OPENB = OPEN.newInstance();
        CLOSEDB = new HashSet<DirectedGraphNode>();
        GSCOREB = new HashMap<DirectedGraphNode, Double>();
        PARENTB = new HashMap<DirectedGraphNode, DirectedGraphNode>();