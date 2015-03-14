package moorea.maths.lambda.functions.algebra;

import java.util.AbstractMap;
import java.util.Map.Entry;

import moorea.maths.algebra.Monoid;
import moorea.maths.lambda.EntryReducer;

/**
 * 
 * @author nicolas
 *
 * @param <A>
 * @param <K>
 */

public class EntryPairMonoidReducer<A,K extends Number> extends EntryReducer<A,K> {

	Monoid<K> monoid;
	
	public EntryPairMonoidReducer(Monoid<K> m) {
		monoid = m;
	}
	// TODO attention tout est faux !!
	
	public Entry<A, K> apply(Entry<A,K> e) {
		A key = e.getKey();
		K val = e.getValue();
		K reducedValue = value.getValue();
		if(reducedValue instanceof Integer) {
			if(monoid.compareElements(val,reducedValue) > 0) {
				reducedValue = val;
			}
		}
		value = new AbstractMap.SimpleEntry<A, K>(key,reducedValue);
		return value;
	}

}
