package moorea.graphs;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import moorea.graphs.io.GraphDisplay;

/**
 * A ContainerGraph is graph that uses ContainerNode nodes, which allow storing one information of one type.
 *   This type is the same for all the nodes of the graph.
 * 
 * @author nicolas
 *
 * @param <K> type of the information stored in the nodes
 */

public class ContainerGraph<K> extends Graph<ContainerNode<K>> {

	Map<Object, ContainerNode<K>> contentToNodeMap = new HashMap<>(); 
	
	public ContainerGraph() {
		super(ContainerNode.class);
	}
	
	
	public ContainerNode<K> getNodeByContent(K content) {
		return contentToNodeMap.get(content);
	}
	
	public List<K> getAllContents() {
		List<K> objects = new LinkedList<>();
		for(ContainerNode<K> n : nodes) {
			objects.add(n.getContent());
		}
		return objects;
	}
	
	//
	
	public ContainerNode<K> createNewNode(K content) {
		ContainerNode<K> n = nodeFactory.createNode();
		n.setContent(content);
		return n;
	}

	public void addNewNode(K content) {
		ContainerNode<K> n = createNewNode(content);
		addExistingNode(n);
	}
	
	@Override
	public void addExistingNode(ContainerNode<K> n) {
		super.addExistingNode(n);
		n.containingGraph = this;
		if(n.getContent() != null) {
			updateContentToNodeMap(n.getContent(), n);
		}
	}
	
	//
	
	public void createEdgeByContent(K o1, K o2) {
		createEdge(getNodeByContent(o1), getNodeByContent(o2));
	}
	
	public void removeEdgeByContent(K o1, K o2) {
		removeEdge(getNodeByContent(o1), getNodeByContent(o2));
	}
	
	// set/get node content
	
	public void updateContentToNodeMap(K content, ContainerNode<K> n) {
		if(content != null) {
			if(contentToNodeMap.containsKey(content)) {
				System.out.println("warning: node with same content already present in graph: "+n);
			}
			contentToNodeMap.put(n.getContent(), n);
		}
	}
	
	// get
	
	public K getNodeContent(ContainerNode<K> n) {
		return n.getContent();
	}
	
	public K getNodeContentById(int idNode) {
		return getNodeContent(getNodeById(idNode));
	}
	
	public K getNodeContentByIndex(int i) {
		return getNodeContent(getNodeByIndex(i));
	}	
	
	// set
	
	public void setNodeContent(ContainerNode<K> n, K content) {
		if(n.getContent() != null) {
			contentToNodeMap.remove(n.getContent());
		}
		n.setContent(content);
		updateContentToNodeMap(content, n);
	}
	
	public void setNodeContentById(int idNode, K content) {
		getNodeById(idNode).setContent(content);
	}
	
	public void setNodeContentByIndex(int i, K content) {
		getNodeByIndex(i).setContent(content);
	}
	
	////
	
	public static void unitTest() {
		
		ContainerGraph<String> cg = new ContainerGraph<>();
		
		cg.addNewNode("aa");
		cg.addNewNode("bb");
		cg.addNewNode("cc");
		
		cg.createEdgeByContent("aa", "bb");
		cg.createEdgeByContent("bb", "cc");
		cg.removeEdgeByContent("bb", "cc");
		
		GraphDisplay.disp(cg);
		
	}
	
	public static void main(String[] args) {
		unitTest();
	}
}
