import java.util.Scanner;
class Fenwick {

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] a = new int[n];
		for(int i=0;i<n;i++)
			a[i] = sc.nextInt();
		int[] fen1 = buildFenwick(a);
		int[] fen2 = buildFenwick(a);
		while(a != null)
		{/*
			int ch = sc.nextInt();
			if(ch == 1)
			{
				int x = sc.nextInt() - 1, val = sc.nextInt();
				int ent = sc.nextInt(), sc.nextInt();
				if(x % 2 == 0)
				{
					update(fen2, ent, val);
					update(fen1, ent, -val);
					update(fen2, ext+1, val);
					update(fen1, ext+1, -val);
				}
				else
				{
					update(fen1, ent, val);
					update(fen2, ent, -val);
					update(fen1, ext+1, val);
					update(fen2, ext+1, -val);
				}
			}
			else
			{
				int x = sc.nextInt() - 1;
				long ans1, ans2;
				if(x % 2 == 0)
				{
					ans1 = sumFenwick(fen2, x);
					ans2 = sumFenwick(fen2, x-1);
				}
				else
				{
					ans1 = sumFenwick(fen1, x);
					ans2 = sumFenwick(fen1, x-1);
				}
				System.out.println(ans1 - ans2);
			}*/
		}
		sc.close();
			
	}
	
	static int[] buildFenwick(int[] arr)
	{
		int[] ans = new int[arr.length+1];
		for(int i=0;i<arr.length;i++)
			for(int j=i+1;j<ans.length;j+=j&-j)
				ans[j] += arr[i];
		return ans;
	}
	
	static long sumFenwick(int[] ft, int x)
	{
		// x = 0-based index in original array
		long ans = 0;
		for(int i=++x;i>0;i-=i&-i)
			ans += ft[i];
		return ans;
	}
	
	static void update(int[] ft, int x, int val)
	{
		// x = 0-based index in original array
		for(int i=++x;i<ft.length;i += i&-i)
			ft[i] += val;
	}
	
}
