package moorea.maths.objects;

import java.util.LinkedList;
import java.util.List;

import moorea.maths.functions.DiscreteFunctionIterator;
import moorea.maths.hypermatrix.HyperMatrix;
import moorea.maths.lambda.FunctionalAlgorithms;
import moorea.maths.lambda.functions.DisplayMapper;

/**
 * 
 * @author nicolas
 *
 */

public class TestMathObjects {

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
	}
	
	public static void main(String[] args) {
		unitTest();
	}
	
}
