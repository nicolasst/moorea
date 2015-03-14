package moorea.maths.lambda.functions;

import moorea.maths.lambda.Mapper;

/**
 * 
 * @author nicolas
 *
 */

public class DisplayMapper extends Mapper {

	String sep;
	
	public DisplayMapper() {
		sep = " ";
	}
	
	public DisplayMapper(String sep) {
		this.sep = sep;
	}
	
	public Object apply(Object e) {
		System.out.print(e+sep);
		return e;
	}

}
