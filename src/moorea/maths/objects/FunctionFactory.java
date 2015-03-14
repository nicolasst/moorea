package moorea.maths.objects;

// TODO: determine if this class is needed

/*

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FunctionFactory<F extends Function<V, N>, V extends Variable, N extends Number> {

	List<V> allVariables = new LinkedList<V>();
	
	Class<F> functionClass;
	Class<V> variableClass;
	Class<N> numberClass;
	
	int variableIdGen=0;
	
	public Random r;
	public ParametrizedRandomGenerator prng;

	// for Discrete Variables only
	int variablesCardinality = 2;
	
	public FunctionFactory(Class<F> cf, Class<V> cv, Class<N> nc, int nbVar) {
		functionClass = cf;
		variableClass = cv;
		numberClass = nc;
		for(int i=0;i<nbVar;i++) {
			V v = createVariable();
			allVariables.add(v);
		}
	}
	
	public FunctionFactory(Class<F> cf, Class<V> cv, Class<N> nc, int nbVar, int vCard) {
		functionClass = cf;
		variableClass = cv;
		numberClass = nc;
		variablesCardinality = vCard;
		for(int i=0;i<nbVar;i++) {
			V v = createVariable();
			allVariables.add(v);
		}
	}

	
	public FunctionFactory(Class<F> cf, Class<V> cv, Class<N> nc, List<V> lv) {
		functionClass = cf;
		variableClass = cv;
		numberClass = nc;
		allVariables.addAll(lv);
	}
	
	public void setRandom(Random r) {
		this.r = r;
	}
	
	public void setParametrizedRandomGenerator(ParametrizedRandomGenerator prng) {
		this.prng = prng;
	}
	
	public List<V> getVariables() {
		return allVariables;
	}
	
	public V createVariable() {
		V v = null;
		try {
			v = variableClass.getConstructor(new Class[] {Integer.class}).newInstance(new Object[]{variableIdGen++});
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		if(v instanceof DiscreteVariable) {
			((DiscreteVariable) v).setCardinality(variablesCardinality);
		}
		return v;
	}
	
	public F createFunction(List<V> scope) {
		F f = null;
		try {
			f = functionClass.getDeclaredConstructor(new Class[] {List.class, Class.class}).newInstance(new Object[]{scope, numberClass});
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return f;
	}
	
	public static <KF extends Function, KN extends Number> KF createFunction(Class<KF> cf, Class<KN> cn, List<? extends VariableNode> scope) {
		KF f = null;
		try {
			f = cf.getDeclaredConstructor(new Class[] {List.class, Class.class}).newInstance(new Object[]{scope, cn});
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return f;
	}
	
	public static <N extends Number> DiscreteFunction<N> generateDiscreteFunctionFromScopeAndGenerateRandomTable(FunctionFactory<DiscreteFunction<N>, DiscreteVariable, N> ff, List<DiscreteVariable> scope) {
		DiscreteFunction f = (DiscreteFunction) ff.createFunction(scope);
		Map<String, Integer> ti = (Map<String,Integer>) ff.generateDiscreteFunctionTable(scope);
		f.setValues(ti);
		return f;
	}
	
	public List<V> generateFunctionScope_FixedSizeRandUniformValues(int scopeSize) {
		assert(scopeSize <= allVariables.size());
		List<V> lv = new LinkedList<V>();
		int nbV = 0;
		while(nbV < scopeSize) {
			V v = allVariables.get(HelperClass.genInt(r,allVariables.size()));
			if(!lv.contains(v)) {
				lv.add(v);
				nbV++;
			}
		}
		//CollectionsUtils.sortAccordingToArbitraryOrder(lv, allVariables);
		FunctionAlgorithms.sortVariablesListAccordingToIncreasingId(lv);
		return lv;
	}
	
	public List<V> generateFunctionScope_indexList(int[] t) {
		List<V> lv = new LinkedList<V>();
		for(int i=0;i<t.length;i++) {
			lv.add(allVariables.get(t[i]));
		}
		FunctionAlgorithms.sortVariablesListAccordingToIncreasingId(lv);
		return lv;
	}
	
	public List<F> generateFunctionList_FixedSizeRandUniformValues(int nbF, int scopeSize) {
		List<F> lf = new LinkedList<F>();
		for(int i=0;i<nbF;i++) {
			lf.add(createFunction(generateFunctionScope_FixedSizeRandUniformValues(scopeSize)));
		}
		return lf;
	}
	
	public List<List<V>> generateFunctionScopeList_FixedSizeRandUniformValues(int nbF, int scopeSize) {
		List<List<V>> lf = new LinkedList<List<V>>();
		for(int i=0;i<nbF;i++) {
			lf.add(generateFunctionScope_FixedSizeRandUniformValues(scopeSize));
		}
		return lf;
	}
	
	public List<F> generateFunctionListFromScopeList(List<List<V>> scopes) {
		List<F> lf = new LinkedList<F>();
		for(List<V> lv : scopes) {
			lf.add(createFunction(lv));
		}
		return lf;
	}
	
	public <F extends Function> List<ProbabilityPotential> fillFunctionList_ProbabilityPotential(List<F> lf) {
		return fillFunctionList_ProbabilityPotential(lf, prng);
	}
	
	public static <F extends Function> List<ProbabilityPotential> fillFunctionList_ProbabilityPotential(List<F> lf, ParametrizedRandomGenerator<? extends Number> prng) {
		List<ProbabilityPotential> lp = new LinkedList<ProbabilityPotential>();
		for(Function f : lf) {
			ProbabilityPotential p = new ProbabilityPotential(f.scope,Double.class);
			Map<String, Double> m = (Map<String,Double>) FunctionFactory.generateDiscreteFunctionTable(f.scope,prng);
			p.setValues(m); 
		}
		return lp;
	}
	
	public <F extends DiscreteFunction> void fillFunctionListWithRandomTable(List<F> lf) {
		fillFunctionListWithRandomTable(lf,prng);
	}
	
	public static <F extends DiscreteFunction> void fillFunctionListWithRandomTable(List<F> lf, ParametrizedRandomGenerator<? extends Number> prng) {
		for(F f : lf) {
			Map<String, Double> m = (Map<String,Double>) FunctionFactory.generateDiscreteFunctionTable(f.scope,prng);
			f.setValues(m); 
		}
	}

	public static <F extends DiscreteFunction> void fillFunctionWithRandomTable(F f, ParametrizedRandomGenerator<? extends Number> prng) {
		Map<String, Double> m = (Map<String,Double>) FunctionFactory.generateDiscreteFunctionTable(f.scope,prng);
		f.setValues(m);
	}
	
	public Map<String, ? extends Number> generateDiscreteFunctionTable(List<DiscreteVariable> scope) {
		return generateDiscreteFunctionTable(scope, prng);
	}
	
	public static Map<String, ? extends Number> generateDiscreteFunctionTable(List<DiscreteVariable> scope, ParametrizedRandomGenerator<? extends Number> prng) {
		Map<String, Number> m = new HashMap<String, Number>();
		int[] assignements = new int[scope.size()];
		int[] cardinalities = new int[scope.size()];
		int i=0;
		//System.out.println(scope);
		for(DiscreteVariable v : scope) {
			cardinalities[i++] = v.getCardinality();
		}
		boolean stop=false;
		while(!stop) {
			String a = "";
			for(i=0;i<scope.size();i++) {
				a += ""+scope.get(i)+":"+assignements[i]+";";
			}
			m.put(a,prng.generateNext());
			//System.out.println(a);
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
		}
		return m;
	}
	
	public static <K extends Function> void displayFunctionList(List<K> lf) {
		for(K f : lf) {
			f.disp();
		}
	}
	
	public ConstantFunction<V,N> generateConstantFunction(N v) {
		ConstantFunction<V, N> cf = new ConstantFunction<V, N>(variableClass,numberClass,v);
		return cf;
	}
	
	public static <V extends Variable> ConstantFunction<V,Integer> generateConstantIntegerFunction(Class<V> ck, int v) {
		ConstantFunction<V, Integer> cf = new ConstantFunction<V, Integer>(ck, Integer.class,v);
		return cf;
	}
	
	//public static <V extends DiscreteVariable> DiscreteConstantFunction<V,Integer> generateConstantIntegerDiscreteFunction(int v) {
	public static DiscreteConstantFunction<Integer> generateConstantIntegerDiscreteFunction(int v) {
		//ConstantFunction<V, Integer> cf = new ConstantFunction<V, Integer>((Class<V>)DiscreteVariable.class, Integer.class,v);
		DiscreteConstantFunction<Integer> cf = new DiscreteConstantFunction<Integer>(Integer.class,v);
		return cf;
	}
	
	//public static <V extends VariableNode> ConstantFunction<V,Integer> generateConstantIntegerFunction(List<V> scope, Class<V> ck, int v) {
	public static DiscreteConstantFunction<Integer> generateConstantIntegerDiscreteFunction(List<DiscreteVariable> scope, int v) {
		DiscreteConstantFunction<Integer> cf = new DiscreteConstantFunction<Integer>(Integer.class, v, scope);
		return cf;
	}

	//public static <V extends VariableNode> ConstantFunction<V,Integer> generateConstantIntegerFunction(List<V> scope, Class<V> ck, int v) {
	public static <N extends Number> DiscreteConstantFunction generateConstantDiscreteFunction(List<DiscreteVariable> scope, Class<N> ck, N val) {
		DiscreteConstantFunction<Integer> cf = new DiscreteConstantFunction(ck, val, scope);
		return cf;
	}

	public ConditionalDiscreteFunction generateConditionalTable(List<DiscreteVariable> conditionedScope, List<DiscreteVariable> conditioningScope) {
		ConditionalDiscreteFunction<N> cf = new ConditionalDiscreteFunction<N>(conditionedScope, conditioningScope, numberClass);
		fillFunctionWithRandomTable(cf, prng);
		return cf;
	}
	
	public ConditionalDiscreteFunction generateNormalisedConditionalProbabilityTable(List<DiscreteVariable> conditionedScope, List<DiscreteVariable> conditioningScope) {
		ConditionalDiscreteFunction<N> cf = new ConditionalDiscreteFunction<N>(conditionedScope, conditioningScope, numberClass);
		fillFunctionWithRandomTable(cf, prng);
		FunctionAlgorithms.normaliseConditionalDiscreteFunction(cf);
		return cf;
	}
	
	public static void main(String[] args) {
		// FunctionFactory instanciation
		FunctionFactory<DiscreteFunction<Integer>,DiscreteVariable,Integer> ff = new FunctionFactory(DiscreteFunction.class,DiscreteVariable.class,Integer.class,10);
		
		// scope generation
		List<DiscreteVariable> s = ff.generateFunctionScope_FixedSizeRandUniformValues(3);
		System.out.println(s);

		// function from scope generation
		DiscreteFunction<Integer> f = ff.createFunction(s);
		System.out.println(f);
		
		// initialise randomness
		ff.r = new Random();
		ParametrizedRandomGenerator.setSharedRandomGenerator(ff.r);

		// test random int table generation
		ff.prng = new UniformRandomIntegerGenerator(0,100);
		
		Map<String, Integer> ti = (Map<String,Integer>) ff.generateDiscreteFunctionTable(s);
		System.out.println(ti);
		f.setValues(ti);
		
		// test random double table generation
		ff.prng = new UniformRandomDoubleGenerator(0.,3.);
		
		Map<String, Double> td = (Map<String,Double>) ff.generateDiscreteFunctionTable(s);
		System.out.println(td);
		
		// list of function scopes generation
		List<List<DiscreteVariable>> ls = ff.generateFunctionScopeList_FixedSizeRandUniformValues(4, 3);
		System.out.println(ls);
		
		// list of functions from list of function scopes generation
		List<DiscreteFunction<Integer>> lf = ff.generateFunctionListFromScopeList(ls);
		System.out.println(lf);
		
		// fill function list with values
		ff.fillFunctionListWithRandomTable(lf);
		System.out.println(lf);
		displayFunctionList(lf);
		
	}
}
*/
