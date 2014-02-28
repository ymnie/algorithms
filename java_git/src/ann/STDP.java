package ann;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import math.*;
import algorithms.*;
import machine_learning.*;
import stdlib.*;

/**
 * @author yimin.nie
 * A Graph-based STDP simulator
 * this class gives the learning rule defined by spike-timing-dependent-plasticity(STDP)
 * the spike time window are chosen as [-40,40]ms, I used a customised class "SizedQueue.java"
 * to store the fired spike trains which only track and store the spike trains within time windows
 * 
 */
public class STDP {
	
	private static final int T=40; //the learning window
	private static final double tau = 10; //the time constant of STDP
	private static double A_plus =1; // the amplitude of STDP, learning phase
	private static double A_minus = -1; //the amplitude of STDP, unlearning phase
	private static double tau_plus = 1; 
	private static double tau_minus = 20;
	private static double eta = 0.00001; //learning rate
	private static double tau_syn = 5;
	private static double L = 0.1;// the ratio of connectivity prob for each neuron
	
	private final int N;  //the size of STDP network
	private double tau_plus_hat;
	private double tau_minus_hat;
	private double penRadius = 0.0002;
	
	private IzhiNeuron net; // the network connected by Izhikevich neurons
	private LinkedList<DirectedEdge>[] adj; //the connection list for each neuron
	private int[] position; //the position of each neuron in this graph
	private double[][] J; // the connection matrix which is a directed edge
	
	private SizedQueue<ArrayList> fired;//recording the fired neurons within time windows
	private ArrayList<Integer> firing;
	
	/*
	 * constructor
	 */
	public STDP(int N)
	{
		this.N = N;
		init();
	}
	
	private void init()
	{
		tau_plus_hat = tau_syn*tau_plus/(tau_syn+tau_plus);
		tau_minus_hat = tau_syn*tau_minus/(tau_syn+tau_minus);
		
		net = new IzhiNeuron(N);   // create the network with size of N
		position = new int[N];    //for drawing the network in the graph
		fired =new SizedQueue<ArrayList>(T);
		firing = new ArrayList<Integer>();
		
		initJ();//initiate J
        initNet();
	
	}
	/*
	 * initiate the network setting
	 */
	private void initNet()
	{
		for(int i=0;i<N;i++)
		{
			int j = (int)MyMath.rand(0,N);
			position[i] = j;	
		}
		// setup the connection weights for the network
		adj = (LinkedList<DirectedEdge>[]) new LinkedList[N];
		for(int i=0;i<N;i++)
			adj[i] = new LinkedList<DirectedEdge>();
		// randomly add edges into this graph
		for (int i=0;i<N;i++)
		{
			for (int j=1;j<=(int)(L*N);j++)
			{
				int n = (int)(Math.random()*N);
				adj[i].add(new DirectedEdge(i,n,J[i][n]));
				
			}
		}
		//draw the initial net
		StdDraw.setXscale(0, N);
		StdDraw.setYscale(0, N);
		
		for(int i=0;i<N;i++)
		{
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenRadius(0.02);
			StdDraw.point(i, position[i]);
			
			for(DirectedEdge e:adj(i)){
				StdDraw.setPenColor(Color.RED);
				StdDraw.setPenRadius(penRadius);
				StdDraw.line(i, position[i], e.to(), position[e.to()]);
			}
				
		}

	}
	/*
	 * initiate the connection matrix
	 */
	private void initJ()
	{
		J = new double[N][N];
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				J[i][j] = 2*MyMath.rand(0, 1)/N;
		for(int i=0;i<N;i++)
			J[i][i]=0.0;
	}
	
	/*
	 * Are two neurons i and j are connected?
	 */
	
	public boolean isConnected(int i, int j)
	{
		for(DirectedEdge e:adj[i])
			if (e.to()==j) return true;
		return false;
	}
	
	/*
	 * get synaptic weight from neuron i to j
	 */
	public double getWeight(int i, int j)
	{
		for(DirectedEdge e:adj[i])
			if(e.to()==j) return e.weight();
		return 0.0;
	}
	/*
	 * show adj list
	 */
	public Iterable<DirectedEdge> adj(int i)
	{
		return adj[i];
	}
	
	public void showAdj()
	{
		for(int i=0;i<N;i++)
		{
			Iterable<DirectedEdge> arr = adj(i);
			for(DirectedEdge e:arr)
				System.out.println(i+"->"+e.to()+","+e.weight());
		}
	}
	
	/*
	 * show the network
	 */
	
	public void showNet(int time) throws Exception
	{

		// start simulations
		for(int t=1;t<=time;t++)
		{
			for (int i=0;i<N;i++)
			{
				double r = new Random().nextDouble();
				if(r<0.2) firing.add(i); 
			}
			fired.enqueue(firing);
			ArrayList<Integer> a = fired.get(0);
			for(int i=1;i<fired.size();i++)
			{
				ArrayList<Integer> b = fired.get(i);
				for(int m=0;m<a.size();m++)
					for(int n=0;n<b.size();n++)
					{
						int x = a.get(m);
						int y = b.get(n);
						
						if(isConnected(x,y)) 
							{
							   J[x][y] -= W(i);
							   J[y][x] += W(-i);
							   penRadius += 0.00002*penRadius;
							   StdDraw.setPenRadius(penRadius);
							   StdDraw.setPenColor(Color.BLACK);
							   StdDraw.line(x, position[x], y,position[y]);
							}
					}
			}
		}
	
	}
	
	/*
	 * show fired patterns
	 */
	public SizedQueue<ArrayList> fired()
	{
		return fired;
	}
	/*
	 * calculate the learning windows according to time difference
	 * between the pre and post synaptic neurons
	 */
	private final double W(double s)
	{
		if(s<=0) 
			return eta*(Math.exp(s/tau_syn)*(A_plus*(1-s/tau_plus)+A_minus*(1-s/tau_minus)));
		else
			return eta*(A_plus*Math.exp(-s/tau_plus)+A_minus*Math.exp(-s/tau_minus));
				
	}
	/*
	 * plot learning windows
	 */
	public void showW()
	{
		StdDraw.setXscale(-50,50);
		StdDraw.setYscale(-2*eta, 2*eta);
		StdDraw.setPenColor(Color.RED);
		StdDraw.setPenRadius(0.01);
		
		for (int s=-50;s<=50;s++)
		{
			double w = W(s);
			StdDraw.point(s,w);
		}
			
	}
	
	
	/*
	 * a test client
	 */
	
	public static void main(String[] args) throws Exception
	{
		STDP st = new STDP(100);
		//st.showW();
		st.showNet(1000);
	}

}
