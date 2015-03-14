package moorea.maths.lambda.functions.numbers;

import moorea.maths.lambda.Mapper;

/**
 * 
 * @author nicolas
 *
 */

public class AddMapper extends Mapper<Integer,Integer> {

	Integer addValue;
	
	public AddMapper(int addValue) {
		this.addValue = addValue;
	}
	
	public Integer apply(Integer e) {
		return e + addValue;
	}
	
}
