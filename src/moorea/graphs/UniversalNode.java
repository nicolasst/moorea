package moorea.graphs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * UniversalNode allows to store any kind of information by attaching this information
 * to a key in a hashtable. A certain number of keys are already defined to represent weighted/unweighted directed/undirected moorea.graphs.
 * 
 * @author nicolas
 *
 * @param <N>
 */

/*
 * What:
 * - encode every kind of nodes
 * 
 * How:
 * - use a hashtable that can be access by any string and return Objects
 * - object needs to be casted appropriately by function calls
 * 
 * e.g.:
 * information.put("UN",new LinkedList()); // UN: undirected neighbors
 *            .put("DN+",new LinkedList()); // DN+: outgoing arcs
 *                  DN-                    // DN-: incoming arcs
 *                  NW, 1);                 // W: node weight
 *                  UEW, new HashMap());     // undirected edge weights
 *                  DEW+, new HashMap());    // directed out edge weights
 *                  DEW-                     // directed in edge weights
 *                  NL, new HashMap());      // store node labels
 *                  UEL,
 *                  DEL+,
 *                  DEL-
 *                  HE                       // hyper edge to which the node belongs to
 *                  
 *  n1.getEdgeWeight(n2)
 *  =
 *  n1.information.get("UEW").get(n2)
 *  
 *  n1.getNeighbours()
 *  =
 *  n1.information.get("UN)
 *  
 *  Needs to separate undirected neighbours, directed neighbours in and out
 *  Therefore the algorithm that manipulate the graph must know of what kind is the graph
 *  
 *  When creating, need to specify of what kind will be the grpah in order to create at the
 *  node level only the relevant data structures. 
 *  cost:                  + weighted  + labelled
 *  	undirected = 1 LL        2 HM        2 HM     
 *      directed   = 2 LL        3 HM        3 HM
 *  min = 1 LL
 *  max = 2 LL + 6 HM
 *  
 *  size of node will be at least size of 1 HM (for 'information') + nbelem*(size of Map.Element) 
 *  to compare with non universal node to:
 *  1 pointer (for LL) for SimpleNode
 *  2 pointer (for LL and Object) for ContainerNode
 *  2 pointer (for LL and HM) + HM for WeightedNode
 *  
 *  We will have:
 *  UniversalNode 
 *  LabelledUNode<K> with "Object getLabel(String label) { return information.get("NL").get(label);}"
 *  ContentUNode<K> with "<K> getContent() {return (K) information.get("NL").get("content");}"
 *  WeightedUN<K> with "<K> getWeight() { return getContent();}"
 *  
 *  getContent and getWeight will access the same info!
 *  to use weight and content use: getWeight and getLabel("content")
 *  
 */

/*
 * default:
 * single edge operations = has, add, remove
 * group edge operations = get 
 * 
 * weighted:
 * single edge operations = get, set
 * group edge operation = set, clear
 * 
 */

public class UniversalNode<N> extends Node<UniversalNode>{

	Map<String,Object> information = new HashMap();
	
	// default constructor : undirected unweighted unlabelled graph

	public UniversalNode() {
		information.put("UE", new LinkedList<>());
	}
	
	public UniversalNode(String... types) {
		//
		boolean isDirected = false;
		boolean isWeighted = false;
		boolean isLabelled = false;
		//
		for (String s : types) {
			if(s.equals("directed")) {
				isDirected = true;
			}
			if(s.equals("weighted")) {
				isWeighted = true;
			}
			if(s.equals("labelled")) {
				isLabelled = true;
			}
		}
		//
		if(isDirected) {
			information.put("DE+", new LinkedList());
			information.put("DE-", new LinkedList());
			if(isWeighted) {
				information.put("DEW+", new HashMap());
				information.put("DEW-", new HashMap());
			}
			if(isLabelled) {
				information.put("NL", new HashMap());
				information.put("DEL+", new HashMap());
				information.put("DEL-", new HashMap());
			}
		} else {
			information.put("UE", new LinkedList());
			if(isWeighted) {
				information.put("UEW", new HashMap());
			}
			if(isLabelled) {
				information.put("NL", new HashMap());
				information.put("UEL", new HashMap());
			}
		}
		
	}
	
	// Undirected aspect
	
	public List<UniversalNode> getNeighbours() {
		return (List<UniversalNode>) information.get("UN");
	}
	
	@Override
	public void addNeighbour(Node n) {
		((List<Node>) information.get("UN")).add(n);
	}
	
	public void removeNeighbour(Node n) {
		((List<Node>) information.get("UN")).remove(n);
	}
	
	public boolean hasNeighbour(Node n) {
		return ((List<Node>) information.get("UN")).contains(n);
	}
	
	// Directed aspect

	// outgoing
	
	public List<Node> getDirectedOutgoingNeighbours() {
		return (List<Node>) information.get("DN+");
	}
	
	public void addDirectedOutgoingNeighbours(Node n) {
		((List<Node>) information.get("DN+")).add(n);
	}
	
	public void removeDirectedOutgoingNeighbours(Node n) {
		((List<Node>) information.get("DN+")).remove(n);
	}

	public boolean hasDirectedOutgoingNeighbour(Node n) {
		return ((List<Node>) information.get("DN+")).contains(n);
	}
	
	// incoming

	public List<Node> getDirectedIncomingNeighbours() {
		return (List<Node>) information.get("DN-");
	}
	
	public void addDirectedIncomingNeighbours(Node n) {
		((List<Node>) information.get("DN-")).add(n);
	}
	
	public void removeDirectedIncomingNeighbours(Node n) {
		((List<Node>) information.get("DN-")).remove(n);
	}

	public boolean hasDirectedIncomingNeighbour(Node n) {
		return ((List<Node>) information.get("DN-")).contains(n);
	}
	
	// // Labelled aspect

	public Object getEdgeLabel(Node n) {
		return ((HashMap) information.get("UEL")).get(n);
	}
	
	public Object setEdgeLabel(Node n, Object key, Object val) {
		return ((HashMap) information.get("UEL")).put(n, val);
	}
	
	public boolean hasEdgeLabel(Node n, Object key) {
		return ((HashMap<Node,HashMap>) (information.get("UEL"))).get(n).get(key) != null;
	}
/*
	public void removeEdgeLabel(Node n,)
	
	public void setEdgeLabels(Object val) {
		for(Node n : getNeighbours()) {
			setEdgeLabel(n, val);
		}
	}
	
	public void clearEdgeLabels() {
		for(Node n : getNeighbours()) {
			setEdgeLabel(n, nu);
		}
	}
*/
	
	// // Weighted aspect
	
	// undirected aspect
	
	public N getEdgeWeight(Node n) {
		return (N) ((HashMap) information.get("UEW")).get(n);
	}
	
	public N setEdgeWeight(Node n, N val) {
		return (N) ((HashMap) information.get("UEW")).put(n, val);
	}


	@Override
	public void removeNeighbours() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <L extends Node> List<L> getNeighbours(Class<L> ck) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
