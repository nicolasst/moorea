package moorea.maths.hypermatrix;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import moorea.maths.objects.Assignment;
import moorea.maths.objects.DiscreteVariable;
import moorea.maths.random.ParametrizedRandomGenerator;
import moorea.maths.random.UniformRandomBooleanGenerator;
import moorea.maths.random.UniformRandomDoubleGenerator;
import moorea.maths.random.UniformRandomIntegerGenerator;

/**
 * 
 * @author nicolas
 *
 * @param <V>
 * @param <K>
 */

public class HyperMatrixFactory<V extends DiscreteVariable, K> {

	Class<V> variableClass;
	Class<K> elementClass;
	
	ParametrizedRandomGenerator<K> prng;
	
	public HyperMatrixFactory(Class<V> cv, Class<K> ck) {
		variableClass = cv;
		elementClass = ck;
	}
	
	public HyperMatrixFactory(Class<K> ck) {
		variableClass = (Class<V>) (Object) DiscreteVariable.class;
		elementClass = ck;
	}
	
	public void setPRNG(ParametrizedRandomGenerator<K> prng) {
		this.prng = prng;
	}
	
	//
	
	public HyperMatrix<DiscreteVariable, K> generateRandomHyperMatrix(List<DiscreteVariable> scope) {
		HyperMatrix<DiscreteVariable, K> hm = new HyperMatrix<>(scope, elementClass);
		DiscreteVariableScopeAssignementIterator ai = new DiscreteVariableScopeAssignementIterator((List<DiscreteVariable>)scope);
		while(ai.hasNext()) {
			Assignment<DiscreteVariable, Integer> a = ai.next();
			K value = prng.generateNext();
			hm.values.put(Assignment.assignementToStringCode(a), value);
		}
		return hm;
	}
	
	public List<HyperMatrix<DiscreteVariable, K>> generateRandomHyperMatrixListFromScopeList(List<List<DiscreteVariable>> l) {
		
		LinkedList<HyperMatrix<DiscreteVariable, K>> subFunctions = new LinkedList<>();
		
		// generate random hm subfunctions
		for(List<DiscreteVariable> lv : l) {
			HyperMatrix<DiscreteVariable, K> hm = generateRandomHyperMatrix(lv);
			subFunctions.add(hm);

			System.out.println("new hm:");
			HyperMatrixAlgorithms.disp(hm);
		}
		
		return subFunctions;
	}
	
	public static void unitTest() {

		DiscreteVariable v1 = new DiscreteVariable(0, 2);
		DiscreteVariable v2 = new DiscreteVariable(1, 2);
		List<DiscreteVariable> scope = new LinkedList<>();
		scope.add(v1);
		scope.add(v2);
		
		HyperMatrixFactory<DiscreteVariable, Integer> hmf = new HyperMatrixFactory<>(Integer.class);
		hmf.setPRNG(new UniformRandomIntegerGenerator(new Random(), 0, 100));
		
		HyperMatrix<DiscreteVariable, Integer> hm = hmf.generateRandomHyperMatrix(scope);
		
		HyperMatrixAlgorithms.disp(hm);
	}
	
	public static void main(String[] args) {
		unitTest();
	}
	
}
