
package net.coderodde.cskit.ds.tree;

import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests <code>OrderStatisticTree</code>.
 *
 * @author Rodion Efremov
 * @version 1.6180 (19.12.2013)
 */
public class OrderStatisticTreeTest {

    private OrderStatisticTree<Integer, Integer> tree =
        new OrderStatisticTree<Integer, Integer>();

    @Test
    public void test1() {
        for (int i = 0; i < 100; ++i) {
            tree.put(i, i);
        }

        assertEquals(100, tree.size());

        for (Integer i = 0; i < tree.size(); ++i) {