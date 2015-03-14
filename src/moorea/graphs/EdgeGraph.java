package moorea.graphs;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import moorea.graphs.Node;
import moorea.graphs.construction.GraphFactory;
import moorea.graphs.construction.NodeFactory;

/**
 * An EdgeGraph represents undirected moorea.graphs as a set of edges.
 *   By default the edges are unlabeled.
 *   
 *   In order to add information to an edge, one has to derive the class Edge.
 *   See for instance WeightedEdge.
 * 
 * @author nicolas
 *
 * @param <E> type of edges
 * @param <K> type of nodes at the ends of the edges
 */

public class EdgeGraph<E extends Edge, K extends Node> {

	Class<E> edgeClass;
	
	// for iteration purpose
	List<E> edges = new LinkedList();
	
	// for quick edge test and access purpose
	Map<Edge,E> edgesMap = new HashMap();
	
	public EdgeGraph() {
		edgeClass = (Class<E>) Edge.class;
	}
	
	public EdgeGraph(Class ck) {
		edgeClass = ck;
	}
	
	public List<E> getEdges() {
		return edges;
	}
	
	public void addNewEdge(K n1, K n2) {
		E e = null;
		try {
			e = (E) edgeClass.getDeclaredConstructor(new Class[]{Node.class,Node.class}).newInstance(new Object[]{n1,n2});
		} catch(Exception er) {
			er.printStackTrace();
			System.exit(1);
		}
		if(!hasEdge(n1, n2)) {
			edges.add(e);
			edgesMap.put(e,e);
		}
	}
	
	public boolean hasEdge(K n1, K n2) {
		return getEdge(n1, n2) != null;
	}
	
	// TODO: broken
	public E getEdge(K n1, K n2) {
		Edge testEdge = new Edge(n1, n2);
		return edgesMap.get(testEdge);
	}
	
	public static void unitTest() {
		NodeFactory nf = NodeFactory.simpleNodeFactory;
		
		List<SimpleNode> nodes = nf.createNodes(3);
		
		EdgeGraph eg = new EdgeGraph();
		eg.addNewEdge(nodes.get(0), nodes.get(1));
		eg.addNewEdge(nodes.get(1), nodes.get(2));
		
		System.out.println(eg.getEdges());
		System.out.println(eg.edgesMap);
		
		// TODO: je sais pas pourquoi mais ca ne marche pas
		System.out.println(eg.getEdge(nodes.get(1), nodes.get(2)));
	}
	
	public static void main(String[] args) {
		unitTest();
	}
}
