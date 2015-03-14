package moorea.graphs.io;


import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import moorea.graphs.DirectedNode;
import moorea.graphs.Graph;
import moorea.graphs.Node;

/**
 * Contains methods to do graph IO on the disk.
 * 
 * TODO: graph input
 * 
 * @author nicolas
 *
 */

public class GraphIO {
	
	/**
	 * Writes the graph in the dotty file format
	 * 
	 * @param g
	 * @param fileName
	 */
	
	public static void toDot(Graph g, String fileName) {
		toDot(g, fileName, null);
	}

	
	public static void toDot(Graph g, String fileName, Map<Node, String> nodeLabels) {
		List<Node> nodes = g.getNodes();
		
		
		if(nodes.size() > 0 && nodes.get(0) instanceof DirectedNode) {
			toDotDirected(g, fileName);
			return;
		}
		
        FileWriter fw;
        PrintWriter pw;
        String color = "";
            try {
        	fw = new FileWriter(fileName);
        	pw = new PrintWriter(fw);
            pw.println("graph todot {");
            for(Node n : nodes) {
            	// get node label
            	String label = ""+n.id;
            	if(nodeLabels != null) {
            		label = nodeLabels.get(n);
            	}
            	// output node
            	pw.println(n.id+" [label=\""+label+"\"];");
            }
            for(Node n : nodes) {
            	for(Node nb : (List<Node>) n.getNeighbours()) {
            		if(n.id < nb.id) {
            			pw.println(n.id+" -- "+nb.id+";");
            		}
            	}
            }
            pw.println("}");
            pw.flush();
            pw.close();
        } catch(Exception ex) {
        	System.out.println(ex);
        	ex.printStackTrace();
        	System.exit(1);
        }        	
	}

	
	public static void toDotDirected(Graph g, String fileName) {
        FileWriter fw;
        PrintWriter pw;
        String color = "";
        String label = "";
        List<DirectedNode> nodes = g.getNodes();
        try {
        	fw = new FileWriter(fileName);
        	pw = new PrintWriter(fw);
            pw.println("digraph todot {");
            for(DirectedNode n : (List<DirectedNode>) nodes) {
            	pw.println(n.id+" [label=\""+n+"\"]");
            }
            for(DirectedNode n : (List<DirectedNode>) nodes) {
            	for(DirectedNode nb : (List<DirectedNode>) n.getOutgoingArcs()) {
            		pw.println(n.id+" -> "+nb.id);
            	}
            }
            pw.println("}");
            pw.flush();
            pw.close();
        } catch(Exception ex) {
        	System.out.println(ex);
        	ex.printStackTrace();
        	System.exit(1);
        }		
	}
	
	/**
	 * Writes the graph in the file format of Gephi
	 * 
	 * @param g
	 * @param fileName
	 */
	
	public static void toGephi(Graph g, String fileName) {
		List<Node> nodes = g.getNodes();
		
        FileWriter fw;
        PrintWriter pw;
        String color = "";
        String label = "";
        try {
        	fw = new FileWriter(fileName);
        	pw = new PrintWriter(fw);
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<gexf xmlns=\"http://www.gexf.net/1.2draft\" version=\"1.2\">");
            pw.println("    <graph mode=\"static\" defaultedgetype=\"undirected\">");
            pw.println("        <nodes>");
            for(Node n : (List<Node>) nodes) {
            	pw.println("<node id=\""+n.id+"\" label=\""+n+"\" />");
            }
            pw.println("        </nodes>");
            pw.println("        <edges>");
            int countEdge=0;
            for(Node n : (List<Node>) nodes) {
            	for(Node nb : (List<Node>) n.getNeighbours()) {
            		if(n.id < nb.id) {
            			pw.println("<edge id=\""+countEdge+"\" source=\""+n.id+"\" target=\""+nb.id+"\" />");
            			countEdge++;
            		}
            	}
            }
            pw.println("        </edges>");
            pw.println("    </graph>");
            pw.println("</gexf>");
            pw.flush();
            pw.close();
        } catch(Exception ex) {
        	System.out.println(ex);
        	ex.printStackTrace();
        	System.exit(1);
        }		
	}
	
	/**
	 * Writes the graph as a list of pair of adjacent node ids.
	 * 
	 * @param g
	 * @param fileName
	 */
	
	public static void toAdjacencyList(Graph g, String fileName) {
		List<Node> nodes = g.getNodes();
		
		if(nodes.size() > 0 && nodes.get(0) instanceof DirectedNode) {
			toDotDirected(g, fileName);
			return;
		}
		
        FileWriter fw;
        PrintWriter pw;
        String color = "";
        String label = "";
        try {
        	fw = new FileWriter(fileName);
        	pw = new PrintWriter(fw);
            for(Node n : nodes) {
            	for(Node nb : (List<Node>) n.getNeighbours()) {
            		if(n.id < nb.id) {
            			pw.println(n.id+" "+nb.id);
            		}
            	}
            }
            pw.flush();
            pw.close();
        } catch(Exception ex) {
        	System.out.println(ex);
        	ex.printStackTrace();
        	System.exit(1);
        }        	
	}

}
