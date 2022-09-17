package net.coderodde.cskit.sorting;

/**
 * This class implements natural merge sort. Stability guaranteed.
 *
 * @author Rodion Efremov
 * @version 1.618-alpha
 */
public class NaturalMergeSort<E extends Comparable<? super E>>
implements ObjectSortingAlgorithm<E> {

    @Override
    public void sort(E[] array) 