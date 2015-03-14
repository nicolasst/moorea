package moorea.maths.lambda;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import moorea.maths.lambda.functions.DisplayMapper;
import moorea.maths.lambda.functions.lists.ListEntryIterator;
import moorea.maths.lambda.functions.numbers.AddMapper;
import moorea.maths.lambda.functions.numbers.MaxEntryReducer;
import moorea.maths.lambda.functions.numbers.MaxReducer;
import moorea.maths.lambda.functions.numbers.SumReducer;


/**
 * This class is used to test the functional programming primitives.
 */

public class TestLA {

	public static void main(String[] args) {
		
		// list generation
		System.out.println("list");
		List<Integer> list = new LinkedList<Integer>();
		int[] values = { 4, 5, 6, 3, 1, 7, 10 };
		for(int i=0;i <values.length; i++) {
			list.add(values[i]);
		}
		System.out.println(list);
		
		//
		Mapper<Integer,Integer> m = new AddMapper(1);
		Reducer<Integer> sumRed = new SumReducer<Integer>(0);
		Reducer<Integer> maxRed = new MaxReducer<Integer>(Integer.class);
		
		//
		System.out.println("map with AddMapper");
		List<Integer> l = FunctionalAlgorithms.map(list, m);
		System.out.println(l);
		
		//
		System.out.println("reduce with SumReducer");
		Integer v = FunctionalAlgorithms.reduce(list, sumRed);
		System.out.println("reduced list value : "+v);

		//
		System.out.println("reduce with MaxReducer");
		v = FunctionalAlgorithms.reduce(list, maxRed);
		System.out.println("reduced list value : "+v);

		// display
		System.out.println("map with DisplayMapper");
		FunctionalAlgorithms.map(list, new DisplayMapper(" "));
		FunctionalAlgorithms.map(list, new DisplayMapper(", "));
		FunctionalAlgorithms.map(list, new DisplayMapper("\n"));
		
		// entry iterator, entry reducer
		System.out.println("reducer with EntryIterator and EntryReducer");
		Entry<Integer,Integer> e = FunctionalAlgorithms.reduce(new ListEntryIterator<Integer>(list), new MaxEntryReducer<Integer, Integer>(Integer.class));
		System.out.println(e);
	}
}
