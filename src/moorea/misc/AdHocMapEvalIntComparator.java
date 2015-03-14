package moorea.misc;

import java.util.Comparator;
import java.util.Map;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class AdHocMapEvalIntComparator<K> implements Comparator<K> {

	Map<K,? extends Number> eval;
	
	public AdHocMapEvalIntComparator(Map<K,? extends Number> m) {
		eval = m;
	}
	
	public int compare(K n, K m) {
		return eval.get(n).intValue() - eval.get(m).intValue();
	}
}
