
package net.coderodde.cskit.ds.list;

import java.util.Iterator;
import java.util.ListIterator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * This class tests TreeList.
 *
 * @author Rodion Efremov
 */
public class TreeListTest {

    private TreeList<Integer> list = new TreeList<Integer>(3);

    @Before
    public void init() {
        list.clear();
    }

    @Test
    public void testAdd_GenericType() {
        list.clear();

        assertTrue(list.isHealthy());

        for (int i = 0; i < 100; ++i) {
            list.add(new Integer(i));
        }

        assertTrue(list.isHealthy());

        Iterator<Integer> iter = list.iterator();
        Integer i = 0;

        while (iter.hasNext()) {
            assertEquals(i, iter.next());
            ++i;
        }

        assertEquals(new Integer(100), i);

        Iterator<Integer> descIter = list.descendingIterator();

        i = 99;

        while (i >= 0) {
            assertEquals(i, descIter.next());
            --i;
        }
