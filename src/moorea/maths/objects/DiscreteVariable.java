package moorea.maths.objects;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represent discrete variable, which distinguish themselves
 * from other variable as they have a finite set of value over which
 * it is possible to iterate.
 * 
 * @author nicolas
 *
 */

public class DiscreteVariable extends Variable {
	
	int cardinality;
	
	// string representation of each value of the variable
	List symbols;
	
	public DiscreteVariable(Integer id) {
		super(id);
	}

	public DiscreteVariable(int id, int cardinality) {
		super(id);
		this.cardinality = cardinality;
		symbols = new LinkedList<>();
		for (int i = 0; i < cardinality; i++) {
			symbols.add(""+i);
		}
	}

	public DiscreteVariable(int id, List symbols) {
		super(id);
		this.cardinality = symbols.size();
		this.symbols = symbols;
	}
	
	//
	
	public int getCardinality() {
		return cardinality;
	}
	
	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}
	
	//
	
	public String toString() {
		return "DV_"+id;
	}
	
}
