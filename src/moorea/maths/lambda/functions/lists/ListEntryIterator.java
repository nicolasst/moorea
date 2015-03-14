package moorea.maths.lambda.functions.lists;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;

import moorea.maths.lambda.EntryIterator;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class ListEntryIterator<K> implements EntryIterator<Integer, K> {

	List<K> list;
	
	int i=0;
	
	public ListEntryIterator(List<K> l) {
		list = l;
	}
	
	public boolean hasNext() {
		return i < list.size();
	}

	public Entry<Integer,K> next() {
		Entry<Integer,K> next = new AbstractMap.SimpleEntry<Integer, K>(i,list.get(i));
		i++;
		return next;
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

}
