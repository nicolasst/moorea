package moorea.maths.hypermatrix;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import moorea.maths.objects.Assignment;
import moorea.maths.objects.DiscreteVariable;
import moorea.maths.objects.Function;

/**
 * 
 * @author nicolas
 *
 */

public class DiscreteVariableScopeAssignementIterator implements Iterator<Assignment<DiscreteVariable,Integer>> {

	List<DiscreteVariable> scope;
	int[] assignements;
	int[] cardinalities;

	boolean[] isConditionalValue;

	public DiscreteVariableScopeAssignementIterator(Function<DiscreteVariable, ?> f) {
		this(f.getScope());
	}
	
	public DiscreteVariableScopeAssignementIterator(List<DiscreteVariable> scope) {
		this.scope = scope;
		assignements = new int[scope.size()];
		cardinalities = new int[scope.size()];
		int i=0;
		for(DiscreteVariable v : scope) {
			cardinalities[i++] = v.getCardinality();
		}
	}
	
	public DiscreteVariableScopeAssignementIterator(List<DiscreteVariable> scope, Assignment<DiscreteVariable, Integer> conditionalAssignement) {
		this(scope);
		isConditionalValue = new boolean[scope.size()];
		for(DiscreteVariable v : conditionalAssignement.keySet()) {
			int index = scope.indexOf(v);
			// takes into account only the conditionnal value present in the scope
			if(index != -1) {
				isConditionalValue[index] = true;
				assignements[index] = conditionalAssignement.get(v);
			}
		}
	}

	boolean stop=false;
	
	public Assignment next() {
		Assignment<DiscreteVariable, Integer> a = new Assignment<>();
		for(int i=0;i<scope.size();i++) {
			a.put(scope.get(i),assignements[i]);
		}
		boolean increment=true;
		int incrementIndex=scope.size()-1;
		while(increment) {
			if(isConditionalValue == null) {
				// correct version without conditional assignements
				if(assignements[incrementIndex] < cardinalities[incrementIndex]-1) {
					assignements[incrementIndex]++;
					increment=false;
				} else {
					assignements[incrementIndex] = 0;
					incrementIndex--;
				}
			} else {
				// correct version with conditional assignements
				if(!isConditionalValue[incrementIndex] && assignements[incrementIndex] < cardinalities[incrementIndex]-1) {
					assignements[incrementIndex]++;
					increment=false;
				} else if (isConditionalValue[incrementIndex]) {
					incrementIndex--;
				} else {
					assignements[incrementIndex] = 0;
					incrementIndex--;
				}
			}
			// common part
			if(incrementIndex==-1) {
				increment=false;
				stop=true;
			}
		}
		return a;
	}

	
/*	public Assignement<VariableNode, Integer> next() {
		Assignement<VariableNode, Integer> a = new Assignement<VariableNode, Integer>();
		for(int i=0;i<scope.size();i++) {
			a.put(scope.get(i),assignements[i]);
		}
		boolean increment=true;
		int incrementIndex=scope.size()-1;
		while(increment) {
			if(assignements[incrementIndex] < cardinalities[incrementIndex]-1) {
				assignements[incrementIndex]++;
				increment=false;
			} else {
				assignements[incrementIndex] = 0;
				incrementIndex--;
			}
			if(incrementIndex==-1) {
				increment=false;
				stop=true;
			}
		}
		return a;
	}
	*/

	public boolean hasNext() {
		return !stop;
	}

	public void remove() {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) {
		/*
		//
		FunctionFactory<DiscreteFunction<Integer>,DiscreteVariable,Integer> ff = new FunctionFactory(DiscreteFunction.class,DiscreteVariable.class, Integer.class, 10);

		// initialise randomness
		ff.r = new Random();
		ff.r.setSeed(0);
		ParametrizedRandomGenerator.setSharedRandomGenerator(ff.r);
		ff.prng = new UniformRandomIntegerGenerator(0,100);
		
		// scope generation
		List<DiscreteVariable> s = ff.generateFunctionScope_FixedSizeRandUniformValues(2);
		System.out.println(s);

		// function from scope generation
		DiscreteFunction<Integer> f = ff.generateDiscreteFunctionFromScopeAndGenerateRandomTable(ff, s);
*/
		DiscreteVariable v1 = new DiscreteVariable(0, 2);
		DiscreteVariable v2 = new DiscreteVariable(0, 2);
		List<DiscreteVariable> scope = new LinkedList<>();
		scope.add(v1);
		scope.add(v2);
		
		// Test
		
		DiscreteVariableScopeAssignementIterator it = new DiscreteVariableScopeAssignementIterator(scope);
		while(it.hasNext()) {
			Assignment a = it.next();
			String as = Assignment.assignementToStringCode(a);
			System.out.println(as);
		}
	}
	
}
