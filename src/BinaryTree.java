import java.util.Scanner;

public class BinaryTree {
	
	public static void main(String...strings)
	{
		BinaryTree bt = new BinaryTree();
		Scanner sc = new Scanner(System.in);
		int Q = 100;
		while(Q-->0)
			bt.insert(Q);
		/*
		Q = 3;
		while(Q-->0)
			bt.delete(sc.nextInt());
			*/
		bt.print(bt.root, "");
	}
	
	Node root;
	
	void print(Node root, String indent)
	{
		if(root == null)
			return;
		System.out.println(indent + root.val);
		print(root.left, indent + " ");
		print(root.right, indent + " ");
	}
	
	public void insert(int k)
	{ root = insert(root, k); }
	
	private Node insert(Node node, int k)
	{
		if(node == null)
			return new Node(k);
		else
		{
			if(k < node.val)
				node.left = insert(node.left, k);
			else
				node.right = insert(node.right, k);
		}
		return node;
	}
	
	public void delete(int k)
	{ root = delete(root, k); }
	
	private Node delete(Node node, int k)
	{
		if(node == null)
			return null;
		if(k < node.val)
			node.left = delete(node.left, k);
		else if(k > node.val)
			node.right = delete(node.right, k);
		else
		{
			if(node.left == null)
				return node.right;
			if(node.right == null)
				return node.left;
			
			int cur = k;
			Node temp = node.right;
			while(temp.left != null)
			{
				cur = temp.left.val;
				temp = temp.left;
			}
			node.val = cur;
			node.right = delete(node.right, cur);
		}
		return node;
	}
	
	static class Node {
		
		int val, height;
		Node left, right, parent;
		
		Node(int v)
		{
			this.val = v;
		}
		
		public String toString()
		{
			return ""+val;
		}
		
	}
	
	public String toString()
	{
		return ""+root;
	}
	
}