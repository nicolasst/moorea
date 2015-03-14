package moorea.maths.random;

/**
 * 
 * @author nicolas
 *
 */

public class ConstantIntegerGenerator extends ParametrizedRandomGenerator<Integer> {

	Integer value;
	
	public ConstantIntegerGenerator(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer generateNext() {
		return value;
	}

}
