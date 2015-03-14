package moorea.maths.algebra;

import moorea.maths.lambda.Reducer;
import moorea.maths.lambda.functions.algebra.OperatorReducer;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class Monoid<K> {

	Class<K> setClass;

	Operator<K> operator;
	
	K neutralElement;
	
	//K minElement;
	//K maxElement;
	
	public static Monoid<Integer> integerSumMonoid = new Monoid(Integer.class, new SumOperator<>(Integer.class), 0);
	public static Monoid<Integer> integerProductMonoid = new Monoid(Integer.class, new ProductOperator(Integer.class), 1);
	public static Monoid<Integer> integerMinMonoid = new Monoid(Integer.class, new MinOperator<>(Integer.class), Integer.MAX_VALUE);
	public static Monoid<Integer> integerMaxMonoid = new Monoid(Integer.class, new MinOperator<>(Integer.class), Integer.MIN_VALUE);

	
	public Monoid(Class<K> sc, Operator<K> op, K ne) {
		setClass = sc;
		operator = op;
		neutralElement = ne;
	}

	public Monoid(Monoid<K> m) {
		setClass = m.setClass;
		operator = m.operator;
		neutralElement = m.neutralElement;
	}
	
	public Reducer<K> generateNewReducer() {
		//System.out.println(operator+" "+neutralElement);
		return new OperatorReducer<K>(operator,neutralElement);
	}
	
	//
	
	public Class<K> getSetClass() {
		return setClass;
	}

	public void setSetClass(Class<K> setClass) {
		this.setClass = setClass;
	}

	public Operator<K> getOperator() {
		return operator;
	}

	public void setOperator(Operator<K> operator) {
		this.operator = operator;
	}

	public K getNeutralElement() {
		return neutralElement;
	}

	public void setNeutralElement(K neutralElement) {
		this.neutralElement = neutralElement;
	}
	
	public String toString() {
		return "("+operator+","+neutralElement+")";
	}
	
	// TODO remove this function
	
	public int compareElements(K e1, K e2) {
		if(setClass == Integer.class) {
			int v1 = ((Integer)e1).intValue();
			int v2 = ((Integer)e2).intValue();
			if(v1 > v2) {
				return 1;
			} else if (v1 == v2) {
				return 0;
			} else {
				return -1;
			}
		} else if(setClass == Double.class) {
			double v1 = ((Double)e1).doubleValue();
			double v2 = ((Double)e2).doubleValue();
			if(v1 > v2) {
				return 1;
			} else if (v1 == v2) {
				return 0;
			} else {
				return -1;
			}
		}
		return -2;
	}
	
}
