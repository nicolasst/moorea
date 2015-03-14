package moorea.misc;

import java.util.Comparator;
import java.util.Map;

/**
 * Compare objects based on a value given by a map.
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class AdHocMapEvalComparator<K> implements Comparator<K> {

	Map<K,Comparable> eval;
	
	public AdHocMapEvalComparator(Map<K,Comparable> m) {
		eval = m;
	}
	
	public int compare(K n, K m) {
		return eval.get(n).compareTo(eval.get(m));
	}
}