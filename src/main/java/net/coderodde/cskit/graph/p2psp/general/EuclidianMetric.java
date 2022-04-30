
package net.coderodde.cskit.graph.p2psp.general;

import net.coderodde.cskit.graph.DirectedGraphNode;

/**
 * This class implements Euclidian metric for use with <tt>A*</tt>-search.
 *
 * @author Rodion Efremov
 * @version 1.618 (16.12.2013)
 */
public class EuclidianMetric extends HeuristicFunction {

    private double[] p;

    public EuclidianMetric(CoordinateMap map, DirectedGraphNode target) {
        super(map, target);
    }

    @Override
    public void setTarget(DirectedGraphNode u) {
        this.target = u;
        this.p = map.get(u);