package moorea.maths.lambda.functions.algebra;

import moorea.maths.lambda.Reducer;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class ProductReducer<K extends Number> extends Reducer<K> {
	
	public ProductReducer(Class<K> ck) {
		if(ck == Integer.class) {
			value = (K) (Integer) 1;
		} else if(ck == Double.class) {
			value = (K) (Double) 1.;
		} else {
			value = null;
		}
		initValue = value;
	}
	
	public ProductReducer(K initValue) {
		super(initValue);
	}
	
	public K apply(K e) {
		if(e instanceof Integer) {
			value = (K) (Integer) (value.intValue() * e.intValue());
		} else if (e instanceof Double) {
			value = (K) (Double) (value.doubleValue() * e.doubleValue());
		}
		return value;
	}

}
