package moorea.maths.lambda.functions.lists;

import java.util.List;

import moorea.maths.lambda.Mapper;

/**
 * 
 * @author nicolas
 *
 */

public class ListSizeMapper extends Mapper<List, Integer> {

	@Override
	public Integer apply(List e) {
		return e.size();
	}

}
