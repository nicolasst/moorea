package moorea.maths.lambda;

import java.util.List;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 * @param <L>
 */

public interface Lambda<K,L> {
	
	public abstract L apply(K e);

}
