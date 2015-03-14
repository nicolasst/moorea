package moorea.maths.random;

import java.util.Random;

/**
 * 
 * @author nicolas
 *
 */

public class GaussianRandomGenerator extends ParametrizedRandomGenerator<Double> {

	double mean;
	double variance;

	public GaussianRandomGenerator(double mean, double var) {
		this.mean = mean;
		variance = var;
	}
	
	public GaussianRandomGenerator(Random r, double mean, double var) {
		super(r);
		this.mean = mean;
		variance = var;
	}

	@Override
	public Double generateNext() {
		return random.nextDouble()*variance+mean;
	}
}
