package moorea.maths.algebra;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class ProductOperator<K extends Number> extends Operator<K> {

	public ProductOperator(Class<K> ck) {
		super(ck);
	}

	public K apply(K arg0, K arg1) {
		K value = null;
		if(arg0 instanceof Integer) {
			value = (K) (Integer) (arg0.intValue() * arg1.intValue());
		} else if (arg0 instanceof Double) {
			value = (K) (Double) (arg0.doubleValue() * arg1.doubleValue());
		}
		return value;

	}
}
