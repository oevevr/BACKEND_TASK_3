package net.coderodde.cskit.sorting;

/**
 * This class implements a run heap for the sake of heap-selection sort.
 *
 * @author Rodion Efremov
 * @version 1.618 (14.12.2013)
 */
class RunHeap<E extends Comparable<? super E>> {
    private E[] array;
    private Run[] runs;
    private int size;

    RunHeap(int size, E[] array) {
        runs = new Run[size];
        this.array = array;
    }

 