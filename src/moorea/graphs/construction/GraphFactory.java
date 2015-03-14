package moorea.graphs.construction;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import moorea.graphs.ContainerGraph;
import moorea.graphs.ContainerNode;
import moorea.graphs.Graph;
import moorea.graphs.LabelledGraph;
import moorea.graphs.LabelledNode;
import moorea.graphs.Node;
import moorea.graphs.SimpleGraph;
import moorea.graphs.SimpleNode;
import moorea.graphs.algorithms.GraphTransformation;
import moorea.graphs.io.GraphDisplay;
import moorea.graphs.io.GraphIO;
import moorea.graphs.traversal.GraphNodeIteratorBFS;
import moorea.graphs.weighted.WeightedGraph;
import moorea.graphs.weighted.WeightedNode;
import moorea.maths.objects.ScopeFactory;
import moorea.misc.ArrayMisc;
import moorea.misc.BidiMap;
import moorea.misc.IntegerGenerator;
import moorea.misc.Tupple2;


/**
 * The Graph class is very flexible thanks to the fact that the type of nodes
 * used can be parameterized.
 * 
 * The type of graph and nodes used can be parameterized in order to build any
 * kind of moorea.graphs.
 * 
 * By default SimpleGraph are build through the simpleGraphFactory field.
 * 
 * @author nicolas
 *
 * @param <G>
 * @param <V>
 */

public class GraphFactory<G extends Graph, V extends Node> {

	protected Class<G> graphClass;
	protected Class<V> nodeClass;
	
	NodeFactory nodeFactory = new NodeFactory(SimpleNode.class);

	public Random random;
	public static Random randomStatic = new Random();
	
	static IntegerGenerator graphIdGen = new IntegerGenerator(0);

	public static GraphFactory<SimpleGraph,SimpleNode> simpleGraphFactory = new GraphFactory(SimpleGraph.class,SimpleNode.class);
	public static GraphFactory<LabelledGraph,LabelledNode> labelledGraphFactory = new GraphFactory(LabelledGraph.class,LabelledNode.class);
	public static GraphFactory<ContainerGraph,ContainerNode> containerGraphFactory = new GraphFactory(ContainerGraph.class,ContainerNode.class);

	
	//public static GraphFactory<SpatialGraph,SimpleNode,Edge<SimpleNode>> spatialGraphFactory = new GraphFactory(SpatialGraph.class,SpatialNode.class,Edge.class);

	
	public GraphFactory(NodeFactory nf) {
		nodeFactory = nf;
		random = randomStatic;
	}
	
	public GraphFactory(GraphFactory gf) {
		graphClass = gf.graphClass;
		nodeClass = gf.nodeClass;
		nodeFactory = new NodeFactory(nodeClass);
		random = randomStatic;
	}
	
	public GraphFactory(Class<G> graphClass, Class<V> nodeClass) {
		this.graphClass = graphClass;
		this.nodeClass = nodeClass;
		nodeFactory = new NodeFactory(nodeClass);
		random = randomStatic;
	}
	
	//
	// Empty moorea.graphs
	//
	
	public G generateEmptyGraph() {
		G g = createGraph();
		return g;
	}
	
	public G createGraph() {
		return createGraph(graphIdGen);
	}
	
	public G createGraph(IntegerGenerator ig) {
		G graph = null;
		try {
			// the class K must explicitly implement the constructor (/!\constructors are not inherited)
			// moreover the constructor must have an explicit 'Integer' parameter and not an 'int' one.
			graph = graphClass.getDeclaredConstructor(new Class[]{}).newInstance(new Object[]{});
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		graph.setNodeClass(nodeClass);
		graph.setNodeFactory(new NodeFactory(nodeClass));
		return graph;
	}
	
	
	/**
	 * Creates an empty graph whose type and node type are the same as the graph given in parameter
	 * @param graph
	 * @return
	 */
	
	public static <G extends Graph> G createEmptyGraphOfSameType(G graph) {
		return (G) createEmptyGraphOfGivenType((Class<G>)graph.getClass(), graph.nodeClass);
	}
	
	/**
	 * Creates an empty graph of arbitrary graph and node types
	 * @param graphClass
	 * @param nodeClass
	 * @return
	 */
	
	public static <G extends Graph, V extends Node> G createEmptyGraphOfGivenType(Class<G> graphClass, Class<V> nodeClass) {
		IntegerGenerator ig = graphIdGen;
		G newGraph = null;
		try {
			// the class K must explicitly implement the constructor (/!\constructors are not inherited)
			// moreover the constructor must have an explicit 'Integer' parameter and not an 'int' one
			newGraph = (G) graphClass.getDeclaredConstructor(new Class[]{}).newInstance(new Object[]{});
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		newGraph.setNodeClass(nodeClass);
		newGraph.setNodeFactory(new NodeFactory(nodeClass));
		return newGraph;
	}

	
	//
	// Graph copy
	//

	/**
	 * Create a new graph of SimpleNode copying the structure of the source graph.
	 * @param source
	 * @return Tupple, first element: the new graph,
	 */
	
	public Tupple2<G,BidiMap> createGraphCopyingStructure(Graph source) {
		G g = createGraph();
		Map<Node,V> map = new HashMap<Node, V>();
		BidiMap<Node,V> bm = new BidiMap<Node, V>();
		for(Node n : (List<Node>) source.getNodes()) {
			V nn = (V) g.createNewNode();
			g.addExistingNode(nn);
			bm.put(n, nn);
		}
		for(Node n1 : (List<Node>) source.getNodes()) {
			V n1n = bm.getAB(n1);
			for(Node n2 : (List<Node>) n1.getNeighbours()) {
				g.createEdge(bm.getAB(n1), bm.getAB(n2));
			}
		}
		//g.setBidiNodeMap(bm);
		//return g;
		return new Tupple2(g,bm);
	}
	
	//
	// Graph from objects lists
	//
	
	public static <K> ContainerGraph<K> buildContainerGraphFromObjectLists(List<List<K>> objectLists) {
		ContainerGraph<K> g = GraphFactory.containerGraphFactory.createGraph();
		List<K> allObjects = ScopeFactory.mergeScopes(objectLists);
		
		// create nodes
		for(K o : allObjects) {
			g.addNewNode(o);
		}

		// create edges
		for(List<K> l : objectLists) {
			K[] vars = (K[]) l.toArray();
			//ArrayMisc.dispArray(vars);
			for (int i = 0; i < vars.length-1; i++) {
				for (int j = i+1; j < vars.length; j++) {
					g.createEdgeByContent(vars[i], vars[j]);
				}
			}
		}
		
		return g;
	}
	
	

	//
	// Specific moorea.graphs : deterministic and simple
	//
	
	
	/**
	 * Creates and returns the complete graph K_n.
	 */
	
	public static Graph generateCompleteSimpleGraph(int n) {
		Graph g = new Graph();
		g.addNewNodes(n);
		for(int i = 0; i<n-1; i++) {
			for(int j = i+1; j<n; j++) {
				//System.out.println(i+"  "+j);
				g.createEdgeByIndex(i, j);
			}
		}
		return g;
	}
	
	public G generateCompleteGraph(int n) {
		G g = createGraph();
		g.addNewNodes(n);
		for(int i = 0; i<n-1; i++) {
			for(int j = i+1; j<n; j++) {
				g.createEdgeByIndex(i, j);
			}
		}
		return g;
	}
	
	public static <G extends Graph> G generateCompleteGraph(GraphFactory gf, int n) {
		G g = (G) gf.createGraph();
		g.addNewNodes(n);
		for(int i = 0; i<n-1; i++) {
			for(int j = i+1; j<n; j++) {
				g.createEdgeByIndex(i, j);
			}
		}
		return g;
	}
	
	public G generateLinearGraph(int size) {
		return generateGridGraph(size, 1, false);
	}
	
	public G generateGridGraph(int width, int heigth) {
		return generateGridGraph(width, heigth, false);
	}
	
	public G generateGridGraph(int width, int heigth, boolean linkFirstAndLastRow) {
		G g = createGraph();
		Node[] lastRow = new Node[heigth];
		Node[] currentRow = new Node[heigth];
		Node[] firstRow = new Node[heigth];
		if(width>0) {
			for(int j=0;j<heigth;j++) {
				lastRow[j] = nodeFactory.createNode();
				firstRow[j] = lastRow[j];
				g.addExistingNode(lastRow[j]);
				// links nodes of the same row
				if(j>0) {
					g.createEdge(lastRow[j-1], lastRow[j]);
				}
			}
		}
		for(int i=1;i<width;i++) {
			for(int j=0;j<heigth;j++) {
				// links each new node with the cooresponding node of the previous row
				currentRow[j] = nodeFactory.createNode();
				g.addExistingNode(currentRow[j]);
				g.createEdge(lastRow[j], currentRow[j]);
				// links nodes of the same row
				if(j>0) {
					g.createEdge(currentRow[j-1], currentRow[j]);
				}
			}
			// updates lasts row
			for(int j=0;j<heigth;j++) {
				lastRow[j] = currentRow[j];
			}
		}
		if(linkFirstAndLastRow) {
			for(int j=0;j<heigth;j++) {
				g.createEdge(firstRow[j], lastRow[j]);
			}
		}
		return g;
	}

	public G generateCycleGraph(int circonference) {
		return generateGridGraph(circonference, 1);
	}
	
	public G generateCycleGraph(int circonference, int width) {
		return generateGridGraph(circonference, width, true);
	}
	
	public G generateRegularRingLattice(int nbNodes, int k) {
		// create ring graph
		G g = generateCycleGraph(nbNodes, 1);
		// connect a node to K (meanDegree neighbours)
		for(int i = 0; i<nbNodes; i++) {
			for(int j = i+1; j<i+1+k; j++) {
				g.createEdge(g.getNodeByIndex(i), g.getNodeByIndex(j % nbNodes));
			}
		}
		return g;
	}
	
	public G generateHyperCube(int size) {
		G g = createGraph(new IntegerGenerator());;
		if(size == 0) {
			g.addNewNode();
		} else {
			g = createGraph(new IntegerGenerator());
			Graph g1 = generateHyperCube(size-1);
			Graph g2 = generateHyperCube(size-1);
			g.getNodes().addAll(g1.getNodes());
			g.getNodes().addAll(g2.getNodes());
			for(int i=0; i<g1.getNodes().size(); i++) {
				g.createEdge((Node)g1.getNodes().get(i), (Node)g2.getNodes().get(i));
			}
			g1=null;
			g2=null;
			GraphTransformation.renumberNodesIdsSequentiallyFrom0(g);
		}
		return g;
	}
	
	public G generateStarGraph(int nbNodes) {
		G g = generateUndirectedTreeGraph(nbNodes-1, 1);
		return g;
	}
	
	public G generateCompleteBipartiteGraph(int m, int n) {
		G g = createGraph();
		g.addNewNodes(m+n);
		for(int i = 0; i<m; i++) {
			for(int j = m; j<m+n; j++) {
				g.createEdge(g.getNodeByIndex(i), g.getNodeByIndex(j));
			}
		}
		return g;
	}
	
	////  complete trees
	
	public G generateUndirectedTreeGraph(int nbChilds, int depth) {
		G g = createGraph();
		Node root = generateTreeStructure(nbChilds, depth, false, false);
		GraphNodeIteratorBFS it = new GraphNodeIteratorBFS(root);
		while(it.hasNext()) {
			Node n = it.next();
			g.addExistingNode(n);
		}
		return g;
	}
	
	public G generateDirectedTreeGraph(int nbChilds, int depth, boolean dirRoot2Leaves) {
		G g = createGraph();
		Node root = generateTreeStructure(nbChilds, depth, true, dirRoot2Leaves);
		GraphNodeIteratorBFS it = new GraphNodeIteratorBFS(root);
		while(it.hasNext()) {
			Node n = it.next();
			g.addExistingNode(n);
		}
		return g;
	}
	
	public Node generateTreeStructure(int nbChilds, int depth, boolean directed, boolean dirRoot2Leaves) {
		Node n = nodeFactory.createNode();
		if(depth == 0) {
			return n;
		}
		for(int i=0; i<nbChilds; i++) {
			Node child = generateTreeStructure(nbChilds,depth-1, directed, dirRoot2Leaves);
			if(!directed) {
				n.addNeighbour(child);
				child.addNeighbour(n);
			} else {
				if(dirRoot2Leaves) {
					n.addNeighbour(child);
				} else {
					child.addNeighbour(n);
				}
			}
		}
		return n;
	}

	//// hierarchical moorea.graphs
	


	/* Génération de réseaux hierarchiques */


	/* Génération de réseaux hierarchiques :
		Les graphes sont générés récursivement // profondeur l :
			l = 0 : le graphe généré est un graphe complet de nBase noeud
			l > 0 : (l*(l-1)/2) * (nBase-1) graphes sont générés:
						pour chaque 0<=k<=l-1:
							génère nBase-1 fois les graphes de niveau k
					le centre est le premier noeud du graphe de niveau l
					pour chaque graphe généré les noeud externes sont reliés au centre
					les noeuds externes du niveau l sont définis comme les noeuds externes des nBase-1 graphes de niveau l-1
	*/

	// extNodes : permet de transmettre au niveau (l) appelant les nodes externes (de l-1)

	// hierarchique au sens de :
	//	linkCenters = false : l'exemple nBase=4 de Erzsebet Ravasz in Hierarchical organization in complex networks
	//	linkCenters = true : l'exemple nBase=4 de Albert Lazslo Barbasi in Network Biolmogy: understanding the cell's functional organization

	// par defaut: center = nodes.get(0)
	
	public static Graph genHierarchicalGraph(int nBase, int level) {
		Graph g = genHierarchicalGraph(nBase, nBase-1, level, true, null);
		return g;
	}
	
	public static Graph genHierarchicalGraph(int nBase, int nbChilds, int level) {
		Graph g = genHierarchicalGraph(nBase, nbChilds, level, true, null);
		return g;
	}
	
	public static Graph genHierarchicalGraph(int nBase, int nbChilds, int level, boolean linkCenters,  List<Node> extNodes) {
		Graph g = generateCompleteSimpleGraph(nBase);
		Node center = g.getNodeByIndex(0);
		// recursion terminal case
		if(level==0) {
			if(extNodes != null) {
				for(Node n : (List<Node>) g.getNodes()) {
					extNodes.add(n);
				}
				//extNodes.remove(center);
			}
			return g;
		}
		// generate descendants
		Graph[][] sons = new Graph[level][nbChilds];
		Map<Integer,List<Node>> sonsExtNodes = new HashMap();
		for(int k=0;k<level;k++) {
			sonsExtNodes.put(k,new LinkedList());		
			for(int i=0;i<nbChilds;i++) {
				sons[k][i] = genHierarchicalGraph(nBase,nbChilds,k,linkCenters,sonsExtNodes.get(k));
			}
		}
		// ajoute les noeuds & liens des fils au graphe courant
		for(int k=0;k<level;k++) {
			for(int i=0;i<nbChilds;i++) {
				g.getNodes().addAll(sons[k][i].getNodes());
			}
		}
		// créer les liens avec les noeuds externes
		for(List<Node> ln : sonsExtNodes.values()) {
			for(Node n : ln) {
				g.createEdge(center,n);
			}
		}
		if(linkCenters) {
			// relie les centres des graphes de niveau l-1 entre eux
			Node[] centers = new Node[nbChilds];
			for(int i=0;i<nbChilds;i++) {
					centers[i] = sons[level-1][i].getNodeByIndex(0);
			}
			for(int i=0;i<nbChilds-1;i++) {
				for(int j=i+1;j<nbChilds;j++) {	
					g.createEdge(centers[i],centers[j]);
				}
			}
		} // TODO else?
		// create extNodes list fort parent
		if(extNodes != null) {
			extNodes.addAll(sonsExtNodes.get(level-1));
		}
		return g;
	}
	
	
	//
	// Specific moorea.graphs : random
	//
	
	//// erdos renyi
	
	public static Graph generateRandomSimpleGraphErdosRenyi(int nbNodes, Random random, double probability) {
		Graph g = new Graph();
		g.addNewNodes(nbNodes);
		for(int i = 0; i<nbNodes-1; i++) {
			for(int j = i+1; j<nbNodes; j++) {
				if(random.nextDouble() < probability) {
					g.createEdgeByIndex(i, j);
				}
			}
		}
		return g;
	}
	
	public static <V extends Node> Graph<V> generateRandomGraphErdosRenyi(int nbNodes, Random random, double probability, Class<V> nodeClass) {
		Graph<V> g = new Graph();
		g.addNewNodes(nbNodes);
		for(int i = 0; i<nbNodes-1; i++) {
			for(int j = i+1; j<nbNodes; j++) {
				if(random.nextDouble() < probability) {
					g.createEdgeByIndex(i, j);
				}
			}
		}
		return g;
	}
	
	public static <G extends Graph, V extends Node> G generateRandomGraphErdosRenyi(int nbNodes, Random random, double probability, Class<G> graphClass, Class<V> nodeClass) {
		G g = null;
		try {
			g = graphClass.getDeclaredConstructor(new Class[]{}).newInstance(new Object[]{});

		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		g.addNewNodes(nbNodes);
		for(int i = 0; i<nbNodes-1; i++) {
			for(int j = i+1; j<nbNodes; j++) {
				if(random.nextDouble() < probability) {
					g.createEdgeByIndex(i, j);
				}
			}
		}
		return g;
	}
	
	public G generateRandomGraphErdosRenyi(int nbNodes, double probability) {
		G g = createGraph();
		g.addNewNodes(nbNodes);
		for(int i = 0; i<nbNodes-1; i++) {
			for(int j = i+1; j<nbNodes; j++) {
				if(random.nextDouble() < probability) {
					g.createEdgeByIndex(i, j);
				}
			}
		}
		return g;
	}
	
	//// scale free

	// TODO cite or recode
	
	public G generateScaleFreeGraph(int size) {
		G target = createGraph();
		List<V> vertexList = new ArrayList<V>();
		List<Integer> degrees = new ArrayList<Integer>();
		int degreeSum = 0;
		IntegerGenerator ig = target.getNodeIdGenerator();
		for (int i = 0; i < size; i++) {
			V newVertex = (V) nodeFactory.createNode();
			target.addExistingNode(newVertex);
			int newDegree = 0;
			while ((newDegree == 0) && (i != 0)) 
			{
				for (int j = 0; j < vertexList.size(); j++) {
					if ((degreeSum == 0)
							|| (random.nextInt(degreeSum) < degrees.get(j)))
					{
						degrees.set(j, degrees.get(j) + 1);
						newDegree++;
						degreeSum += 2;
						if (random.nextInt(2) == 0) {
							target.createEdge(vertexList.get(j), newVertex);
						} else {
							target.createEdge(newVertex, vertexList.get(j));
						}
					}
				}
			}
			vertexList.add(newVertex);
			degrees.add(newDegree);
		}
		return target;
	}

	//// watts strogatz
	
	public G generateRandomGraphWattsStrogatz(int nbNodes, int meanDegree, double beta) {
		// otherwise might loop infinitely
		assert(nbNodes > 2*meanDegree);
		// create ring graph
		G g = generateRegularRingLattice(nbNodes, meanDegree);
		// rewire each node with the beta probability
		for(int i = 0; i<nbNodes-1; i++) {
			for(int j = i+1; j<i+1+meanDegree; j++) {
				if(random.nextDouble() < beta) {
					j = j % nbNodes;
					g.removeEdgeById(i, j);
					while(true) {
						int idx = random.nextInt(g.getNodes().size());
						// avoids loops or multigraphs
						if(idx == i || g.hasEdgeByIndex(i, idx)) {
							continue;
						}
						// create edge
						else {
							g.createEdgeByIndex(i, idx);
							break;
						}
					}
				}
			}
		}
		return g;
	}	
	
	/**
	 * Generate moorea.graphs based on a string description
	 
	 	<graph type>_<parameters>
	 	
	 	graph types:	parameters:							comments:
	 	// random graphs
	 	sf				<nb nodes>							scale free
	 	er				<nb nodes>_p<link prob>				erdos renyi
	 					<nb nodes>_d<average density>		erdos renyi
	 	ws				<nb node>_<K>_<beta>				watts strogatz (K: mean degree, beta: rewiring proba)
	 	// non random graphs
	 	grid			<width>_<height>					grid graph
	 	cycle			<circumference>_<thickness>			cycle graph
	 	tree			<depth>_<nb childs>					perfect tree
	 	kn				<nb nodes>							complete graph
	 	kmn				<nb nodes>							complete bipartite graph
	 	hyperc			<nb dimensions>						hypercube
	 	regringlat		<nb nodes>_<nb neighb>				regular ring lattice
	 	star			<nb nodes>							star graph
	 	
	 	exemples:
	 	"grid_10_10", "er_100_d3"
	 */
	
	public G generateGraph(String graphCode) {
		G g = null;
		Scanner sc = new Scanner(graphCode);
		sc.useDelimiter("_");
		String gType = sc.next();

		// scale free random graph
		if(gType.equals("sf") || gType.endsWith("ba")) {

			int nbNodes = sc.nextInt();
			if(nbNodes == 0) {
				return generateEmptyGraph();
			}
			return generateScaleFreeGraph(nbNodes);
		}
		// erdos renyi random graph
		if(gType.equals("er")) {
			int nbNodes = sc.nextInt();
			String p1 = sc.next();
			double proba = 0.0;
			if(p1.charAt(0) == 'p') {
				proba = new Double(p1.substring(1));
			} else
			if(p1.charAt(0) == 'd') {
				proba = new Double(p1.substring(1)) / ((double) nbNodes);
			} else {
				System.err.println(p1+" bad graph code element");
				System.exit(1);
			}
			return generateRandomGraphErdosRenyi(nbNodes, proba);
		} 
		// watts strogatz
		if(gType.equals("ws")) {
			int nbNodes = sc.nextInt();
			if(nbNodes == 0) {
				return generateEmptyGraph();
			}
			int meanDegree = sc.nextInt();
			double beta = sc.nextDouble();
			return generateRandomGraphWattsStrogatz(nbNodes, meanDegree, beta);
		} 
		// grid graph
		if(gType.equals("grid")) {
			int width = sc.nextInt();
			int heigth = sc.nextInt();
			return generateGridGraph(width, heigth);
		}
		// grid graph, square
		if(gType.equals("gridsq")) {
			int totNodes = sc.nextInt();
			if(totNodes == 0) {
				return generateEmptyGraph();
			}
//			double ratio=0.5;
//			if(sc.hasNext()) {
//				ratio = sc.nextDouble();
//			}
			int width1 = (int) (Math.ceil(Math.sqrt(totNodes)));
//			int width2 = (int) (Math.ceil(Math.sqrt(totNodes))*(1.-ratio));
			g = generateGridGraph(width1, width1);
			int nbNodesToRemove = width1*width1 - totNodes;
			for (int i = 0; i < nbNodesToRemove; i++) {
				g.removeNode(g.getNodeById(i));
			}
			GraphTransformation.renumberNodesIdsSequentiallyFrom0(g);
			return g;
		}
		// cycle graph
		if(gType.equals("cycle")) {
			int circumference = sc.nextInt();
			int thickness = 1;
			if(sc.hasNext()) {
				thickness = sc.nextInt();
			}
			return generateGridGraph(circumference, thickness, true);
		}
		// perfect tree
		if(gType.equals("tree")) {
			int depth = sc.nextInt();
			int nbChilds = sc.nextInt();
			return generateUndirectedTreeGraph(nbChilds,depth);
		}
		
		// complete graph
		if(gType.equals("kn")) {
			int nbNodes = sc.nextInt();
			return generateCompleteGraph(nbNodes);
		}
		// complete graph
		if(gType.equals("kmn")) {
			int m = sc.nextInt();
			int n = sc.nextInt();
			return generateCompleteBipartiteGraph(m,n);
		}
		// hypercube
		if(gType.equals("hyperc")) {
			int nbNodes = sc.nextInt();
			return generateHyperCube(nbNodes);
		}
		// regular ring lattice
		if(gType.equals("regringlat")) {
			int nbNodes = sc.nextInt();
			int k = sc.nextInt();
			return generateRegularRingLattice(nbNodes, k);
		}
		// star
		if(gType.equals("star")) {
			int nbNodes = sc.nextInt();
			return generateStarGraph(nbNodes);
		}
		return g;
	}
	
	public static void main(String[] args) {
		
		randomStatic = new Random();
		
		Graph g = null;
		
//		g = simpleGraphFactory.generateHyperCube(4);
		
//		g = simpleGraphFactory.generateRandomGraphWattsStrogatz(10, 4, 0.2);
		
//		g = simpleGraphFactory.generateStarGraph(6);

//		g = simpleGraphFactory.generateRegularRingLattice(5, 3); // pentagram

//		g = simpleGraphFactory.generateCompleteBipartiteGraph(5,3);
				
//		g = simpleGraphFactory.generateTreeGraph(3,5);
		
		g = simpleGraphFactory.generateGraph("cycle_10_2");
		
		GraphDisplay.disp(g);
		GraphIO.toDot(g,"g.dot");
		GraphIO.toGephi(g,"g.gefx");
	}

}
