package net.coderodde.cskit.graph;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import static net.coderodde.cskit.Utilities.checkNotNull;

/**
 * This class defines nodes of undirected graphs.
 *
 * @author Rodion Efremov
 * @version 1.618033 (28.12.2013)
 */
public class UndirectedGraphNode
implements Iterable<UndirectedGraphNode> {

    private Set<Und