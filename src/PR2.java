import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class PR2{

	void solve()
	{
		
	}
	
	public static void main(String[] args) throws Exception {new PR2().run(true);}
	
	private byte[] bufferArray = new byte[1024];
	private int bufLength = 0;
	private int bufCurrent = 0;
	InputStream inputStream;
	PrintWriter out;
	
	public void run(boolean stdio) throws FileNotFoundException
	{
		inputStream = stdio ? System.in : new FileInputStream("");
		out = stdio ? new PrintWriter(System.out) : new PrintWriter(new File(""));
		solve();
		out.flush();
	}
	
	int nextByte()
	{
		if(bufLength==-1)
			throw new InputMismatchException();
		if(bufCurrent>=bufLength)
		{
			bufCurrent = 0;
			try
			{bufLength = inputStream.read(bufferArray);}
			catch(IOException e)
			{ throw new InputMismatchException();}
			if(bufLength<=0)
				return -1;
		}
		return bufferArray[bufCurrent++];
	}
	
	boolean isSpaceChar(int x)
	{return (x<33 || x>126);}
	
	boolean isDigit(int x)
	{return (x>='0' && x<='9');}
	
	int nextNonSpace()
	{
		int x;
		while((x=nextByte())!=-1 && isSpaceChar(x));
		return x;
	}
	
	int ni()
	{
		long ans = nl();
		if ( Integer.MIN_VALUE <= ans && ans <= Integer.MAX_VALUE )
			return (int)ans;
		throw new InputMismatchException();
	}
	
	long nl()
	{
		long ans = 0;
		boolean neg = false;
		int x = nextNonSpace();
		if(x=='-') 
		{
			neg = true;
			x = nextByte();
		}
		while(!isSpaceChar(x))
		{
			if(isDigit(x))
			{
				ans = ans*10 + x -'0';
				x = nextByte();
			}
			else
				throw new InputMismatchException();
		}
		return neg ? -ans:ans;
	}
	
	String ns()
	{
		StringBuilder sb = new StringBuilder();
		int x = nextNonSpace();
		while(!isSpaceChar(x))
		{
			sb.append((char)x);
			x = nextByte();
		}
		return sb.toString();
	}
	
	char nc()
	{ return (char)nextNonSpace();}
	
	double nd()
	{ return (double)Double.parseDouble(ns()); }
	
	char[] ca()
	{ return ns().toCharArray();}
	
	char[] ca(int n)
	{
		char[] ans = new char[n];
		int p =0;
		int x = nextNonSpace();
		while(p<n)
		{
			ans[p++] = (char)x;
			x = nextByte();
		}
		return ans;
	}
	
	int[] ia(int n)
	{
		int[] ans = new int[n];
		for(int i=0;i<n;i++)
			ans[i]=ni();
		return ans;
	}
	
}

