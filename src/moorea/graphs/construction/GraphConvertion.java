package moorea.graphs.construction;

import java.util.List;

import moorea.graphs.EdgeGraph;
import moorea.graphs.Graph;
import moorea.graphs.Node;
import moorea.graphs.weighted.WeightedEdgeGraph;
import moorea.graphs.weighted.WeightedGraph;
import moorea.graphs.weighted.WeightedNode;
import moorea.maths.random.ConstantIntegerGenerator;
import moorea.maths.random.ParametrizedRandomGenerator;
import moorea.misc.BidiMap;


/**
 * This class is used to convert moorea.graphs between their different representations:
 * - adjacency list, edge list, matrix (TODO)
 * and types:
 * - non weighted to weighted
 * - directed to non directed (TODO)
 * 
 * @author nicolas
 *
 */

public class GraphConvertion {
	
	/**
	 * Converts a Graph to a WeightedGraph 
	 * @param g
	 * @return
	 */
	
	public static WeightedGraph<Integer> weightedGraphFromGraph(Graph<? extends Node> g) {
		return weightedGraphFromGraph(g, new ConstantIntegerGenerator(1));
	}

	public static WeightedGraph<Integer> weightedGraphFromGraph(Graph<? extends Node> g, Integer defaultValue) {
		return weightedGraphFromGraph(g, new ConstantIntegerGenerator(defaultValue));
	}
		
	public static <N> WeightedGraph<N> weightedGraphFromGraph(Graph<? extends Node> g, ParametrizedRandomGenerator<N> prg) {
		WeightedGraph<N> wg = new WeightedGraph();
		BidiMap<Node, WeightedNode<N>> oldToNew = new BidiMap();
		for(Node n : g.getNodes()) {
			WeightedNode<N> nn = wg.createNewNode();
			wg.addExistingNode(nn);
			oldToNew.put(n,nn);
		}
		for(Node n : g.getNodes()) {
			for(Node nb : (List<Node>) n.getNeighbours()) {
				wg.createEdge(oldToNew.getAB(n), oldToNew.getAB(nb));
				wg.setEdgeWeight(oldToNew.getAB(n), oldToNew.getAB(nb), prg.generateNext());
			}
		}
		return wg;
	}

	
	// both moorea.graphs share same node list
	
	/**
	 * Converts an (adjacency list based) Graph to an (edge list based) EdgeGraph 
	 * @param g
	 * @return
	 */
	
	public static <K extends Node> EdgeGraph graphToEdgeGraph(Graph<K> g) {
		EdgeGraph eg = new EdgeGraph();
		
		for(K n1 : g.getNodes()) {
			for(K n2 : (List<K>) n1.getNeighbours()) {
				eg.addNewEdge(n1, n2);
			}
		}

		return eg;
	}
	
	/**
	 * Converts an (adjacency list based) WeightedGraph to an (edge list based) WeightedEdgeGraph 
	 * @param g
	 * @return
	 */
	
	public static <K extends Node, N> WeightedEdgeGraph<K, N> weightedGraphToWeightedEdgeGraph(WeightedGraph g) {
		WeightedEdgeGraph eg = new WeightedEdgeGraph();
		
		for(WeightedNode n1 : (List<WeightedNode>) g.getNodes()) {
			for(WeightedNode n2 : (List<WeightedNode>) n1.getNeighbours()) {
				N weight = (N) g.getEdgeWeight(n1, n2);
				eg.addNewEdge(n1, n2);
			}
		}

		return eg;
	}	

}
