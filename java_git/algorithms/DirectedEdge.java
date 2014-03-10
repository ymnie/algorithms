package algorithms;

/**
 * @author yimin.nie
 * the class of directed edge, please refer the difference between weighted edge
 * and directed edge 
 */
public class DirectedEdge {
	
	private final int v;
	private final int w;
	private final double weight;
	
	public DirectedEdge(int v, int w, double weight)
	{
		this.v= v;
		this.w= w;
		this.weight = weight;
	}
	
	public int from() {return v;}
	public int to() {return w;}
	public double weight(){return weight;}
	
}
