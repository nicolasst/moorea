package moorea.graphs.io;

import java.util.List;

import moorea.graphs.ContainerGraph;
import moorea.graphs.ContainerNode;
import moorea.graphs.DirectedNode;
import moorea.graphs.Graph;
import moorea.graphs.LabelledGraph;
import moorea.graphs.LabelledNode;
import moorea.graphs.Node;
import moorea.graphs.weighted.WeightedNode;


/**
 * Contain method to display graph on system.out
 * 
 * @author nicolas
 *
 */

public class GraphDisplay {

	public static void print(Graph g) {
		System.out.println(g.getNodes());
	}
	
	
	public static void disp(Graph g) {
		List<Node> nodes = g.getNodes();
		
		System.out.println("nb nodes: "+nodes.size());
		System.out.println(nodes);
		for(Node n : nodes) {
			if(n instanceof DirectedNode) {
				System.out.println(n+" inArcs: "+((DirectedNode)n).getIncommingArcs()+" outArcs: "+((DirectedNode)n).getOutgoingArcs());
			} else if(n instanceof WeightedNode) {
				System.out.println(n+" "+((WeightedNode)n).edgeWeights);
			}
			else if(n instanceof Node) {
				System.out.println(n+" "+n.getNeighbours());
			}
		}
	}

	public static <K> void disp(ContainerGraph<K> g) {
		System.out.println("nodes:");
		for(ContainerNode<K> n : g.getNodes()) {
			System.out.println(n+" : "+n.getContent());
		}
		System.out.println("edges:");
		for(ContainerNode<K> n : g.getNodes()) {
			System.out.println("of "+n+" : "+n.getNeighbours());
		}

	}
	
	public static void disp(LabelledGraph g) {
		System.out.println("nodes:");
		for(LabelledNode n : g.getNodes()) {
			System.out.println(n+" object: "+n.getNodeObject()+" node labels: "+n.getNodeLabels()+" edge labels: "+n.getEdgeLabels());
		}
		System.out.println("edges:");
		for(LabelledNode n : g.getNodes()) {
			System.out.println("of "+n+" : "+n.getNeighbours());
		}
	}
}
