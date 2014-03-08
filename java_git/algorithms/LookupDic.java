package algorithms;

import stdlib.In;

/**
 * @author yimin.nie 
 * this class shows the lookup dictionary from input file
 *  
 */
public class LookupDic {

	public static void main(String[] args) {
		
		BST<String, String> st = new BST<String,String>(); // binary search  tree 
		In in = new In("C:/Users/yimin.nie/Desktop/javafile/ip.csv");
		while(in.hasNextLine())
		{
			String line = in.readLine();
			String[] tokens = line.split("\\,");
			String key = tokens[0];
			String val = tokens[1];
			st.put(key, val);	
		}
		
		st.print();
	}

}
