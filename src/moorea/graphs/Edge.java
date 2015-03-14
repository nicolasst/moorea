package moorea.graphs;

public class Edge<K extends Node>  {

	public K n1;
	public K n2;
	
	public Edge(K n1, K n2) {
		this.n1 = n1;
		this.n2 = n2;
	}

	@Override
	public int hashCode() {
		String s1 = n1.toString();
		String s2 = n2.toString();
		String tmp = null;
		if(s1.compareTo(s2)>0) {
			tmp = s2;
			s2 = s1;
			s1 = tmp;
		}
		String hashString = s1+s2;
		return hashString.hashCode();
	}
	
	public boolean equals(Edge e) {
		if(n1 == e.n1 && n2 == e.n2) {
			return true;
		} else
		if(n1 == e.n2 && n2 == e.n1) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "Edge("+n1+","+n2+")";
	}
}
