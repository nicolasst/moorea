package moorea.maths.matrix;

import moorea.maths.lambda.FunctionalAlgorithms;
import moorea.maths.lambda.Reducer;
import moorea.misc.FileLogger;
import moorea.misc.Logger;

/**
 * 
 * @author nicolas
 *
 */

public class MatrixAlgorithms {


	public static <K> K getOptimalElement(Matrix<K> m, Reducer<K> reducer) {
		return getOptimalElement(m.values, reducer);
	}
	
	public static <K> K getOptimalElement(K[][] a, Reducer<K> reducer) {
		MatrixValueIterator<K> it = new MatrixValueIterator<K>(a);
		return FunctionalAlgorithms.reduce(it, reducer);
	}


	public static <N> void display(Matrix<N> m) {
		display(m.values);
	}
	
	public static <K> void display(K[][] a) {
		int nbLines = a.length;
		int nbCols = a[0].length;
		for(int i = 0; i<nbLines; i++) {
			for(int j = 0; j<nbCols; j++) {
				System.out.print(a[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static <K> void displayHideValue(Matrix m, K valueToHide) {
		displayHideValue(m.values, valueToHide);
	}
		
	
	public static <K> void displayHideValue(K[][] a, K valueToHide) {
		int nbLines = a.length;
		int nbCols = a[0].length;
		for(int i = 0; i<nbLines; i++) {
			for(int j = 0; j<nbCols; j++) {
				boolean testEq = false;
				if(a[i][j] == null) {
					System.out.print("X ");
					continue;
				} else 
				if(valueToHide instanceof Integer) {
					if(((Integer)valueToHide).intValue() == ((Integer) a[i][j]).intValue()) {
						testEq = true;
					}
				} else if(valueToHide instanceof Double) {
					if(((Double)valueToHide).doubleValue() == ((Double) a[i][j]).doubleValue()) {
						testEq = true;
					}
				} else {
					testEq = (a[i][j] == valueToHide);
				}
				//if(a[i][j] != valueToHide) {
				if(!testEq) {
					System.out.print(a[i][j]+" ");
				} else {
					System.out.print("_ ");
				}
			}
			System.out.println();
		}
	}
	
	public static void toFile(Matrix m, String fname) {
		Logger file = new FileLogger(fname);
		
		int nbLines = m.values.length;
		int nbCols = m.values[0].length;
		file.println(nbLines);
		file.println(nbCols);
		for(int i = 0; i<nbLines; i++) {
			for(int j = 0; j<nbCols; j++) {
				file.print(m.values[i][j]+" ");
			}
			file.println();
		}
		file.close();
	}
	

}
