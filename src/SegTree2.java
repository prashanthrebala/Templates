public class SegTree2 {

	int[] tree;
	int[] lazy;
	int[] arr;
	
	public SegTree2(int[] arr)
	{
		int p = 1;
		while(p<arr.length) p<<=1;
		this.arr = arr;
		tree = new int[2*p-1];
		lazy = new int[2*p-1];
		build();
	}
	
	public int query(int i,int j){return query(0,0,arr.length-1,i,j);}

	public void updateRange(int x,int y,int val){update(0,0,arr.length-1,x,y,val);}
	
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
		if(lazy[cur]!=0) //TODO complete
		{
			tree[cur] += lazy[cur];
			if(left!=right)
			{
				int len = right-left+1;
				int mid = left + (right-left)/2;
				lazy[2*cur+1] += (lazy[cur]*(mid-left+1))/len; //propagate to left
				lazy[2*cur+2] += (lazy[cur]*(right-mid))/len; //propagate to right
			}
			lazy[cur] = 0;
		}
		
		if(j<left || i>right) //query outside range
			return 0;	//TODO 
		
		if(i<=left && right<=j) //range inside query 
			return tree[cur];
		
		//Partial
		int mid = left + (right-left)/2;
		int ans1 = query(2*cur+1,left,mid,i,j);
		int ans2 = query(2*cur+2,mid+1,right,i,j);
		return ans1 + ans2; //TODO
	}
	
	private void update(int cur,int left,int right,int x,int y,int val)
	{
		if(left>right) return;
		
		if(lazy[cur]!=0) //Node must be updated TODO complete
		{
			tree[cur] += lazy[cur]; 
			if(left!=right) //Propagate to next
			{
				int len = right-left+1;
				int mid = left + (right-left)/2;
				lazy[2*cur+1] += (lazy[cur]*(mid-left+1))/len; //propagate to left
				lazy[2*cur+2] += (lazy[cur]*(right-mid))/len; //propagate to right
			}
			lazy[cur] = 0;
		}
		
		if(x>right || y<left) //query outside range
			return;
		
		if(x<=left && right<=y) //range inside query
		{
			tree[cur] += (right-left+1)*val; //TODO
			
			if(left!=right)
			{
				int mid = left + (right-left)/2;
				lazy[2*cur+1] += (mid-left+1)*val; //TODO
				lazy[2*cur+2] += (right-mid)*val; //TODO
			}
			
			//Next updates have been propagated return
			return;
		}
		
		//Partial
		int mid = left + (right-left)/2;
		update(2*cur+1,left,mid,x,y,val);
		update(2*cur+2,mid+1,right,x,y,val);
		
		tree[cur] = tree[2*cur+1] + tree[2*cur+2]; //TODO
		
	}
	
}
