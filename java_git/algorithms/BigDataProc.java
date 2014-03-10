package algorithms;

import java.awt.RenderingHints.Key;

import stdlib.In;

/**
 * @author yimin.nie
 * this class shows how to process big data using hash symbol table
 */
public class BigDataProc {
	
	private HashST<String, Integer> st;  // the Hash table to account the frequencies
	private In in;
	private final int M;   //the size of hash table
	
	public BigDataProc(int M)
	{
		st = new HashST<String,Integer>();
		this.M = M;
	}
	/**
	 * scanning the data from given file and store them into hash table 
	 * @param filename
	 */
	public void ScanData(String filename)
	{
		st = new HashST<String,Integer>(M);
		in = new In(filename);
		while(in.hasNextLine())
		{
			String line = in.readLine();
			String[] tokens = line.split(" ");
			for(String words:tokens)
				if(!st.contains(words)) st.put(words, 1);
				else st.put(words, st.get(words)+1);
		}
		
	}
	/**
	 * for each chain in the hash table, select the biggest K items using 
	 * a min heap with fixed size
	 * @param K
	 */
	public void getBigK(int K)
	{
		SizedMinHeap<Integer> pq = new SizedMinHeap<Integer>(K);
		for(int i = 0;i<M;i++)
		{
			SequentialSearchST<String,Integer> list = st.st(i);
			for(String word:list.keys())
				pq.insert(list.get(word));
			
		}
		pq.print();
	}
	
	/**
	 * heler functions 
	 * @param key
	 * @return
	 */
	
	private int hash(Key key)
	{
		return (key.hashCode() & 0x7fffffff) % M;
	}
	
	public static void main(String[] args)
	{
		BigDataProc big = new BigDataProc(100);
		String file = "C:/Users/yimin.nie/Desktop/javafile/tale.txt";
		big.ScanData(file);
		big.getBigK(5);
	}

}
