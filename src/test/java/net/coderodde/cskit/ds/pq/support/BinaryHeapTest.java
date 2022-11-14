
package net.coderodde.cskit.ds.pq.support;

import net.coderodde.cskit.ds.pq.BinaryHeap;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.Test;
import static net.coderodde.cskit.Utilities.getAscendingArray;
import static net.coderodde.cskit.Utilities.isSorted;
import static net.coderodde.cskit.Utilities.shuffle;
import net.coderodde.cskit.sorting.Range;
import static org.junit.Assert.*;

/**
 * This class test <code>BinaryHeap</code>.
 *
 * @author Rodion Efremov
 */
public class BinaryHeapTest {
    private BinaryHeap<Integer, Integer> pq =
            new BinaryHeap<Integer, Integer>(9);

    @Test(expected = NoSuchElementException.class)
    public void testSize() {