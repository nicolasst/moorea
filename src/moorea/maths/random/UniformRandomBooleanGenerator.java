package moorea.maths.random;

import java.util.Random;

/**
 * 
 * @author nicolas
 *
 */

public class UniformRandomBooleanGenerator extends ParametrizedRandomGenerator<Boolean> {

	double p;
	
	public UniformRandomBooleanGenerator(double p) {
		this.p = p;
	}
	
	public UniformRandomBooleanGenerator(Random r, double p) {
		super(r);
		this.p = p;
	}
	
	@Override
	public Boolean generateNext() {
		if(random.nextDouble() > p) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
