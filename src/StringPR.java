
public class StringPR {
	
	public static void main(String[] args)
	{
		
	}
	
	
	public static long[] generateHash(char[] s)
	{
		long[] ans = new long[s.length];
		int P = 31;
		long MOD = 3037000181L;
		
		ans[0] = s[0]-96; //or 64
		long mul = 1;
		for(int i=1;i<ans.length;i++)
		{
			mul = mul*P;
			mul %= MOD;
			ans[i] = (s[i]-96) * mul % MOD;
			ans[i] = (ans[i] + ans[i-1]) % MOD;
		}
	
		return ans;
	}
	
	public static long[] getHashForLength(int length, long[] hash, long[] inv3b)
	{
		long[] ans = new long[hash.length-length+1];
		long MOD = 3037000181L;
		
		for(int i=0;i<ans.length;i++)
		{
			ans[i] = hash[i+length-1];
			if(i > 0)
				ans[i] = (ans[i] - hash[i-1] + MOD) % MOD;
			ans[i] *= inv3b[i];
			ans[i] %= MOD;
		}
	
		return ans;
	}
	
	public static int[] pi(char[] s)
	{
		int n = s.length;
		int[] pi = new int[n];
		int l = 0;
		int r = 1;
		while(r < n)
		{
			if(s[l] == s[r])
				pi[r++] = l++ + 1; 
			else
				if(l != 0)
					l = pi[l-1];
				else
					r++;
		}
		return pi;
	}
	
	public static int[] Z(char[] s)
	{
		int n = s.length;
		int[] z = new int[n];
		int l = 0;
		int r = 0;
		for(int i=1;i<n;i++)
		{
			if(i>r)
			{
				l = r = i;
				while(r<n && s[r-l]==s[r])
					r++;
				z[i] = r-- - l;
			}
			else
			{
				if(z[i-l] <= r-i)
					z[i] = z[i-l];
				else
				{
					l = i;
					while(r<n && s[r-l]==s[r])
						r++;
					z[i] = r-- - l;
				}
			}
		}
		return z;
	}
	
	
}
