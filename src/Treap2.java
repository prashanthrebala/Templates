import java.util.Random;
public class Treap2 {

	Random ran = new Random();
	
	
	
	public class Node {
		int val, count;
		Node left, right, parent;
		long priority;
		
		Node(int val)
		{
			this.val = val;
			priority = ran.nextLong();
		}
		
	}
	
}
