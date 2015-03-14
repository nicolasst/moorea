package moorea.maths.lambda.functions.algebra;

import moorea.maths.algebra.Operator;
import moorea.maths.lambda.Reducer;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class MonoidReducer<K> extends Reducer<K> {

	Operator<K> operator;
	
	public MonoidReducer(Operator<K> op, K neutralElement) {
		super(neutralElement);
	}
	
	public K apply(K e) {
		value = operator.apply(value,e);
		return value;
	}

}
