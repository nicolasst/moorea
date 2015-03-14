package moorea.maths.lambda.functions.numbers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import moorea.maths.lambda.BorderEffectLambda;

/**
 * 
 * @author nicolas
 *
 */

public class MaxIndexReducer extends BorderEffectLambda<Integer, Integer, List<Integer>>{

	public MaxIndexReducer() {
		super(Arrays.asList(new Integer[]{-1,Integer.MIN_VALUE,0}));
	}
	
	// data:
	// 0          1          2
	// indexBest, valueBest, currentIndex

	public Integer apply(Integer e) {
		if(e>data.get(1)) {
			data.set(0, data.get(2));
			data.set(1, e);
		}
		return data.get(1);
	}



}
