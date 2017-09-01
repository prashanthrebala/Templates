import java.util.Arrays;

class Trie2{
	
	public int size;
	private Node[] node;
	private int nodePtr;
	private Node root;
	
	public Trie2()
	{
		size = 0;
		node = new Node[100000];
		node[0] = new Node();
		root = node[0];
		nodePtr = 1;
		
	}
	
	public void add(String s)
	{
		Node cur = root;
		for(int i=0;i<s.length();i++)
		{
			char ch = s.charAt(i);
			if(cur.kids[ch-'a'] == 0)
			{
				if(nodePtr == node.length) 
					expand();
				node[nodePtr] = new Node();
				cur.kids[ch-'a'] = nodePtr++;  
			}
			cur = node[cur.kids[ch-'a']];
			cur.pref++;
		}
		cur.occ++;
		size++;
	}
	
	public boolean contains(String s)
	{
		Node cur = root;
		for(int i=0;i<s.length();i++)
		{
			char ch = s.charAt(i);
			if(cur.kids[ch-'a'] == 0)
				return false;
			cur = node[cur.kids[ch-'a']];
		}
		return cur.occ != 0;
	}
	
	public int countOf(String s)
	{
		Node cur = root;
		for(int i=0;i<s.length();i++)
		{
			char ch = s.charAt(i);
			if(cur.kids[ch-'a'] == 0)
				return 0;
			cur = node[cur.kids[ch-'a']];			
		}
		return cur.occ;
	}
	
	public int countOfPrefix(String s)
	{
		Node cur = root;
		for(int i=0;i<s.length();i++)
		{
			char ch = s.charAt(i);
			if(cur.kids[ch-'a'] == 0)
				return 0;
			cur = node[cur.kids[ch-'a']];
		}
		return cur.pref;
	}
	
	public void remove(String s)
	{
		if(del(s,root,0)) 
			size--;
	}
	
	private boolean del(String s,Node n,int d)
	{
		if(d==s.length())
		{
			if(n.occ==0)
				return false;
			n.occ--;
			n.pref--;
			return true;
		}
		if(n.kids[s.charAt(d)-'a'] != 0)
		{
			boolean found = del(s,node[n.kids[s.charAt(d)-'a']],d+1);
			if(found)
			{
				if(node[n.kids[s.charAt(d)-'a']].pref == 0)
					n.kids[s.charAt(d)-'a'] = 0;
				n.pref--;
				return true;
			}
		}
		return false;
	}
	
	private class Node{
		
		int[] kids;
		int occ;
		int pref;
		
		Node()
		{
			kids = new int[26];
			occ = 0;
			pref = 0;
		}
		
		public String toString()
		{ 
			StringBuilder sb = new StringBuilder();
			sb.append("[ ");
			for(int i=0;i<26;i++)
				if(kids[i] != 0)
					sb.append(" [" + (char)('a' + i) + ", " + node[kids[i]].toString()+"],");
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}
		
	}
	
	private void expand()
	{ node = Arrays.copyOf(node, nodePtr + nodePtr/2); }
	
}