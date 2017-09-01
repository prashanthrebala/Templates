import java.util.HashMap;
import java.util.Scanner;

@SuppressWarnings("unused")
public class StringAutomaton {

	private Node root;
	private int nodeCount = 1;
	
	public StringAutomaton()
	{
		root = new Node('*');
		root.par = root;
	}
	
	public void add(String str)
	{
		Node cur = root;
		for(char c : str.toCharArray())
		{
			if(!cur.kids.containsKey(c))
			{
				cur.kids.put(c, new Node(c));
				nodeCount++;
			}
			Node temp = cur;
			cur = cur.kids.get(c);
			cur.par = temp;
		}
		cur.occ++;
		cur.isEnd = true;
		cur.strLength = str.length();
		cur.string = new String(str);
	}
	
	public void buildAutomaton()
	{
		Node[] q = new Node[nodeCount];
		q[0] = this.root;
		q[0].fall = this.root;
		int ptr = 1;
		for(int p=0;p<ptr;p++)
		{
			Node cur = q[p];
			for(Node nex : cur.kids.values())
				q[ptr++] = nex;
			
			Node curFall = cur.par.fall;
			while(curFall.kids.get(cur.KEY) == null && curFall != root)
				curFall = curFall.fall;
			
			curFall = curFall.kids.get(cur.KEY);
			
			if(curFall == null || curFall == cur)
				curFall = root;
			
			cur.fall = curFall;
		}
	}
	
	public void searchThrough(String str)
	{
		Node cur = root;
		char[] text = str.toCharArray();
		for(char ch : text)
		{
			while(cur.kids.get(ch) == null && cur != root)
				cur = cur.fall;
			
			if(cur.kids.get(ch) != null)
				cur = cur.kids.get(ch);
			
			Node temp = cur;
			
			while(temp != root)
			{
				if(temp.isEnd)
					System.out.println(temp.string);
				temp = temp.fall;
			}
		}
	}

	private class Node {
		
		public final char KEY;
		private HashMap<Character, Node> kids = new HashMap<>();
		private Node par;
		private Node fall;
		private int occ = 0;
		private int strLength;
		private String string;
		private boolean isEnd = false;
		
		Node(char cur)
		{ this.KEY = cur; }
		
		public String toString()
		{ return KEY + " : " + kids.toString(); }
		
	}
}
