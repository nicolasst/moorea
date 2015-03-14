package moorea.graphs.traversal;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import moorea.graphs.DirectedNode;
import moorea.graphs.Graph;
import moorea.graphs.Node;

/**
 * 
 * @author nicolas
 *
 * @param <V>
 */

public class GraphNodeIteratorBFS<V extends Node> extends GraphNodeIterator<V> {

	Graph<V> g;
	V root;

	LinkedList<V> fifo = new LinkedList<V>();
	Set<V> mark = new HashSet<V>();
	
	boolean directedTraversal = false;
	boolean directedFatherToChildTraversal;
	
	public GraphNodeIteratorBFS(Graph<V> g, V root) {
		this.g = g;
		this.root = root;
		fifo.add(root);
		mark.add(root);
	}
	
	public GraphNodeIteratorBFS(V root) {
		this.root = root;
		fifo.add(root);
		mark.add(root);
	}


	public GraphNodeIteratorBFS(Graph<V> g, V root, boolean fatherToChildTraversal) {
		this(g,root);
		directedTraversal = true;
		directedFatherToChildTraversal = fatherToChildTraversal;
	}

	public GraphNodeIteratorBFS(Graph<V> g, V root, boolean directedTraversal, boolean fatherToChildTraversal) {
		this(g,root);
		this.directedTraversal = directedTraversal;
		directedFatherToChildTraversal = fatherToChildTraversal;
	}

	
	@Override
	public boolean hasNext() {
		return fifo.size()>0;
	}

	@Override
	public V next() {
		V n = fifo.removeFirst();
		List<V> nodes = null;
		if(!directedTraversal) {
			nodes = (List<V>) n.getNeighbours();
		} else {
			if(directedFatherToChildTraversal) {
				nodes = ((DirectedNode)n).getOutgoingArcs();
			} else {
				nodes = ((DirectedNode)n).getIncommingArcs();
			}
		}
		for(V nb : nodes) {
			if(!mark.contains(nb)) {
				fifo.add(nb);
				mark.add(nb);
			}
		}
		return n;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
	}

}
