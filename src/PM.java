import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class PM{

	private byte[] inbarr = new byte[1024];
	private int lenbuf = 0, bufptr = 0;
	InputStream is;
	PrintWriter out;
	
	void solve() {
		
	}
	
	public static void main(String...strings){new PM();}
	
	public PM() {
		is = System.in;
		out = new PrintWriter(System.out);
		solve();
		out.flush();
	}
	
	int readByte() {
		if(lenbuf==-1)
			throw new InputMismatchException();
		if(bufptr>=lenbuf) {
			bufptr = 0;
			try {
				lenbuf = is.read(inbarr);
			} catch(Exception e) { 
				throw new InputMismatchException();
			}
			if(lenbuf<=0)
				return -1;
		}
		return inbarr[bufptr++];
	}
	
	boolean isSpaceChar(int b) {return (b<33 || b>126);}	
	char nc() { return (char)skip();}
	
	int skip() {
		int b;
		while((b=readByte())!=-1 && isSpaceChar(b));
		return b;
	}
	
	String ns() {
		StringBuilder builder = new StringBuilder();
		int b = skip();
		while(!isSpaceChar(b)) {
			builder.appendCodePoint(b);
			b = readByte();
		}
		return builder.toString();
	}
	
	int ni() {
		int ret = 0;
		boolean minus = false;
		int b = skip();
		if(b=='-') {
			minus = true;
			b = readByte();
		}
		while(!isSpaceChar(b) && b >= '0' && b <= '9') {
			ret = ret*10 + b -'0';
			b = readByte();
		}
		return minus ? -ret:ret;
	}
	
	long nl() {
		long ret = 0;
		boolean minus = false;
		int b = skip();
		if(b=='-') {
			minus = true;
			b = readByte();
		}
		while(!isSpaceChar(b) && b >= '0' && b <= '9') {
			ret = ret*10 + b -'0';
			b = readByte();
		}
		return minus ? -ret:ret;
	}
	
	int[] na(int n) {
		int[] ret = new int[n];
		for(int i=0;i<n;i++){
			ret[i] = ni();
		}
		return ret;
	}
	
}
