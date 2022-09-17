
package net.coderodde.cskit.sorting;

/**
 * This class implements iterative merge sort.
 *
 * @author Rodion Efremov
 * @version 1.618 (12.12.2013)
 */
public class IterativeMergeSort<E extends Comparable<? super E>>
implements ObjectSortingAlgorithm<E> {

    @Override
    public void sort(E[] array) {
        sort(array, new Range(0, array.length - 1));
    }

    @Override
    public void sort(E[] array, Range r) {
        if (r.from <= r.to) {
            ascendingSort(array, r.from, r.to);
        } else {
            descendingSort(array, r.to, r.from);
        }
    }

    private void ascendingSort(E[] array, int from, int to) {
        if (from == to) {
            return;
        }

        final int N = to - from + 1;
        // Total amount of merge passes:
        final int NPASSES = (int) Math.ceil(Math.log(1.0 * N) / Math.log(2.0));

        E[] buffer = array.clone();
        E[] source = null;
        E[] destination = null;

        if ((NPASSES & 1) == 0) {
            source = array;
            destination = buffer;
        } else {
            source = buffer;
            destination = array;
        }

        for (int chunkSize = 1; chunkSize < N; chunkSize <<= 1) {
            int kChunk;

            for (kChunk = 0; kChunk < N / chunkSize; kChunk += 2) {
                // Merging chunk kChunk with (kChunk + 1):
                int l = chunkSize * kChunk + from;
                int r = l + chunkSize;