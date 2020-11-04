
package net.coderodde.cskit;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import net.coderodde.cskit.Utilities.Pair;
import net.coderodde.cskit.Utilities.Triple;
import static net.coderodde.cskit.Utilities.Triple;
import static net.coderodde.cskit.Utilities.allWeakEquals;
import static net.coderodde.cskit.Utilities.debugPrintArray;
import static net.coderodde.cskit.Utilities.epsilonEquals;
import static net.coderodde.cskit.Utilities.generateSimpleGraph;
import static net.coderodde.cskit.Utilities.getPathCost;
import static net.coderodde.cskit.Utilities.getPresortedArray;
import static net.coderodde.cskit.Utilities.getRandomGraph;
import static net.coderodde.cskit.Utilities.getRandomIntegerArray;
import static net.coderodde.cskit.Utilities.isConnectedPath;
import static net.coderodde.cskit.Utilities.isSorted;
import static net.coderodde.cskit.Utilities.isSpanningTree;
import static net.coderodde.cskit.Utilities.line;
import static net.coderodde.cskit.Utilities.pathsAreSame;
import static net.coderodde.cskit.Utilities.spanningTreesEqual;
import static net.coderodde.cskit.Utilities.sumEdgeWeights;
import static net.coderodde.cskit.Utilities.title;
import static net.coderodde.cskit.Utilities.title2;
import net.coderodde.cskit.ds.list.TreeList;
import net.coderodde.cskit.ds.pq.BinaryHeap;
import net.coderodde.cskit.ds.pq.FibonacciHeap;
import net.coderodde.cskit.ds.pq.PriorityQueue;
import net.coderodde.cskit.ds.tree.OrderStatisticTree;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;
import net.coderodde.cskit.graph.UndirectedGraphEdge;
import net.coderodde.cskit.graph.UndirectedGraphNode;
import net.coderodde.cskit.graph.UndirectedGraphWeightFunction;
import net.coderodde.cskit.graph.flow.BidirectionalEdmondKarpFlowFinder;
import net.coderodde.cskit.graph.flow.EdmondKarpFlowFinder;
import net.coderodde.cskit.graph.flow.FlowFinder;
import net.coderodde.cskit.graph.mst.KruskalMSTFinder;
import net.coderodde.cskit.graph.mst.MinimumSpanningTreeFinder;
import net.coderodde.cskit.graph.mst.PrimMSTFinder;
import net.coderodde.cskit.graph.p2psp.general.AStarFinder;
import net.coderodde.cskit.graph.p2psp.general.BHPAFinder;
import net.coderodde.cskit.graph.p2psp.general.BidirectionalDijkstraFinder;
import net.coderodde.cskit.graph.p2psp.general.CoordinateMap;
import net.coderodde.cskit.graph.p2psp.general.DijkstraFinder;
import net.coderodde.cskit.graph.p2psp.general.EuclidianMetric;
import net.coderodde.cskit.graph.p2psp.general.FastSuboptimalFinder;
import net.coderodde.cskit.graph.p2psp.general.GeneralPathFinder;
import net.coderodde.cskit.graph.p2psp.general.WhangboFinder;
import net.coderodde.cskit.graph.p2psp.uniform.BreadthFirstSearchFinder;
import net.coderodde.cskit.graph.p2psp.uniform.BidirectionalBFSFinder;
import net.coderodde.cskit.graph.p2psp.uniform.ParallelBidirectionalBFSFinder;
import net.coderodde.cskit.graph.p2psp.uniform.UniformCostPathFinder;
import net.coderodde.cskit.sorting.BatchersSort;
import net.coderodde.cskit.sorting.CombSort;
import net.coderodde.cskit.sorting.CountingSort;
import net.coderodde.cskit.sorting.HeapSelectionSort;
import net.coderodde.cskit.sorting.HeapSort;
import net.coderodde.cskit.sorting.IterativeMergeSort;
import net.coderodde.cskit.sorting.NaturalMergeSort;
import net.coderodde.cskit.sorting.ObjectSortingAlgorithm;
import net.coderodde.cskit.sorting.TreeSort;

/**
 * Hello from cskit. This is a performance demo.
 */
public class Demo{

    public static void main(String... args) {
//        debugTreeList();
//        debugTreeList3();
//        profileTreeList();
//        profileObjectSortingAlgorithms(new BatchersSort<Integer>(),
//                                       new CombSort<Integer>(),
//                                       new CountingSort<Integer>(),
//                                       new HeapSelectionSort<Integer>(),
//                                       new IterativeMergeSort<Integer>(),
//                                       new NaturalMergeSort<Integer>(),
//                                       new TreeSort<Integer>(),
//                                       new HeapSort<Integer>(
//                                            new BinaryHeap<Integer, Integer>()),
//                                       new HeapSort<Integer>(
//                                            new FibonacciHeap<Integer, Integer>())
//                );
//        profileShortestPathAlgorithms();
        profileBreadthFirstSearchAlgorithms();
//        profileOrderStatisticTree();
//        profileMaxFlowAlgorithms();
//        profileMSTAlgorithms();
//        debugMaxFlowAlgorithms();
//        profileFibonacciHeap();
    }

    public static void profileOrderStatisticTree() {
        title("OrderStatisticTree demo");
        OrderStatisticTree<Integer, Integer> m1 =
                new OrderStatisticTree<Integer, Integer>();
        Map<Integer, Integer> m2 = new TreeMap<Integer, Integer>();

        long ta = System.currentTimeMillis();

        for (int i = 0; i < 100000; ++i) {
            m1.put(i, i);
        }

        long tb = System.currentTimeMillis();

        System.out.println("OST.put() in " + (tb - ta) + " ms.");

        ta = System.currentTimeMillis();

        for (int i = 0; i < 100000; ++i) {
            m2.put(i, i);
        }

        tb = System.currentTimeMillis();

        System.out.println("TreeMap.put() in " + (tb - ta) + " ms.");

        ta = System.currentTimeMillis();

        for (int i = 0; i < 102000; ++i) {
            m1.get(i);
        }

        tb = System.currentTimeMillis();

        System.out.println("OST.get() in " + (tb - ta) + " ms.");

        ta = System.currentTimeMillis();

        for (int i = 0; i < 102000; ++i) {
            m2.get(i);
        }
