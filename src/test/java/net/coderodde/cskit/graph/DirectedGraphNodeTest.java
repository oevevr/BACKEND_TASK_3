
package net.coderodde.cskit.graph;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests <code>DirectedGraphNode</code>.
 *
 * @author Rodion Efremov
 */
public class DirectedGraphNodeTest {

    @Test
    public void testAddChild() {
        DirectedGraphNode A = new DirectedGraphNode("A");
        DirectedGraphNode B1 = new DirectedGraphNode("B1");
        DirectedGraphNode B2 = new DirectedGraphNode("B2");
        DirectedGraphNode C = new DirectedGraphNode("C");

        A.addChild(B1);
        A.addChild(B2);
        C.addChild(A); // C is a parent of A.

        Set<DirectedGraphNode> childrenSet = new HashSet<DirectedGraphNode>();

        for (DirectedGraphNode u : A) {
            childrenSet.add(u);
        }

        assertTrue(childrenSet.contains(B1));
        assertTrue(childrenSet.contains(B2));
        assertEquals(2, childrenSet.size());

        ////

        Iterator<DirectedGraphNode> parentIterOfA =
                A.parentIterable().iterator();

        assertTrue(parentIterOfA.hasNext());
        assertEquals(C, parentIterOfA.next());
        assertFalse(parentIterOfA.hasNext());
    }

    @Test
    public void testRemoveChild() {
        DirectedGraphNode A = new DirectedGraphNode("A");
        DirectedGraphNode B1 = new DirectedGraphNode("B1");
        DirectedGraphNode B2 = new DirectedGraphNode("B2");