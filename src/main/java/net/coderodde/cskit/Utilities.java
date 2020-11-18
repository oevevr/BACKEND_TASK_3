
package net.coderodde.cskit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.UndirectedGraphEdge;
import net.coderodde.cskit.graph.UndirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;
import net.coderodde.cskit.graph.UndirectedGraphWeightFunction;
import net.coderodde.cskit.graph.p2psp.general.CoordinateMap;
import net.coderodde.cskit.graph.p2psp.general.HeuristicFunction;
import net.coderodde.cskit.sorting.Range;

/**
 * This class contains general utilities.
 *
 * @author Rodion Efremov
 * @version 1.6 (7.12.2013)
 */
public class Utilities {

    public static class Pair<F, S> {
        public F first;
        public S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        public Pair() {

        }
    }

    public static class Triple<F, S, T> {
        public F first;
        public S second;
        public T third;

        public Triple(F first, S second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public Triple() {

        }
    }

    public static <T> T checkNotNull(T field, String errmsg) {
        if (field == null) {
            throw new NullPointerException(errmsg);
        }

        return field;
    }

    public static void checkModCount(long expected,
            long actual,
            String errmsg) {
        if (expected != actual) {
            throw new ConcurrentModificationException(errmsg);
        }
    }

    public static double getPathCost(List<DirectedGraphNode> path,
                                     DirectedGraphWeightFunction w) {
        double cost = 0;

        for (int i = 0; i < path.size() - 1; ++i) {
            cost += w.get(path.get(i), path.get(i + 1));
        }

        return cost;
    }

    public static List<DirectedGraphNode> tracebackPath(
            DirectedGraphNode target,
            Map<DirectedGraphNode, DirectedGraphNode> parentMap) {
        ArrayList<DirectedGraphNode> path = new ArrayList<DirectedGraphNode>();

        while (target != null) {
            path.add(target);
            target = parentMap.get(target);
        }

        java.util.Collections.reverse(path);
        return path;
    }

    public static DirectedGraphNode findTouchNode(
            Set<DirectedGraphNode> levelA,
            Set<DirectedGraphNode> levelB,
            Map<DirectedGraphNode, DirectedGraphNode> parentMapA,
            Map<DirectedGraphNode, DirectedGraphNode> parentMapB,
            Map<DirectedGraphNode, Integer> distanceMapA,
            Map<DirectedGraphNode, Integer> distanceMapB) {
        Integer tmp;
        DirectedGraphNode touch = null;
        int minDistance = Integer.MAX_VALUE;

        for (DirectedGraphNode u : levelA) {
            tmp = distanceMapB.get(u);

            if (tmp != null && minDistance > tmp + distanceMapA.get(u)) {
                minDistance = tmp + distanceMapA.get(u);
                touch = u;
            }
        }

        for (DirectedGraphNode u : levelB) {
            tmp = distanceMapA.get(u);

            if (tmp != null && minDistance > tmp + distanceMapB.get(u)) {
                minDistance = tmp + distanceMapB.get(u);
                touch = u;
            }
        }

        return touch;
    }