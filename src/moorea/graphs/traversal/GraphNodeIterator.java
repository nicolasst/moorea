package moorea.graphs.traversal;



import java.util.Iterator;

import moorea.graphs.Node;

/**
 * 
 * @author nicolas
 *
 * @param <V>
 */

public abstract class GraphNodeIterator<V extends Node> implements Iterator<V> {

	public abstract boolean hasNext();

	public abstract V next();

	public abstract void remove();
}
