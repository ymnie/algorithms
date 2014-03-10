package algorithms;

import java.util.NoSuchElementException;

/**
 * @author yimin.nie
 * the max heap with fixed size.
 * @param <Value>
 */
public class SizedMaxHeap<Value extends Comparable<Value>>{
	
	private int N; //the current size
	private final int maxN;//the final size of the heap
	private Value[] pq; // store items from 1 - N
	
	public SizedMaxHeap(int maxN)
	{
		this.N = 0;
		this.maxN = maxN;
		this.pq = (Value[]) new Comparable[1+maxN];
	}
	
	public int size(){return N;}
	public boolean isEmpty(){return N == 0;}
	
	public void insert(Value val)
	{
		if(N < maxN){
			pq[++N] = val;
			swim(N);
		}
		else if (N>=maxN && val.compareTo(pq[1])<0){
			removeMax();
			pq[++N] = val;
			swim(N);
		}
		else if(N>=maxN && val.compareTo(pq[1])>0) return;
	}
	
	public Value DelMax()
	{
		if(isEmpty()) throw new NoSuchElementException("the heap is empty");
		Value max = pq[1];
		swap(1,N--);
		sink(1);
		pq[N+1] = null;
		return (Value) max;
	}
	private void removeMax()
	{
		if(isEmpty()) throw new NoSuchElementException("the heap is empty");
		swap(1,N--);
		sink(1);
		pq[N+1] = null;
	}
	
	
	
	/*
	 * helper functions
	 */
	private boolean less(int i, int j)
	{
		return pq[i].compareTo(pq[j])<0;
	}
	
	private void swim(int k)
	{
		while(k>1 && less(k/2,k))
		{
			swap(k,k/2);
			k = k/2;
		}
	}
	private void swap(int i, int j)
	{
		Value temp = pq[i];
		pq[i] = pq[j];
		pq[j] = temp;
	}
	
	private void sink(int k)
	{
		while(2*k<=N)
		{
			int j = 2*k;
			if(j<N && less(j,j+1)) j++;
			if(!less(k,j)) break;
			swap(k,j);
			k = j;
		}
	}
	
	public void print()
	{
		for(int i=1;i<=N;i++)
			System.out.print(pq[i]+" ");
		System.out.println();
	}
	
	public static void main(String[] args)
	{
		SizedMaxHeap<Integer> pq = new SizedMaxHeap<Integer>(5);
		int[] a = {10,9,8,7,6,5,4,3,2,1,-98};
		for(int i =0;i<a.length;i++)
			pq.insert(a[i]);
		pq.print();
	}

	
}
