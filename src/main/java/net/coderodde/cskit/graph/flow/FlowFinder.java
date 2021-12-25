
package net.coderodde.cskit.graph.flow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.coderodde.cskit.Utilities.Pair;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;

/**
 * This abstract class defines the API for maximum-flow algorithms.
 *
 * @author Rodion Efremov
 * @version 1.61803
 */
public abstract class FlowFinder {

    public abstract Pair<DirectedGraphWeightFunction, Double> find(DirectedGraphNode source,
                                                      DirectedGraphNode sink,
                                                      DirectedGraphWeightFunction w);

    public static final void resolveParallelEdges(
            List<DirectedGraphNode> graph, DirectedGraphWeightFunction w) {
        List<DirectedGraphNode> toAdd = new ArrayList<DirectedGraphNode>();
        int index = 0;

        for (DirectedGraphNode from : graph) {
            for (DirectedGraphNode to : from) {
                if (to.hasChild(from)) {
                    double weight = w.get(from, to);
                    to.removeChild(from);
                    DirectedGraphNode newOne =
                            new DirectedGraphNode(
                            "Antiparallel node from " + to.getName() +
                            " to " + from.getName());

                    to.addChild(newOne);
                    newOne.addChild(from);

                    w.put(to, newOne, weight);
                    w.put(newOne, from, weight);

                    toAdd.add(newOne);
                }
            }
        }

        graph.addAll(toAdd);
    }

    public static final void removeSelfLoops(List<DirectedGraphNode> graph) {
        for (DirectedGraphNode u : graph) {
            u.removeChild(u);
        }