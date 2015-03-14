package moorea.graphs;


import java.util.List;

import moorea.graphs.construction.NodeFactory;
import moorea.maths.matrix.Matrix;
import moorea.misc.BidiMap;
import moorea.misc.IntegerGenerator;

/**
 * Represent a graph with a matrix.
 * 
 * Depending on the use of the matrix, this class can represent both:
 * - weighted and unweighted moorea.graphs
 * - directed and undirected moorea.graphs
 * 
 * The class decouples the indices of the matrix and the id of the nodes,
 * in order to allows more flexibility. Object, which need not to be nodes,
 * are stored in a list.
 * 
 * By default the class Object should be used. Richer graph structure can be
 * implemented by providing richer objects to the generic type.
 * 
 * @author nicolas
 *
 * @param <K> type of the nodes
 * @param <N> type of the elements of the matrix
 */

public class MatrixGraph<K, N> extends Graph {
	
	Matrix<N> adjacency;

	BidiMap<K, Integer> bmNodIndex = new BidiMap<>();
	
	Class<N> matrixElementClass;
	
	List<K> objects;
	
	public MatrixGraph(Class<N> matrixElementClass) {
		this.matrixElementClass = matrixElementClass;
	}
	
	public MatrixGraph(Class<N> matrixElementClass, List<K> objects) {
		this.matrixElementClass = matrixElementClass;
		setNodeObjectsSet(objects);
	}
	
	public void setNodeObjectsSet(List<K> objects) {
		this.objects = objects;
		for (int i = 0; i < this.objects.size(); i++) {
			bmNodIndex.put(this.objects.get(i), i);
		}
	}

	@Override
	public void createEdge(Node n1, Node n2) {
		super.createEdge(n1, n2);
	}
/*
	@Override
	public boolean hasEdge(Node n1, Node n2) {
		return adjacency.values[bmNodIndex.getAB(n1)][bmNodIndex.getAB(n2)] != null;
	}

	@Override
	public void removeEdge(Node n1, Node n2) {
		adjacency.values[bmNodIndex.getAB(n1)][bmNodIndex.getAB(n2)] = null;
	}
*/
	@Override
	public void removeAllEdges() {
		// TODO Auto-generated method stub
		super.removeAllEdges();
	}
	
	

}
