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

    public ChebyshevMetric(CoordinateMap map, DirectedGraphNode target) {
        super(map, target);
        this.p = map.get(target);
    }

    @Override
    public void setTarget(DirectedGraphNode u) {
        this.target = u;
        this.p = map.get(u);
    }

    @Override
    public double get(DirectedGraphNode u) {
        double[] q = map.get(u);
        double max = Double.MIN_VALUE;

        for (int i = 0; i < q.length; ++i) {
            double tmp = Math.abs(