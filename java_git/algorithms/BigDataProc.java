package algorithms;

import stdlib.In;

/**
 * @author yimin.nie
 * this class shows how to process big data using hash symbol table
 */
public class BigDataProc {
	
	private HashST<String, Integer> st;  // the Hash table to account the frequencies
	private In in;
	private final int M;   //the size of hash table
	private final int K;
	SizedMinHeap<Integer> pq ;
	
	public BigDataProc(int M, int K)
	{
		this.M = M;
		this.K = K;
		st = new HashST<String,Integer>(M);
		pq = new SizedMinHeap<Integer>(K);
		
	}
	/**
	 * scanning the data from given file and store them into hash table 
	 * @param filename
	 */
	public void ScanData(String filename)
	{
		in = new In(filename);
		while(in.hasNextLine())
		{
			String line = in.readLine();
			String[] tokens = line.split(" ");
			for(String words:tokens)
			{
				if(st.contains(words)) st.put(words, st.get(words)+1);
				else st.put(words, 1);
			}		
		}	
	}
	/**
	 * for each chain in the hash table, select the biggest K items using 
	 * a min heap with fixed size
	 */
	public void getBigK()
	{
		
		for(int i = 0;i<M;i++)
		{
			SequentialSearchST<String,Integer> list = st.st(i);
			for(String word:list.keys())
				pq.insert(list.get(word));
			
		}
		pq.print();
	}
	
	/*
	 * a test client
	 */
	
	public static void main(String[] args)
	{
		String file = "../mydata.txt";
		TimeWatch watch = new TimeWatch(); // measure the running time
		BigDataProc big = new BigDataProc(5000,5); //use a hash table with size 5000 to find the biggest 5 occurrences
		
		big.ScanData(file);
		big.getBigK();
		System.out.println("the elipsed time is:"+ watch.elisedTime());
	}

}
