package moorea.graphs.spatialgraphs;

import moorea.graphs.SimpleNode;

/**
 * A SpatialNode is an undirected adjacency list based node that stores spatial coordinates.
 * 
 * @author nicolas
 *
 */

public class SpatialNode extends SimpleNode {

	double[] coord;
	
	public SpatialNode(Integer i) {
		super(i);
	}

	public double[] getCoord() {
		return coord;
	}

	public void setCoord(double[] coord) {
		this.coord = coord;
	}
	
}
