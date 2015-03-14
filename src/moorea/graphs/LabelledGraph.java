package moorea.graphs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class provides an adjacency list based graph, where the objects within
 * the nodes of the graph are totally independent of the encoding of the
 * graph, and which also provides the ability to annotate any node and edge of
 * the graph.
 * 
 * The point of this class is that is is <i>very easy</i> to build sophisticated
 * moorea.graphs and implement straightforwardly adjacency list based algorithms.
 * However:
 * - it is less efficient than dedicated class to store information.
 * - algorithms taking advantage of the labels (e.g. marking in Dijstra's)
 *     will modify the state of the graph, which is not desirable.
 * 
 * It is therefore aimed at quick prototyping of algorithms.
 * 
 * Above the three standard types of accessing a node: reference, id and index;
 * This class provide a fourth way: by the reference of the object contained
 * within the node.
 * 
 * The labels on the nodes and edges can be either predicates or functions.
 * - predicate: what matters whether a symbol is associated
 * - function: what matters is the value associated with the symbol
 * 
 * TODO implement the functions createEdge*AndSetLabel
 * 
 * @author nicolas
 *
 */

public class LabelledGraph extends Graph<LabelledNode> {

	Map<Object,LabelledNode> objectToNodeMap = new HashMap<>(); 
	
	public LabelledGraph() {
		super(LabelledNode.class);
	}
	
	@Override
	public void addExistingNode(LabelledNode n) {
		super.addExistingNode(n);
		if(n.getNodeObject() != null) {
			objectToNodeMap.put(n.getNodeObject(),n);
		}
	}
	
	// access to the objects
	
	public LabelledNode getNodeByObject(Object o) {
		return objectToNodeMap.get(o);
	}
	
	public List<Object> getAllObjects() {
		List objects = new LinkedList<>();
		for(LabelledNode n : nodes) {
			objects.add(n.getNodeObject());
		}
		return objects;
	}
	
	//
	
	public void createEdgeByObject(Object o1, Object o2) {
		createEdge(getNodeByObject(o1), getNodeByObject(o2));
	}
	
	//
	// access to labels
	//

	// by node reference
	
	// node labels
	
//	public void setNodeLabel(LabelledNode n, Object key) {
//		n.setNodeLabel(key);
//	}
	
	public void setNodeLabel(LabelledNode n, Object key, Object value) {
		n.setNodeLabel(key, value);
	}
	
	public boolean hasNodeLabel(LabelledNode n, Object key) {
		return n.hasNodeLabel(key);
	}

	public Object getNodeLabel(LabelledNode n, Object key) {
		return n.getNodeLabel(key);
	}

	public void removeNodeLabel(LabelledNode n, Object key) {
		n.removeNodeLabel(key);
	}
	
	// edge label
	
//	public void setEdgeLabel(LabelledNode n, LabelledNode m, Object key) {
//		n.setEdgeLabel(m, key);
//		m.setEdgeLabel(n, key);
//	}
	
	public void setEdgeLabel(LabelledNode n, LabelledNode m, Object key, Object value) {
		n.setEdgeLabel(m, key, value);
		m.setEdgeLabel(n, key, value);
	}
	
	public boolean hasEdgeLabel(LabelledNode n, LabelledNode m, Object key) {
		return n.hasEdgeLabel(m, key);
	}
	
	public <K> K getEdgeLabel(LabelledNode n, LabelledNode m, Object key) {
		return (K) n.getEdgeLabel(m, key);
	}

	public void removeEdgeLabel(LabelledNode n, LabelledNode m, Object key) {
		n.removeEdgeLabel(m, key);
		m.removeEdgeLabel(n, key);
	}
	
	
	// node mark
	
	public void setNodeMark(LabelledNode n, Object key) {
		n.setNodeMark(key);
	}
	
	public boolean hasNodeMark(LabelledNode n, Object key) {
		return n.hasNodeMark(key);
	}

	public void removeNodeMark(LabelledNode n, Object key) {
		n.removeNodeMark(key);
	}
	
	// edge mark
	
	public void setEdgeMark(LabelledNode n, LabelledNode m, Object key) {
		n.setEdgeMark(m, key);
		m.setEdgeMark(n, key);
	}
	
	public boolean hasEdgeMark(LabelledNode n, LabelledNode m, Object key) {
		return n.hasEdgeMark(m, key);
	}
	
	public void removeEdgeMark(LabelledNode n, LabelledNode m, Object key) {
		n.removeEdgeMark(m, key);
		m.removeEdgeMark(n, key);
	}
	
	//// by object reference
	
	// node label

//	public void setNodeLabelByObject(Object o1, Object key) {
//		LabelledNode n = getNodeByObject(o1);
//		setNodeLabel(n, key);
//	}
	
	public void setNodeLabelByObject(Object o1, Object key, Object value) {
		LabelledNode n = getNodeByObject(o1);
		setNodeLabel(n, key, value);
	}
	
	public boolean hasNodeLabelByObject(Object o1, Object key) {
		LabelledNode n = getNodeByObject(o1);
		return hasNodeLabel(n, key);
	}
	
	public Object getNodeLabelByObject(Object o1, Object key) {
		LabelledNode n = getNodeByObject(o1);
		return getNodeLabel(n, key);
	}
	
	public void removeNodeLabelByObject(Object o1, Object key) {
		LabelledNode n = getNodeByObject(o1);
		removeNodeLabel(n, key);
	}
	
	// edge label
	
//	public void setEdgeLabelByObject(Object o1, Object o2, Object key) {
//		LabelledNode n = getNodeByObject(o1);
//		LabelledNode m = getNodeByObject(o2);
//		setEdgeLabel(n, m, key);
//	}
	
	public void setEdgeLabelByObject(Object o1, Object o2, Object key, Object value) {
		LabelledNode n = getNodeByObject(o1);
		LabelledNode m = getNodeByObject(o2);
		setEdgeLabel(n, m, key, value);
	}
	
	public boolean hasEdgeLabelByObject(Object o1, Object o2, Object key) {
		LabelledNode n = getNodeByObject(o1);
		LabelledNode m = getNodeByObject(o2);
		return hasEdgeLabel(n, m, key);
	}

	public Object getEdgeLabelByObject(Object o1, Object o2, Object key) {
		LabelledNode n = getNodeByObject(o1);
		LabelledNode m = getNodeByObject(o2);
		return getEdgeLabel(n, m, key);
	}
	
	public void removeEdgeLabelByObject(Object o1, Object o2, Object key) {
		LabelledNode n = getNodeByObject(o1);
		LabelledNode m = getNodeByObject(o2);
		removeEdgeLabel(n, m, key);
	}

	// node mark
	
	public void setNodeMarkByObject(Object o1, Object key) {
		LabelledNode n = getNodeByObject(o1);
		setNodeLabel(n, key, key);
	}
	
	public boolean hasNodeMarkByObject(Object o1, Object key) {
		LabelledNode n = getNodeByObject(o1);
		return hasNodeLabel(n, key);
	}
	
	public void removeNodeMarkByObject(Object o1, Object key) {
		LabelledNode n = getNodeByObject(o1);
		removeNodeLabel(n, key);
	}
	
	// edge label
	
	public void setEdgeMarkByObject(Object o1, Object o2, Object key) {
		LabelledNode n = getNodeByObject(o1);
		LabelledNode m = getNodeByObject(o2);
		setEdgeLabel(n, m, key, key);
	}
	
	public boolean hasEdgeMarkByObject(Object o1, Object o2, Object key) {
		LabelledNode n = getNodeByObject(o1);
		LabelledNode m = getNodeByObject(o2);
		return hasEdgeLabel(n, m, key);
	}
	
	public void removeEdgeMarkByObject(Object o1, Object o2, Object key) {
		LabelledNode n = getNodeByObject(o1);
		LabelledNode m = getNodeByObject(o2);
		removeEdgeLabel(n, m, key);
	}
	
	//// by index
	
	// node label
	
//	public void setNodeLabelByIndex(int i, Object key) {
//		LabelledNode n = nodes.get(i);
//		setNodeLabel(n, key);
//	}
	
	public void setNodeLabelByIndex(int i, Object key, Object value) {
		LabelledNode n = nodes.get(i);
		setNodeLabel(n, key, value);
	}
	
	public boolean hasNodeLabelByIndex(int i, Object key) {
		LabelledNode n = nodes.get(i);
		return hasNodeLabel(n, key);
	}
		
	public Object getNodeLabelByIndex(int i, Object key) {
		LabelledNode n = nodes.get(i);
		return getNodeLabel(n, key);
	}
	
	public void removeNodeLabelByIndex(int i, Object key) {
		LabelledNode n = nodes.get(i);
		removeNodeLabel(n, key);
	}
	
	// edge label
	
//	public void setEdgeLabelByIndex(int i, int j, Object key) {
//		LabelledNode n = nodes.get(i);
//		LabelledNode m = nodes.get(j);
//		setEdgeLabel(n, m, key);
//	}
	
	public void setEdgeLabelByIndex(int i, int j, Object key, Object value) {
		LabelledNode n = nodes.get(i);
		LabelledNode m = nodes.get(j);
		setEdgeLabel(n, m, key, value);
	}

	public boolean hasEdgeLabelByIndex(int i, int j, Object key) {
		LabelledNode n = nodes.get(i);
		LabelledNode m = nodes.get(j);
		return hasEdgeLabel(n, m, key);
	}
	
	public Object getEdgeLabelByIndex(int i, int j, Object key) {
		LabelledNode n = nodes.get(i);
		LabelledNode m = nodes.get(j);
		return getEdgeLabel(n, m, key);
	}
	
	public void removeEdgeLabelByIndex(int i, int j, Object key) {
		LabelledNode n = nodes.get(i);
		LabelledNode m = nodes.get(j);
		removeEdgeLabel(n, m, key);
	}
	
	// node mark
	
	public void setNodeMarkByIndex(int i, Object key) {
		LabelledNode n = nodes.get(i);
		setNodeLabel(n, key, key);
	}
	
	public boolean hasNodeMarkByIndex(int i, Object key) {
		LabelledNode n = nodes.get(i);
		return hasNodeLabel(n, key);
	}
	
	public void removeNodeMarkByIndex(int i, Object key) {
		LabelledNode n = nodes.get(i);
		removeNodeLabel(n, key);
	}
	
	// edge mark
	
	
	public void setEdgeMarkByIndex(int i, int j, Object key) {
		LabelledNode n = nodes.get(i);
		LabelledNode m = nodes.get(j);
		setEdgeLabel(n, m, key, key);
	}

	public boolean hasEdgeMarkByIndex(int i, int j, Object key) {
		LabelledNode n = nodes.get(i);
		LabelledNode m = nodes.get(j);
		return hasEdgeLabel(n, m, key);
	}
	
	public void removeEdgeMarkByIndex(int i, int j, Object key) {
		LabelledNode n = nodes.get(i);
		LabelledNode m = nodes.get(j);
		removeEdgeLabel(n, m, key);
	}
	
	//// by id

	// node label
	
//	public void setNodeLabelById(int i, Object key) {
//		LabelledNode n = getNodeById(i);
//		setNodeLabel(n, key);
//	}
	
	public void setNodeLabelById(int i, Object key, Object value) {
		LabelledNode n = getNodeById(i);
		setNodeLabel(n, key, value);
	}

	public boolean hasNodeLabelById(int i, Object key) {
		LabelledNode n = getNodeById(i);
		return hasNodeLabel(n, key);
	}
	
	public Object getNodeLabelById(int i, Object key) {
		LabelledNode n = getNodeById(i);
		return getNodeLabel(n, key);
	}
	
	public void removeNodeLabelById(int i, Object key) {
		LabelledNode n = getNodeById(i);
		removeNodeLabel(n, key);
	}
	
	// edge label

//	public void setEdgeLabelById(int i, int j, Object key) {
//		LabelledNode n = getNodeById(i);
//		LabelledNode m = getNodeById(j);
//		setEdgeLabel(n, m, key);
//	}
	
	public void setEdgeLabelById(int i, int j, Object key, Object value) {
		LabelledNode n = getNodeById(i);
		LabelledNode m = getNodeById(j);
		setEdgeLabel(n, m, key, value);
	}

	public boolean hasEdgeLabelById(int i, int j, Object key) {
		LabelledNode n = getNodeById(i);
		LabelledNode m = getNodeById(j);
		return hasEdgeLabel(n, m, key);
	}
	
	public Object getEdgeLabelById(int i, int j, Object key) {
		LabelledNode n = getNodeById(i);
		LabelledNode m = getNodeById(j);
		return getEdgeLabel(n, m, key);
	}

	public void removeEdgeLabelById(int i, int j, Object key) {
		LabelledNode n = getNodeById(i);
		LabelledNode m = getNodeById(j);
		removeEdgeLabel(n, m, key);
	}

	// node mark
	
	public void setNodeMarkById(int i, Object key) {
		LabelledNode n = getNodeById(i);
		setNodeLabel(n, key, key);
	}

	public boolean hasNodeMarkById(int i, Object key) {
		LabelledNode n = getNodeById(i);
		return hasNodeLabel(n, key);
	}

	public void removeNodeMarkById(int i, Object key) {
		LabelledNode n = getNodeById(i);
		removeNodeLabel(n, key);
	}
	
	// edge label
	
	public void setEdgeMarkById(int i, int j, Object key) {
		LabelledNode n = getNodeById(i);
		LabelledNode m = getNodeById(j);
		setEdgeLabel(n, m, key, key);
	}

	public boolean hasEdgeMarkById(int i, int j, Object key) {
		LabelledNode n = getNodeById(i);
		LabelledNode m = getNodeById(j);
		return hasEdgeLabel(n, m, key);
	}

	public void removeEdgeMarkById(int i, int j, Object key) {
		LabelledNode n = getNodeById(i);
		LabelledNode m = getNodeById(j);
		removeEdgeLabel(n, m, key);
	}
	
	//// operate on all node and all edges
	
	// node labels
	
//	public void setAllNodeLabels(Object key) {
//		setAllNodeLabels(key, null);
//	}

	
	public void setAllNodeLabels(Object key, Object value) {
		for(LabelledNode n : nodes) {
			setNodeLabel(n, key,value);
		}
	}
	
	public void setAllNodeMarks(Object key) {
		setAllNodeMarks(key);
	}

	public void setAllEdgeMarks(Object key) {
		setAllEdgeLabels(key, key);
	}
	
//	public void setAllEdgeLabels(Object key) {
//		setAllNodeLabels(key, null);
//	}
	
	public void setAllEdgeLabels(Object key, Object value) {
		for(LabelledNode n : nodes) {
			n.setAllEdgeLabel(key,value);
		}
	}
	
	public void removeAllNodeLabels(Object key) {
		for(LabelledNode n : nodes) {
			n.removeNodeLabel(key);
		}
	}

	public void removeAllNodeMarks(Object key) {
		for(LabelledNode n : nodes) {
			n.removeNodeLabel(key);
		}
	}
	
	public void removeAllEdgeLabels(Object key) {
		for(LabelledNode n : nodes) {
			n.removeAllEdgeLabel(key);
		}
	}
	
	public void removeAllLabelsAndMarks() {
		for(LabelledNode n : nodes) {
			n.clearNodeLabels();
			n.clearEdgeLabels();
		}
	}
	
}
