package moorea.graphs.traversal;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import moorea.graphs.Node;

/**
 * 
 * @author nicolas
 *
 */

public class GraphNodeIteratorBoundedBFS extends GraphNodeIterator {

	Node root;

	LinkedList<Node> fifo = new LinkedList();
	Set<Node> mark = new HashSet();
	Map<Node,Integer> mapNodeToDepth = new HashMap();
	
	int maxDepth;
	
	
	public GraphNodeIteratorBoundedBFS(int depth, Node root) {
		this.root = root;
		fifo.add(root);
		mark.add(root);
		mapNodeToDepth.put(root,0);
		//
		maxDepth = depth;
	}
		
	public GraphNodeIteratorBoundedBFS(int depth, Node root, Map<Node,Integer> mapNodeToDepth) {
		this.root = root;
		fifo.add(root);
		mark.add(root);
		mapNodeToDepth.put(root,0);
		//
		maxDepth = depth;
		this.mapNodeToDepth = mapNodeToDepth;
	}
	
	@Override
	public boolean hasNext() {
		return fifo.size()>0;
	}

	@Override
	public Node next() {
		Node n = fifo.removeFirst();
		List<Node> nodes = n.getNeighbours();
		for(Node nb : nodes) {
			if(!mark.contains(nb) && mapNodeToDepth.get(n)+1 < maxDepth) {
				fifo.add(nb);
				mark.add(nb);
				mapNodeToDepth.put(nb, mapNodeToDepth.get(n) + 1);
			}
		}
		return n;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
