package moorea.maths.lambda;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import moorea.maths.lambda.functions.IdentityMapper;

/**
 * Provides support for functionnal operations.
 * 
 * The main purpose of this class is to use SemiRing object to perform computation
 * using highly reusable algorithms.
 * 
 * Be warned that reusability comes at a price: computations using loop abstraction
 * (i.e. lambda expression and a mapper or reducer) are about 3 times slower (like scala!)
 * than explicit iteration with non reusable code.
 * 
 * @author nicolas
 *
 */

public class FunctionalAlgorithms {

	// iteration over list without modification (~ map without modification)
	
	public static <K> void iterate(List<K> list, Lambda<K,?> func) {
		for(K e : list) {
			func.apply(e);
		}
	}

	public static <K> void iterate(Iterator<K> it, Lambda<K,?> func) {
		while(it.hasNext()) {
			K e = it.next();
			func.apply(e);
		}
	}

	// map, reduce with lists
	
	public static <K,L> List<L> map(List<K> list, Lambda<K,L> func) {
		List<L> ml = new LinkedList<L>();
		for(K e : list) {
			ml.add(func.apply(e));
		}
		return ml;
	}

	public static <K> K reduce(List<K> list, Lambda<K,K> func) {
		K r = null;
		for(K e : list) {
			r = func.apply(e);
		}
		return r;
	}
	
	// map, reduce with iterators
	
	public static <K,L> List<L> map(Iterator<K> it, Lambda<K,L> func) {
		List<L> ml = new LinkedList<L>();
		while(it.hasNext()) {
			K e = it.next();
			ml.add(func.apply(e));
		}
		return ml;
	}
	
	public static <K> K reduce(Iterator<K> it, Lambda<K,K> func) {
		K r = null;
		while(it.hasNext()) {
			K e = it.next();
			r = func.apply(e);
		}
		return r;
	}

	// arg map

	// arg reduce : returns (arg_red, red_val)
	
	// for reducer that selects a particular value in a list,
	// in order to know how to access to the value, and not only the value
	
	// (normal iterator/reducer : get value)
	// iterator : [ K ]
	// lambda : K-> K

	// (impossible)
	// XEntryMapper : [ K -> (A,K) ]
	// XEntryMapper<A> : K -> (A,K)
	// FunctionalAlgorithms.map( (List<K>) l , new IndexMapper())
	
	// (entry iterator/reducer : get value and "index")
	// XEntryIterator : [ K -> (A,K) ] 
	// XEntryLambda : [ (A,K) -> (A,K) ]
	// OpEntryReducer : [ (A,K) ] -> (A,K)

	// so this is done throught the regular map and reduce function, but with special purpose iterators and reducers

	// iterator to list
	
	public static <K> List<K> listFromIterator(Iterator<K> it) {
		return FunctionalAlgorithms.map(it, new IdentityMapper<K>());
	}
	
	// border effects operations
	
	//public static <K,L,D> void iterate(List<K> list, BorderEffectLambda<K,L,D> f, D data) {
	//	iterate()
	//}
}
