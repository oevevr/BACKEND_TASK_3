
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
 * This class implements a bidirectional <tt>A*</tt>-search algorithm due to
 * Kaindl, H., Kainz, G. (Bidirectional Heuristic Search Reconsidered.)
 *
 * @author Rodion Efremov
 * @version 1.618 (16.12.2013)
 */
public class BHPAFinder extends GeneralPathFinder {

    private HeuristicFunction h;
    private HeuristicFunction h2;
    private PriorityQueue<DirectedGraphNode, Double> OPEN2;
    private Set<DirectedGraphNode> CLOSED2;
    private Map<DirectedGraphNode, Double> GSCORE_MAP2;
    private Map<DirectedGraphNode, DirectedGraphNode> PARENT_MAP2;

    public BHPAFinder(PriorityQueue<DirectedGraphNode, Double> OPEN,
                      HeuristicFunction h,
                      HeuristicFunction h2) {
        super(OPEN);
        this.h = h;
        this.h2 = h2;

        OPEN2 = OPEN.newInstance();
        CLOSED2 = new HashSet<DirectedGraphNode>();