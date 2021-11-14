package net.coderodde.cskit.graph.flow;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.coderodde.cskit.Utilities;
import net.coderodde.cskit.Utilities.Pair;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;

/**
 * This class implements the Edmond-Karp maximum-flow algorithm.
 *
 * @author Rodion Efremov
 * @version 1.61803 (23.12.2013)
 */
public class EdmondKarpFlowFinder extends FlowFinder {

    @Override
    public Pair<DirectedGraphWeightFunction, Double> find(D