package algorithms;

/**
 * @author yimin.nie
 * this is a priority queue which put the minimal value on the top of 
 * the heap
 */
public class MinPQ <Value extends Comparable <Value>> {
	
	private int N;
	private Value[] pq;
	
	public MinPQ(int maxN)
	{
		this.N = 0;
		pq = (Value[]) new Comparable[1+maxN];
	}
	
	
	public boolean isEmpty(){return N == 0;}
	public int size(){return N;}
	
	public void insert(Value val)
	{
		pq[++N] = val;
		swim(N);
	}
	
	public Value getMin()
	{ return (Value) pq[1];}
	public Value delMin()
	{
		Value min = pq[1];
		exch(1,N--);
		pq[N+1] = null;
		sink(1);

		return min;
		
	}
	
	private void exch(int i, int j)
	{
		Value t;
		t= pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}
	
	private boolean less(int i, int j)
	{
		return pq[i].compareTo(pq[j])<0;
	}
	
	private void swim(int k)
	{
		while (k>1 && less(k,k/2))
		{
			exch(k/2,k);
			k=k/2;
		}
	}
	
	private void sink(int k)
	{
		while(2*k<=N)
		{
			int j = 2*k;
			if(j<N && less(j+1,j)) j++;
			if(less(k,j))break;
			exch(k,j);
			k = j;
		}
	}
	
	public void printPQ()
	{
		for (int i = 1; i<=N; i++)
			System.out.println(pq[i]);
	}
	
	public static void main(String[] args)
	{
		MinPQ<Integer> anna = new MinPQ<Integer>(100);
		anna.insert(5);
		anna.insert(6);
		anna.insert(2);
		anna.insert(100);
		anna.insert(3);
		anna.printPQ();
		anna.delMin();
		anna.printPQ();
		
	}
	
	

}
