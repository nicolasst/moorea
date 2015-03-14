package moorea.maths.algebra;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public abstract class Operator<K> {

	protected Class<K> setClass;
	
	public Operator(Class<K> sc) {
		setClass = sc;
	}
	
	public abstract K apply(K arg0, K arg1);
}
