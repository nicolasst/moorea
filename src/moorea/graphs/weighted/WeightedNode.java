package moorea.graphs.weighted;


import java.util.HashMap;
import java.util.Map;

import moorea.graphs.Node;
import moorea.graphs.SimpleNode;

/**
 * 
 * @author nicolas
 *
 * @param <N>
 */

public class WeightedNode<N> extends SimpleNode<WeightedNode<N>> {

	public Map<Node,N> edgeWeights = new HashMap();
	
	public WeightedNode(Integer id) {
		super(id);
	}

	public void setEdgeWeight(Node n, N value) {
		edgeWeights.put(n,value);
	}
	
	public N getEdgeWeight(Node n) {
		return edgeWeights.get(n);
	}
	
	public String toString() {
		return "WN_"+id;
	}
}
