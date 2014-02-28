package machine_learning;

import java.awt.Color;
import java.util.HashMap;

import math.Matrix;
import math.MyMath;
import stdlib.StdDraw;

/**
 * @author yimin.nie
 * This class tries to give an idea on how to use Marr's associative memory theory to 
 * store and retrieve patterns. All the input patterns are stored in a Hash Map (inMap).
 * all correspondence patterns are stored in a stored matrix "pat" 
 */
public class Marr {

	private int[][] pat; // storage matrix
	private final int N; //the size of pattern matrix
	private final int M;
	private int k=0; //the HashMap index
	//use two hash maps to store the corresponding two patterns that need to be matched
	private HashMap<Integer,int[]> inMap = new HashMap<Integer,int[]>();
	private HashMap<Integer,int[]> outMap = new HashMap<Integer,int[]>();
	
	/*
	 * constructor
	 */
	public Marr(int N, int M)
	{
		this.N=N;
		this.M=M;
		this.pat = new int[N][M];
	}
	/*
	 * create the pattern matrix according to inputs vectors
	 */

	public void addNewPat()
	{
		int[] a = patGenerator(N);
		int[] b = patGenerator(M);
		
		if (a==null||b==null) throw new IllegalArgumentException("inputs should not be empty");
		else if(a.length!=N||b.length!=M) throw new IllegalArgumentException("please give legal inputs");
		inMap.put(k, a);
		outMap.put(k, b);
		pat=MyMath.xor(pat, outerProd(a, b));
		k++;
	}
	
	/*
	 * add pat by specifying inputs, this is a heteroassociation
	 */
	
	public void addNewPat(int[] a, int[] b)
	{
		if (a==null||b==null) throw new IllegalArgumentException("inputs should not be empty");
		else if(a.length!=N||b.length!=M) throw new IllegalArgumentException("please give legal inputs");
		inMap.put(k, a);
		outMap.put(k, b);
		pat=MyMath.xor(pat, outerProd(a, b));
		k++;
	}

	/*
	 * add pat, this is an autoassociation
	 */

	public  void addNewAutoPat()
	{
		int[] a = patGenerator(N);
		if(a ==null)throw new IllegalArgumentException("inputs should not be empty");
		inMap.put(k, a);
		pat=MyMath.xor(pat, outerProd(a,a));
		k++;
	}
	
	/*
	 * get all stored inputs and outputs vectors
	 */
	public HashMap<Integer,int[]> getInput()
	{
		return inMap;
	}
	public HashMap<Integer,int[]> getOut()
	{
		return outMap;
	}
	
	/*
	 * get special input and output pairs according to index
	 */
	public int[] getInput(int k){return inMap.get(k);}
	public int[] getOut(int k){return outMap.get(k);}
	/*
	 * a binary pattern generator
	 */
	
	private int[] patGenerator(int N)
	{
		int[] result = new int[N];
		for(int i=0;i<N;i++)
		{
			double rand = Math.random();
			if(rand<=0.25) result[i] = 1;
			else result[i] = 0;
			
		}
		return result;
	}
	
	/*
	 * apply associative memory to retrieve the stored patterns, given input pattern
	 */
	public int[] retrivePat(int[] in)
	{
		int devidor = MyMath.ones(in); // calculate the division factors
		//System.out.println("devidor is"+devidor);
		int N = in.length;
		int[] result = new int[N];
		result = Matrix.divBy(Matrix.innerProd(in, pat), devidor);
		//result = Matrix.innerProd(in, pat);
		return result;
	}
	
	public void show()
	{
		StdDraw.setPenColor(Color.RED);
		StdDraw.setXscale(0, N);
		StdDraw.setYscale(0, M);
		
		for(int i =0;i<=N;i++){
			StdDraw.line(0, i, M, i);
		}
		for (int i=0;i<=M;i++){
			StdDraw.line(i, 0, i, N);
		}
		for(int i =0;i<N;i++)
			for(int j=0;j<M;j++)
			{
				if(pat[i][j] == 1){
					StdDraw.setPenColor(Color.BLUE);
					StdDraw.filledRectangle(i+0.5, j+0.5, 0.5, 0.5);
				}
					
			}
	}

	public static int[][] outerProd(int[] a, int[] b)
	{
		if(a==null ||b ==null) throw new IllegalArgumentException("the inputs should not be null");
		int N = a.length;
		int M = b.length;
		int[][] J = new int[N][M];
		for(int i =0;i<N;i++)
			for(int j=0;j<M;j++)
				J[i][j] = a[i]*b[j];
		return J;
	}
	
	public static void main(String[] args)
	{
		
		int size = 50;
		Marr marr = new Marr(size,size);

		for(int i=0;i<6;i++)
			marr.addNewPat();
			//marr.addNewAutoPat(); 
		marr.show();
		int[] a = marr.getInput(3);  //select a pattern which needs to be retrieved
		int[] b = marr.getOut(3); //the pattern needs to be retrieved, it has the same index with input pattern
		System.out.println("the input patterns is:");
		Matrix.print(a);
		System.out.println("the pattern need to be retrieved is:");
		Matrix.print(b);
		System.out.println("------retrieved pattern is------");
		int[] result = marr.retrivePat(a);
		Matrix.print(result);
		
		
	}
}
