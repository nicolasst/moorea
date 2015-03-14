package moorea.graphs;

import java.util.LinkedList;
import java.util.List;

/**
 * DirectedNode is the base class to use to implement adjacency list based directed moorea.graphs.
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class DirectedNode<K extends DirectedNode> extends Node {

	LinkedList<K> incommingArcs = new LinkedList<K>();
	LinkedList<K> outgoingArcs = new LinkedList<K>();
	
	public DirectedNode() {
	}
	
	public DirectedNode(Integer id) {
		this.id = id;
	}

	public void addChild(K n) {
		outgoingArcs.add(n);
	}

	public void addFather(K n) {
		incommingArcs.add(n);
	}

	public void addOutgoingArc(K n) {
		outgoingArcs.add(n);
	}
	
	public void addIncomingArc(K n) {
		incommingArcs.add(n);
	}
	
	public void removeOutgoingArc(K n) {
		outgoingArcs.remove(n);
	}
	
	public void removeIncomingArc(K n) {
		incommingArcs.remove(n);
	}
	
	public List<K> getIncommingArcs() {
		return incommingArcs;
	}
	
	public List<K> getOutgoingArcs() {
		return outgoingArcs;
	}
	
	public String toString() {
		return super.toString();
	}

	// operate on the level of the underlying undirected node for the methods inherited from the Node interface
	
	@Override
	public List<Node> getNeighbours() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNeighbour(Node n) {
		if(outgoingArcs.contains(n)) return true;
		if(incommingArcs.contains(n)) return true;
		return false;
	}

	@Override
	public void addNeighbour(Node n) {
		// doesn't make sense for directed node
	}

	@Override
	public void removeNeighbour(Node n) {
		outgoingArcs.remove(n);
		incommingArcs.remove(n);
	}

	@Override
	public void removeNeighbours() {
		outgoingArcs.clear();
		incommingArcs.clear();
	}

	@Override
	public List getNeighbours(Class ck) {
		// TODO Auto-generated method stub
		return null;
	}
}
