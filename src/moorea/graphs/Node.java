package moorea.graphs;

import java.util.List;

/**
 * Abstract class to represented most basic node operations.
 * 
 * @author nicolas
 *
 * @param <K>
 */

public abstract class Node<K extends Node> {
	
	public int id;
	
	public abstract List<K> getNeighbours();

//	public abstract <L extends Node> List<L> getNeighbours2();
	
	public abstract <L extends Node> List<L> getNeighbours(Class<L> ck);
	
	public abstract boolean hasNeighbour(Node n);

	public abstract void addNeighbour(Node n);
	
	public abstract void removeNeighbour(Node n);
	
	public abstract void removeNeighbours();
}
