package net.coderodde.cskit.graph.p2psp.uniform;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static net.coderodde.cskit.Utilities.tracebackPath;

import net.coderodde.cskit.graph.DirectedGraphNode;

/**
 * Implements the classic, point-to-point breadth-first search.
 *
 * @author Rodion Efremov
 * @version 1.6 (7.12.2013)
 */
public class BreadthFirstSearchFinder implements UniformCostPathFinder {

    private final Map<DirectedGraphNode, DirectedGraphNode> parentMap =
          new HashMap<DirectedGraphNode, DirectedGraphNode>();

    @Override
    public List<Dire