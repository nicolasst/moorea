package moorea.graphs.algorithms;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import moorea.graphs.Edge;
import moorea.graphs.Graph;
import moorea.graphs.Node;
import moorea.graphs.construction.GraphConvertion;
import moorea.graphs.construction.GraphFactory;
import moorea.graphs.traversal.GraphNodeIterator;
import moorea.graphs.weighted.WeightedEdge;
import moorea.graphs.weighted.WeightedEdgeGraph;
import moorea.graphs.weighted.WeightedGraph;
import moorea.graphs.weighted.WeightedNode;
import moorea.misc.AdHocMapEvalComparator;
import moorea.misc.BidiMap;
import moorea.misc.Tupple2;

/**
 * Contains all the method related to the extraction of subgraphs, spanning trees and connected components.
 * 
 * @author nicolas
 *
 */

public class GraphExtraction {

	// connected components
	
	// /!\ the returned moorea.graphs use the same set of nodes as the graph in parameter
		
	/**
	 * Returns the connected component in g to which the node n belongs.
	 */
	
	public static List<Node> getConnectedComponent(Graph g, Node n) {
		List<Node> ln = new LinkedList<Node>();
		ln.add(n);
		for(Node nb : (List<Node>) n.getNeighbours()) {
			if(!ln.contains(nb)) {
				ln.add(nb);	
			}
		}
		return ln;
	}

	/**
	 * returns the list of connected components of a graph
	 * @param g
	 * @return
	 */
	
	public static List<? extends Graph> getConnectedComponents(Graph g) {
			List<Graph> lg = new LinkedList<Graph>();
			List<Node> ln = new LinkedList<Node>();
			ln.addAll(g.getNodes()); // copy references in order not to modify g's node list
			Set<Node> isInCC = new HashSet();
			while(ln.size()>0) {
				Graph gc = GraphFactory.createEmptyGraphOfSameType(g);
				List<Node> cc = new LinkedList<Node>();
				List<Node> stack = new LinkedList<Node>();
				Node n = ln.remove(0);
				cc.add(n);
				stack.add(n);
				isInCC.add(n);
				while(stack.size() > 0) {
					n = stack.remove(0);
					for(Node nn : (List<Node>) n.getNeighbours()) {
						if(!isInCC.contains(nn)) {
							isInCC.add(nn);
							cc.add(nn);
							stack.add(nn);
							ln.remove(nn);
						}
					}
				}
				gc.addExistingNodes(cc); // add same reference as graph in parameter
				lg.add(gc);
			}
			return lg;
		}


	/**
	 * Returns the largest connected component of a graph.
	 */
	
	public static <V extends Node> Graph<V>  getLargestConnectedComponent(Graph<V> g) {
		List<Graph<V>> lg = (List<Graph<V>>) getConnectedComponents(g);
		return getLargestConnectedComponentFromList(lg);
	}
	
	/**
	 * Returns the largest graph in a list of moorea.graphs.
	 */
	
	public static <V extends Node, G extends Graph<V>> G getLargestConnectedComponentFromList(List<G> lg) {
		G argMax = null;
		int val = Integer.MIN_VALUE;
		for(G gc : lg) {
			if(gc.getNodes().size() > val) {
				argMax = gc;
				val = argMax.getNodes().size();
			}
		}
		return argMax;
	}
	
	// spanning trees
	

	public static Graph getSpanningTree(Graph g, GraphNodeIterator it) {
		Graph st = GraphFactory.createEmptyGraphOfSameType(g);
		BidiMap<Node, Node> bm = new BidiMap();
		for(Node n : (List<Node>) g.getNodes()) {
			Node nt = st.createNewNode();
			bm.put(n,nt);
		}
		Set<Node> prevNodes = new HashSet();
		Node n1 = it.next();
		while(it.hasNext()) {
			Node n2 = it.next();
		}
		return st;
	}
	
	
	/**
	 * Computes the minimal spanning tree of a WeightedGraph using the Kruskal algorithm.
	 * @return tupple, first: maximal spanning tree, second: bijection between nodes of the spanning tree and of the weighted graph
	 */
	
	public static <N> Tupple2<Graph,BidiMap> minimalSpanningTreeKruskal(WeightedGraph<N> wg) {
		// spaning tree
		Graph g = new Graph();
		
		// convert adjacency list weighted graph to edge list weighted graph
		WeightedEdgeGraph weg = GraphConvertion.weightedGraphToWeightedEdgeGraph(wg);
		
		List<WeightedNode<N>> nodes = wg.getNodes();
		List<WeightedEdge<?,N>> edges = weg.getEdges();
		
		// build evaluation map
		Map<Edge,N> eval = new HashMap();
		for (WeightedEdge<?,N> we : edges) {
			eval.put(we, we.getWeight());
		}		

		// create nodes of the spanning tree g and bijection with wg
		BidiMap<Node,Node> bm = new BidiMap();
		for(Node n : nodes) {
			Node nn = g.createNewNode();
			bm.put(n, nn);
		}
		
		// to manage connect components
		Map<Node,Integer> ccVal = new HashMap<Node, Integer>();
		// init connected component ids
		int cpt=0;
		for(Node n : (List<Node>) g.getNodes()) {
			ccVal.put(n, cpt++);
		}
		
		// kruskal picks edges from a priority queue
		// priority queue picks the element with the least valuation
		PriorityQueue<Edge> heap = new PriorityQueue(eval.size(),new AdHocMapEvalComparator(eval));
		heap.addAll(eval.keySet());
		
		//
		while(heap.size() != 0) {
			Edge<Node> e = heap.poll();
			// get the nodes of g corresponding to the ones of wg
			Node nt1 = bm.getAB(e.n1);
			Node nt2 = bm.getAB(e.n2);
			// get cc ids
			int s1 = ccVal.get(nt1);
			int s2 = ccVal.get(nt2);
			// if cc ids are different then merge, else, ignore
			if(s1 != s2) {
				int sn = s1;
				for(Node n : getConnectedComponent(g,nt2)) {
					ccVal.put(n,sn);
				}
				g.createEdge(nt1,nt2);
			}
		}
		return new Tupple2(g, bm);
	}
	
	// TODO: unit test
	
}
