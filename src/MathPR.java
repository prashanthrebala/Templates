import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
public class MathPR {
	
	final int MOD = 1000000007;
	final BigInteger MODZ = BigInteger.valueOf(MOD);
	
	void test()
	{
		// from 999836351609 to 999999999989
		//System.out.println((1L<<63) );
		Scanner sc = new Scanner(System.in);
		
		int n = 100000;
		double d = sT();
		System.out.println(primes(n).length);
		d = gT(d);
		System.out.println(d);
		d = sT();
		d = gT(d);
		System.out.println(d);
		
		
		sc.close();
	}
	
	class IntSet {
	
		private int[] a;
		
		public IntSet(int n){ a = new int[(n >> 5) + 1]; }
		
		public void add(int x)
		{
			int i = x >> 5;
			int j = x & 31;
			a[i] |= 1 << j;
		}
		
		public void remove(int x)
		{
			int i = x >> 5;
			int j = x & 31;
			a[i] &= ~(1 << j);
		}
		
		public boolean contains(int x)
		{
			int i = x >> 5;
			int j = x & 31;
			return (a[i]>>j & 1) == 1;
		}
		
	}
	
	long[][] nCrTable(int n)
	{
		long[][] ans = new long[n+1][n+1];
		for(int i=0;i<=n;i++)
			ans[i][0] = ans[i][i] = 1L;
		for(int i=1;i<=n;i++)
			for(int j=1;j<i;j++)
				ans[i][j] = ans[i-1][j] + ans[i-1][j-1];
		return ans;
	}
	
	long nPr(int n, int r, long[][] fif, int mod)
	{
		if(r < 0 || n < 0 || n < r)
			return 0;
		return fif[0][n] * fif[1][n-r] % mod;
	}
	
	long nCr(int n, int r, long[][] fif, int mod)
	{
		if(r < 0 || n < 0 || n < r)
			return 0;
		return fif[0][n] * fif[1][r] % mod * fif[1][n-r] % mod;
	}
	
	long[][] enumFactorials(int n, int mod)
	{
		long[] num = new long[n+1];
		long[] den = new long[n+1];
		num[0] = 1;
		for(int i=1;i<=n;i++)
			num[i] = num[i-1] * i % mod;
		long m = mod;
		long x = 0, y = 1, a = num[n];
		while(a > 1)
		{
			long q = a / m;
			long t = m;
			m = a % m;
			a = t;
			t = x;
			x = y - q*x;
			y = t;
		}
		if(y < 0)
			y += mod;
		den[n] = y;
		for(int i=n-1;i>=0;i--)
			den[i] = den[i+1] * (i+1) % mod;
		return new long[][]{num, den};
	}
	
	int[] log2(int n)
	{
		int[] ret = new int[n+1];
		int log = 0, p2 = 2;
		for(int i=1;i<=n;i++)
		{
			if(i == p2)
			{
				log++;
				p2<<=1;
			}
			ret[i] = log;
		}
		return ret;
	}
	
	int[][] sparseAndLogGen(int[] a, int[] log2)
	{
		int n = a.length;
		int log = 0, p2 = 2;
		for(int i=1;i<=n;i++)
		{
			if(i == p2)
			{
				log++;
				p2<<=1;
			}
			log2[i] = log;
		}
		int m = log2[n] + 1;
		int[][] spar = new int[n][m];
		for(int i=0;i<n;i++)
			spar[i][0] = i;
		for(int j=1,k=1;j<m;j++,k<<=1)
			for(int i=0;i+2*k<=n;i++)
				spar[i][j] = a[spar[i][j-1]] < a[spar[i+k][j-1]] ? spar[i][j-1] : spar[i+k][j-1];
		return spar;
	}
	
	int getMin(int[][] spar, int i, int j, int[] log2, int[] a)
	{
		int k = log2[j-i+1];
		return a[spar[i][k]] < a[spar[j-(1<<k)+1][k]] ?  spar[i][k] : spar[j-(1<<k)+1][k];
	}
	
	int[] matrixExp(int[][] a, int[] x, long pow)
	{
		int N = a.length;
		int[][] res = new int[N][N];
		for(int i=0;i<N;i++) res[i][i] = 1;
		while(pow != 0)
		{
			if((pow & 1) == 1)
				res = multiply(res, a);
			a = multiply(a, a);
			pow >>= 1;
		}
		
		int[] ans = new int[N];
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				ans[i] += (int)(1L * res[i][j] * x[j]);
		
		return ans;
	}
	
	int[][] multiply(int[][] a, int[][] b)
	{
		int X = a.length;
		int Y = b[0].length;
		int Z = b.length;
		int[][] c = new int[X][Y];
		for(int i=0;i<X;i++)
			for(int j=0;j<Y;j++)
				for(int k=0;k<Z;k++)
					c[i][j] += (int)(1L * a[i][k] * b[k][j]);
		return c;
	}
	
	public long pow(long a, long b, long mod)
	{
		long ans = 1;
		long mul = a % mod;
		while(b != 0)
		{
			if((b&1) == 1)
				ans = ans * mul % mod;
			mul = mul * mul % mod;
			b>>=1;
		}
		return ans;
	}
	
	public long inv(long a, long mod)
	{
		long m = mod;
		long x = 0, y = 1;
		while(a > 1)
		{
			long q = a / m;
			long t = m;
			m = a % m;
			a = t;
			t = x;
			x = y - q*x;
			y = t;
		}
		if(y < 0)
			y += mod;
		return y;
	}
	
	public long gcd(long a, long b)
	{
		long r;
		while(b != 0)
		{
			r = a % b;
			a = b;
			b = r;
		}
		return a;
	}

	public int[] primes(int n)
	{
		boolean[] notPrime = new boolean[n+1];
		notPrime[0] = notPrime[1] = true;
		for(int i=2;i*i<=n;i++)
			if(!notPrime[i])
				for(int j=i*i;j<=n;j+=i)
					notPrime[j] = true;
		int[] primes = new int[n];
		int ptr = 0;
		for(int i=2;i<=n;i++)
			if(!notPrime[i])
				primes[ptr++] = i;
		return Arrays.copyOf(primes, ptr);
	}
	
	public boolean[] primeBoolean(int n)
	{
		boolean[] prime = new boolean[n+1];
		prime[0] = prime[1] = true;
		for(int i=2;i*i<=n;i++)
			if(!prime[i])
				for(int j=i*i;j<=n;j+=i)
					prime[j] = true;
		for(int i=0;i<=n;i++)
			prime[i] = !prime[i];
		return prime;
	}
	
	int[] enumLPF(int n)
	{
		int[] ans = new int[n+1];
		for(int i=2;i<=n;i++)
			if(ans[i] == 0)
				for(int j=i;j<=n;j+=i)
					if(ans[j] == 0)
						ans[j] = i;
		return ans;
	}
	
	int phi(int n, int[] primes)
	{
		int ans = n;
		for(int i=0;i<primes.length && primes[i]<=n;i++)
			if(ans % primes[i] == 0)
				ans -= ans / primes[i];
		return ans;
	}
	
	public static void main(String[] args){new MathPR().test();}
	
	double sT(){return System.currentTimeMillis();}
	double gT(double s){return System.currentTimeMillis()-s;}
	
	
}
