package moorea.maths.random;

import java.util.Random;

/**
 * 
 * @author nicolas
 *
 */

public class UniformRandomIntegerGenerator extends ParametrizedRandomGenerator<Integer> {

	int min;
	int max;
	
	public UniformRandomIntegerGenerator(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public UniformRandomIntegerGenerator(Random r, int min, int max) {
		super(r);
		this.min = min;
		this.max = max;
	}

	
	@Override
	public Integer generateNext() {
		
		return random.nextInt(max-min)+min;
	}
	
	public static int genInt(Random r, int max) {
		return genInt(r,0,max);
	}
	
	public static int genInt(Random r, int min, int max) {
		return min + r.nextInt(max);
	}
	
}
