package net.coderodde.cskit.graph.flow;

import net.coderodde.cskit.Utilities.Pair;
import net.coderodde.cskit.graph.DirectedGraphNode;
import net.coderodde.cskit.graph.DirectedGraphWeightFunction;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests Edmond-Karp's maximum flow algorithm.
 *
 * @author Rodion Efremov
 * @version 1.61803
 */
public class EdmondKarpFlowFinderTest {

    @Test
    public void testFind() {
        DirectedGraphNode Vancouver = new DirectedGraphNode("Vancover");
        DirectedGraphNode Edmonton = new DirectedGraphNode("Edmonton");
        DirectedGraphNode Calgary = new DirectedGraphNode("Calgary");
        DirectedGraphNode Saskatoon = new DirectedGraphNode("Saskatoon");
        DirectedGraphNode Regina = new DirectedGraphNode("Regina");
        DirectedGraphNode Winnipeg = new 