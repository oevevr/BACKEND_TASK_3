
package net.coderodde.cskit.graph;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import static net.coderodde.cskit.Utilities.checkModCount;
import static net.coderodde.cskit.Utilities.checkNotNull;
import net.coderodde.cskit.AllIterable;
import net.coderodde.cskit.ParentIterable;

/**
 * This class models nodes of a directed graph.
 *
 * @author Rodion Efremov
 * @version 1.6 (7.12.2013)
 */
public class DirectedGraphNode
implements
Iterable<DirectedGraphNode>,
ParentIterable<DirectedGraphNode>,
AllIterable<DirectedGraphNode> {

    public static final float DEFAULT_LOAD_FACTOR = 1.05f;

    /**
     * HashSets use a power of 2 as capacities.
     */
    public static final int DEFAULT_INITIAL_CAPACITY = 1024;

    /**
     * The name of this node. It is advised to have each node have a unique name.
     */
    private final String name;

    /**
     * The set of incoming nodes.
     */
    private final Set<DirectedGraphNode> in;

    /**
     * The set of out-going nodes.
     */
    private final Set<DirectedGraphNode> out;

    private long modCount;

    /**
     * Constructs a new <code>DirectedGraphNode</code>.
     *
     * @param name the name of this node.