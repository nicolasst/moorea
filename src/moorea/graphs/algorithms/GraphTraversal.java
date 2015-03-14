package moorea.graphs.algorithms;



import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import moorea.graphs.Graph;
import moorea.graphs.Node;

/**
 * Contains simple method to traverse a graph. Richer traversal need to be done
 * using NodeIterator from the traversal subpackage.
 * 
 * @author nicolas
 *
 */

public class GraphTraversal {
	
	/**
	 * Displays the breadth first search (BFS) traversal of the graph g from the node r.
	 */
	
	public static <K extends Node> void breadthFirstSearch(Graph<K> g, K r) {
		LinkedList<K> fifo = new LinkedList<K>();
		Set<K> mark = new HashSet<>();
		fifo.add(r);
		while(fifo.size()>0) {
			K n = fifo.removeFirst();
			if(!mark.contains(n)) {
				System.out.println((K)n);
				for(K nb : (List<K>) n.getNeighbours()) {
					if(!mark.contains(nb)) {
						fifo.add(nb);
					}
				}
			}
			mark.add(n);
		}
	}
	
	/**
	 * Displays the depth first search (DFS) traversal of the graph g from the node r.
	 */
	
	public static void depthFirstSearch(Graph<Node> g, Node r) {
		LinkedList<Node> fifo = new LinkedList<Node>();
		Set<Node> mark = new HashSet<Node>();
		fifo.add(r);
		while(fifo.size()>0) {
			Node n = fifo.removeLast();
			if(!mark.contains(n)) {
				System.out.println(n);
				for(Node nb : (List<Node>) n.getNeighbours()) {
					if(!mark.contains(nb)) {
						fifo.add(nb);
					}
				}
			}
			mark.add(n);
		}
	}
	
	/**
	 * Check if a path actually exists in a graph
	 */
	
	public static boolean followPath(Graph<Node> g, List<Node> path) {
		Node n = g.getNodeByNode(path.get(0));
		if(n == null) {
			System.out.println("first node not present in graph: "+n);
			return false;
		}
		for (int i = 0; i < path.size()-1; i++) {
			Node next = path.get(i+1);
			if(!n.getNeighbours().contains(next)) {
				System.out.println("neighbour not present: "+next);
				return false;
			}
			n = next;
		}
		return true;
	}
	
}
