package moorea.graphs.construction;

import java.util.LinkedList;
import java.util.List;

import moorea.graphs.Node;
import moorea.graphs.SimpleNode;
import moorea.misc.IntegerGenerator;

/**
 * The Graph class is very flexible thanks to the fact that the type of nodes
 * used can be parameterized. This class build the actual nodes using reflexion.
 * 
 * @author nicolas
 *
 * @param <V>
 */

public class NodeFactory<V extends Node> {

	Class<V> nodeClass;
	IntegerGenerator nodeIdGenerator;
	
	public static NodeFactory<SimpleNode> simpleNodeFactory = new NodeFactory(SimpleNode.class);
	
	public NodeFactory(Class<V> nodeClass) {
		this.nodeClass = nodeClass;
		nodeIdGenerator = new IntegerGenerator(0);
	}

	public NodeFactory(Class<V> nodeClass, IntegerGenerator ig) {
		this.nodeClass = nodeClass;
		nodeIdGenerator = ig;
	}
	
	public IntegerGenerator getIntegerGenerator() {
		return nodeIdGenerator;
	}
	
	public V createNode() {
		return createNode(nodeClass, nodeIdGenerator);
	}
	
	public V createNode(Integer id) {
		IntegerGenerator ig = new IntegerGenerator(id);
		return createNode(nodeClass,ig);
	}
	
	public V createNode(IntegerGenerator ig) {
		return createNode(nodeClass, ig);
	}
	
	public static <K extends Node> K createNode(Class<K> ck, IntegerGenerator ig) {
		K kn = null;
		try {
			Integer nextId = null;
			nextId = ig.next();
			// the class K must explicitly implement the constructor (/!\constructors are not inherited)
			// moreover the constructor must have an explicit 'Integer' parameter and not an 'int' one.
			kn = ck.getDeclaredConstructor(new Class[]{Integer.class}).newInstance(new Object[]{nextId});
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return kn;
	}
	
	public List<V> createNodes(int n) {
		return createNodes(n, nodeIdGenerator);
	}
	
	public List<V> createNodes(int n, IntegerGenerator ig) {
		List<V> nodes = new LinkedList();
		for (int i = 0; i < n; i++) {
			nodes.add(createNode(nodeClass, ig));
		}
		return nodes;
	}
	
}
