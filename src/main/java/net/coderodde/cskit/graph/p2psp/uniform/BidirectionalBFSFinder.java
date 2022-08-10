package net.coderodde.cskit.graph.p2psp.uniform;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static net.coderodde.cskit.Utilities.tracebackPathBidirectional;
import net.coderodde.cskit.graph.DirectedGraphNode;

/**
 * This class implements bidirectional breadth-first search algorithm.
 *
 * @author Rodion Efremov
 * @version 1.6
 */
public class BidirectionalBFSFinder
implements UniformCostPathFinder {

    // We choose to reuse the data structures. This resembles losing virginity:
    // it all expands and becomes loose enough. :)
    private Map<DirectedGraphNode, DirectedGraphNode> parentMapA =
            new HashMap<DirectedGraphNode, DirectedGraphNode>();

    private Map<DirectedGraphNode, DirectedGraphNode> parentMapB =
            new HashMap<DirectedGraphNode, DirectedGraph