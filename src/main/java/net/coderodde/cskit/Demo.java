
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

        tb = System.currentTimeMillis();

        System.out.println("TreeMap.get() in " + (tb - ta) + " ms.");

        ta = System.currentTimeMillis();

        for (int i = 0; i < 102000; ++i) {
            m1.remove(i);
        }

        tb = System.currentTimeMillis();

        System.out.println("OST.remove() in " + (tb - ta) + " ms.");

        ta = System.currentTimeMillis();

        for (int i = 0; i < 102000; ++i) {
            m2.remove(i);
        }

        tb = System.currentTimeMillis();

        System.out.println("TreeMap.remove() in " + (tb - ta) + " ms.");

        for (int i = 0; i < 100000; ++i) {
            m1.put(i, i);
            m2.put(i, i);
        }

        ta = System.currentTimeMillis();

        for (int i = 0; i < 20000; ++i) {
            m1.entryAt(i);
        }

        tb = System.currentTimeMillis();

        System.out.println("OST.entryAt() in " + (tb - ta) + " ms.");

        ta = System.currentTimeMillis();

        for (int i = 0; i < 20000; ++i) {
            getFromTreeMapHack(i, m2);
        }

        tb = System.currentTimeMillis();

        System.out.println("TreeMap dirty select() hack in " + (tb - ta)
                + " ms.");

        ta = System.currentTimeMillis();

        for (int i = 0; i < 20000; ++i) {
            m1.rankOf(i);
        }

        tb = System.currentTimeMillis();

        System.out.println("OST.rankOf() in " + (tb - ta) + " ms.");

        ta = System.currentTimeMillis();

        for (int i = 0; i < 20000; ++i) {
            getRankOfTreeMapHack(i, m2);
        }

        tb = System.currentTimeMillis();

        System.out.println("TreeMap dirty rankOf() hack in " + (tb - ta)
                + " ms.");

        line();
    }

    private static <K, V>   int getRankOfTreeMapHack(K key, Map<K, V> map) {
        int index = 0;

        for (K k : map.keySet()) {
            if (k.equals(key)) {
                return index;
            }

            ++index;
        }

        return -1;
    }

    private static <K, V> K getFromTreeMapHack(int key, Map<K, V> map) {
        int pos = 0;

        for (K k : map.keySet()) {
            if (key == pos) {
                return k;
            }

            pos++;
        }

        return null;
    }

    public static void profileBreadthFirstSearchAlgorithms() {
        title("Uniform cost graph search");
        final long SEED = System.currentTimeMillis();
        final Random r = new Random(SEED);
        final int SIZE = 100000;
        final float LOAD_FACTOR = 5.5f / SIZE;

        System.out.println("Nodes in the graph: " + SIZE + ", load factor: "
                + LOAD_FACTOR);

        System.out.println("Seed: " + SEED);

        List<DirectedGraphNode> graph =
                generateSimpleGraph(SIZE, LOAD_FACTOR, r);

        DirectedGraphNode source = graph.get(r.nextInt(SIZE));
        DirectedGraphNode target = graph.get(r.nextInt(SIZE));

        UniformCostPathFinder finder1 =
                new BreadthFirstSearchFinder();

        UniformCostPathFinder finder2 =
                new BidirectionalBFSFinder();

        UniformCostPathFinder finder3 =
                new ParallelBidirectionalBFSFinder();
        
        long ta = System.currentTimeMillis();
        List<DirectedGraphNode> path1 = finder1.find(source, target);
        long tb = System.currentTimeMillis();

        System.out.println("BreadthFirstSearchFinder in " + (tb - ta) + " ms.");

        ta = System.currentTimeMillis();
        List<DirectedGraphNode> path2 = finder2.find(source, target);
        tb = System.currentTimeMillis();

        System.out.println("BidirectionalBFSFinder in "
                + (tb - ta) + " ms.");

        ta = System.currentTimeMillis();
        List<DirectedGraphNode> path3 = finder3.find(source, target);
        tb = System.currentTimeMillis();

        System.out.println("ParallelBidirectionalBFSFinder in "
                + (tb - ta) + " ms.");
        
        line();

        boolean eq = path1.size() == path2.size()
                  && path2.size() == path3.size();

        if (eq == true) {
            System.out.println("Paths are of same length: " + eq
                    + ", length: " + path1.size());
        } else {
            System.out.println("Erroneous paths! Lengths: "
                    + path1.size() + ", " + path2.size()
                    + " and " + path3.size() + ".");
        }

        boolean ok1 = (isConnectedPath(path1)
                && path1.get(0).equals(source)
                && path1.get(path1.size() - 1).equals(target));

        boolean ok2 = (isConnectedPath(path2)
                && path2.get(0).equals(source)
                && path2.get(path2.size() - 1).equals(target));

        boolean ok3 = (isConnectedPath(path3)
                && path3.get(0).equals(source)
                && path3.get(path3.size() - 1).equals(target));

        System.out.println("Breadth-first search path OK: " + ok1);
        System.out.println("Bidirectional breadth-first search path OK: "
                + ok2);
        System.out.println("Bidirectional parallel BFS path OK: "
                + ok3);
        
        line();

        System.gc();
    }

    private static void profileObjectSortingAlgorithms(
            ObjectSortingAlgorithm<Integer>... algos) {
        title("Object sorting algorithms");

        ////

        long SEED = System.currentTimeMillis();

        System.out.println("Seed: " + SEED);

        int SIZE = 200000;
        Random r = new Random();

        Integer[] array = getRandomIntegerArray(SIZE, 0, 100, r);

        profileSortingAlgorithmsOn(array, "Small amount of different elements"
                + ", size: " + SIZE + ", random order", algos);

        ////

        SIZE = 20000;

        array = getRandomIntegerArray(SIZE, 0, 100, r);

        profileSortingAlgorithmsOn(array, "Small amount of different elements"
                + ", size: " + SIZE + ", random order", algos);

        ////

        SIZE = 200000;

        array = getRandomIntegerArray(SIZE, r);

        profileSortingAlgorithmsOn(array, "Random elements, size: " + SIZE,
                                   algos);

        ////

        SIZE = 20000;

        array = getRandomIntegerArray(SIZE, r);

        profileSortingAlgorithmsOn(array, "Random elements, size: " + SIZE,
                                   algos);

        ////

        SIZE = 200000;
        int RUNS = 16;

        array = getPresortedArray(SIZE, RUNS);

        profileSortingAlgorithmsOn(array, "Presorted array of " + SIZE +
                " elements with " + RUNS + " runs", algos);

        ////

        SIZE = 20000;
        RUNS = 16;
