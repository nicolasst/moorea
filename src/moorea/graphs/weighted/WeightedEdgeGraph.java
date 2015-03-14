package moorea.graphs.weighted;

import moorea.graphs.EdgeGraph;
import moorea.graphs.Node;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 * @param <N>
 */

public class WeightedEdgeGraph<K extends Node, N> extends EdgeGraph<WeightedEdge<K, N>, K> {


	public WeightedEdgeGraph() {
		super(WeightedEdge.class);
	}
	
	public void addNewEdge(K n, K m, N value) {
		super.addNewEdge(n, m);
		getEdge(n, m).setWeight(value);
	}
	
	public void setEdgeWeight(K n, K m, N value) {
		getEdge(n, m).setWeight(value);
	}
	
	public N getEdgeWeight(K n, K m) {
		return getEdge(n,m).getWeight();
	}
}
