package moorea.maths.objects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import moorea.maths.random.RandomMisc;
import moorea.misc.IntegerGenerator;

/**
 * This class is used to create variables and scopes (list of variables).
 * These are to be further used to create functions.
 * 
 * @author nicolas
 *
 * @param <V>
 */

public class ScopeFactory<V extends Variable> {

	Class<V> variableClass;
	List<V> variables;
	
	IntegerGenerator varIdGen = new IntegerGenerator();
	
	Random random;
	
	public ScopeFactory(Class<V> cv) {
		variableClass = cv;
		variables = new LinkedList<>();
	}
	
	public void configureRandomness(Random r) {
		random = r;
	}
	
	// generate variables and lists of variables
	
	public V createVariable() {
		V v = null;
		try {
			v = variableClass.getConstructor(new Class[] {Integer.class}).newInstance(new Object[]{varIdGen.next()});
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		variables.add(v);
		return v;
	}

	public List<V> createVariables(int nb) {
		List<V> l = new LinkedList<>();
		for (int i = 0; i < nb; i++) {
			V v = createVariable();
			l.add(v);
		}
		return l;
	}
	
	public V createDiscreteVariable(int cardinality) {
		V v = null;
		if(variableClass != DiscreteVariable.class) {
			System.out.println("incompatible variable classes: "+variableClass+" "+DiscreteVariable.class);
			System.exit(1);			
		}
		try {
			v = variableClass.getConstructor(new Class[] {Integer.class}).newInstance(new Object[]{varIdGen.next()});
			((DiscreteVariable)v).setCardinality(cardinality);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		variables.add(v);
		return v;
	}
	
	public List<V> createDiscreteVariables(int nbVars, int cardinality) {
		List<V> l = new LinkedList<>();
		for (int i = 0; i < nbVars; i++) {
			V v = createDiscreteVariable(cardinality);
			l.add(v);
		}
		return l;
	}
	
	public List<V> createDiscreteVariables(int ... cardinality) {
		List<V> l = new LinkedList<>();
		for (int i = 0; i < cardinality.length; i++) {
			V v = createDiscreteVariable(cardinality[i]);
			l.add(v);
		}
		return l;
	}
	
	// sample variables from list
	
	public List<V> sampleScopeUniformRandom(int scopeSize) {
		assert(scopeSize <= variables.size());
		List<Integer> indices = RandomMisc.sampleIntegerList(random, scopeSize, 0, variables.size(), false);
		return sampleScopeIndexList(indices);
	}
	
	public List<V> sampleScopeIndexList(List<Integer> indices) {
		List<V> l = new LinkedList<V>();
		for(Integer idx : indices) {
			l.add(variables.get(idx));
		}
		return l;
	}

	public static <V> List<V> mergeScopes(List<List<V>> ll) {
		Set<V> allVars = new HashSet<>();
		for(List<V> l : ll) {
			for(V v : l) {
				allVars.add(v);
			}
		}
		return new LinkedList(Arrays.asList(allVars.toArray()));
	}
	
	public static void unitTest() {
		ScopeFactory<DiscreteVariable> sf = new ScopeFactory<>(DiscreteVariable.class);
		sf.configureRandomness(new Random());
		
		sf.createDiscreteVariables(10, 2);
		
		List<DiscreteVariable> scope = sf.sampleScopeUniformRandom(3);
		
		System.out.println(scope);
	}
	
	public static void main(String[] args) {
		unitTest();
	}
	
}
