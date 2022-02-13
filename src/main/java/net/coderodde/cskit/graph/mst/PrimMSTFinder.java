package net.coderodde.cskit.graph.mst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.coderodde.cskit.Utilities.Pair;
import static net.coderodde.cskit.Utilities.checkNotNull;
import static net.coderodde.cskit.Utilities.expandGraph;
import net.coderodde.cskit.ds.pq.BinaryHeap;
import net.coderodde.cskit.ds.pq.PriorityQueue;
import net.coderodde.cskit.graph.UndirectedGraphEdge;
import net.coderodde.cskit.graph.UndirectedGraphNode;
import net.coderodde.cskit.graph.UndirectedGraphWeightFunction;

/**
 * This class implements Prim's algorithm for finding minimum spanning trees.
 *
 * @author Rodion Efremov
 * @version 1.618033 (6.1.2014)
 */
public class PrimMSTFinder extends MinimumSpanningTreeFinder {

    private PriorityQueue<UndirectedGraphNode, Double> Q;

    private Set<UndirectedGraphNode> set =
            new HashSet<UndirectedGraphNode>();

    private Map<UndirectedGraphNode, UndirectedGraphNode> parent =
    new HashMap<UndirectedGraphNode, UndirectedGraphNode>();


    public PrimMSTFinder(PriorityQueue<UndirectedGraphNode, Double> Q) {
        this.Q = Q.newInstance();
    }

    public PrimMSTFinder() {
        this.Q = new BinaryHeap<UndirectedGraphNode, Double>();
    }

    @Override
    public Pair<List<UndirectedGraphEdge>, Double>
            find(List<UndirectedGraphNode> graph,
                 UndirectedGraphWeightFunction w) {
        checkNotNull(graph, "'graph' is null");
        checkNotNull(w, "Weigth function 'w' is null.");

        if (graph.isEmpty()) {
            return null;
        }

        set.clear();
        set.addAll(expandGr