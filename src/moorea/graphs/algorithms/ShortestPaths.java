package moorea.graphs.algorithms;


import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import moorea.graphs.Graph;
import moorea.graphs.Node;
import moorea.graphs.construction.GraphConvertion;
import moorea.graphs.weighted.WeightedGraph;
import moorea.graphs.weighted.WeightedNode;
import moorea.maths.algebra.Operator;
import moorea.maths.algebra.Semiring;
import moorea.maths.algebra.SetComparator;
import moorea.misc.AdHocMapEvalComparator;
import moorea.misc.Tupple2;

/**
 * Contains all the methods related to shortest path computation.
 * 
 * The algorithms are all based on the explicit use of a semiring.
 * 
 * As a consequence the very algorithms implemented can be directly
 * reused to perform computation for a large number problems: all
 * that is needed is to provide a graph an a semiring.
 * 
 * @author nicolas
 *
 */

public class ShortestPaths {
	
	/*
	 * Warning : if the neutral elements of the semiring contains MAX_VALUE or MIN_VALUE, the algorithm will overflows or underflow.
	 * It is therefore necessary to use values lower than the boundaries and be ascertain that after N^3 iterations the internal representation
	 * boundaries will not be reached.
	 * The other option would be to check for over/under flows and for the semiring operator type, which would undermine perfrormances.
	 */
	
	public static <N> N[][] getAllSPFloydWrashall(Graph<Node> g, Semiring<N> sr) {
		N initValue = null;
		if(sr.getSetClass() == Integer.class) {
			initValue = (N) (Integer) 1;
		}
		if(sr.getSetClass() == Double.class) {
			initValue = (N) (Double) 1.;
		}
		N[][] matrix = GraphConvertion.adjacencyMatrixFromGraph(g, sr.getSetClass(), sr.getDotNeutralElement(), initValue, sr.getSumNeutralElement()); // TODO weighted graph should be treated differntly
		return getAllSPFloydWrashall(matrix, sr);
	}		
	
	public static <N> N[][] getAllSPFloydWrashall(N[][] matrix, Semiring<N> sr) {
	
		int n = matrix.length;
		Operator<N> sumOp = sr.getSumOperator(); // 
		Operator<N> dotOp = sr.getDotOperator(); // combination of subproblems solutions
		for(int k = 0; k < n; k++) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					// eventually check for underflow / overflow  
					matrix[i][j] = dotOp.apply( matrix[i][j], sumOp.apply( matrix[i][k], matrix[k][j]));
				}
			}
		}
		return matrix;
	}
	
	/**
	 * Returns the shortest path in a WeightedGrpah g between the Node s and the Node t using the Dijkstra algoritm.
	 */
	
	// TODO field comparator is redundant with the use of the dotOperator (which implements the min operator)
	
	// memo
	
	// semiring constructor:
	//		set
	//		sum operator
	//		sum neutral element
	//		dot operator
	//		dot neutral element
	//		name
		
	// public static Semiring<Integer> shortestPathSRint = new Semiring<Integer>(
	//		Integer.class,
	//		new SumOperator<Integer>(Integer.class), 0,
	//		new MinOperator<Integer>(Integer.class), maxValue,
	//		"shortest path SR integer");
	
	public static <N extends Comparable> Tupple2<List<Node>,N> dijkstra(WeightedGraph<N> g, Node s, Node t, Semiring<N> semiring) {
		
		Operator<N> sumOperator = semiring.getSumOperator();
		Operator<N> dotOperator = semiring.getDotOperator();
		SetComparator<N> fieldComparator = semiring.getSetComparator();
		
		List<WeightedNode<N>> nodes = g.getNodes();
		
		int size = nodes.size();
		
		List<Node> p = new LinkedList<Node>();

		Map<Node,N> eval = new HashMap<Node, N>(size);
		Set<Node> mark = new HashSet<Node>(size);
		Map<Node,Node> pred = new HashMap<Node, Node>(size);
		
		Comparator comp = new AdHocMapEvalComparator(eval);

		PriorityQueue<Node> heap = new PriorityQueue(size,comp);

		for(Node n : nodes) {
			//eval.put(n,Integer.MAX_VALUE);
			eval.put(n,semiring.getDotNeutralElement());
		}
		//eval.put(s,0);
		eval.put(s,semiring.getSumNeutralElement());
		pred.put(s,null);
		mark.add(s);

		for(Node n : nodes) {
			heap.add(n);
		}

		while(heap.size()>0) {
			WeightedNode n = (WeightedNode) heap.poll();
			for(WeightedNode a : (List<WeightedNode>) n.getNeighbours()) {
				boolean updateHeap = false;
				if(!mark.contains(a)) {
					mark.add(a);
					//eval.put(a,eval.get(n).intValue()+g.getEdgeWeight(n,a).intValue());
					eval.put(a,
							sumOperator.apply(
								eval.get(n),
								g.getEdgeWeight(n,a)
							)
					);
					
					pred.put(a,n);
					updateHeap = true;
				} else {
					//int vn = eval.get(n).intValue();
					//int va = eval.get(a).intValue();
					//int c = g.getEdgeWeight(n,a).intValue();
					N vn = eval.get(n);
					N va = eval.get(a);
					N w = g.getEdgeWeight(n,a);
					//if(vn+c<va) {
					//if(fieldComparator.compare(sumOperator.apply(vn, c),va) < 0) {
					N vaNew = dotOperator.apply(sumOperator.apply(vn, w),va);
					if(vaNew != va) {
						//eval.put(a,eval.get(n).intValue()+g.getEdgeWeight(n,a).intValue());
//						eval.put(a,
//								sumOperator.apply(
//										eval.get(n),
//										g.getEdgeWeight(n,a)
//								)
//						);
						eval.put(a, vaNew);
						pred.put(a,n);
						updateHeap = true;
					}
				}
				if(updateHeap) {
					heap.remove(a);
					heap.add(a);
					updateHeap = false;
				}
			}
		}

		//System.out.println(pred);
		
		Node n = t;
		Node tmp = pred.get(t);
		p.add(t);
		while(tmp != null) {
			n = tmp;
			p.add(0,n);
			tmp = pred.get(n);
		}
		
//		System.out.println(p);
//		System.out.println(eval.get(t));

		return new Tupple2(p, eval.get(t));
	}

}
