
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
    }

    public static final DirectedGraphNode
            createSuperSource(DirectedGraphWeightFunction w,
                              DirectedGraphNode... sources) {
        DirectedGraphNode superSource = new DirectedGraphNode("Super source");

        for (DirectedGraphNode source : sources) {
            superSource.addChild(source);
            w.put(superSource, source, Double.POSITIVE_INFINITY);
        }

        return superSource;
    }

    public static final DirectedGraphNode
            createSuperSink(DirectedGraphWeightFunction w,
                            DirectedGraphNode... sinks) {
        DirectedGraphNode superSink = new DirectedGraphNode("Super sink");

        for (DirectedGraphNode sink : sinks) {
            sink.addChild(superSink);
            w.put(sink, superSink, Double.POSITIVE_INFINITY);
        }

        return superSink;
    }

    public static final void pruneSource(DirectedGraphNode source) {
        Iterator<DirectedGraphNode> iterator =
                source.parentIterable().iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    public static final void pruneSink(DirectedGraphNode sink) {
        Iterator<DirectedGraphNode> iterator =
                sink.iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    protected double findMinimumEdgeAndRemove(List<DirectedGraphNode> path,
                                            DirectedGraphWeightFunction c,
                                            DirectedGraphWeightFunction f) {
        double min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < path.size() - 1; ++i) {
            if (min > residualEdgeWeight(path.get(i), path.get(i + 1), f, c)) {
                min = residualEdgeWeight(path.get(i), path.get(i + 1), f, c);
            }
        }

        for (int i = 0; i < path.size() - 1; ++i) {
            DirectedGraphNode from = path.get(i);
            DirectedGraphNode to = path.get(i + 1);
            f.put(from, to, f.get(from, to) + min);
        }

        return min;
    }
