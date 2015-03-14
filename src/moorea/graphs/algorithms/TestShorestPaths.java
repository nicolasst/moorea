package moorea.graphs.algorithms;



import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import moorea.graphs.Graph;
import moorea.graphs.Node;
import moorea.graphs.SimpleGraph;
import moorea.graphs.construction.GraphConvertion;
import moorea.graphs.construction.GraphFactory;
import moorea.graphs.io.GraphDisplay;
import moorea.graphs.io.GraphIO;
import moorea.graphs.weighted.WeightedGraph;
import moorea.maths.algebra.MinOperator;
import moorea.maths.algebra.Semiring;
import moorea.maths.algebra.SumOperator;
import moorea.maths.matrix.MatrixAlgorithms;
import moorea.maths.matrix.MatrixFactory;

/**
 * Unit test for shortet paths.
 * 
 * @author nicolas
 *
 */

public class TestShorestPaths {


	public static void main(String[] args) {

		Random random = new Random(0);
		
		GraphFactory.randomStatic = random;
		
		//Graph g = GraphFactory.generateRandomGraphErdosRenyi(20, random, 0.1);
		//Graph g = GraphFactory.generateRandomSpatialGraphRadiusIncreasing(50, 2, 0.1);
		//Graph g = GraphFactory.genHierarchicalGraph(1,2,3,false,null);
		//Graph g = GraphFactory.generateTreeGraph(2, 4);
		SimpleGraph g = GraphFactory.simpleGraphFactory.generateCycleGraph(5,1);
		
		GraphDisplay.disp(g);
		GraphIO.toDot(g,"g.dot");
		
		Integer maxValue=100000;
		
		Semiring<Integer> sr = new Semiring<Integer>((Integer.class), new SumOperator<Integer>(Integer.class), 0, new MinOperator<Integer>(Integer.class), maxValue);//Integer.MAX_VALUE, "shortest path SR");

		System.out.println("adjacencyMatrixFromGraph");
		
		Integer[][] gMatrix = MatrixFactory.adjacencyMatrixFromGraph((Graph)g, sr.getSetClass(), sr.getDotNeutralElement(), 1, sr.getSumNeutralElement());
		
		MatrixAlgorithms.displayHideValue(gMatrix,sr.getDotNeutralElement());
		
		System.out.println();
		System.out.println("allSPFloydWarshall");
		
		Integer[][] spMatrix = ShortestPaths.getAllSPFloydWrashall((Graph)g, sr);

		MatrixAlgorithms.displayHideValue(spMatrix,sr.getDotNeutralElement());
		
		System.out.println("");
		System.out.println("sp dijkstra");

		WeightedGraph<Integer> wg = GraphConvertion.weightedGraphFromGraph(g, 1);
		
		List<Node> p = ShortestPaths.dijkstra(wg, wg.getNodeByIndex(0), wg.getNodeByIndex(2), Semiring.shortestPathSRint).getFirst();
		System.out.println(p);
		
	}

}
