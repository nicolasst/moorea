package moorea.graphs;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import moorea.graphs.construction.NodeFactory;
import moorea.misc.IntegerGenerator;

/**
 * The Graph class is the backbone of the library.
 * 
 * Two important design choice that have been made are:
 * 
 * - to store as less information possible within this class and the maximum
 * within the nodes, in order to facilitates the distribution of the graph.
 * 
 * - to use parameterized node types in order to facilitate code reuse. The
 * moorea.graphs used a default node type SimpleNode that can be overided by the
 * generic type in order to implement richer moorea.graphs structure.
 * 
 * As a consequence of these choice, all information, even including edges is
 * stored within the nodes.
 * 
 * Because of this flexible approach, directed moorea.graphs are also based on this class.
 * 
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class Graph<K extends Node> {

	// store all the nodes of the graph
	protected List<K> nodes;
	
	// for access performance use a hashmap to access node by id
	protected Map<Integer, K> nodeIdToNodeMap = new HashMap();
	
	public Class<K> nodeClass;
	NodeFactory<K> nodeFactory;
	
	public Graph() {
		nodeClass = (Class<K>) SimpleNode.class;
		nodeFactory = (NodeFactory<K>) NodeFactory.simpleNodeFactory;
		nodes = new LinkedList();
	}
	
	// parameter is of type Class and not Class<K>
	// in order to allow templated subtypes of Node, e.g. WeightedNode<N>
	
	public Graph(Class nodeClass) {
		this.nodeClass = nodeClass;
		nodeFactory = new NodeFactory(nodeClass);
		nodes = new LinkedList();
	}
	
	//
	// Nodes
	//
	
	// creation
	
	public void setNodeClass(Class ck) {
		nodeClass = ck;
	}
	
	public void setNodeFactory(NodeFactory nf) {
		nodeFactory = nf;
	}
	
	public List<K> getNodes() {
		return nodes;
	}
	
	public K createNewNode() {
		return nodeFactory.createNode();
	}

	public void addNewNode() {
		K n = createNewNode();
		addExistingNode(n);
	}

	public void addNewNodes(int nb) {
		for (int i = 0; i < nb; i++) {
			addNewNode();
		}
	}
	
	public void addExistingNode(K n) {
		nodes.add(n);
		if(nodeIdToNodeMap.containsKey(n.id)) {
			System.err.println("warning: node id already present in graph");
		}
		nodeIdToNodeMap.put(n.id, n);
	}
	
	public void addExistingNodes(List<K> ln) {
		for(K n : ln) {
			addExistingNode(n);
		}
	}
	
	public void resetNodeIdMap() {
		nodeIdToNodeMap.clear();
	}
	
	public void updateNodeId(K n, int id) {
		n.id = id;
		nodeIdToNodeMap.put(id,n);
	}
	
	// access
	
	public K getNodeByNode(Node n) {
		return getNodeById(n.id);
	}
	
	public K getNodeById(int idNode) {
		return nodeIdToNodeMap.get(idNode);
	}

	public List<K> getNodesById(List<Integer> idNodes) {
		List<K> ret = new LinkedList();
		for(Integer i : idNodes) {
			ret.add(getNodeById(i));
		}
		return ret;
	}
	
	public K getNodeByIndex(int i) {
		return nodes.get(i);
	}
	
	// deletion
	
	public void removeNode(K n) {
		for(K nb : (List<K>) n.getNeighbours()) {
			nb.removeNeighbour(n);
		}
		nodes.remove(n);
	}
	
	//
	// Edges
	//
	
	// creation
	
	public void createEdge(K n1, K n2) {
		if(!n1.hasNeighbour(n2)) {
			n1.addNeighbour(n2);
		}
		if(!n2.hasNeighbour(n1)) {
			n2.addNeighbour(n1);
		}
	}

	public void createEdgeByIndex(int n1, int n2) {
		createEdge(nodes.get(n1),nodes.get(n2));
	}
	
	public void createEdgeById(int id1, int id2) {
		createEdge(nodeIdToNodeMap.get(id1),nodeIdToNodeMap.get(id2));
	}
	
	// test
	
	public boolean hasEdgeById(int id1, int id2) {
		return hasEdge(getNodeById(id1),getNodeById(id2));
	}
	
	public boolean hasEdgeByIndex(int n1, int n2) {
		return hasEdge(nodes.get(n1),nodes.get(n2));
	}
	
	public boolean hasEdge(K n1, K n2) {
		return n1.hasNeighbour(n2);
	}

	// deletion
	
	public void removeEdge(K n1, K n2) {
		n1.removeNeighbour(n2);
		n2.removeNeighbour(n1);
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
	
	//
	// MISC
	//
	
	public IntegerGenerator getNodeIdGenerator() {
		return nodeFactory.getIntegerGenerator();
	}
	

}
