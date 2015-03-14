package moorea.graphs.weighted;

import moorea.graphs.Edge;
import moorea.graphs.Node;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 * @param <N>
 */

public class WeightedEdge<K extends Node,N> extends Edge<K> {

	N weight;
	
	public WeightedEdge(K v1, K V2) {
		super(v1, V2);
	}

	public WeightedEdge(K v1, K V2, N weight) {
		super(v1, V2);
		this.weight = weight;
	}

	public void setWeight(N weight) {
		this.weight = weight;
	}
	
	public N getWeight() {
		return weight;
	}
}
