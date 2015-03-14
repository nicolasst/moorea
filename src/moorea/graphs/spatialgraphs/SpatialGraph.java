package moorea.graphs.spatialgraphs;


import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import moorea.graphs.Graph;
import moorea.graphs.Node;

/**
 * Represent undirected moorea.graphs whose node have a location in a N dimensional space.
 * 
 * @author nicolas
 *
 */

public class SpatialGraph extends Graph<SpatialNode> {
//public class SpatialGraph extends AdjacencyListGraph<SimpleNode, Edge<SimpleNode>> {

	double[][] positionMatrix;
	Map<Node,List<Double>> nodePositions;
	
	//
	
	// should be for Node class: getSpacialNeighbours(double radius)

	public List<SpatialNode> getNodesWithinRadiusOfNode(Node n, double radius) {
	
		return null;
	}
	
	
	//
	
	public void toDot(String fileName) {
        FileWriter fw;
        PrintWriter pw;
        String color = "";
        String label = "";
        try {
        	fw = new FileWriter(fileName);
        	pw = new PrintWriter(fw);
            pw.println("graph todot {");
            for(Node n : nodes) {
            	pw.println(n.id+" [label=\""+n+"\"]");
            }
            for(Node n : nodes) {
            	for(Node nb : (List<Node>) n.getNeighbours()) {
            		if(n.id < nb.id) {
            			pw.println(n.id+" -- "+nb.id);
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

	public double[][] getPositionMatrix() {
		return positionMatrix;
	}

	public void setPositionMatrix(double[][] positionMatrix) {
		this.positionMatrix = positionMatrix;
	}
	
	
	public void setNodesPositions(Map<Node,List<Double>> nodePositions) {
		this.nodePositions = nodePositions;
	}
	
	public void setNodePosition(Node n, double x, double y) {
		nodePositions.put(n, new LinkedList(Arrays.asList(new Double[]{x,y})));
	}
	
	public void setNodePosition(Node n, List<Double> pos) {
		nodePositions.put(n,pos);
	}

	public void buildPositionMatrix() {
		int nbDims = nodePositions.get(nodePositions.keySet().iterator().next()).size();
		positionMatrix = new double[getNodes().size()][nbDims];
		for(Node n : getNodes()) {
			for (int i = 0; i < nbDims; i++) {
				positionMatrix[n.id][i] = nodePositions.get(n).get(i);
			}
		}
	}
	
	
	public void toTikz(String filename) {
		toTikz(filename, null, null);
	}
	
	public void toTikz(String filename, Map<Node,String> nodeColors, Map<Node,Map<Node,String>> edgeColors) {
		toTikz(filename, nodeColors, edgeColors, true, true);
		
	}
	
	public void toTikz(String filename, Map<Node,String> nodeColors, Map<Node,Map<Node,String>> edgeColors, boolean drawNodes, boolean drawEdges) {
		double MAXD = 1000.;
		double X_MAX = MAXD;
		double Y_MAX = MAXD;
		double page_x_max = 15;
		double page_y_max = 15;
		//
		boolean printGrid = false;
		int nbN = nodes.size();
		// build adjMat repr if not already the case
		double[][] pos = positionMatrix;
		double xReducFact = page_x_max/(double)X_MAX;
		double yReducFact = page_y_max/(double)Y_MAX;
		
		double maxX = 1.;
		double maxY = 1.;

		for (int i = 0; i < pos.length; i++) {
			if(pos[i][0] > maxX) {
				maxX = pos[i][0];
			}
			if(pos[i][1] > maxY) {
				maxY = pos[i][1];
			}
		}

		xReducFact *= 1./maxX;
		yReducFact *= 1./maxY;

		
		try {
			FileWriter fw = new FileWriter(filename);
			PrintWriter pw = new PrintWriter(fw);
			// latex preamble
			pw.println("\\documentclass{article}\n\\usepackage[utf8]{inputenc}\n\\usepackage{graphicx}\n\\usepackage{color}\n\\usepackage{tikz}\n\\usetikzlibrary{shapes}\n\\usepackage[francais]{babel}\n\\usepackage[a3paper]{geometry}\n\\usepackage{a4wide}\n\\addtolength{\\oddsidemargin}{-.875in}\n\\begin{document}\n\\begin{tikzpicture}");

			if(printGrid) {
				pw.println("\\draw[step=1cm] (0,0) grid (15,15);");
			}
			// draw links
			double fade,p;
			if(drawEdges)
			for(Node n : nodes) {
				for(Node nb : (List<Node>) n.getNeighbours()) {
					int i = n.id;
					int j = nb.id;
					if(i>j) {
						continue;
					}
					String color = "gray!80";
					if(edgeColors != null) {
						if(edgeColors.get(n) != null && edgeColors.get(n).get(nb) != null) {
							color = edgeColors.get(n).get(nb);
						} else if(edgeColors.get(nb) != null && edgeColors.get(nb).get(n) != null) {
							color = edgeColors.get(nb).get(n);
						}
					}
					//System.out.println(pos[i][0]*MAXD*xReducFact+" "+String.format("%.6f",pos[i][0]*MAXD*xReducFact));
					//String x1 = String.format("%.6f",pos[i][0]*MAXD*xReducFact);
					//String y1 = String.format("%.6f",pos[i][1]*MAXD*xReducFact);
					//String x2 = String.format("%.6f",pos[j][0]*MAXD*xReducFact);
					//String y2 = String.format("%.6f",pos[j][1]*MAXD*xReducFact);
					String x1 = ""+pos[i][0]*MAXD*xReducFact;
					String y1 = ""+pos[i][1]*MAXD*xReducFact;
					String x2 = ""+pos[j][0]*MAXD*xReducFact;
					String y2 = ""+pos[j][1]*MAXD*xReducFact;
					pw.println("\\draw [color="+color+",line width=0.05pt] ("+x1+","+y1+") -- ("+x2+","+y2+");");
				}
			}
			// draw nodes
			//if(nbN <=1000 && edges.size()<=4000) {
			
			if(drawNodes)
			for(Node n : nodes) {
				int i = n.id;
				//pw.println("\\draw [fill,color="+"orange"+"!60] ("+(pos[i][0]*MAXD*xReducFact)+","+(pos[i][1]*MAXD*yReducFact)+") circle (0.05);");
				String color="orange";
				if(nodeColors != null && nodeColors.containsKey(n)) {
					color = nodeColors.get(n);
				}
				//String x1 = String.format("%.6f",pos[i][0]*MAXD*xReducFact);
				//String y1 = String.format("%.6f",pos[i][1]*MAXD*xReducFact);
				String x1 = ""+pos[i][0]*MAXD*xReducFact;
				String y1 = ""+pos[i][1]*MAXD*xReducFact;
				pw.println("\\draw [fill,color="+color+"!60] ("+x1+","+y1+") circle (0.08);");
			}
			// end
			pw.println("\\end{tikzpicture}\n\\end{document}\n");
			pw.close();
		} catch(Exception ex) {
 			System.out.println(" pb IO "+ex);
			ex.printStackTrace();
			System.exit(1);
		}
	}

	
}
