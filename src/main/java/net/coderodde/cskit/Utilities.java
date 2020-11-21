
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

    public static List<DirectedGraphNode> tracebackPathBidirectional(
            DirectedGraphNode touchNode,
            Map<DirectedGraphNode, DirectedGraphNode> parentMapA,
            Map<DirectedGraphNode, DirectedGraphNode> parentMapB) {
        ArrayList<DirectedGraphNode> path = new ArrayList<DirectedGraphNode>();
        DirectedGraphNode tmp = touchNode;

        while (tmp != null) {
            path.add(tmp);
            tmp = parentMapA.get(tmp);
        }

        java.util.Collections.reverse(path);
        tmp = parentMapB.get(touchNode);

        while (tmp != null) {
            path.add(tmp);
            tmp = parentMapB.get(tmp);
        }

        return path;
    }

    public static final void line() {
        System.out.println(
                "________________________________________"
                + "________________________________________");
    }

    public static final void title(String text) {
        int w = text.length() + 2;
        int leftPadding = (80 - w) / 2;
        int rightPadding = 80 - w - leftPadding;
        StringBuilder sb = new StringBuilder(80);

        for (int i = 0; i < leftPadding; ++i) {
            sb.append('/');
        }

        sb.append(' ');
        sb.append(text);
        sb.append(' ');

        for (int i = 0; i < rightPadding; ++i) {
            sb.append('/');
        }

        System.out.println(sb.toString());
    }

    public static final void title2(String text) {
        int w = text.length() + 2;
        int leftPadding = (80 - w) / 2;
        int rightPadding = 80 - w - leftPadding;
        StringBuilder sb = new StringBuilder(80);

        for (int i = 0; i < leftPadding; ++i) {
            sb.append('-');
        }

        sb.append(' ');
        sb.append(text);
        sb.append(' ');

        for (int i = 0; i < rightPadding; ++i) {
            sb.append('-');
        }

        System.out.println(sb.toString());
    }

    public static final boolean isConnectedPath(List<DirectedGraphNode> candidate) {
        for (int i = 0; i < candidate.size() - 1; ++i) {
            if (candidate.get(i).hasChild(candidate.get(i + 1)) == false) {
                return false;
            }
        }

        return true;
    }

    public static final boolean pathsAreSame(List<DirectedGraphNode>... paths) {
        for (int i = 0; i < paths.length - 1; ++i) {
            if (paths[i].size() != paths[i + 1].size()) {
                return false;
            }
        }

        for (int i = 0; i < paths[0].size(); ++i) {
            for (int p = 0; p < paths.length - 1; ++p) {
                if (paths[p].get(i).equals(paths[p + 1].get(i)) == false) {
                    return false;
                }
            }
        }

        return true;
    }

    public static final List<DirectedGraphNode> generateSimpleGraph(int size, float edgeLoadFactor, Random r) {
        List<DirectedGraphNode> graph = new ArrayList<DirectedGraphNode>(size);

        for (int i = 0; i < size; ++i) {
            graph.add(new DirectedGraphNode("" + i));
        }

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (r.nextFloat() < edgeLoadFactor) {
                    graph.get(i).addChild(graph.get(j));
                }
            }
        }

        for (int i = 0; i < size - 1; ++i) {
            graph.get(i).addChild(graph.get(i + 1));
        }

        graph.get(graph.size() - 1).addChild(graph.get(0));

        return graph;
    }

    public static final Pair<List<DirectedGraphNode>, DirectedGraphWeightFunction>
            getWeightedGraph(int size, float elf, Random r) {
        List<DirectedGraphNode> graph = new ArrayList<DirectedGraphNode>(size);
        DirectedGraphWeightFunction w = new DirectedGraphWeightFunction();

        for (int i = 0; i < size; ++i) {
            graph.add(new DirectedGraphNode("" + i));
        }

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (r.nextFloat() < elf) {
                    graph.get(i).addChild(graph.get(j));
                    w.put(graph.get(i), graph.get(j), 10.0 * r.nextDouble());
                }
            }
        }

        for (int i = 0; i < size - 1; ++i) {
            graph.get(i).addChild(graph.get(i + 1));
            w.put(graph.get(i), graph.get(i + 1), 10.0 * r.nextDouble());
        }

        graph.get(graph.size() - 1).addChild(graph.get(0));
        w.put(graph.get(graph.size() - 1), graph.get(0), 1.0 * r.nextDouble());

        return new Pair<List<DirectedGraphNode>, DirectedGraphWeightFunction>
                   (graph, w);
    }

    public static final boolean isSpanningTree(List<UndirectedGraphEdge> tree) {
        Set<UndirectedGraphNode> set = new HashSet<UndirectedGraphNode>();

        for (UndirectedGraphEdge e : tree) {
            set.add(e.getA());
            set.add(e.getB());
        }

        return set.size() > tree.size();
    }

    public static final double
            sumEdgeWeights(List<UndirectedGraphEdge> edges) {
        double sum = 0.0;

        for (UndirectedGraphEdge e : edges) {
            sum += e.getWeight();
        }

        return sum;
    }

    public static final boolean
            spanningTreesEqual(List<UndirectedGraphEdge> tree1,
                               List<UndirectedGraphEdge> tree2) {
        if (tree1.size() != tree2.size()) {
            return false;
        }

        Collections.sort(tree1, new UndirectedGraphEdge.AscendingComparator());
        Collections.sort(tree2, new UndirectedGraphEdge.AscendingComparator());

        for (int i = 0; i < tree1.size(); ++i) {
            if (!Utilities.epsilonEquals(0.01,
                                         tree1.get(i).getWeight(),
                                         tree2.get(i).getWeight())) {
                return false;
            }

            if (tree1.get(i).equals(tree2.get(i)) == false) {
                return false;
            }
        }

        return true;
    }

    public static final List<DirectedGraphNode>
            copy(List<DirectedGraphNode> graph) {
        List<DirectedGraphNode> copyGraph =
                new ArrayList<DirectedGraphNode>(graph.size());

        Map<DirectedGraphNode, DirectedGraphNode> map =
                   new HashMap<DirectedGraphNode,
                               DirectedGraphNode>(graph.size());

        for (DirectedGraphNode u : graph) {
            DirectedGraphNode copy = new DirectedGraphNode(u.getName());
            copyGraph.add(copy);
            map.put(u, copy);
        }

        for (DirectedGraphNode u : graph) {
            DirectedGraphNode copyFrom = map.get(u);

            for (DirectedGraphNode child : u) {
                DirectedGraphNode copyTo = map.get(child);
                copyFrom.addChild(copyTo);
            }
        }

        return copyGraph;
    }

    public static final List<DirectedGraphNode>
            getResidualGraphOf(List<DirectedGraphNode> graph) {
        List<DirectedGraphNode> residualGraph =
                new ArrayList<DirectedGraphNode>(graph.size());

        Map<DirectedGraphNode, DirectedGraphNode> map =
                   new HashMap<DirectedGraphNode,
                               DirectedGraphNode>(graph.size());

        for (DirectedGraphNode u : graph) {
            DirectedGraphNode copy = new DirectedGraphNode(u.getName());
            residualGraph.add(copy);
            map.put(u, copy);
        }

        for (DirectedGraphNode u : graph) {
            DirectedGraphNode from = u;
            DirectedGraphNode residualTo = map.get(u);

            for (DirectedGraphNode child : u) {
                DirectedGraphNode residualFrom = map.get(child);
                residualFrom.addChild(residualTo);
            }
        }

        return residualGraph;
    }

    public static final Pair<List<UndirectedGraphNode>,
                             UndirectedGraphWeightFunction>
            getRandomUndirectedGraph(int size,
                                     float elf,
                                     Random r,
                                     double maxWeight) {
        List<UndirectedGraphNode> graph =
                new ArrayList<UndirectedGraphNode>(size);
        UndirectedGraphWeightFunction c = new UndirectedGraphWeightFunction();

        for (int i = 0; i < size; ++i) {
            graph.add(new UndirectedGraphNode("" + i));
        }

        for (int i = 1; i < size; ++i) {
            for (int j = 0; j < i; ++j) {
                if (r.nextFloat() < elf) {
                    UndirectedGraphNode a = graph.get(i);
                    UndirectedGraphNode b = graph.get(j);
                    double w = maxWeight * r.nextDouble();
                    a.connect(b);
                    c.put(a, b, w);
                }
            }
        }

        for (int i = 0; i < size - 1; ++i) {
            UndirectedGraphNode a = graph.get(i);
            UndirectedGraphNode b = graph.get(i + 1);
            double w = maxWeight * r.nextDouble();
            a.connect(b);
            c.put(a, b, w);
        }

        graph.get(graph.size() - 1).connect(graph.get(0));
        c.put(graph.get(graph.size() - 1),
              graph.get(0),
              maxWeight * r.nextDouble());

        return new Pair<List<UndirectedGraphNode>,
                        UndirectedGraphWeightFunction>(graph, c);
    }

    public static final Pair<List<DirectedGraphNode>, DirectedGraphWeightFunction>
            getRandomFlowNetwork(int size,
                                 float elf,
                                 Random r,
                                 double maxCapacity) {
        List<DirectedGraphNode> graph = new ArrayList<DirectedGraphNode>(size);
        DirectedGraphWeightFunction c = new DirectedGraphWeightFunction();

        for (int i = 0; i < size; ++i) {
            graph.add(new DirectedGraphNode("" + i));
        }

        for (int i = 1; i < size; ++i) {
            for (int j = 0; j < i; ++j) {
                if (r.nextFloat() < elf) {
                    DirectedGraphNode from = graph.get(i);
                    DirectedGraphNode to = graph.get(j);

                    from.addChild(to);
                    c.put(from, to, maxCapacity * r.nextDouble());
                }
            }
        }

        for (int i = 0; i < size - 1; ++i) {
            DirectedGraphNode from = graph.get(i);
            DirectedGraphNode to = graph.get(i + 1);

            from.addChild(to);
            c.put(from, to, maxCapacity * r.nextDouble());
        }

        graph.get(graph.size() - 1).addChild(graph.get(0));
        c.put(graph.get(graph.size() - 1),
              graph.get(0),
              maxCapacity * r.nextDouble());

        return new Pair<List<DirectedGraphNode>, DirectedGraphWeightFunction>(
                graph,
                c);
    }