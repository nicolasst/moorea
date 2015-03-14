package moorea.graphs;


/**
 * A DirectedGraph is a graph that uses directed nodes to represent directed moorea.graphs.
 *   By default the class of node used is DirectedNode.
 *   
 *   Default nodes do not allow to store any kind of information except adjacency.
 *   Storing information in the node requires deriving the DirectedNode class,
 *   using it as the generic parameter K and providing it to the constructor.
 * 
 * @author nicolas
 *
 * @param <K> subtype of DirectedNode
 */

public class DirectedGraph<K extends DirectedNode> extends Graph<K> {

	public DirectedGraph() {
		super(DirectedNode.class);
	}
	
	public DirectedGraph(Class<K> ck) {
		super(ck);
	}
	
	//
	// Directed Edges
	//
	
	// creation
	
	@Override
	public void createEdge(K n1, K n2) {
		if(!hasEdge(n1, n2)) {
			n1.addOutgoingArc(n2);
		}
		if(!hasEdge(n2, n1)) {
			n2.addIncomingArc(n1);
		}
	}

	public void createEdgeByIndex(int n1, int n2) {
		createEdge(nodes.get(n1),nodes.get(n2));
	}
	
	// test
	
	public boolean hasEdgeById(int id1, int id2) {
		return hasEdge(getNodeById(id1),getNodeById(id2));
	}
	
	public boolean hasEdgeByIndex(int n1, int n2) {
		return hasEdge(nodes.get(n1),nodes.get(n2));
	}
	
	public boolean hasEdge(K n1, K n2) {
		return n1.getOutgoingArcs().contains(n2);
	}

	// deletion
	
	@Override
	public void removeEdge(K n1, K n2) {
		n1.removeOutgoingArc(n2);
		n2.removeIncomingArc(n1);
	}
	
	public void removeEdgeById(int id1, int id2) {
		K n1 = getNodeById(id1);
		K n2 = getNodeById(id2);
		removeEdge(n1, n2);
	}
	
	public void removeAllEdges() {
		for(K n : nodes) {
			n.removeNeighbours();
		}
	}
}
