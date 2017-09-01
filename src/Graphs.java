import java.util.Arrays;

class Graph{
	
	public int[][] buildUG(int n,int[] v1,int[] v2)
	{
		int[][] graph = new int[n][];
		int[] cn = new int[graph.length];
		for(int i=0;i<v1.length;i++)
		{
			cn[v1[i]]++;
			cn[v2[i]]++;
		}
		for(int i=0;i<graph.length;i++)
			graph[i] = new int[cn[i]];
		for(int i=0;i<v1.length;i++)
		{
			graph[v1[i]][--cn[v1[i]]] = v2[i];
			graph[v2[i]][--cn[v2[i]]] = v1[i];
		}
		return graph;
	}
	
	public int[][] buildDG(int n,int[] v1,int[] v2)
	{
		int[][] graph = new int[n][];
		int[] cn = new int[graph.length];
		for(int i=0;i<v1.length;i++)
			cn[v1[i]]++;
		for(int i=0;i<graph.length;i++)
			graph[i] = new int[cn[i]];
		for(int i=0;i<v1.length;i++)
			graph[v1[i]][--cn[v1[i]]] = v2[i];
		return graph;
	}
	
	public int[][] buildUGFromPar(int[] par)
	{
		int n = par.length + 1;
		int[][] g = new int[n][];
		int[] cn = new int[n];
		for(int i=0;i<n-1;i++)
		{
			cn[i]++;
			cn[par[i]]++;
		}
		for(int i=0;i<n;i++)
			g[i] = new int[cn[i]];
		for(int i=0;i<n-1;i++)
		{
			g[i][--cn[i]] = par[i];
			g[par[i]][--cn[par[i]]] = i; 
		}
		return g;
	}
	
	public int[][][] buildWUG(int n, int[] v1,int[] v2,int[] wt)
	{
		int[][][] graph = new int[n][][];
		int[] cn = new int[graph.length];
		for(int i=0;i<v1.length;i++)
		{
			cn[v1[i]]++;
			cn[v2[i]]++;
		}
		for(int i=0;i<graph.length;i++)
			graph[i] = new int[cn[i]][2];
		for(int i=0;i<v1.length;i++)
		{
			--cn[v1[i]]; --cn[v2[i]];
			graph[v1[i]][cn[v1[i]]][0] = v2[i];
			graph[v1[i]][cn[v1[i]]][1] = wt[i];
			graph[v2[i]][cn[v2[i]]][0] = v1[i];
			graph[v2[i]][cn[v2[i]]][1] = wt[i];
		}
		return graph;
	}
	
	public int[][][] buildWDG(int n, int[] v1,int[] v2,int[] wt)
	{
		int[][][] graph = new int[n][][];
		int[] cn = new int[graph.length];
		for(int i=0;i<v1.length;i++)
			cn[v1[i]]++;
		for(int i=0;i<graph.length;i++)
			graph[i] = new int[cn[i]][2];
		for(int i=0;i<v1.length;i++)
		{
			--cn[v1[i]];
			graph[v1[i]][cn[v1[i]]][0] = v2[i];
			graph[v1[i]][cn[v1[i]]][1] = wt[i];
		}
		return graph;
	}
	
	public int[][] pod(int[][] graph, int root)
	{
		int n = graph.length;
		int[] par = new int[n];
		int[] ord = new int[n];
		int[] dep = new int[n];

		par[root] = -1;
		ord[0]  = root;
		
		for(int p=0,r=1;p<r;p++)
		{
			int cur = ord[p];
			for(int kid : graph[cur])
			{
				if(par[cur] != kid)
				{
					ord[r++] = kid;
					par[kid] = cur;
					dep[kid] = dep[cur] + 1;
				}
			}
		}
		return new int[][]{par,ord,dep};
	}
	
	public int[][] pod(int[][][] graph, int root)
	{
		int n = graph.length;
		int[] par = new int[n];
		int[] ord = new int[n];
		int[] dep = new int[n];
		int[] nwt = new int[n];
		int[] pwt = new int[n];
		
		par[root] = -1;
		ord[0]  = root;
		
		for(int p=0,r=1;p<r;p++)
		{
			int cur = ord[p];
			for(int[] nex : graph[cur])
				if(par[cur] != nex[0])
				{
					par[nex[0]] = cur; 
					ord[r++] = nex[0];
					dep[nex[0]] = 1 + dep[cur];
					nwt[nex[0]] = nex[1];
					pwt[nex[0]] = pwt[cur] + nex[1];
				}
		}
		
		return new int[][]{par, ord, dep, nwt, pwt};
	}
	
	public int lca(int a, int b, int[][] spar, int[] dep)
	{
		if(dep[a] < dep[b])
			b = ancestor(spar, b, dep[b] - dep[a]);
		else if(dep[b] < dep[a])
			a = ancestor(spar, a, dep[a] - dep[b]);
		
		if(a == b)
			return a;
		
		for(int i=spar[0].length-1;i>=0;i--)
			if(spar[a][i] != -1 && spar[a][i] != spar[b][i])
			{
				a = spar[a][i];
				b = spar[b][i];
			}
		
		return spar[a][0];
	}
	
	public int ancestor(int[][] spar, int a, int dep)
	{
		for(int i=0;dep>0 && a!=-1;i++,dep>>>=1)
			if((dep & 1) == 1)
				a = spar[a][i];
		return a;
	}
	
	public int[][] buildSparseParent(int[] par)
	{
		int n = par.length;
		int m = Integer.numberOfTrailingZeros(Integer.highestOneBit(n-1)) + 1;
		int[][] spar = new int[n][m];
		for(int i=0;i<n;i++)
			spar[i][0] = par[i];
		
		for(int j=1;j<m;j++)
			for(int i=0;i<n;i++)
				spar[i][j] = spar[i][j-1] == -1 ? -1 : spar[spar[i][j-1]][j-1];
		
		return spar;
	}
	
	
	//ordinary dfs
	public void dfs(int[][] graph,boolean[] visited,int cur)
	{
		visited[cur] = true;
		for(int nex : graph[cur])
			if(!visited[nex])
				dfs(graph,visited,nex);
	}
	
	//for order array
	public int dfsptr = 0;
	public void dfs(int[][] graph,boolean[] visited,int cur,int[] ord)
	{
		visited[cur] = true;
		ord[dfsptr++] = cur;
		for(int nex : graph[cur])
			if(!visited[nex])
				dfs(graph,visited,nex,ord);
	}
	
	//for depth array
	public void dfs(int[][] graph,boolean[] visited,int cur,int[] dep,int d)
	{
		visited[cur] = true;
		dep[cur] = d;
		for(int nex : graph[cur])
			if(!visited[nex])
				dfs(graph,visited,nex,dep,d+1);
	}
	
	public int[][] bfs(int[][] graph,int source)
	{
		int[] ord = new int[graph.length];
		int[] rch = new int[graph.length];
		Arrays.fill(rch,-1);
		ord[0] = source;
		rch[source] = 0;
		for(int cur=0,ptr=1;cur<ptr;cur++)
		{
			int src = ord[cur];
			for(int nex : graph[src])
			{
				if(rch[nex] == -1)
				{
					ord[ptr++] = nex;
					rch[nex] = rch[src] + 1;
				}
			}
		}
		return new int[][]{ord,rch};
	}
	
	public int[][] tourOSED(int[][] g, int root)
	{
		int n = g.length;
		int[] ord = new int[n];
		int[] str = new int[n];
		int[] ext = new int[n];
		int[] dep = new int[n];
		
		boolean[] vis = new boolean[n];
		int[] stack = new int[n];
		int ptr = 0, top = 0;
		vis[root] = true;
		ord[ptr++] = root;
		stack[++top] = root;
		int time = 0;
		while(top >= 0)
		{
			int cur = stack[--top];
			for(int nex : g[stack[top]]) if(!vis[nex])
			{
				vis[nex] = true;
				ord[ptr++] = nex;
				str[nex] = ++time;
				stack[top++] = nex;
			}
			ext[stack[--top]] = time;
		}
		return new int[][]{ord, str, ext, dep};
	}
	
}

public class Graphs {
	public static void main(String[] args)
	{
		Graph gu = new Graph();
		int n = 7;
		int[] v1 = {0, 0, 1, 1, 1, 4};
		int[] v2 = {1, 2, 3, 5, 6, 3};
		
		int[][] g = gu.buildUG(n, v1, v2);
		int[][] stat = gu.tourOSED(g, 0);
		System.out.println(Arrays.deepToString(new Object[]{stat}));
	}
}
