
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
 * This class implements a bidirectional <tt>A*</tt>-search algorithm that is
 * fast yet suboptimal in general case.
 *
 * @author Rodion Efremov
 * @version 1.618 (16.12.2013)
 */
public class FastSuboptimalFinder extends GeneralPathFinder {

    private HeuristicFunction h;
    private HeuristicFunction h2;
    private PriorityQueue<DirectedGraphNode, Double> OPEN2;
    private Set<DirectedGraphNode> CLOSED2;
    private Map<DirectedGraphNode, Double> GSCORE_MAP2;
    private Map<DirectedGraphNode, DirectedGraphNode> PARENT_MAP2;

    public FastSuboptimalFinder(PriorityQueue<DirectedGraphNode, Double> OPEN,
                         HeuristicFunction h,
                         HeuristicFunction h2) {
        super(OPEN);
        this.h = h;
        this.h2 = h2;

        OPEN2 = OPEN.newInstance();
        CLOSED2 = new HashSet<DirectedGraphNode>();
        GSCORE_MAP2 = new HashMap<DirectedGraphNode, Double>();
        PARENT_MAP2 = new HashMap<DirectedGraphNode, DirectedGraphNode>();
    }

    @Override
    public List<DirectedGraphNode> find(DirectedGraphNode source,
                                        DirectedGraphNode target,
                                        DirectedGraphWeightFunction w) {
        h.setTarget(target);
        OPEN.clear();
        CLOSED.clear();
        GSCORE_MAP.clear();
        PARENT_MAP.clear();

        h2.setTarget(source);
        OPEN2.clear();
        CLOSED2.clear();
        GSCORE_MAP2.clear();
        PARENT_MAP2.clear();

        OPEN.insert(source, h.get(source));