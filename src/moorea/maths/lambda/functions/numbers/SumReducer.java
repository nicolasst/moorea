package moorea.maths.lambda.functions.numbers;

import moorea.maths.lambda.Reducer;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */


public class SumReducer<K extends Number> extends Reducer<K> {

		
	public SumReducer(Class<K> ck) {
		assert(ck != null);
		if(ck == Integer.class) {
			value = (K) (Integer) 0;
		} else if(ck == Double.class) {
			value = (K) (Double) 0.;
		}
		initValue = value;
	}
	
	public SumReducer(K initValue) {
		super(initValue);
	}
	
	public K apply(K e) {
		if(e instanceof Integer) {
			value = (K) (Integer) (value.intValue() + e.intValue());
		} else if (e instanceof Double) {
			value = (K) (Double) (value.doubleValue() + e.doubleValue());
		}
		return value;
	}
	
	
	// This class is programmed on-purpose without using the value field of Reducer
	
	/*
	//K sum;
	Integer intSum;
	Double doubleSum;
	//Boolean boolSum;
		
	public SumReducer(Class<K> ck) {
		assert(ck != null);
		if(ck == Integer.class) {
			//value = (K) (Integer) 0;
			intSum = 0; //new Integer(0);
		} else if(ck == Double.class) {
			//value = (K) (Double) 0.;
			doubleSum = 0.; //new Double(0.);
		}		
	}
	
	public SumReducer(K initValue) {
		super(initValue);
		if(initValue instanceof Integer) {
			intSum = initValue.intValue();
		} else if(initValue instanceof Double) {
			doubleSum = initValue.doubleValue();
		}
	}
	
	public K apply(K e) {
		if(e instanceof Integer) {
			intSum = intSum + e.intValue();
			return (K) intSum;
		} else if (e instanceof Double) {
			doubleSum = doubleSum + e.doubleValue();
			return (K) doubleSum;
		}
		return null;
	}
	*/

}
