package moorea.graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * SimpleNode is the base class to represent adjacency list based undirected moorea.graphs.
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class SimpleNode<K extends Node> extends Node<K> {

	List<K> neighbours = new ArrayList<>();
	
	public SimpleNode(Integer id) {
		this.id = id;
	}
	
	public String toString() {
		return "SN_"+id;
	}

	@Override
	public List<K> getNeighbours() {
		return neighbours;
	}

	public <L> List<L> getNeighbours2() {
		return (List<L>) neighbours;
	}
	
	@Override
	public <L extends Node> List<L> getNeighbours(Class<L> ck) {
		return (List<L>) neighbours;
	}
	
	@Override
	public boolean hasNeighbour(Node n) {
		return neighbours.contains(n);
	}

	@Override
	public void removeNeighbours() {
		neighbours = null;
	}

	@Override
	public void removeNeighbour(Node n) {
		neighbours.remove(n);
	}

	@Override
	public void addNeighbour(Node n) {
		neighbours.add((K)n);
	}

}
