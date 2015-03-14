package moorea.graphs;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import moorea.graphs.construction.GraphFactory;
import moorea.graphs.io.GraphDisplay;


public class TestGraphs {

	public static void main(String[] args) {
		{
		Graph g = new Graph(SimpleNode.class);
		g.addNewNode();
		
		GraphDisplay.print(g);
		}
		
		// SimpleGraph
			
		{
        SimpleGraph g = new SimpleGraph();// GraphFactory.simpleGraphFactory.createGraph();
        
        g.addNewNode(); // id 0
        g.addNewNode(); // id 1
        SimpleNode sn = g.createNewNode(); // id 2
        g.addExistingNode(sn);
        g.addNewNodes(2); // id 3, id 4
 
        g.createEdgeByIndex(0,1);
        g.createEdgeByIndex(1,2);
        g.createEdgeById(2,3);
        g.createEdgeById(3,0);
        
		GraphDisplay.disp(g);
		
		int[] weightList = { 1000, 1230, 1100, 1090};
		String[] nameList = { "Paris", "Berlin", "Brno", "Minsk" };
		
		
		
		//int i=0;
		
		Map<Node, Map<Node, Integer>> edgeWeights = new HashMap();
		
		
		
		/*for(SimpleNode n : g.getNodes()) {
			edgeWeights.put(n, new HashMap());
			
			for(Node nb : (List<Node>) n.getNeighbours()) {
				edgeWeights.get(n).put(nb,weightList[i++]);
			}
		}*/
		
//		edgeWeights.put(key, value)

		}
		
		// LabelledNode
		
		{
			LabelledGraph g = new LabelledGraph();
			g.addNewNodes(4);
			
			Object nameKey = "name";
			Object orgKey = "organisation";
			Object distKey = "distance";
			Object visitKey = "visited";
			
			g.setNodeLabelByIndex(0, nameKey, "Paris");
			g.setNodeLabelByIndex(1, nameKey, "Berlin");
			g.setNodeLabelByIndex(2, nameKey, "Brno");
			g.setNodeLabelByIndex(3, nameKey, "Minsk");		
		
			g.setEdgeLabelByIndex(0, 1, distKey, 1000);
			g.setEdgeLabelByIndex(0, 2, distKey, 1230);
			g.setEdgeLabelByIndex(1, 3, distKey, 1100);
			g.setEdgeLabelByIndex(2, 3, distKey, 1090);
			
			g.setAllNodeLabels(orgKey, "EU");
			g.setNodeLabelByIndex(3, orgKey, "TS");

			dijkstraLabelled(g, g.getNodeById(0), g.getNodeById(3), distKey);
			
			GraphDisplay.disp(g);
		}
		

	}


	public static List<Node> dijkstraExternalWeights(Graph g, Node s, Node t, Map<Node, Map<Node, Integer>> edgeWeights) {
		
		List<Node> nodes = g.getNodes();
		int size = nodes.size();
		
		List<Node> p = new LinkedList<Node>();

		final Map<Node,Integer> eval = new HashMap(size);
		Set<Node> mark = new HashSet<Node>(size);
		Map<Node,Node> pred = new HashMap<Node, Node>(size);
		
		Comparator comp = new Comparator<Node>() {
			@Override
			public int compare(Node n, Node m) {
					return eval.get(n).intValue() - eval.get(m).intValue();
				}
		};

		PriorityQueue<Node> heap = new PriorityQueue(size,comp);

		for(Node n : nodes) {
			eval.put(n,Integer.MAX_VALUE);
		}
		eval.put(s,0);
		pred.put(s,null);
		mark.add(s);

		for(Node n : nodes) {
			heap.add(n);
		}

		while(heap.size()>0) {
			Node n = (Node) heap.poll();
			for(Node a : (List<Node>) n.getNeighbours()) {
				boolean updateHeap = false;
				if(!mark.contains(a)) {
					mark.add(a);
					eval.put(a, eval.get(n) + edgeWeights.get(n).get(a));
					pred.put(a,n);
					updateHeap = true;
				} else {
					int vn = eval.get(n);
					int va = eval.get(a);
					int c = edgeWeights.get(n).get(a);
					if(vn+c<va) {
						eval.put(a, eval.get(n) + edgeWeights.get(n).get(a));
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

		return p;
	}

	
	
	public static List<Node> dijkstraLabelled(LabelledGraph g, LabelledNode s, LabelledNode t, final Object weightKey) {
		
		List<LabelledNode> nodes = g.getNodes();
		
		List<Node> p = new LinkedList<Node>();

		
		final Object markKey = "marked";
		final Object evalKey = "evaluation";
		final Object predKey = "predecessor";
		
		g.setAllNodeLabels(evalKey, 10000);
		g.setAllNodeLabels(predKey, null);
		g.removeAllNodeMarks(markKey);
		
		Comparator comp = new Comparator<LabelledNode>() {
			public int compare(LabelledNode o1, LabelledNode o2) {
				return (int) o1.getNodeLabel(evalKey) - (int) o2.getNodeLabel(evalKey);
			};
		};

		PriorityQueue<Node> heap = new PriorityQueue(nodes.size(), comp);

		g.setNodeLabel(s, evalKey, 0);
		g.setNodeMark(s, markKey);
		
		heap.addAll(nodes);

		while(heap.size()>0) {
			LabelledNode n = (LabelledNode) heap.poll();
			for(LabelledNode a : (List<LabelledNode>) n.getNeighbours()) {
				boolean updateHeap = false;
				if(!a.hasNodeMark(markKey)) {
					a.setNodeMark(markKey);
					g.setNodeLabel(a, evalKey, (int) n.getNodeLabel(evalKey) + (int) g.getEdgeLabel(n, a, weightKey));
					g.setNodeLabel(a, predKey, n);
					updateHeap = true;
				} else {
					int vn = n.getNodeLabel(evalKey);
					int va = a.getNodeLabel(evalKey);
					int c = n.getEdgeLabel(a, weightKey);
					if(vn+c<va) {
						a.setNodeLabel(evalKey, vn + va);
						a.setNodeLabel(predKey, n);
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
		
		LabelledNode n = t;
		LabelledNode tmp = t.getNodeLabel(predKey);
		p.add(t);
		while(tmp != null) {
			n = tmp;
			p.add(0,n);
			tmp = n.getNodeLabel(predKey);
		}
		
		System.out.println("path: "+p);

		return p;
	}
	
}
