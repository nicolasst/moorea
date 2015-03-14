package moorea.maths.lambda.functions;

import moorea.maths.lambda.Mapper;

/**
 * 
 * @author nicolas
 *
 * @param <K>
 */

public class IdentityMapper<K> extends Mapper<K,K> {

	public K apply(K e) {
		return e;
	}

}
