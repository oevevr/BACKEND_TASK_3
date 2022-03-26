package net.coderodde.cskit.graph.p2psp.general;

import java.util.List;
import static net.coderodde.cskit.Utilities.tracebackPath;
import net.coderodde.cskit.ds.pq.PriorityQueue;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;

/**
 * This class implements <tt>A*</tt>-search algorithm.
 *
 * @author Rodion Efremov
 * @version 1.618 (16.12.2013)
 */
public class AStarFinder extends GeneralPathFinder {

    private HeuristicFunction h;

    public AStarFinder(PriorityQueue<DirectedGraphNode, Double> OPEN,
                       HeuristicFunction h) {
        super(OPEN);
        this.h = h;
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

        OPEN.insert(source, h.get(source));
        PARENT_MAP.put(source, null);
        GSCORE_MAP.put(source, 0.0);

        while (OPEN.isEmpty() == f