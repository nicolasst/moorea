package moorea.maths.matrix;

import java.util.Iterator;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class MatrixValueIterator<K> implements Iterator<K> {

	K[][] array;
	int nbLines, nbCols;
	int i, j;
	
	public MatrixValueIterator(K[][] a) {
		array = a;
		nbLines = a.length;
		nbCols = a[0].length;
	}

	public K next() {
		K val = array[i][j];
		if(j==nbCols-1) {
			j=0;
			i++;
		} else {
			j++;
		}
		return val;
	}
	
	public boolean hasNext() {
		return i<nbLines;
	}

	public void remove() {
		// TODO Auto-generated method stub
	}

}
