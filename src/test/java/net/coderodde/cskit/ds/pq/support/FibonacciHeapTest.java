package net.coderodde.cskit.ds.pq.support;

import java.util.NoSuchElementException;
import java.util.Random;
import net.coderodde.cskit.ds.pq.FibonacciHeap;
import org.junit.Test;
import static net.coderodde.cskit.Utilities.getAscendingArray;
import static net.coderodde.cskit.Utilities.isSorted;
import static net.coderodde.cskit.Utilities.shuffle;
import net.coderodde.cskit.sorting.Range;
import static org.junit.Assert.*;

/**
 * This class test <code>FibonacciHeap</code>.
 *
 * @author Rodion Efremov
 */
public class FibonacciHeapTest {
    private FibonacciHeap<Integer, Integer> pq =
        new FibonacciHeap<Integer, Integer>(9);

    @Test(expected = NoSuchElementException.class)
    public void testSize() {
        assertTrue(pq.isEmpty());
        assertEquals(pq.size(), 0);
