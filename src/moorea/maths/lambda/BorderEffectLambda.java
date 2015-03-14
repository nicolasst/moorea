package moorea.maths.lambda;

/**
 * This class is necessary when one wants to store a value accross iteration,
 * for instance the index of the max value in a list.
 * 
 * @author nicolas
 *
 * @param <K>
 * @param <L>
 * @param <D>
 */

public abstract class BorderEffectLambda<K,L,D> implements Lambda<K,L> {

	protected D data;
	protected Object[] args;
	
	public BorderEffectLambda() {
	}
	
	public BorderEffectLambda(D d, Object ... args) {
		data = d;
		this.args = args;
	}
	
	public abstract L apply(K e);

}
