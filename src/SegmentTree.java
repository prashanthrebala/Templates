
public class SegmentTree {
	
	public int B, S, N;
	public int[] tree;
	public int[] lazy;
	public final int DEF = 0; 
	
	SegmentTree(int[] a)
	{
		N = a.length;
		for(S = 1;S < N;S <<= 1);
		S <<= 1;
		tree = new int[S];
		lazy = new int[S];
		B = S>>1;
		for(int i=0;i<N;i++) tree[B+i] = a[i];
		for(int i=B+N;i<S;i++) tree[i] = DEF;
		for(int i=B-1;i>0;i--) propagate(i);
	}
	
	public int query(int l, int r)
	{ 
		if (l > r) 
			return DEF;
		return query(1, l, r, 0, B-1);
	}
	
	private int query(int cur, int l, int r, int curL, int curR)
	{
		if(curL > r || curR < l)
			return 0;
		
		if(lazy[cur] != 0)
		{
			tree[cur] += lazy[cur];
			if(cur < B)
			{
				int mid = (curL + curR + 1)/2;
				int left  = (mid - curL) * lazy[cur] / (curR - curL + 1);
				int right = (curR - mid + 1) * lazy[cur] / (curR - curL + 1);
				lazy[2*cur] += left;
				lazy[2*cur+1] += right;
			}
			lazy[cur] = 0;
		}
		
		if(l <= curL && curR <= r)
			return tree[cur];
		int mid = (curL + curR + 1)/2;
		int left  = query(2*cur, l, r, curL, mid-1);
		int right = query(2*cur+1, l, r, mid, curR);
		return left + right;
	}
	
	void pointUpdate(int pos, int val)
	{
		tree[B+pos] = val;
		for(int i=(B+pos)>>1;i>0;i>>=1)
			propagate(i);
	}
	
	void rangeUpdate(int l, int r, int val)
	{
		if(l > r)
			return;
		rangeUpdate(1, l, r, 0, B-1, val);
	}
	
	void rangeUpdate(int cur, int l, int r, int curL, int curR, int val)
	{
		if(curL > r || curR < l)
			return;
		int mid = (curL + curR + 1)/2;
		
		if(lazy[cur] != 0)
		{
			tree[cur] += lazy[cur];
			if(cur < B)
			{
				int left  = (mid - curL) * lazy[cur] / (curR - curL + 1);
				int right = (curR - mid + 1) * lazy[cur] / (curR - curL + 1);
				lazy[2*cur] += left;
				lazy[2*cur+1] += right;
			}
			lazy[cur] = 0;
		}
		
		if(l <= curL && curR <= r)
		{
			int left  = (mid - curL) * val;
			int right = (curR - mid + 1) * val;
			if(cur < B)
			{
				lazy[2*cur] += left;
				lazy[2*cur+1] += right;
			}
			tree[cur] += left + right;
			return;
		}
		
		rangeUpdate(2*cur, l, r, curL, mid-1, val);
		rangeUpdate(2*cur+1, l, r, mid, curR, val);
		propagate(cur);
		
	}
	
	void propagate(int x)
	{ tree[x] =  tree[2*x] + tree[2*x+1]; }
	
}
