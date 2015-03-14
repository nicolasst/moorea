package moorea.maths.matrix;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moorea.maths.objects.Assignment;
import moorea.maths.objects.DiscreteVariable;
import moorea.maths.objects.Function;


/**
 * 
 * @author nicolas
 *
 * @param <N>
 */

public class Matrix<N> {
	
	public N[][] values;
	
	public Class<N> elementType;
	public int[] dimensions;
	public int height, width;
	
	public Matrix(Class<N> type, int size) {
		values = (N[][]) Array.newInstance(type,new int[]{size,size});
		elementType = type;
		height = size;
		width = size;
	}
	
	public Matrix(Class<N> type, int nbLines, int nbCollumns) {
		values = (N[][]) Array.newInstance(type,new int[]{nbLines,nbCollumns});
		elementType = type;
		height = nbLines;
		width = nbCollumns;
	}
	
	public Matrix(Class<N> type, N[][] values) {
		elementType = type;
		this.values = values;
		height = values.length;
		width = values[0].length;
		/*for(int i=0;i<vals.length;i++) {
			for(int j=0;j<vals[i].length;j++) {
				values[i][j] = vals[i][j];
			}
		}*/
	}
	
	public void setValues(N val) {
		for(int i=0;i<values.length;i++) {
			for(int j=0;j<values[i].length;j++) {
				values[i][j] = val;
			}
		}
	}
	
	/*public Matrix(Class<N> type, int[] sizes) {
		values = (N[][]) Array.newInstance(type,sizes);
		elementType = type;
		dimensions = sizes;
	}*/
	
}
