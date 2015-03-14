package moorea.graphs.weighted;

import moorea.graphs.Graph;

/**
 * 
 * @author nicolas
 *
 * @param <N>
 */

public class WeightedGraph<N> extends Graph<WeightedNode<N>> {

	public WeightedGraph() {
		super(WeightedNode.class);
	}
	
	public void createEdge(WeightedNode<N> n, WeightedNode<N> m, N value) {
		createEdge(n, m);
		n.setEdgeWeight(m, value);
		m.setEdgeWeight(n, value);
	}
	
	public void setEdgeWeight(WeightedNode<N> n, WeightedNode<N> m, N value) {
		n.setEdgeWeight(m, value);
		m.setEdgeWeight(n, value);
	}
	
	public N getEdgeWeight(WeightedNode<N> n, WeightedNode<N> m) {
		return n.getEdgeWeight(m);
	}
	
}
