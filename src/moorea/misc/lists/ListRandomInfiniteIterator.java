package moorea.misc.lists;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import moorea.maths.random.UniformRandomIntegerGenerator;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class ListRandomInfiniteIterator<K> implements Iterator<K> {

	List<K> list;

	Random rand;
	
	int nbIterations = 0;
	int maxIterations;
	
	public ListRandomInfiniteIterator(List<K> l) {
		list = l;
		maxIterations = -1;
		rand = new Random();
	}

	public ListRandomInfiniteIterator(List<K> l, Random rand) {
		list = l;
		maxIterations = -1;
		this.rand = rand;
	}

	public ListRandomInfiniteIterator(List<K> l, int maxIterations) {
		list = l;
		this.maxIterations = maxIterations;
		this.rand = new Random();
	}
	
	public ListRandomInfiniteIterator(List<K> l, Random rand, int maxIterations) {
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
		K next = list.get(UniformRandomIntegerGenerator.genInt(rand, list.size()));
		nbIterations ++;
		return next;
	}

	public void remove() {
		// TODO Auto-generated method stub
	}

}
