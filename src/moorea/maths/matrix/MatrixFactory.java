package moorea.maths.matrix;


import java.util.Iterator;


/**
 * 
 * @author nicolas
 *
 */

public class MatrixFactory {
	
	public static <N> Matrix<N> fillMatrix(Matrix m, Iterator<N> it) {
		for(int i=0;i<m.height;i++) {
			for(int j=0;j<m.width;j++) {
				m.values[i][j] = it.next();
			}
		}
		return m;
	}
	
	public static <N> Matrix<N> createAndFillMatrix(Class<N> cl, int size, Iterator<N> it) {
	
		Matrix<N> m = new Matrix<N>(cl, size);
		fillMatrix(m, it);
		return m;
	}

	public static <N> Matrix<N> createAndFillSymetricMatrix(Class<N> cl, int size, Iterator<N> it) {
		Matrix<N> m = new Matrix<N>(cl, size);
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(i>=j) {
					m.values[i][j] = it.next();
					m.values[j][i] = m.values[i][j];
				}
			}
		}
		return m;
	}
	
	public static <N> void setMatrixDiagonal(Matrix<N> m, N value) {
		int size = m.values.length;
		for(int i=0;i<size;i++) {
			try {
			m.values[i][i] = value;
			} catch(Exception e) {
				System.err.println("matrix is not squared");
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
