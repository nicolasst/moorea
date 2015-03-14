package moorea.maths.lambda;

import java.util.AbstractMap;
import java.util.Map.Entry;

/**
 * 
 * @author nicolas
 *
 * @param <A>
 * @param <K>
 */

public abstract class EntryReducer<A,K> extends Reducer<Entry<A,K>> {
	
	public EntryReducer() {
	}
	
	public EntryReducer(K initValue) {
		value = new AbstractMap.SimpleEntry<A,K>(null,initValue);
	}
	
	public abstract Entry<A,K> apply(Entry<A,K> e);
	
}
