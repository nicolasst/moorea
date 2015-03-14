package moorea.graphs.traversal;

import moorea.graphs.Node;
import moorea.maths.lambda.Lambda;

/**
 * 
 * @author nicolas
 *
 * @param <V>
 */

public class GraphNodeIteratorAdHoc<V extends Node> extends GraphNodeIterator<V> {

	Lambda<?, Node> nextFunction;
	Lambda<?,Boolean> terminationCondition;
	
	public GraphNodeIteratorAdHoc(Lambda<?, Node> nextFunction, Lambda<?,Boolean> terminationCondition) {
		this.nextFunction = nextFunction;
		this.terminationCondition = terminationCondition;
	}
	
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public V next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
