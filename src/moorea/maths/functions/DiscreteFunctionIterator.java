package moorea.maths.functions;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import moorea.maths.objects.Assignment;
import moorea.maths.objects.DiscreteVariable;
import moorea.maths.objects.Function;
import moorea.maths.objects.Variable;

/**
 * 
 * @author nicolas
 *
 */

// TODO : use an DiscreteVariableScopeAssignementIterator instead (code duplication)

public class DiscreteFunctionIterator implements Iterator<String> {

	List<DiscreteVariable> scope;
	int[] assignements;
	int[] cardinalities;
	
	boolean[] isConditionalValue;

	public DiscreteFunctionIterator(Function<DiscreteVariable, ?> f) {
		this(f.getScope());
	}
	
	public DiscreteFunctionIterator(List<DiscreteVariable> scope) {
		this.scope = scope;
		assignements = new int[scope.size()];
		cardinalities = new int[scope.size()];
		isConditionalValue = new boolean[scope.size()];
		int i=0;
		for(DiscreteVariable v : scope) {
			cardinalities[i++] = v.getCardinality();
		}
	}

	public DiscreteFunctionIterator(List<DiscreteVariable> scope, Assignment<DiscreteVariable, Integer> conditionalAssignement) {
		this(scope);
		for(Variable v : conditionalAssignement.keySet()) {
			int index = scope.indexOf(v);
			// takes into account only the conditionnal value present in the scope
			if(index != -1) {
				isConditionalValue[index] = true;
				assignements[index] = conditionalAssignement.get(v);
			}
		}
	}
	
	boolean stop=false;
	
	public String next() {
		String a = "";
		for(int i=0;i<scope.size();i++) {
			a += ""+scope.get(i)+":"+assignements[i]+";";
		}
		boolean increment=true;
		int incrementIndex=scope.size()-1;
		while(increment) {
			/* correct version without conditionnal assignement
			if(assignements[incrementIndex] < cardinalities[incrementIndex]-1) {
				assignements[incrementIndex]++;
				increment=false;
			} else {
				assignements[incrementIndex] = 0;
				incrementIndex--;
			}
			*/
			if(!isConditionalValue[incrementIndex] && assignements[incrementIndex] < cardinalities[incrementIndex]-1) {
				assignements[incrementIndex]++;
				increment=false;
			} else if (isConditionalValue[incrementIndex]) {
				incrementIndex--;
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

	public boolean hasNext() {
		return !stop;
	}

	public void remove() {
		// TODO Auto-generated method stub
	}

	/*
	public static void main(String[] args) {
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

		
		// Test
		
		DiscreteFunctionIterator it = new DiscreteFunctionIterator(f);
		while(it.hasNext()) {
			String a = it.next();
			System.out.println(a+" "+f.getValue(a));
		}
	}
	*/
	
}
