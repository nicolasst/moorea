package moorea.maths.lambda.functions.algebra;

import moorea.maths.algebra.Operator;
import moorea.maths.lambda.Reducer;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class OperatorReducer<K> extends Reducer<K> {

	Operator<K> operator;
	
	public OperatorReducer(Operator<K> op, K initValue) {
		super(initValue);
		operator = op;
	}
	
	public K apply(K e) {
		value = operator.apply(value,e);
		return value;
	}

}
