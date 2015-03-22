package moorea.maths.random;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import moorea.maths.lambda.BorderEffectLambda;
import moorea.maths.lambda.FunctionalAlgorithms;
import moorea.maths.lambda.functions.numbers.SumReducer;

/**
 * The main purpose of this class is to be reused by Bayesian networks.
 * 
 * @author nicolas
 *
 */

public class ProbabilityDistribution {


	public static List<Double> buildDistributionFromObjectsIntegerWeights(List<Integer> objectWeights) {
		// compute the sum of the list
		double sum = (double) FunctionalAlgorithms.reduce(objectWeights, new SumReducer<>(0));
		
		// normalise it
		List<Double> probNode = FunctionalAlgorithms.map(
				objectWeights,
				new BorderEffectLambda<Integer, Double, Double>(sum) {
					public Double apply(Integer e) {
						return e.doubleValue() / data;
					}
				}
			);
		return probNode;
	}

	public static <K> Map<K,Double> buildDistributionFromObjectsIntegerWeights(Map<K,Integer> mapWeights) {
		List<Integer> objectWeights = new LinkedList<>(mapWeights.values());
		// compute the sum of the list
		double sum = (double) FunctionalAlgorithms.reduce(objectWeights, new SumReducer<>(0));
		
		Map<K,Double> normalisedWeigths = new HashMap<K, Double>();
		
		// normalise it
		for(K key : mapWeights.keySet()) {
			normalisedWeigths.put(key,mapWeights.get(key).doubleValue()/sum);
		}
		
		return normalisedWeigths;
	}

	
	public static List<Double> buildDistributionFromObjectsDoubleWeights(List<Double> objectWeights) {
		// compute the sum of the list
		double sum = 0.;
		for(Double d : objectWeights) {
			sum += d;
		}
		
		// normalise it		
		List<Double> ret = new LinkedList<>();
		for(Double d : objectWeights) {
			ret.add(d / sum);
		}

		return ret;
	}
	
	public static <K> Map<K,Double> buildDistributionFromObjectsDoubleWeights(Map<K,Double> mapWeights) {
		List<Double> objectWeights = new LinkedList<>(mapWeights.values());
		// compute the sum of the list
		double sum = (double) FunctionalAlgorithms.reduce(objectWeights, new SumReducer<>(0.));
		
		Map<K,Double> normalisedWeigths = new HashMap<K, Double>();
		
		// normalise it
		for(K key : mapWeights.keySet()) {
			normalisedWeigths.put(key,mapWeights.get(key).doubleValue()/sum);
		}
		
		return normalisedWeigths;
	}

	
	// of course it would be better to use binary search, but for this project we don't really care
	
	public static int drawObjectIndexFromDistribution(List<Double> objectProbs, double p) {
		double sum = objectProbs.get(0);
		int index = 0;
		for (int i = 1; i < objectProbs.size(); i++) {
			if(sum>p) {
				break;
			}
			index = i;
//			if(p <sum && p <= sum + objectProbs.get(i+1)) {
//				index = i;
//				break;
//			}
			sum += objectProbs.get(i);
		}
		if(index == -1) {
			index = objectProbs.size()-1;
		}
		return index;
	}
	
	public static <K> K drawObjectFromDistribution(Map<K,Double> objectProbs, double p) {
		double sum = 0.;
		int index = -1;
		K object = null;
		for (K key : objectProbs.keySet()) {
			object = key;
			if(sum < p && p <= sum + objectProbs.get(key)) {
				break;
			}
			sum += objectProbs.get(key);
		}
		return object;
	}
	
	public static int drawObjectIndexFromDistribution(double[] objectProbs, double p) {
		double sum = 0.;
		int index = -1;
		for (int i = 0; i < objectProbs.length-1; i++) {
			if(sum < p && p <= sum + objectProbs[i+1]) {
				index = i;
				break;
			}
			sum += objectProbs[i+1];
		}
		if(index == -1) {
			index = objectProbs.length-1;
		}
		return index;
	}
	
}
