package net.coderodde.cskit.graph.p2psp.general;

import net.coderodde.cskit.graph.DirectedGraphNode;

/**
 * This class provides a metric for heuristic search based on Tshebyshev
 * distance metric.
 *
 * @author Rodion Efremov
 * @version 1.618 (16.12.2013)
 */
public class ChebyshevMetric<W extends Comparable<? super W>> extends HeuristicFunction {

    private double[] p;

    public ChebyshevMetric(CoordinateMap m