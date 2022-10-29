
package net.coderodde.cskit.sorting;

/**
 * This class implements a queue specialized for natural merge sort.
 *
 * @author Rodion Efremov
 * @version 1.618 (9.12.2013)
 */
public class RunQueue {

    private static class Node {
        Run run;
        Node next;

        Node(Run run) {
            this.run = run;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /**
     * Appends the first run to this queue, thus establishing the invariant for
     * further appendage of runs.
     *
     * @param first the very first element.
     */
    public RunQueue(Run first) {
        Node node = new Node(first);
        head = tail = node;
        size = 1;
    }

    /**
     * Appends a run to the tail of this queue.
     *
     * @param run the run to append.
     */
    public void append(Run run) {
        Node newNode = new Node(run);
        tail.next = newNode;
        tail = newNode;
        ++size;
    }

    /**
     * Returns the first run of this queue.
     *
     * @return the run in the head.
     */
    public Run first() {
        return head.run;
    }

    /**
     * Returns the second run.
     *
     * @return the run right after the head of this queue.
     */
    public Run second() {
        return head.next.run;
    }

    /**
     * Returns the tail run of this queue.
     *
     * @return the tail run of this queue.
     */
    public Run last() {
        return tail.run;
    }

    /**
     * Duh.
     *