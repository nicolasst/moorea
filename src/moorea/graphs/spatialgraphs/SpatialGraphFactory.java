package moorea.graphs.spatialgraphs;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moorea.graphs.Graph;
import moorea.graphs.Node;
import moorea.graphs.algorithms.GraphExtraction;
import moorea.graphs.construction.GraphFactory;

/**
 * Builds SpatialGraph moorea.graphs.
 * Contains special methods to generate positions.
 * 
 * @author nicolas
 *
 */

public class SpatialGraphFactory extends GraphFactory {

	public SpatialGraphFactory(Class graphClass, Class nodeClass) {
		super(graphClass, nodeClass);
	}

	/** Create nodes positions matrix in 2 dimensions */
	
	public double[][] genNodeLocation(int nbNodes) {
		return genNodeLocation(nbNodes,2);
	}

	/** Create nodes positions matrix in arbitrary dimension */
	
	public double[][] genNodeLocation(int nbNodes, int nbDimensions) {
		double[][] pos = new double[nbNodes][nbDimensions];
		for(int i=0;i<nbNodes;i++) {
			for(int j=0;j<nbDimensions;j++) {
				pos[i][j] = random.nextDouble();
				pos[i][j] = random.nextDouble();
			}
		}
		return pos;
	}
	
	/** Generate spatial graph from node matrix position */
	
	public Graph generateSituatedRandomGraphRadius(int nbNodes, int nbDimensions, double radius) {
		double[][] pos = genNodeLocation(nbNodes, nbDimensions);
		return generateSituatedRandomGraphRadius(nbNodes,pos,radius);
	}
	
	public  Graph generateSituatedRandomGraphRadius(int nbNodes, double[][] pos, double radius) {
		SpatialGraph sg = new SpatialGraph();
		int i,j;
		// create spatial nodes from position matrix
		sg.setPositionMatrix(pos);
		for(i=0;i<nbNodes;i++) {
			SpatialNode sn = new SpatialNode(i);
			sn.setCoord(pos[i]);
			sg.addExistingNode(sn);
		}
		// create edges
		double dist;
		int nbDimensions = pos[0].length;
		for(i=0; i<nbNodes-1; i++) {
			for(j=i+1;j<nbNodes;j++) {
				dist=0.;
				for(int k=0; k<nbDimensions; k++) {
					dist += ((double)(pos[i][k]-pos[j][k]))*((double)(pos[i][k]-pos[j][k]));
				}
				dist = Math.sqrt(dist);
				if(dist < radius) {
					sg.createEdgeByIndex(i, j);
				}
			}
		}
		sg.toTikz("g.tex");
		sg.toDot("g.dot");
		return sg;
	}
	
	public  Graph generateRandomSpatialGraphRadiusIncreasing(int nbNodes, int nbDimensions, double radius) {
		double[][] pos = genNodeLocation(nbNodes, nbDimensions);
		return generateRandomSpatialGraphRadiusIncreasing(nbNodes,pos,radius);
	}

	
	public static Graph generateRandomSpatialGraphRadiusIncreasing(int nbNodes, double[][] pos, double radius) {
		// create graph
		SpatialGraph sg = new SpatialGraph();
		// create nodes
		sg.setPositionMatrix(pos);
		double[][] distMatrix = new double[nbNodes][nbNodes];
		int[] color = new int [nbNodes];
		int i,j;
		for(i=0;i<nbNodes;i++) {
			SpatialNode sn = new SpatialNode(i);
			sn.setCoord(pos[i]);
			sg.addExistingNode(sn);
		}
		// create edges
		double dist;
		int nbDim = pos[0].length;
		for(i=0;i<nbNodes-1;i++) {
			for(j=i+1;j<nbNodes;j++) {
				dist=0.;
				for(int k=0;k<nbDim;k++) {
					dist += ((double)(pos[i][k]-pos[j][k]))*((double)(pos[i][k]-pos[j][k]));
				}
				dist = Math.sqrt(dist);
				if(dist < radius) {
					sg.createEdgeByIndex(i,j);
				}
				distMatrix[i][j] = dist;
				distMatrix[j][i] = dist;
			}
		}
		// connect the different connected component by increasing progresively the radius
		int ccColor = 0;
		double progrRadius = radius;
		Graph gcc = null;
		do {
			// get connected components]
			// what solved the bug:
// 0) change type of getConnectedComponents:
//			public static <V extends Node, E extends Edge> List<Graph<V,E>> getConnectedComponents(Graph<V,E> g) {
//		->	public static <V extends Node, E extends Edge, G extends Graph<V,E>> List<Graph<V,E>> getConnectedComponents(G g) {
// 1) because Collections in java are not covariant (List<SubClassOfGraph> is not a subtype of List<Graph>)
			// List<Graph> lgcc -> List<? extends Graph> lgcc
// 2) needs to parametrise everything in the call
			// GraphAlgorithm.getConnectedComponents -> GraphAlgorithm.<SpatialNode,Edge<SpatialNode>,SpatialGraph>getConnectedComponents
			//List<? extends Graph> lgcc = GraphAlgorithm.<SpatialNode,Edge<SpatialNode>,SpatialGraph>getConnectedComponents(sg);
			List<? extends Graph> lgcc = GraphExtraction.getConnectedComponents(sg);
			ccColor = 0;
			for(Graph cc : lgcc) {
				for(Node nn : (List<Node>) cc.getNodes()) {
					color[nn.id] = ccColor;
				}
				ccColor++;
			}
			// update radius parameter
			progrRadius = progrRadius + radius;
			// connect the two closest connected component by their nearest neighbour
			Map<Integer,Integer> mapNewCC = new HashMap();
			for(i=0;i<nbNodes-1;i++) {
				for(j=i+1;j<nbNodes;j++) {
					if(distMatrix[i][j] < progrRadius && color[i] != color[j]) {
						sg.createEdgeByIndex(i,j);
						mapNewCC.put(Math.min(color[i],color[j]),Math.max(color[i],color[j]));
					}
				}
			}
			// update the color of the new connected component
			int noChangeCol=-1;
			for(i=0;i<nbNodes;i++) {
				int c = color[i]; // (opt pas indis pour aller plus vite)
				if(c != noChangeCol) {
					Integer iw = mapNewCC.get(c);
					if(iw != null) {
						color[i] = iw.intValue();
					} else {
						noChangeCol = c;
					}
				}
			}
		} while(ccColor>1);
		sg.toTikz("g.tex");
		sg.toDot("g.dot");
		return sg;
	}
	
}
