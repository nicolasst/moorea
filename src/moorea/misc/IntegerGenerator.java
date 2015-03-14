package moorea.misc;

import java.util.Iterator;

/**
 * TODO: remove an update reference to moorea.maths.random
 * 
 * @author nicolas
 *
 */

public class IntegerGenerator implements Iterator<Integer> {

	int value;
	
	public IntegerGenerator() {
		value = 0;
	}
	
	public IntegerGenerator(int startVal) {
		value = startVal;
	}
	
	public Integer getCurrentValue() {
		return value;
	}
	
	public boolean hasNext() {
		return true;
	}

	public Integer next() {
		Integer val = new Integer(value++);
		return val;
	}

	public void remove() {
		// TODO Auto-generated method stub
	}

}
