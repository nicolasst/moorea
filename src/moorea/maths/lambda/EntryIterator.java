package moorea.maths.lambda;

import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author nicolas
 *
 * @param <A>
 * @param <K>
 */

public interface EntryIterator<A,K> extends Iterator<Map.Entry<A,K>> {
	
	public boolean hasNext();

	public Map.Entry<A, K> next();

	public void remove();
	
}
