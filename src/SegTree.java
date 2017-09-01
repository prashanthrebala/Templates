public class SegTree {

//SUM OF RANGE
	
	int[] tree;
	int[] arr;
	
	SegTree(int[] arr)
	{
		int p = 1;
		while(p<arr.length) p<<=1;
		this.arr = arr;
		tree = new int[2*p-1];
		build();
	}
	
	public int query(int i,int j){return query(0,0,arr.length-1,i,j);}

	private void build(){construct(0,0,arr.length-1);}
	
	private void construct(int root,int left,int right)
	{
		if(left==right)
		{
			tree[root] = arr[left];
			return;
		}
		int mid = left + (right-left)/2;
		construct(2*root+1,left,mid);
		construct(2*root+2,mid+1,right);
		tree[root] = tree[2*root+1] + tree[2*root+2]; //TODO
	}

	private int query(int cur,int left,int right,int i,int j)
	{
		if(j<left || i>right) //query outside range
			return 0;	//TODO 
		
		if(i<=left && right<=j) //range inside query 
			return tree[cur]; //TODO
		
		int mid = left + (right-left)/2; // partial
		int ans1 = query(2*cur+1,left,mid,i,j);
		int ans2 = query(2*cur+2,mid+1,right,i,j);
		return ans1 + ans2; //TODO
	}
}
