
package net.coderodde.cskit.graph.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.coderodde.cskit.Utilities.Pair;
import static net.coderodde.cskit.Utilities.checkNotNull;
import static net.coderodde.cskit.Utilities.expandGraph;
import net.coderodde.cskit.ds.disjointset.DisjointSet;
import net.coderodde.cskit.graph.UndirectedGraphEdge;
import net.coderodde.cskit.graph.UndirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;
import net.coderodde.cskit.graph.UndirectedGraphWeightFunction;

/**
 * This class implements Kruskal's minimum spanning tree algorithm.
 *
 * @author Rodion Efremov
 * @version 1.618033 (28.12.2013)
 */
public class KruskalMSTFinder extends MinimumSpanningTreeFinder {

    private Map<UndirectedGraphNode, DisjointSet<UndirectedGraphNode>> map =
    new HashMap<UndirectedGraphNode, DisjointSet<UndirectedGraphNode>>();

    private Set<UndirectedGraphNode> set =
    new HashSet<UndirectedGraphNode>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<List<UndirectedGraphEdge>, Double>
           find(List<UndirectedGraphNode> graph,
                UndirectedGraphWeightFunction w) {
        Double weight = 0.0;
        List<UndirectedGraphEdge> edgeList = checkPrerequisites(graph, w);

        if (edgeList == null) {
            return null;
        }

        DisjointSet<UndirectedGraphNode> ds =
                new DisjointSet<UndirectedGraphNode>();

        List<UndirectedGraphEdge> mst =
                new ArrayList<UndirectedGraphEdge>(set.size() - 1);

        Set<UndirectedGraphNode> CLOSED =
                new HashSet<UndirectedGraphNode>(graph.size());
