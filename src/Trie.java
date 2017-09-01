import java.util.HashMap;

class Trie{
	
	private Node root;
	public int size = 0;
	
	public Trie()
	{
		root = new Node();
	}
	
	public void add(String s)
	{
		Node cur = root;
		for(int i=0;i<s.length();i++)
		{
			char ch = s.charAt(i);
			if(!cur.kids.containsKey(ch))
				cur.kids.put(ch,new Node());
			cur = cur.kids.get(ch);
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
			if(!cur.kids.containsKey(ch))
				return false;
			cur = cur.kids.get(ch);
		}
		return cur.occ!=0;
	}
	
	public int countOf(String s)
	{
		Node cur = root;
		for(int i=0;i<s.length();i++)
		{
			char ch = s.charAt(i);
			if(!cur.kids.containsKey(ch))
				return 0;
			cur = cur.kids.get(ch);			
		}
		return cur.occ;
	}
	
	public int countOfPrefix(String s)
	{
		Node cur = root;
		for(int i=0;i<s.length();i++)
		{
			char ch = s.charAt(i);
			if(!cur.kids.containsKey(ch))
				return 0;
			cur = cur.kids.get(ch);
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
		if(n.kids.containsKey(s.charAt(d)))
		{
			boolean found = del(s,n.kids.get(s.charAt(d)),d+1);
			if(found)
			{
				if(n.kids.get(s.charAt(d)).pref==0)
					n.kids.remove(s.charAt(d));
				n.pref--;
				return true;
			}
		}
		return false;
	}
	
	private class Node{
		
		HashMap<Character,Node> kids;
		int occ;
		int pref;
		
		Node()
		{
			kids = new HashMap<>();
			occ = 0;
			pref = 0;
		}
		
		public String toString()
		{ return "["+kids.toString()+"count: "+occ+"]";	}
		
	}
}

/*
    public void add(int x)
    {
    	add(String.format("%32s",Integer.toBinaryString(x)).replaceAll(" ","0"));
	}

 */

