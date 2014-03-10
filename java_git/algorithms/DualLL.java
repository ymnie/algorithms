package algorithms;

/**
 * @author yimin.nie
 * the class for a dual liked list
 * @param <Value>
 */
public class DualLL<Value> {
	
	private Node head;
	
	private class Node<Value>
	{
		private Value val;
		private Node pre, post;
		private int N;
		
		public Node(Value val, int N)
		{ this.val = val; this.N = N;}
		
	}
	
	public boolean isEmpty(){return head == null;}
	public int size(){return size(head);}
	private int size(Node x)
	{
		if (x == null) return 0;
		else return x.N;
	}
	
	public void put(Value val)
	{ head = put(head, val);}
	
	private Node put(Node x, Value val)
	{
		if (x == null) x = new Node(val,1);
		else{
		x.post = put(x.post,val);
		x.post.pre = x;
		x.N = size(x.post) + 1;
		}
		
//		else{
//			int cmp = val.compareTo((Value) x.val);
//			if (cmp < 0) {x.pre = put(x.pre,val); x.pre.post = x;}
//			else if(cmp > 0) { x.post = put(x.post,val); x.post.pre = x;}
//			else x.val = val;
//		}
//		x.N = size(x.post) + 1;

		return x;
	} 
	
	public void delete(Value val)
	{
		Node x = head;
		while(x.val != val)
		{
			x = x.post;
		}
		if( x.post == null) x.pre.post = null;
		else{
		x.pre.post = x.post;
		x.N = size(x.post) + 1;
		}
			
	}
	
	public void printLL()
	{
		for (Node x = head; x!= null; x = x.post)
			System.out.println(x.val);
	}
	
	
	public static void main(String[] args)
	{
		DualLL<Integer> anna = new DualLL<Integer>();
		anna.put(3);
		anna.put(5);
		anna.put(9);
		anna.put(1);
		//anna.printLL();
		anna.delete(9);
		anna.printLL();
	}

	
}
