package moorea.misc.lists;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class ListCyclicInfiniteIterator<K> implements Iterator<K> {

	List<K> list;

	Random rand;
	
	int nbIterations = 0;
	int maxIterations;
	
	public ListCyclicInfiniteIterator(List<K> l) {
		list = l;
		maxIterations = -1;
		rand = new Random();
	}

	public ListCyclicInfiniteIterator(List<K> l, Random rand) {
		list = l;
		maxIterations = -1;
		this.rand = rand;
	}

	public ListCyclicInfiniteIterator(List<K> l, int maxIterations) {
		list = l;
		this.maxIterations = maxIterations;
	}
	
	public ListCyclicInfiniteIterator(List<K> l, Random rand, int maxIterations) {
		list = l;
		this.maxIterations = maxIterations;
		this.rand = rand;
	}
	
	public boolean hasNext() {
		if(maxIterations == -1 || nbIterations < maxIterations) {
			return true;
		}
		return false;
	}

	public K next() {
		K e = list.get(nbIterations);
		nbIterations = (nbIterations + 1) % list.size();
		return e;
	}
	
	public void remove() {
		// TODO Auto-generated method stub

	}

}
