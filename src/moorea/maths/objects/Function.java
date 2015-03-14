package moorea.maths.objects;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author nicolas
 *
 * @param <V>
 * @param <K>
 */

public abstract class Function<V extends Variable, K> {


	protected List<V> scope = new LinkedList<V>();
	
	public Class<V> domainClass;
	public Class<K> codomainClass;
	
	public Function() {
	}
	
	public Function(Class<K> cc) {
		codomainClass = cc;
	}
	
	public Function(Class<V> dc, Class<K> cc) {
		domainClass = dc;
		codomainClass = cc;
	}
	
	public Function(List<V> scope, Class<K> cc) {
		for(V v : scope) {
			this.scope.add(v);
		}
		codomainClass = cc;
	}
	
	public Function(List<V> scope, Class<V> dc, Class<K> cc) {
		domainClass = dc;
		for(V v : scope) {
			this.scope.add(v);
		}
		codomainClass = cc;
	}

	public void setClassesTypes(Class<V> dc, Class<K> cc) {
		domainClass = dc;
		codomainClass = cc;
	}

	// the only important method
	
	abstract public K getValue(Assignment<V, K> a);

	//abstract public K getValue(String a);
	
	public List<V> getScope() {
		return scope;
	}
	
	public void setScope(List<V> scope) {
		this.scope = scope;
	}
	
	public String toString() {
		return "scope: "+scope;
	}
	
	public void disp() {
		System.out.println(toString());
	}

	// TODO : check if this is still relevant
	//
	// needed when the variable of a function are created from other variables.
	// they logically represent the same variable,
	// but they are different objects and thus have different reference
	// and therefore cannot be compared directly.
	//
	
	/*
	public static <K1 extends VariableNode, K2 extends VariableNode> List<K2> translateScope(List<K1> lv, Map<K1,K2> map) {
		List<K2> lvt = new LinkedList<K2>();
		for(K1 k : lv) {
			lvt.add(map.get(k));
		}
		return lvt;
	} */
	
	/*
	public boolean equals(Function f) {
		if(this instanceof DiscreteFunction && f instanceof DiscreteFunction) {
			return ((DiscreteFunction) this).equals((DiscreteFunction) f);
		}
		// TODO implements other cases
		return false;
	}*/
	
}
