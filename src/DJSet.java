public class DJSet {

	public int[] root;
	public int[] size;
	public boolean[] head;
	public int setCount;
	
	public DJSet(int n)
	{
		root = new int[n];
		size = new int[n];
		head = new boolean[n];
		setCount = n;
		for(int i=0;i<n;i++)
		{
			root[i] = i;
			size[i] = 1;
			head[i] = true;
		}
	}
	
	public int root(int x)
	{ return root[root[x]] == root[x] ? root[x] : (root[x] = root(root[x]));}
	
	public boolean union(int x, int y)
	{
		int a = root(x);
		int b = root(y);
		
		if(a == b)
			return false;
		
		setCount--;
		
		if(size[a] < size[b])
		{
			head[a] = false;
			root[a] = b;
			size[b] += size[a];
		}
		else
		{
			head[b] = false;
			root[b] = a;
			size[a] += size[b];
		}
		return true;
	}
	
	public boolean find(int x, int y)
	{ return root(x) == root(y); }
	
	public int[] getSets()
	{
		int[] heads = new int[setCount];
		int ptr = 0;
		for(int i=0;i<head.length;i++)
			if(head[i])
				heads[ptr++] = i;
		return heads;
	}
	
}
