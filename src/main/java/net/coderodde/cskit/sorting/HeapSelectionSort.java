
package net.coderodde.cskit.sorting;

/**
 * This class implements an adaptive, unstable sorting algorithm I came to call
 * "Heap selection sort", and which runs in time <tt>O(n log(m))</tt>, where
 * <code>n</code> is the amount of elements and <code>m</code> is the amount
 * of runs in the input array.
 *
 * @author Rodion Efremov
 * @version 1.618 (14.12.2013)
 */
public class HeapSelectionSort<E extends Comparable<? super E>>
implements ObjectSortingAlgorithm<E> {

    @Override
    public void sort(E[] array) {
        sort(array, new Range(0, array.length - 1));
    }