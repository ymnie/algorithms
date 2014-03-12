package algorithms;

import stdlib.In;
import stdlib.Out;

/**
 * @author yimin.nie
 * The application of using Hash table to process large input files
 */ 
public class Bigdata {
	
	private int N; //the number of key-value pair
	private final int M; //the size of hash table
	private HashST<String,Integer> st;
	private SizedMinHeap<Integer> pq; // a heap to store the big k items
	private static final int K = 20;
	
	public Bigdata(int M, String file)
	{
		this.M = M;
		st = new HashST<String,Integer>(M);// create a hash table with size M
		pq = new SizedMinHeap<Integer>(K);
		
		/*
		 * scan the entire document and count the frequencies 
		 */
		In in = new In(file);
		while(in.hasNextLine())
		{
			String[] tokens = in.readLine().split(" ");
			for(String words:tokens)
				if(!st.contains(words)) st.put(words, 1);
				else st.put(words, st.get(words) + 1);
		}
		this.N = st.size();
	}
	/*
	 * read data from input files which are separated by K output files
	 */
	public void divData()
	{
		Out out;
		for(int i = 0;i<M; i++)
		{
			String outfile = "C:/Users/.../out"+ i + ".txt";
			out = new Out(outfile);
			for (String words:st.st(i).keys())
			{
				out.print(words + " " + st.get(words));
				out.println();
			}				
		}
	}
	
	/*
	 * process these files one by one 
	 */
	public void procData()
	{
		In in;
		for (int i =0;i<M;i++) //read data from each files
		{
			String file = "C:/Users/.../out"+ i + ".txt";
			in = new In(file);
			while(in.hasNextLine())
			{
				String[] tokens = in.readLine().split(" ");
				pq.insert(Integer.parseInt(tokens[1])); //each time insert the biggest k items into the heap
			}	
		}
		
		Comparable[] list = pq.pq();
		for(int i=1;i<=K;i++)
		{
			Comparable val = list[i];
			Comparable key = st.keyOf(val);
			System.out.println("the biggest occurences are: ");
			System.out.println(key+" : "+val);
		}
		
		
	}
	
	/*
	 * a test client
	 */
	public static void main(String[] args)
	{
		int M = 100; //specify the number of files that need to be write into
		String inFile = "C:/Users/.../myData.txt";
		TimeWatch watch = new TimeWatch();
		Bigdata big = new Bigdata(M,inFile);
		big.divData();
		big.procData();
		System.out.println("the elipsed time is: " + watch.elisedTime()/1000.0 + " seconds");
	}

}
