package moorea.graphs.algorithms;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import moorea.graphs.Graph;
import moorea.graphs.Node;
import moorea.graphs.SimpleGraph;
import moorea.graphs.construction.GraphFactory;
import moorea.graphs.io.GraphIO;
import moorea.misc.BidiMap;
import moorea.misc.IntegerGenerator;

/**
 * Contains method to transform one graph into another one.
 * 
 * @author nicolas
 *
 */

public class GraphTransformation {

	/**
	 * Sequentially renumber nodes of a graph starting from 0 and following
	 * the order of the nodes in the node list.
	 * 
	 * @param g
	 */
	
	public static void renumberNodesIdsSequentiallyFrom0(Graph g) {
		IntegerGenerator ig = new IntegerGenerator(0);
		g.resetNodeIdMap();
		for(Node n : (List<Node>) g.getNodes()) {
			g.updateNodeId(n, ig.next());
		}
	}
	
	/**
	 * Create a random bijection between node sets of two moorea.graphs.
	 * 
	 * @param g1
	 * @param g2
	 * @return
	 */
	
	public static BidiMap<Node, Node> createNodeBijectionRandom(Graph g1, Graph g2) {

		List<Node> ln1 = new LinkedList<>(g1.getNodes());
		List<Node> ln2 = new LinkedList<>(g2.getNodes());
		Collections.shuffle(ln1);
		Collections.shuffle(ln2);
		
		BidiMap<Node, Node> bm = new BidiMap();

		int count=0;
		for(Node n : ln1) {
			bm.put(n, ln2.get(count++));
			if(count == ln2.size()) {
				break;
			}
		}
		
		return bm;
	}

	/**
	 * Create edges in g1 from neighborhood information of g2 given a bijection between
	 * the node sets of the two moorea.graphs.
	 * 
	 * @param g1
	 * @param g2
	 * @param nodeMap
	 * @return
	 */
	
	public static Graph mergeNeighbourhooods(Graph g1, Graph g2, BidiMap<Node, Node> nodeMap) {
		return mergeNeighbourhooods(g1, g2, nodeMap, Integer.MAX_VALUE);
	}
	
	public static Graph mergeNeighbourhooods(Graph g1, Graph g2, BidiMap<Node, Node> nodeMap, int limit) {
		
		int count = 0;
		
		for(Node n1 : nodeMap.getMapAB().keySet()) {
			Node n2 = nodeMap.getAB(n1);
			//
			for(Node nb2 : (List<Node>) n2.getNeighbours()) {
				g1.createEdge(n1, nodeMap.getBA(nb2));
			}
			//
			count++;
			if(count>=limit) {
				break;
			}
		}
		
		return g1;
	}
	
	public static void unitTest() {
		SimpleGraph g1 = GraphFactory.simpleGraphFactory.generateCycleGraph(100,1);
		SimpleGraph g2 = GraphFactory.simpleGraphFactory.generateCycleGraph(100,1);
		
		BidiMap<Node, Node> nodeMap = createNodeBijectionRandom(g1, g2);
		
		mergeNeighbourhooods(g1, g2, nodeMap);
		
		GraphIO.toDot(g1, "g.dot");
	}
	
	public static void main(String[] args) {
		unitTest();
	}

	
}
