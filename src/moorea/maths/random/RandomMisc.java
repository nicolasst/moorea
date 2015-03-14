package moorea.maths.random;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author nicolas
 *
 */

public class RandomMisc {
	
	public static Random createNewRandom() {
		Random r = new Random();
		long seed = r.nextLong();
		System.out.println("seed (generated): "+seed);
		r.setSeed(seed);
		return r;
	}
	
	public static Random createNewRandom(long seed) {
		Random r = new Random();
		System.out.println("seed (given): "+seed);
		r.setSeed(seed);
		return r;
	}
	
	public static int generateIntegerWithinBounds(Random r, int max) {
		return generateIntegerWithinBounds(r,0,max);
	}
	
	public static int generateIntegerWithinBounds(Random r, int min, int max) {
		return min + r.nextInt(max);
	}
	
	public static List<Integer> sampleIntegerList(Random random, int nbSamples, int min, int max, boolean withRedraw) {
		List<Integer> l = new LinkedList<>();
		while(true) {
			int val = generateIntegerWithinBounds(random, min, max);
			if(!withRedraw) {
				if(l.contains(val)) {
					continue;
				}
			}
			l.add(val);
			if(l.size() == nbSamples) {
				break;
			}
		}
		return l;
	}
	
}
