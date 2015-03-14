package moorea.graphs.traversal;


import java.util.Iterator;
import java.util.Random;

import moorea.graphs.Graph;
import moorea.graphs.Node;
import moorea.misc.lists.ListRandomInfiniteIterator;

/**
 * 
 * @author nicolas
 *
 * @param <V>
 */

public class GraphNodeIteratorRandom<V extends Node> implements Iterator<V> {

	Graph<V> g;
	ListRandomInfiniteIterator<V> lri;
	
	public GraphNodeIteratorRandom(Graph<V> g) {
		this.g = g;
		lri = new ListRandomInfiniteIterator<V>(g.getNodes());
	}

	public GraphNodeIteratorRandom(Graph<V> g, Random r) {
		this.g = g;
		lri = new ListRandomInfiniteIterator<V>(g.getNodes(), r);
	}

	public GraphNodeIteratorRandom(Graph<V> g, int maxIterations) {
		this.g = g;
		lri = new ListRandomInfiniteIterator<V>(g.getNodes(), maxIterations);
	}

	public GraphNodeIteratorRandom(Graph<V> g, Random r, int maxIterations) {
		this.g = g;
		lri = new ListRandomInfiniteIterator<V>(g.getNodes(), r, maxIterations);
	}
	
	public boolean hasNext() {
		return lri.hasNext();
	}

	public V next() {
		return lri.next();
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

}
