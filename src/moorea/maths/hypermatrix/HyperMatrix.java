package moorea.maths.hypermatrix;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import moorea.maths.functions.DiscreteFunctionIterator;
import moorea.maths.lambda.FunctionalAlgorithms;
import moorea.maths.lambda.functions.DisplayMapper;
import moorea.maths.objects.Assignment;
import moorea.maths.objects.DiscreteVariable;
import moorea.maths.objects.Function;
import moorea.maths.objects.ModificableFunction;
import moorea.maths.objects.Variable;

/**
 * 
 * @author nicolas
 *
 * @param <V>
 * @param <K>
 */

public class HyperMatrix<V extends Variable, K> extends Function<V, K> implements ModificableFunction<V, K> {

	List<V> scope;
	
	Map<String, K> values = new HashMap<>();

	public HyperMatrix(List<V> scope, Class<K> cc) {
		super(scope, cc);
	}
	
	// function
	
	public K getValue(Assignment a) {
		return getValue(Assignment.assignementToStringCode(a));
	}

	public K getValue(String keyCode) {
		return values.get(keyCode);
	}
	
	// modificable function
	
	public void setValue(Assignment a, K val) {
		setValue(Assignment.assignementToStringCode(a),val);
	}

	public void setValue(String a, K val) {
		values.put(a,val);
	}
	
	
	
	public static void unitTest() {
		
		DiscreteVariable v1 = new DiscreteVariable(0,3);
		DiscreteVariable v2 = new DiscreteVariable(2,3);
		
		List<DiscreteVariable> scope = new LinkedList<>();
		scope.add(v1);
		scope.add(v2);
		
		HyperMatrix<DiscreteVariable, Integer> hm = new HyperMatrix<>(scope, Integer.class);
		
		System.out.println(scope);
		System.out.println(hm);

		FunctionalAlgorithms.map(new DiscreteFunctionIterator(hm.getScope()),new DisplayMapper("\n"));

		HyperMatrixAlgorithms.disp(hm);
		
	}
	
	public static void main(String[] args) {
		unitTest();
	}

	
}
