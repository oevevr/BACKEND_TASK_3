
package net.coderodde.cskit.graph.p2psp.general;

import java.util.List;
import static net.coderodde.cskit.Utilities.tracebackPath;
import net.coderodde.cskit.ds.pq.PriorityQueue;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;

/**
 * This class implements the classical Dijkstra's algorithm for finding point-
 * 2-point shortest paths.
 *
 * @author Rodion Efremov