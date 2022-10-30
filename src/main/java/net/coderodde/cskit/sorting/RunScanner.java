
package net.coderodde.cskit.sorting;

import java.util.Comparator;
import static net.coderodde.cskit.Utilities.reverse;

/**
 * This class implements the run scanning logic.
 *
 * @author Rodion Efremov
 * @version 1.618 (9.12.2013)
 */
public class RunScanner<E extends Comparable<? super E>> {

    RunQueue scanAndReturnRunQueue(E[] array, int from, int to) {
        if (from <= to) {
            return ascendingScanQueue(array, from, to);
        } else {
            return descendingScanQueue(array, to, from);
        }
    }

    RunHeap<E> scanAndReturnRunHeap(E[] array, int from, int to) {
        if (from <= to) {
            return ascendingScanHeap(array, from, to);
        } else {
            return descendingScanHeap(array, to, from);
        }
    }
