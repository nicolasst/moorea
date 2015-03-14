package moorea.maths.algebra;

import java.util.Comparator;

/**
 * 
 * @author nicolas
 *
 * @param <N>
 */

public class SetComparator<N> implements Comparator<N> {

	Class<N> fieldClass;
	
	public SetComparator(Class<N> fieldClass) {
		this.fieldClass = fieldClass;
	}

	@Override
	public int compare(N arg0, N arg1) {
		if(fieldClass == Integer.class) {
			return ((Integer) arg0).intValue() - ((Integer) arg1).intValue();
		}
		else if(fieldClass == Double.class) {
			/*double diff = ((Double) arg0).doubleValue() - ((Double) arg1).doubleValue();
			if(diff <0) {
				return -1;
			} else if(diff >0) {
				return 1;
			} else {
				return 0;
			}*/
			return ((Double)arg0).compareTo((Double)arg1);
		}
		else if(fieldClass == Boolean.class) {
			return ((Boolean)arg0).compareTo((Boolean)arg1);
		} else {
			System.err.println("error: unsuported field comapraison");
			System.exit(1);
		}
		return 0;
	}

}
