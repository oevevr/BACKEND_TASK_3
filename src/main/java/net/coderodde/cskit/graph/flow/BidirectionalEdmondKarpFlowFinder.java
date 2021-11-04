
package net.coderodde.cskit.graph.flow;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.coderodde.cskit.Utilities.Pair;
import static net.coderodde.cskit.Utilities.findTouchNode;
import static net.coderodde.cskit.Utilities.tracebackPathBidirectional;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;

/**
 * This class implements the Edmond-Karp maximum-flow algorithm.
 *
 * @author Rodion Efremov
 * @version 1.61803 (24.12.2013)
 */
public class BidirectionalEdmondKarpFlowFinder extends FlowFinder {

    private final Map<DirectedGraphNode, DirectedGraphNode> parentMapA =
          new HashMap<DirectedGraphNode, DirectedGraphNode>();

    private final Map<DirectedGraphNode, DirectedGraphNode> parentMapB =
          new HashMap<DirectedGraphNode, DirectedGraphNode>();
