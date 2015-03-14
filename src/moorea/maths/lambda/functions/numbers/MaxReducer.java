package moorea.maths.lambda.functions.numbers;

import moorea.maths.lambda.Reducer;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class MaxReducer<K extends Number> extends Reducer<K> {
	
	public MaxReducer(Class<K> ck) {
		if(ck == Integer.class) {
			initValue = (K) (Integer) Integer.MIN_VALUE;
		} else if(ck == Double.class) {
			initValue = (K) (Double) Double.MIN_VALUE;
		}
		value = initValue;
	}
	
	public MaxReducer(K initValue) {
		super(initValue);
	}
	
	public K apply(K e) {
		if(e instanceof Integer) {
			value = (K) (Integer) (Math.max(value.intValue(),e.intValue()));
		} else if (e instanceof Double) {
			value = (K) (Double) (Math.max(value.doubleValue(),e.doubleValue()));
		}
		return value;
	}

}
