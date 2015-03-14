package moorea.misc;

/**
 * 
 * Useful to return 3 arguments.
 *  
 * @author nicolas
 *
 * @param <K1>
 * @param <K2>
 * @param <K3>
 */

public class Tupple3<K1, K2, K3> {

	public K1 first;
	public K2 second;
	public K3 third;
	
	public Tupple3(K1 o1, K2 o2, K3 o3) {
		first = o1;
		second = o2;
		third = o3;
	}
	

	public static <K> K getFirst(Tupple3<K,?, ?> t) {
		return t.first;
	}
	
	public static <K> K getSecond(Tupple3<?, K, ?> t) {
		return t.second;
	}

	public static <K> K getThird(Tupple3<?, ?, K> t) {
		return t.third;
	}

}
