package moorea.graphs;

/**
 * A ContainerNode is an undirected node that contains only one information of a given type.
 * 
 * @author nicolas
 *
 * @param <K> type of the information contained.
 */

public class ContainerNode<K> extends SimpleNode<ContainerNode> {

	K content;
	
	ContainerGraph<K> containingGraph;

	public ContainerNode(Integer id) {
		super(id);
	}
	
	public K getContent() {
		return content;
	}

	public void setContent(K content) {
		this.content = content;
		if(containingGraph != null && this.content != null) {
			containingGraph.contentToNodeMap.remove(this.content);
			containingGraph.updateContentToNodeMap(content, this);
		}
	}

	public String toString() {
		return "CN_"+id;
	}
	
}
