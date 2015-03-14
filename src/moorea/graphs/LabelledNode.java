package moorea.graphs;

import java.util.HashMap;
import java.util.Map;

/**
 * The core class for the LabelledGraph class.
 * 
 * @author nicolas
 *
 */

public class LabelledNode extends SimpleNode<LabelledNode> {
	
	Object object;

	Map<Object,Object> nodeLabels = new HashMap<>();
	
	Map<Node,Map<Object,Object>> edgeLabels = new HashMap<>();
	
	public LabelledNode(Integer id) {
		super(id);
	}
	
	// object value
	
	public void setNodeObject(Object object) {
		this.object = object;
	}
	
	public Object getNodeObject() {
		return object;
	}
	
	// node labels
	
//	public void setNodeLabel(Object key) {
//		setNodeLabel(key, null);
//	}
	
	public void setNodeLabel(Object key, Object value) {
		nodeLabels.put(key, value);
	}
	
	public boolean hasNodeLabel(Object key) {
		return nodeLabels.containsKey(key);
	}
	
//	public Object getNodeLabel(Object key) {
//		return nodeLabels.get(key);
//	}
	
	public <K> K getNodeLabel(Object key) {
		return (K) nodeLabels.get(key);
	}
	
	public void removeNodeLabel(Object key) {
		nodeLabels.remove(key);
	}
	
	public void clearNodeLabels() {
		nodeLabels.clear();
	}
	
	public Map getNodeLabels() {
		return nodeLabels;
	}
	
	// edge labels
	
//	public void setEdgeLabel(Node n, Object key) {
//		setEdgeLabel(n, key, null);
//	}
	
	public void setEdgeLabel(Node n, Object key, Object value) {
		if(!neighbours.contains(n)) {
			//System.err.println("warning: setting edge label of non existing edge");
			addNeighbour(n);
		}
		if(!edgeLabels.containsKey(n)) {
			edgeLabels.put(n, new HashMap<>());
		}
		edgeLabels.get(n).put(key, value);
	}
	
	public boolean hasEdgeLabel(Node n, Object key) {
		return edgeLabels.containsKey(n);
	}
	
	public <K> K getEdgeLabel(Node n, Object key) {
		return (K) edgeLabels.get(n).get(key);
	}
	
	public void removeEdgeLabel(Node n, Object key) {
		edgeLabels.get(n).remove(key);
	}
	
	public void clearEdgeLabels() {
		edgeLabels.clear();
	}
	
	public Map getEdgeLabels() {
		return edgeLabels;
	}
	
	
	// node marks

	public void setNodeMark(Object key) {
		nodeLabels.put(key, key);
	}

	public boolean hasNodeMark(Object key) {
		return nodeLabels.containsKey(key);
	}

	public void removeNodeMark(Object key) {
		nodeLabels.remove(key);
	}

	// edge marks

	public void setEdgeMark(Node n, Object key) {
		if(!neighbours.contains(n)) {
			System.err.println("warning: setting edge label of non existing edge");
		}
		if(!edgeLabels.containsKey(n)) {
			edgeLabels.put(n, new HashMap<>());
		}
		edgeLabels.get(n).put(key, key);
	}

	public boolean hasEdgeMark(Node n, Object key) {
		return edgeLabels.containsKey(n);
	}

	public void removeEdgeMark(Node n, Object key) {
		edgeLabels.get(n).remove(key);
	}
	
	// operate on all nodes

//	public void setAllNodeLabel(Object key) {
//		setAllNodeLabel(key, null);
//	}

	public void setAllNodeMark(Object key) {
		setAllNodeLabel(key, key);
	}

	public void setAllNodeLabel(Object key, Object value) {
		for(Node n : neighbours) {
			setNodeLabel(n, key);
		}
	}

	public void removeAllNodeLabelsAndMarks(Object key) {
		nodeLabels.clear();
	}	
	
	// operate on all edges

//	public void setAllEdgeLabel(Object key) {
//		setAllEdgeLabel(key, null);
//	}

	public void setAllEdgeMark(Object key) {
		setAllEdgeLabel(key, key);
	}
	
	public void setAllEdgeLabel(Object key, Object value) {
		for(Node n : neighbours) {
			setEdgeLabel(n, key, value);
		}
	}
	
	public void removeAllEdgeLabel(Object key) {
		for(Node n : edgeLabels.keySet()) {
			if(edgeLabels.containsKey(key)) {
				removeEdgeLabel(n, key);
			}
		}
	}
	
	//
	
	public String toString() {
		return "LN_"+id;
	}
	
}
