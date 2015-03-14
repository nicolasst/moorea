package moorea.graphs.measures;



import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import moorea.graphs.Graph;
import moorea.graphs.Node;
import moorea.graphs.algorithms.ShortestPaths;
import moorea.graphs.traversal.GraphNodeIteratorBFS;
import moorea.maths.algebra.Semiring;
import moorea.maths.lambda.BorderEffectLambda;
import moorea.maths.lambda.FunctionalAlgorithms;
import moorea.maths.lambda.functions.numbers.MaxReducer;
import moorea.maths.matrix.MatrixAlgorithms;


/**
 * Contains methods to compute simple graph measures.
 * 
 * @author nicolas
 *
 */

public class GraphMeasure {

	/**
	 * Computer the diameter of a graph by computing all pair of shortest path.
	 * 
	 * @param g
	 * @return
	 */
	
	public static int computeDiameterAllSPFW(Graph g) {
		Integer[][] dist = ShortestPaths.getAllSPFloydWrashall(g, Semiring.shortestPathSR);
		int diameter = MatrixAlgorithms.getOptimalElement(dist, new MaxReducer(Integer.class));
		if(diameter == Semiring.maxValue) {
			//return Integer.MIN_VALUE;
			return 0;
		}
		return diameter;
	}
	
	/**
	 * Compute the height of a graph doing a BFS.
	 * 
	 * @param g
	 * @param root
	 * @return
	 */

	public static int computeHeight(Graph g, Node root) {
		List<Map.Entry<Node,Integer>> heap = new LinkedList();
		int maxHeight = Integer.MIN_VALUE;
		heap.add(new AbstractMap.SimpleEntry(root,0));
		while(heap.size()>0){
			Map.Entry<Node,Integer> e = heap.remove(0);
			Node n = e.getKey();
			for(Node nb : (List<Node>) n.getNeighbours()) {
				heap.add(new AbstractMap.SimpleEntry(nb,e.getValue()+1));				
			}
			int h = e.getValue();
			if(h > maxHeight) {
				maxHeight = h;
			}
		}
		return maxHeight;
	}
	
	/**
	 * Compute the distance from one node to another one doing a BFS.
	 * 
	 * @param g
	 * @param root
	 * @return
	 */
	
	public static Map<Node,Integer> computeDistanceFromNode(Graph g, Node root) {
		Map<Node,Integer> valuation = new HashMap();
		valuation.put(root, 0);
		List<Node> heap = new LinkedList();
		heap.add(root);
		while(heap.size()>0){
			Node n = heap.remove(0);
			int height = valuation.get(n)+1;
			for (Node node : heap) {
				if(!valuation.containsKey(node)) {
					valuation.put(node,height);
					heap.add(node);
				}
			}
		}
		return valuation;
	}
	
	/**
	 * Compute the diamete using a BFS.
	 * 
	 * @param g
	 * @return
	 */
	
	public static int computeDiameterWithBFS(Graph g) {
		int maxDephtFromNode = Integer.MIN_VALUE;
		for(Node n : (List<Node>) g.getNodes()) {
			Map nodeMarks = new HashMap();
			nodeMarks.put(n, 0);
			BorderEffectLambda<Node, Integer, Map<Node,Integer>> nodeMarker =
					new BorderEffectLambda<Node, Integer, Map<Node,Integer>>(nodeMarks) {
						public Integer apply(Node e) {
							int level = data.get(e);
							for(Node n : (List<Node>) e.getNeighbours()) {
								if(!data.containsKey(n)) {
									data.put(n, level+1);
								}
							}
							return level;
						}
					};
			FunctionalAlgorithms.iterate(new GraphNodeIteratorBFS(g, n),nodeMarker);
			int dephtFromNode = FunctionalAlgorithms.reduce(nodeMarks.values().iterator(), new MaxReducer(0));
			if(dephtFromNode>maxDephtFromNode) {
				maxDephtFromNode = dephtFromNode;
			}
			//System.out.println(nodeMarks);
		}
		return maxDephtFromNode;
	}

}
