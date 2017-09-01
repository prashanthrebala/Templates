import java.util.Arrays;

public class StringAutomaton2 {
	
	private Node root;
	private Node[] node;
	private int nodePtr = 1;
	
	public StringAutomaton2()
	{
		node = new Node[100];
		node[0] = new Node('*');
		root = node[0];
		root.par = root;
	}
	
	public void add(String str)
	{
		Node cur = root;
		for(char c : str.toCharArray())
		{
			if(cur.kids[c-'a'] == -1)
			{
				if(nodePtr == node.length) expand();
				node[nodePtr] = new Node(c);
				cur.kids[c-'a'] = nodePtr++;
			}
			Node temp = cur;
			cur = node[cur.kids[c-'a']];
			cur.par = temp;
		}
		cur.isEnd = true;
	}
	
	public void buildAutomaton()
	{
		Node[] q = new Node[nodePtr];
		q[0] = root;
		q[0].fall = root;
		int ptr = 1;
		for(int p=0;p<ptr;p++)
		{
			Node cur = q[p];
			for(int i=0;i<26;i++)
				if(cur.kids[i] != -1)
					q[ptr++] = node[cur.kids[i]];
			
			Node curFall = cur.par.fall;
			while(cur.KEY != '*' && curFall.kids[cur.KEY-'a'] == -1 && curFall != root)
				curFall = curFall.fall;
			
			if(cur.KEY != '*' && curFall.kids[cur.KEY - 'a'] != -1)
				curFall = node[curFall.kids[cur.KEY - 'a']];
			
			if(curFall == cur)
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
			while((ch == ' ' || cur.kids[ch-'a'] == -1) && cur != root)
				cur = cur.fall;
			
			if(ch != ' ' && cur.kids[ch-'a'] != -1)
				cur = node[cur.kids[ch-'a']];
			
			Node temp = cur;
			
			while(temp != root)
			{
				if(temp.isEnd)
				{/*TODO*/}
				temp = temp.fall;
			}
		}
	}

	@SuppressWarnings("unused")
	private String getStringFromEnd(Node n)
	{
		StringBuilder sb = new StringBuilder();
		while(n != root)
		{
			sb.append(n.KEY);
			n = n.par;
		}
		return sb.reverse().toString();
	}
	
	private void expand()
	{ node = Arrays.copyOf(node, nodePtr + nodePtr/2); }
	
	private class Node {
		
		private final char KEY;
		private int[] kids;
		private boolean isEnd = false;
		private Node par;
		private Node fall;
		
		Node(char cur)
		{
			this.KEY = cur;
			kids = new int[26];
			Arrays.fill(kids, -1);
		}
		
	}
}
