package moorea.maths.lambda.functions.numbers;

import java.util.AbstractMap;
import java.util.Map.Entry;

import moorea.maths.lambda.EntryReducer;

/**
 * 
 * @author nicolas
 *
 * @param <A>
 * @param <K>
 */

public class MaxEntryReducer<A,K extends Number> extends EntryReducer<A,K> {
	
	public MaxEntryReducer(Class<K> ck) {
		A arg = null;
		K value = null;
		if(ck == Integer.class) {
			value = (K) (Integer) Integer.MIN_VALUE;
		} else if(ck == Double.class) {
			value = (K) (Double) Double.MIN_VALUE;
		}
		this.value = new AbstractMap.SimpleEntry<A,K>(arg,value);
	}
	
	public MaxEntryReducer(K initValue) {
		super(initValue);
	}
	
	public Entry<A, K> apply(Entry<A,K> e) {
		A arg = e.getKey();
		K val = e.getValue();
		K kValue = value.getValue();
		if(kValue instanceof Integer) {
			if(val.intValue() > kValue.intValue()) {
				kValue = val;
			}
		} else if (kValue instanceof Double) {
			if(kValue.doubleValue() > kValue.doubleValue()) {
				kValue = val;
			}		
		}
		value = new AbstractMap.SimpleEntry<A, K>(arg,kValue);
		return value;
	}

}
