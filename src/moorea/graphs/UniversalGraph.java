package moorea.graphs;

import java.util.List;

/**
 * A UniversalNode is a graph that use UniversaleNode, which are nodes
 * that can store any information on any aspect of the graph using hashtables.
 * 
 * The purpose of this class is to regroup in a single place all the different kinds of moorea.graphs:
 * directed, undirected, weighted, unweighted, labeled, unlabeled, etc.
 * The intent is to make a class a versatile as moorea.graphs in Python's NetworkX.
 * 
 * The benefit is that it is very simple to manipulate a graph.
 * The drawback is that performances poorer than using ad-hoc moorea.graphs.
 * 
 * TODO this class has never been test
 * 
 * @author nicolas
 *
 * @param <N>
 */

public class UniversalGraph<N> extends Graph<UniversalNode>{
	
	List<UniversalNode<N>> nodes;

}
