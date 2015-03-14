package moorea.misc;

/**
 * 
 * Useful to return 2 arguments.
 * 
 * @author nicolas
 *
 * @param <K1>
 * @param <K2>
 */

public class Tupple2<K1, K2> {

	public K1 first;
	public K2 second;
	
	public Tupple2(K1 o1, K2 o2) {
		first = o1;
		second = o2;
	}
	
	public K1 getFirst() {
		return first;
	}
	

	public K2 getSecond() {
		return second;
	}
	
	// for imediate access to only one field of a tupple
	// typicallt when returning and only one field is of interest
	
	public static <K> K getFirst(Tupple2<K,?> t) {
		return t.first;
	}
	
	public static <K> K getSecond(Tupple2<?, K> t) {
		return t.second;
	}
}
