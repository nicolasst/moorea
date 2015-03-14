package moorea.maths.lambda.functions.lists;

import java.util.LinkedList;
import java.util.List;

import moorea.maths.lambda.Reducer;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class ListConcatenerReducer<K> extends Reducer<List<K>> {

	public ListConcatenerReducer() {
		value = new LinkedList<K>();
	}
	
	public List<K> apply(List<K> e) {
		value.addAll(e);
		return value;
	}

}
