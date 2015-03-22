package moorea.maths.objects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

 /** 
  * an assignment maps the object variable reference (not its id) to a value
  * 
  * V : type of variable
  * N : type of value assigned to the variable

 there is a string code representation for assignment
 e.g. 0:12;1:3;
 which makes use of the variable id to assign a value

 given a list of variable reference, it is possible to switch between both representations

 * @author nicolas
 *
 * @param <V>
 * @param <N>
 */

public class Assignment<V extends Variable, N> extends HashMap<V,N> {
	
	/** builds an assignement from a string representation */
	
	public static <V extends Variable, N> Assignment<V, N> stringCodeToAssignement(String a, List<V> lv) {
		Assignment<V, N> aa = new Assignment<>();
		String[] allAssignements = a.split(";");
		Map<Integer,V> mapIdToVariable = new HashMap<>();
		for(V v : lv) {
			mapIdToVariable.put(v.id, v);
		}
		for(int i=0;i<allAssignements.length;i++) {
			String[] singleAssignement = allAssignements[i].split(":");
			int varId = Integer.parseInt(singleAssignement[0]);
			V v = mapIdToVariable.get(varId);
			N val = null;
			if(v instanceof DiscreteVariable) {
				val = (N) (Object) Integer.parseInt(singleAssignement[1]);
			}
			aa.put(v,val);
		}
		return aa;
	}
	
	/** constructs a string representation from an assignment */
	
	public static String assignementToStringCode(Assignment<? extends Variable, ?> a) {
		String as = "";
		List<Variable> lv = new LinkedList<Variable>();
		lv.addAll(a.keySet());
		Variable.sortVariablesListAccordingToIncreasingId(lv);
		for(Variable v : lv) {
			as += v+":"+a.get(v)+";";
		}
		return as;
	}

	/** reduce an assignment to certain variables only, Assignment representation */
	
	public static Assignment truncateAssignementToScope(Assignment<? extends Variable,?> a, List<? extends Variable> scope) {
		Assignment aa = new Assignment();
		aa.putAll(a);
		for(Variable v : a.keySet()) {
			if(!scope.contains(v)) {
				aa.remove(v);
			}
		}
		return aa;
	}

	/** reduce an assignment to certain variables only, string representation */
	
	public static String truncateAssignementToScope(String a, List<Variable> lv, List<Variable> scope) {
		String r ="";
		Assignment aa = stringCodeToAssignement(a, lv);
		aa = truncateAssignementToScope(aa, scope);
		return assignementToStringCode(aa);
	}
	
	/** merge a list of assignments */
	
	public static Assignment mergeAssignementList(List<Assignment> la) {
		Assignment r = new Assignment();
		for(Assignment a : la) {
			mergeAssignements(r, a);
		}
		return r;
	}

	/** merge two assignments
	 * error if assignement are incompatible */

	public static Assignment mergeAssignements(Assignment assignementToModify, Assignment assignementToIncorporate) {
		for(Object v : assignementToIncorporate.keySet()) {
			if(assignementToModify.get(v) != null && assignementToIncorporate.get(v) != assignementToModify.get(v)) {
				System.err.println("incompatible key values: "+v);
				System.err.println(assignementToIncorporate);
				System.err.println(assignementToModify);
				System.exit(1);
			}
			assignementToModify.put(v, assignementToIncorporate.get(v));
		}
		return assignementToModify;
	}
	

	/** pretty print an assignment */
	
	public static String prettyPrint(Assignment a) {
		List<Variable> lv = new LinkedList();
		lv.addAll(a.keySet());
		Variable.sortVariablesListAccordingToIncreasingId(lv);
		String s = "{";
		for(int i=0;i<lv.size();i++) {
			Variable v = lv.get(i);
			s+= ""+v+"="+a.get(v)+((i<lv.size()-1)?",":"");
		}
		s+="}";
		return s;
	}
	
	public String toString() {
		return prettyPrint(this);
	}
	
	
	public static void unitTest() {
		Variable v1 = new Variable(0);
		Variable v2 = new Variable(1);
		List<Variable> lv = new LinkedList();
		lv.add(v1);
		lv.add(v2);
		Assignment<Variable, Integer> a = new Assignment<Variable, Integer>();
		a.put(v1, 12);
		a.put(v2, 3);
		System.out.println(a);
		
		String as = assignementToStringCode(a);
		System.out.println(as);
		
		Assignment<Variable, Integer> a2 = stringCodeToAssignement(as, lv);
		System.out.println(a2);
		
		Variable v3 = new Variable(2);
		Assignment a3 = new Assignment();
		a3.put(v2, 3);
		a3.put(v3, 5);
		List<Assignment> la = new LinkedList();
		la.add(a);
		la.add(a3);
		Assignment ma = mergeAssignementList(la);
		System.out.println(ma);
		
		/* expected output:
		{0=12,1=3}
		0:12;1:3;
		{0=12,1=3}
		{0=12,1=3,2=5}
		*/
	}
	
	public static void main(String[] args) {
		unitTest();
	}

}
