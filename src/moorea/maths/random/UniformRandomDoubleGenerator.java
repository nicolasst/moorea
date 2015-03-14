package moorea.maths.random;

import java.util.Random;

/**
 * 
 * @author nicolas
 *
 */

public class UniformRandomDoubleGenerator extends ParametrizedRandomGenerator<Double> {

	double min;
	double max;

	public UniformRandomDoubleGenerator(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
	public UniformRandomDoubleGenerator(Random r, double min, double max) {
		super(r);
		this.min = min;
		this.max = max;
	}

	@Override
	public Double generateNext() {
		return random.nextDouble()*(max-min)+min;
	}

}