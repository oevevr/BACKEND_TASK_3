
package net.coderodde.cskit.graph.p2psp.uniform;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import static net.coderodde.cskit.Utilities.findTouchNode;
import static net.coderodde.cskit.Utilities.tracebackPathBidirectional;
import net.coderodde.cskit.graph.DirectedGraphNode;

/**
 * This class implement parallel bidirectional breadth-first search algorithm.
 *
 * @author Rodion Efremov
 * @version 1.61 (8.12.2013)
 */
public class ParallelBidirectionalBFSFinder implements UniformCostPathFinder {

    private Map<DirectedGraphNode, DirectedGraphNode> parentMapA =
            new HashMap<DirectedGraphNode, DirectedGraphNode>();

    private Map<DirectedGraphNode, DirectedGraphNode> parentMapB =
            new HashMap<DirectedGraphNode, DirectedGraphNode>();

    private Semaphore mutexA = new Semaphore(1, true);
    private Semaphore mutexB = new Semaphore(1, true);

    private Set<DirectedGraphNode> levelA = new HashSet<DirectedGraphNode>();
    private Set<DirectedGraphNode> levelB = new HashSet<DirectedGraphNode>();

    private Map<DirectedGraphNode, Integer> distanceMapA =
            new HashMap<DirectedGraphNode, Integer>();

    private Map<DirectedGraphNode, Integer> distanceMapB =
            new HashMap<DirectedGraphNode, Integer>();

    @Override
    public List<DirectedGraphNode> find(DirectedGraphNode source, DirectedGraphNode target) {
        clear();

        distanceMapA.put(source, 0);
        distanceMapB.put(target, 0);

        parentMapA.put(source, null);
        parentMapB.put(target, null);

        levelA.add(source);
        levelB.add(target);

        ForwardSearchThread threadA = new ForwardSearchThread(source);
        threadA.start();

        BackwardsSearchThread threadB = new BackwardsSearchThread(target,
                                                                  threadA);
        threadA.setBrotherThread(threadB);