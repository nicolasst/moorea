package moorea.maths.lambda;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public abstract class Reducer<K> implements Lambda<K,K> {

	protected K initValue;
	protected K value;
	
	public Reducer() {
	}
	
	public Reducer(K initValue) {
		this.initValue = initValue;
		value = initValue;
	}
	
	public Reducer(Reducer<K> r) {
		initValue = r.initValue;
		value = initValue;
	}
	
	public Reducer reInitialiseValue() {
		value = initValue;
		return this;
	}
	
	public void setInitialValue(K val) {
		initValue = val;
	}
	}
