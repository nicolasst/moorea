package moorea.maths.objects;

//TODO: determine if this class is needed

/**
 * 
 * @author nicolas
 *
 * @param <V>
 * @param <K>
 */

public interface ModificableFunction<V extends Variable, K> {

	abstract public K getValue(Assignment<V, K> a);
	
}
