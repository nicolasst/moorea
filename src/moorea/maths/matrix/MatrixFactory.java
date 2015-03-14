package moorea.maths.matrix;


import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import moorea.graphs.Graph;
import moorea.graphs.Node;

/**
 * 
 * @author nicolas
 *
 */

public class MatrixFactory {
	
	public static <N> Matrix<N> generateRandomMatrix(Class<N> cl, int size, Iterator<N> it) {
	
		Matrix<N> m = new Matrix<N>(cl, size);
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				m.values[i][j] = it.next();
			}
		}
		return m;
	}

	public static <N> Matrix<N> generateRandomSymetricMatrix(Class<N> cl, int size, Iterator<N> it) {
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
	
	public static <N> N[][] adjacencyMatrixFromGraph(Graph<Node> g, Class<N> cn, N zero, N one, N diagonalElement) {
		int size = g.getNodes().size();
		//System.out.println(g.getNodes()+" "+size);
		N[][] matrix = (N[][]) Array.newInstance(cn, new int[]{size, size});
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				matrix[i][j] = zero;
			}
		}
		for(Node n : (List<Node>) g.getNodes()) {
			matrix[n.id][n.id] = diagonalElement;
			/*if(!(n instanceof DirectedNode)) {
				for(Node nb : (List<Node>) ((DirectedNode)n).getOutgoingArcs()) {
					matrix[n.id][nb.id] = one;
				}
			} else {*/
				for(Node nb : (List<Node>) n.getNeighbours()) {
					matrix[n.id][nb.id] = one;
					matrix[nb.id][n.id] = one;
				}
			}
		//}
		return matrix;
	}
}
