import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

//import BinaryTree.Node;

public class Treap {

	
	static int maxHeight = 0;
	static HashSet<Long> set = new HashSet<>();
	
	public static void main(String...strings )
	{
		Scanner sc = new Scanner(System.in);
		Treap treap = new Treap();
		int Q = 20;
		while(Q-->0)
			treap.insert(treap.gen.nextInt(500),0);
		//System.out.println("PRACTICAL: " + treap.maxHeight);
		//System.out.println("THEORETICAL: " + (int)(Math.log(6250000/4) / Math.log(2)));
		print(treap.root, "", '\0');
		//Q = 100;
		/*
		while(Q-->0)
		{
			treap.delete(sc.nextInt());
			print(treap.root, "", '\0');
			printInorder(treap.root);
			System.out.println();
		}
		*/
		while(true)
		{
			int s = sc.nextInt();
			if(s == 1)
				treap.insert(-1, -1);
			if(s == 2)
				treap.insert(-1, 1);
			print(treap.root, "", '\0');
		}
	}
	
	void traverse(Node cur, int dep)
	{
		if(cur == null)
			maxHeight = Math.max(dep, maxHeight);
		else
		{
			traverse(cur.left, dep+1);
			traverse(cur.right, dep+1);
		}
	}
	
	static void print(Node root, String indent, char app)
	{
		if(root == null)
			return;
		System.out.println(indent + (indent.length() > 0 ? "---"+app+"---" : "") + root.toString() + "\n");
		print(root.left, indent + "\t", 'L');
		print(root.right, indent + "\t", 'R');
	}
	
	private Random gen = new Random();
	
	Node root;
	
	static void printInorder(Node node)
	{
		if(node == null)
			return;
		printInorder(node.left);
		System.out.print(node.value + " ");
		printInorder(node.right);
	}
	
	public void insert(int value, int sp)
	{ root = insert(root, value, sp); }
	
	public void delete(int value)
	{ root = delete(root, value); }
	
	public Node insert(Node node, int value, int sp)
	{
		if(node == null)
			node = new Node(value, sp);
		else if(value <= node.value)
		{
			node.left = insert(node.left, value, sp);
			if(node.left.priority > node.priority)
				node = rightRotate(node);
		}
		else
		{
			node.right = insert(node.right, value, sp);
			if(node.right.priority > node.priority)
				node = leftRotate(node);
		}
		return node;
	}
	
	public Node delete(Node node, int value)
	{
		if(node == null)
			return null;
		
		if(value < node.value)
			node.left = delete(node.left, value);
		else if(value > node.value)
			node.right = delete(node.right, value);
		else
		{
			if(node.left == null)
				node = node.right;
			else if(node.right == null)
				node = node.left;
			else
			{
				if(node.left.priority < node.right.priority)
				{
					node = leftRotate(node);
					node.left = delete(node.left, value);
				}
				else
				{
					node = rightRotate(node);
					node.right = delete(node.right, value);
				}
			}
		}
		return node;
	}
	
	public int count(Node node)
	{ return node == null ? 0 : node.count; }
	
	public Node update(Node node)
	{
		if(node == null)
			return null;
		node.count = 1;
		if(node.left != null)
			node.count += node.left.count;
		if(node.right != null)
			node.count += node.right.count;
		return node;
	}
	
	public Node rightRotate(Node node)
	{
		Node leftOfParent = node.left;
		Node rightOfChild = leftOfParent.right;
		leftOfParent.right = node;
		node.left = rightOfChild;
		return leftOfParent;
	}
	
	public Node leftRotate(Node node)
	{
		Node rightOfParent = node.right;
		Node leftOfChild = rightOfParent.left;
		rightOfParent.left = node;
		node.right = leftOfChild;
		return rightOfParent;
	}
	
	public class Node {
		
		Node left, right, parent;
		int value, count;
		long priority;
		
		Node(int value, int sp)
		{
			this.value = value;
			if(sp == 0)
			priority = gen.nextLong();
			if(sp > 0)
			priority = -1L>>>1;
			if(sp < 0)
			priority = 1L<<63;
		}
		
		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append("[ ");
			sb.append("value="+value+", ");
			sb.append("left="+(left == null ? "null" : left.value)+", ");
			sb.append("right="+(right == null ? "null" : right.value)+", ");
			sb.append("count="+count);
			sb.append("]");
			return sb.toString();
		}
		
	}
	
}
