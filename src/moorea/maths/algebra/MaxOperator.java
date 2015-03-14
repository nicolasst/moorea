package moorea.maths.algebra;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class MaxOperator<K extends Number> extends Operator<K> {

	public MaxOperator(Class<K> ck) {
		super(ck);
	}

	public K apply(K arg0, K arg1) {
		K value = null;
		if(arg0 instanceof Integer) {
			value = (K) (Integer) (Math.max(arg0.intValue(),arg1.intValue()));
		} else if (arg0 instanceof Double) {
			value = (K) (Double) (Math.max(arg0.doubleValue(),arg1.doubleValue()));
		}
		return value;

	}
}
