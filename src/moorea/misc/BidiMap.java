package moorea.misc;

import java.util.HashMap;
import java.util.Map;

/**
 * Bidirectional map, with added convenient methods not present in apache commons.
 * 
 * @author nicolas
 *
 * @param <K>
 * @param <V>
 */

// convention when creating a new graph from the nodes of a previous graph :
// K : corresponds to the type of the nodes of the old graph
// V : corresponds to the type of the nodes of the new graph
// mapAB : maps from old nodes to new nodes
// mapBA : maps from new nodes to old nodes

public class BidiMap<K,V> {

	Map<K, V> mapAB = new HashMap<K, V>();
	Map<V, K> mapBA = new HashMap<V, K>();
	
	public BidiMap() {
	}
	
	public BidiMap(BidiMap<K,V> b) {
		Map<K,V> mab = b.getMapAB();
		for(K k : mab.keySet()) {
			mapAB.put(k, mab.get(k));
			mapBA.put(mab.get(k),k);
		}
	}
	
	public void put(K ka, V kb) {
		mapAB.put(ka, kb);
		mapBA.put(kb, ka);
	}
	
	public void remove(K ka, V kb) {
		mapAB.remove(ka);
		mapBA.remove(kb);
	}
	
	public V getAB(K key) {
		return mapAB.get(key);
	}	
	
	public K getBA(V key) {
		return mapBA.get(key);
	}
	
	public Map<K,V> getMapAB() {
		return mapAB;
	}
	
	public Map<V, K> getMapBA() {
		return mapBA;
	}
	
	public String toString() {
		return ""+mapAB+"\n"+mapBA;
	}
	
	public BidiMap<K,V> clone() {
		return new BidiMap(this);
	}
	
	// G<K1>   ->   G<K2>   ->   G<K3>
	//   BM<K1,K2>   ->   BM<K2,K3>
	// mapOldNew<?,K>    this<K,V>
	
	// returns a new BidiMap (no other border effect)
	
	public <K1> BidiMap<K1,V> getTranslatedBidiMap(BidiMap<K1,K> mapOldNew) {
		BidiMap<K1,V> bmOldOldNew = new BidiMap<K1, V>();
		for(K k : mapAB.keySet()) {
			K1 k1 = mapOldNew.getBA(k);
			bmOldOldNew.put(k1, mapAB.get(k));
		}
		return bmOldOldNew;
	}
	
	public static void unitTest() {
		String s1="a";
		String s2="b";
		BidiMap<Integer, String> bm = new BidiMap<Integer, String>();
		bm.put(1, s1);
		bm.put(2, s2);
		System.out.println(bm);
		BidiMap<Integer, String>bm2 = bm.clone();
		System.out.println(bm2);

	}
	
	public static void main(String[] args) {
		unitTest();
	}
	
}
