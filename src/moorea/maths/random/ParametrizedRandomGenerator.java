package moorea.maths.random;

import java.util.Iterator;
import java.util.Random;

/**
 * 
 * @author nicolas
 *
 * @param <N>
 */

public abstract class ParametrizedRandomGenerator<N> implements Iterator<N> {

	Random random;
	
	static Random sharedRandomGenerator;
	
	ParametrizedRandomGenerator() {
		// assumes that sharedRandomGenerator is initialised elsewhere
		random = sharedRandomGenerator;
	}
	
	ParametrizedRandomGenerator(Random r) {
		random = r;
	}
	
	public static void setSharedRandomGenerator(Random r) {
		sharedRandomGenerator = r;
	}
	
	abstract public N generateNext();
	
	public boolean hasNext() {
		return true;
	}
	
	public N next() {
		return generateNext();
	}
	
	public void remove() {
		// 
	}
}
