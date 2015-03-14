package moorea.maths.objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Variable is just an id.
 * 
 * Continuous and discrete variable need to extend this class.
 * 
 * @author nicolas
 *
 */

public class Variable {
	
	int id;
	
	public Variable(int id) {
		this.id = id;
	}

	
	public String toString() {
		return "V_"+id;
	}
	
	/**
	 * Sort a list of variables according to the increasing value of their id field.
	 * This function is mostly used to uniformise functions' scopes in order for an human to quickly read them.
	 */

	public static void sortVariablesListAccordingToIncreasingId(List<? extends Variable> lv)  {
		Map<Variable, Integer> eval = new HashMap<Variable, Integer>();
		for(Variable v : lv) {
			eval.put(v,v.id);
		}
		Collections.sort(lv,new moorea.misc.AdHocMapEvalIntComparator<Variable>(eval));
	}
	
}
