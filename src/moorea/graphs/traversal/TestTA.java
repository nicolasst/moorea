package moorea.graphs.traversal;

import moorea.graphs.Graph;
import moorea.graphs.Node;
import moorea.graphs.algorithms.GraphTraversal;
import moorea.graphs.construction.GraphFactory;
import moorea.graphs.io.GraphDisplay;
import moorea.graphs.io.GraphIO;
import moorea.maths.lambda.FunctionalAlgorithms;
import moorea.maths.lambda.functions.DisplayMapper;

/**
 * 
 * @author nicolas
 *
 */

public class TestTA {

	public static void main(String[] args) {
		
		//
		System.out.println("graph");
		Graph g = GraphFactory.generateCompleteSimpleGraph(5);
		GraphDisplay.disp(g);
		
		//
		System.out.println("iterator dfs traversal");
		GraphNodeIterator it = new GraphNodeIteratorDFS(g, g.getNodeByIndex(0));
		while(it.hasNext()) {
			Node n = it.next();
			System.out.println(n);
		}
		
		//
		System.out.println("static dfs traversal");
		GraphTraversal.depthFirstSearch(g, g.getNodeByIndex(0));
		
		//
		System.out.println("iterator bfs traversal");
		it = new GraphNodeIteratorBFS(g, g.getNodeByIndex(0));
		while(it.hasNext()) {
			Node n = it.next();
			System.out.println(n);
		}
		
		//
		System.out.println("static bfs traversal");
		GraphTraversal.breadthFirstSearch(g, g.getNodeByIndex(0));
		
		// lambda
		
		//
		System.out.println("lambda dfs traversal");
		FunctionalAlgorithms.map(new GraphNodeIteratorDFS(g, g.getNodeByIndex(0)),new DisplayMapper("\n"));

		//
		System.out.println("lambda bfs traversal");
		FunctionalAlgorithms.map(new GraphNodeIteratorBFS(g, g.getNodeByIndex(0)),new DisplayMapper("\n"));

		
	}

}
