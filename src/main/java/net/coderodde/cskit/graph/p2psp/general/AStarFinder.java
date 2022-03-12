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

    public AStarFinder(PriorityQue