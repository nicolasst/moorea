package moorea.maths.hypermatrix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import moorea.maths.algebra.Monoid;
import moorea.maths.algebra.Semiring;
import moorea.maths.lambda.Reducer;
import moorea.maths.objects.Assignment;
import moorea.maths.objects.DiscreteVariable;
import moorea.maths.objects.Variable;

/**
 * 
 * @author nicolas
 *
 */

public class HyperMatrixAlgorithms {

	/**
	 * Display an hypermatrix: assignments and values
	 * @param hm
	 */
	
	public static void disp(HyperMatrix hm) {
		System.out.println(hm);
		DiscreteVariableScopeAssignementIterator it = new DiscreteVariableScopeAssignementIterator(hm.getScope());
		while(it.hasNext()) {
			Assignment a = it.next();
			String as = Assignment.assignementToStringCode(a);
			System.out.println(as+"="+hm.getValue(a));
		}
	}
	
	/**
	 * Summarise an hypermatrix over a given scope.
	 * 
	 * @param sr
	 * @param newScope
	 * @param hm
	 * @return
	 */
	
	public static <K> HyperMatrix<DiscreteVariable, K> summarise(Reducer<K> reducer, List<DiscreteVariable> newScope, HyperMatrix<DiscreteVariable, K> hm) {
		
		// summarisedScope = f.scope \setminus finalScope
		List<DiscreteVariable> summarisedScope = new LinkedList<>(hm.getScope());
		summarisedScope.removeAll(newScope);
		
		// result hm creation
		HyperMatrix<DiscreteVariable, K> shm = new HyperMatrix<>(newScope, hm.codomainClass);
		
		// iterate over 'finalScope'
		DiscreteVariableScopeAssignementIterator it = new DiscreteVariableScopeAssignementIterator(newScope);
		
		// how to summarise over summarised scope
		//Reducer<K> reducer = sr.generateNewDotReducer();

		//
		while(it.hasNext()) {
			// next partial assignement
			Assignment<DiscreteVariable, Integer> a = it.next();

			// reinit the summariser instead of creating a new object
			reducer.reInitialiseValue();
			
			// get the summarised value
			K val = summariseDiscreteFunctionAssignement(hm,a,reducer);

			// set it in the result hm
			shm.setValue(Assignment.assignementToStringCode(a), val);
		}
		
		return shm;
	}
	
	/**
	 * Compute the summary value given a partial assignment.
	 * 
	 * @param f
	 * @param conditionalAssignement
	 * @param reducer
	 * @return
	 */
	
	public static <K> K summariseDiscreteFunctionAssignement(HyperMatrix<DiscreteVariable, K> f, Assignment<DiscreteVariable, Integer> conditionalAssignement, Reducer<K> reducer) {
		
		List<DiscreteVariable> scope = f.getScope();
		
		// iterate over 'scope' given 'conditionalAssignment'
		DiscreteVariableScopeAssignementIterator it = new DiscreteVariableScopeAssignementIterator(scope, conditionalAssignement); 

		//
		String argMax=null;
		K val = null;
		
		//
		while(it.hasNext()) {
			String a = Assignment.assignementToStringCode(it.next());
			val = reducer.apply(f.getValue(a));
		}
		
		return val;
	}
	
	
	public static <K> HyperMatrix<DiscreteVariable,K> mergeDiscreteFunctionsList(List<HyperMatrix<DiscreteVariable,K>> lf, Monoid<K> monoid) {
		return mergeDiscreteFunctionsList(lf, monoid.generateNewReducer());
	}
	
	public static <K> HyperMatrix<DiscreteVariable,K> mergeDiscreteFunctionsList(List<HyperMatrix<DiscreteVariable,K>> lhm, Reducer<K> reducer) {
		assert(lhm.size() > 0);
		Class<K> cn = lhm.get(0).codomainClass;
		// computed merged scoped
		List<DiscreteVariable> scope = new LinkedList<DiscreteVariable>();
		Set<DiscreteVariable> scopeVars = new HashSet<DiscreteVariable>(); 
		for(HyperMatrix<DiscreteVariable,K> f : lhm) {
			scopeVars.addAll(f.getScope());
		}
		scope.addAll(scopeVars);
		return mergeDiscreteFunctionsList(reducer, scope, lhm);
	}
	
	public static <K> HyperMatrix<DiscreteVariable,K> mergeDiscreteFunctionsList(Reducer<K> reducer, List<DiscreteVariable> scope, List<HyperMatrix<DiscreteVariable,K>> lhm) {

		if(lhm.size() == 0) {
			return null;
		}
		
		HyperMatrix<DiscreteVariable,K> mhm = new HyperMatrix<>(scope,lhm.get(0).codomainClass);
		
		DiscreteVariableScopeAssignementIterator it = new DiscreteVariableScopeAssignementIterator(scope);
		while(it.hasNext()) {
			Assignment<DiscreteVariable, Integer> a = it.next();
			K val = null;
			reducer.reInitialiseValue();
			for(HyperMatrix<DiscreteVariable,K> hm : lhm) {
				Assignment<DiscreteVariable, K> aReduced = Assignment.truncateAssignementToScope(a, hm.getScope());
				String sar = Assignment.assignementToStringCode(aReduced);
				val = reducer.apply(hm.getValue(sar));
			}
			mhm.setValue(Assignment.assignementToStringCode(a),val);
		}
		return mhm;
	}
	
}
