package algorithms;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.View;


/**
 * @author yimin.nie
 * this is the demo for binary search trees, this includes how to put elements on the BST
 * in sequence order and how to visit the whole tree in pre- mid and -post root order using 
 * recursive method
 *
 */
public class BST<Value extends Comparable<Value>>  //the binary search tree
{
	private Node root; // the root of BST
	private ArrayList<Value> preOrder = new ArrayList<Value>();
	private ArrayList<Value> inOrder = new ArrayList<Value>();
	private ArrayList<Value> postOrder = new ArrayList<Value>();
	private ArrayList<Value> leaf = new ArrayList<Value>();
	
	private class Node<Value>
	{
		private Value val;
		private Node left,right;
		private int N;
		private int D; //depth of a node
		
		public Node(Value val, int N)
		{
			this.val = val;
			this.N = N;
			this.D = 0;
		}
	}
	public boolean isEmpty(){return root == null;}
	public Node root(){return root;}
	
		public int size(Node x)
		{//get the size of the node x
			if ( x == null) return 0;
			return x.N; 
		}
		
		public int depth()
		{
			return depth(root);
		}
		
		public int depth(Node x)
		{
			if (x == null) return 0;
			else return x.D;
		}
		
		/*
		 * put new value in the BST
		 */
		public void put(Value val)
		{
			root = put(root,val);
		}
		
		public Node<Value> put(Node<Value> x, Value val)
		{
			if(x == null) x = new Node<Value>(val,1);
			int cmp = val.compareTo(x.val);
			
			if (cmp == 0) x.val = val;
			else if (cmp < 0) x.left = put(x.left,val);
			else x.right = put(x.right,val);
			x.N = size(x.left) + size(x.right) + 1;
			x.D = 1 + maxD(depth(x.left),depth(x.right)); 
			return x;
		}
		/*
		 * get the value from the BST
		 */
		public Value get(Value val){
			
			return get(root,val);
		}
	    private Value get(Node x, Value val)
		{
			if(x == null) return null;
			int cmp = val.compareTo((Value) x.val);
			
			if(cmp < 0) return get(x.left,val);
			else if(cmp>0) return get(x.right,val);
			else return (Value) x.val;
					
		}
	    /*
	     * find the min value from the BST
	     */
		
		public Value min()
		{
			return (Value) min(root).val;
		}
		
		public Node min(Node x)
		{
			if(x.left == null) return x;
			else return min(x.left);
		}
		
		/*
		 * find the max value from the BST
		 */
		public Value max()
		{
			return max(root);
		}
		
		private Value max(Node x)
		{
			if(x == null) return null;
			else if (x.right == null) return (Value) x.val;
			else return max(x.right);
		}
		
		/*
		 * find the floor value from the BST
		 * the floor is the largest value which are less them given Node
		 */
		public Value floor(Node x)
		{
			if(x ==null) return null;
			else if(x.left == null) return (Value) x.val;
			else return max(x.left);
		}
		
		/*
		 * find the ceiling value from the BST
		 * the ceiling is the smallest value which are larger than given node
		 * 
		 */
		public Value ceiling (Node x)
		{
			if (x == null) return null;
			else if(x.right == null) return (Value) x.val;
			else return (Value) min(x.right).val;
		}

		/*
		 * preorder visit the BST
		 */
		
		public void preVisitBST()
		{
			preVisitBST(root);
			System.out.println(preOrder);
		}
		
		
		private void preVisitBST(Node x)  //visit the whole BST
		{

			if (x == null) return;
			
			 preOrder.add((Value)x.val); 
			 preVisitBST(x.left);
			 preVisitBST(x.right);
		}
	    /*
		 * inOrder BST
		 */
		public void inVisitBST()
		{
			inVisitBST(root);
			System.out.println(inOrder);
		}
		private void inVisitBST(Node x)
		{
			if(x == null) return;
			inVisitBST(x.left);
			inOrder.add((Value)x.val);
			inVisitBST(x.right);
			
		}
		/*
		 * postOrder BST
		 */
		public void postVisitBST()
		{
			postVisitBST(root);
			System.out.println(postOrder);
		}
		private void postVisitBST(Node x)
		{
			if(x ==null) return;
			postVisitBST(x.left);
			postVisitBST(x.right);
			postOrder.add((Value)x.val);
		}

		
		/**
		 * deletion operation 
		 */
		public void deleteMin()
		{
			root = deleteMin(root);
		}
		
		public Node deleteMin(Node x)
		{
			if( x == null) return null;
			else if (x.left == null) return x.right;
			else x.left = deleteMin(x.left);
			x.N = size(x.left) + size(x.right) + 1;
			x.D = 1 + maxD(depth(x.left),depth(x.right)); 
			return x;
		}
		private int maxD(int i, int j)
		{
			if (i>j) return i;
			else if (i<j) return j;
			else return i;
		}
		
	
		public void delete(Value val)
		{
			root = delete(root,val);
		}
		private Node delete(Node x, Value val)
		{
			if (x == null) return null;
			int cmp = val.compareTo((Value) x.val);
			if(cmp < 0) x.left = delete(x.left,val);
			else if (cmp > 0) x.right = delete(x.right,val);
			else
			{
				if (x.right == null) return x.left;
				else if(x.left == null) return x.right;
				Node t = x;
				x = min(t.right);
				x.right = deleteMin(t.right);
				x.left = t.left;
			}
			x.N = size(x.left) + size(x.right) + 1;
			x.D = 1 + maxD(depth(x.left),depth(x.right)); 
			return x;
		}
		
		public void mirrorBST()
		{ root = mirrorBST(root);
		  inVisitBST(root);
		
		}
		private Node mirrorBST(Node x)
		{
			if (x == null) return null;
			Node t = x.left;
			x.left = x.right;
			x.right = t;
			mirrorBST(x.left);
			mirrorBST(x.right);
			return x;
		}
		
		/*
		 * reconstruct the BST given the postOrder in an array
		 */
		
		public void reconsBST(int[] a)
		{
			Node newRoot = reconsBST(a,0,a.length-1);
			postVisitBST(newRoot);
			System.out.println(postOrder);
		}
		private Node reconsBST(int[] a, int m, int n)
		{
			if(m>n) return null;
			Node tempRoot =  new Node(a[n],1);
			int k=m;
			while(a[k]<a[n]) k++;
			Node leftRoot = reconsBST(a, m, k-1);
			Node rightRoot = reconsBST(a, k, n-1);
			tempRoot.left = leftRoot;
			tempRoot.right = rightRoot;
			return tempRoot;
		}
		
		/*
		 * select all leaf node between two given nodes, leaf node has no children
		 * basic idea
		 * using recursive principle and calculate leaf node by two parts:
		 * one if from start to root, the other is from root to end
		 * (1)
		 * if start is a leaf, select it and go to its parent
		 * if start is not a leaf but no right child, go to its parent
		 * if start is not a leaf but has right child, go to its ceil
		 * 
		 * (2)
		 * if end is a leaf, go to its floor (parent of parent)
		 * if end is not a leaf but has a left child, go to its floor
		 */
		
		
		/*
		 * judge if a binary tree is a BST, the first code is wrong
		 */
		public boolean isBST()
		{
			return isBST(root);
		}
//		private boolean isBST(Node x)
//		{
//			 if (x ==null) return false;
//			 if (x.left==null && x.right!=null)
//			 {
//				 Value value = (Value)x.right.val;
//				 if (value.compareTo((Value)x.val)>0) return true;
//				 else return false;
//			 }
//			 if(x.right ==null&&x.left!=null)
//			 {
//				 Value value = (Value)x.left.val;
//				 if (value.compareTo((Value)x.val)<0) return true;
//				 else return false;
//			 }
//			 return(isBST(x.left)&&isBST(x.right));
//		}
//	
		//we can do inOrder traversal and check if it is ordered
		private boolean isBST(Node x)
		{
			if(x==null) return false;
			inVisitBST(x);
			if(isIncreasing(inOrder)) return true;
			return true;
		}
		
		private boolean isIncreasing(ArrayList<Value> arr)
		{
			int N = arr.size();
			for (int i=0; i<N;i++)
				if (arr.get(i+1).compareTo(arr.get(i))<0) return false;
			return true;
			
		}
		
		
		
		public static void main(String[] args)
		{
			BST<Integer> anna = new BST<Integer>();
			anna.put(5);
			anna.put(2);
			anna.put(7);
			anna.put(1);
			anna.put(3);
			anna.put(6);
			anna.put(8);
			
			anna.preVisitBST();
			anna.inVisitBST();
			anna.postVisitBST();
			System.out.println(anna.ceiling(anna.root()));
			
			int[] a ={1,3,2,6,8,7,5};
			//anna.reconsBST(a);
			System.out.println(anna.isBST());
		}
		
		
	
}
