package net.coderodde.cskit.graph.p2psp.general;

import net.coderodde.cskit.graph.DirectedGraphNode;
/**
 * This class defines the API for heuristic functions mainly used in
 * <tt>A*</tt>-search.
 *
 * @author Rodion Efremov
 * @version 1.618 (16.12.2013)
 */
public abstract class HeuristicFunction {

    protected CoordinateMap map;
    protected DirectedGraphNode target;

    public HeuristicFunction