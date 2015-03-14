package moorea.graphs;

import moorea.graphs.io.GraphDisplay;
import moorea.graphs.io.GraphIO;

public class TestLabelledGraph {

	public static void unitTest() {
		LabelledGraph g = new LabelledGraph();
		g.addNewNodes(4);
		
		LabelledNode n1 = g.createNewNode();
		Object o = new String("AAA");
		n1.setNodeObject(o);
		g.addExistingNode(n1);
		
		// label keys
		String nameKey = "name";
		String relKey = "knows";
		String capacityKey = "capacity";
		String markKey = "mark";
		
		// all the different ways of setting a node label
		g.getNodeByIndex(0).setNodeLabel(nameKey, "toto");
		g.setNodeLabelByIndex(1, nameKey, "tata");
		g.setNodeLabelById(2, nameKey, "titi");

		LabelledNode n = g.getNodes().get(3);
		g.setNodeLabel(n, nameKey, "tutu");
		
		g.setNodeLabelByObject(o, nameKey, "tyty");
		
		// almost all the different ways of setting an edge label 
		g.setEdgeMarkByIndex(0, 1, relKey);
		g.setEdgeLabelById(1, 2, capacityKey, 2);
		g.setEdgeMark(g.getNodeByIndex(2), g.getNodeByObject(o), markKey);
		
		GraphDisplay.disp(g);
	}
	
	public static void unitTest2() {
		LabelledGraph g = new LabelledGraph();
		g.addNewNodes(4);
		
		LabelledNode n1 = g.createNewNode();
		Object o = new Object();
		n1.setNodeObject(o);
		g.addExistingNode(n1);
		
		// node label keys
		String nameKey = "name";
		String organisationKey = "organisation";

		// edge label keys
		String distanceKey = "distance";
		String markKey = "mark";
		
		// all the different ways of setting a node label
		g.getNodeByIndex(0).setNodeLabel(nameKey, "Paris");
		g.setNodeLabelByIndex(1, nameKey, "Berlin");
		g.setNodeLabelById(2, nameKey, "Warsaw");

		LabelledNode n = g.getNodes().get(3);
		g.setNodeLabel(n, nameKey, "Minsk");
		
		g.setNodeLabelByObject(o, nameKey, "Moscow");
		
		// almost all the different ways of setting an edge label 
	//	g.setEdgeLabelByIndex(0, 1,);
	//	g.setEdgeLabelById(1, 2, capacityKey, 2);
		g.setEdgeMark(g.getNodeByIndex(2), g.getNodeByObject(o), markKey);
		
		GraphDisplay.disp(g);
	}
	
	public static void unitTest3() {
		LabelledGraph g = new LabelledGraph();
		g.addNewNodes(4);
		
		LabelledNode n1 = g.createNewNode();
		Object o = new Object();
		n1.setNodeObject(o);
		g.addExistingNode(n1);
		
		// node label keys
		String nameKey = "name";
		String organisationKey = "organisation";

		// edge label keys
		String distanceKey = "distance";
		String visitedKey = "visited";
		
		// all the different ways of setting a node label
		g.getNodeByIndex(0).setNodeLabel(nameKey, "Paris");
		g.setNodeLabelByIndex(1, nameKey, "Berlin");
		g.setNodeLabelById(2, nameKey, "Warsaw");

		LabelledNode n = g.getNodes().get(3);
		g.setNodeLabel(n, nameKey, "Minsk");
		
		g.setNodeLabelByObject(o, nameKey, "Moscow");
		
		// almost all the different ways of setting an edge label 
		g.setEdgeLabelByIndex(0, 1, distanceKey, 100);
		g.setEdgeLabelById(1, 2, distanceKey, 200);
		g.setEdgeLabel(g.getNodeByIndex(3), g.getNodeByObject(o), distanceKey, 300);
		
		GraphDisplay.disp(g);
	}
	
	public static void main(String[] args) {
		unitTest();
	}
	
}
