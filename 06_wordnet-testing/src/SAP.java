import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;

public final class SAP {
    private final Digraph g;

    /**
     * Construct a Shortest Ancestral Path object and assign the Digraph {@code g} to run on
     * @param G Digraph (not necessarily a DAG)
     */
    public SAP(Digraph G) {
        g = G;
    }

    /**
     * Find the length of shortest ancestral path between node {@code v} and node {@code w}
     * @param v node
     * @param w node
     * @return path length between node {@code v} and node {@code w}, -1 if no such path
     */
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v > (g.V() - 1) || w > (g.V() - 1)) {
            throw new java.lang.IllegalArgumentException("length() called with a vertex not in digraph");
        }
        return findSAP(v, w, "length");
    }

    /**
     * Find the common ancestor of node {@code v} and node {@code w}
     * @param v node
     * @param w node
     * @return common ancestor of node {@code v} and node {@code w}; -1 if no such ancestor
     */
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v > (g.V() - 1) || w > (g.V() - 1)) {
            throw new java.lang.IllegalArgumentException("length() called with a vertex not in digraph");
        }
        return findSAP(v, w, "ancestor");
    }
    
    /**
     * Find the shortest ancestral path length and common ancestor
     * @param v node
     * @param w node
     * @param flag {"length" or "ancestor"} to indicate which to return
     * @return shortest ancestral path length if {@code flag} = "length"; common ancestor if {@code flag} = "ancestor"
     */
    private int findSAP(int v, int w, String flag) {
        // BFS search from w to every other vertex
        BreadthFirstDirectedPaths distFromW = new BreadthFirstDirectedPaths(g, w);
        
        int graphLength = g.V();
        int minLen = Integer.MAX_VALUE;
        int minAncestor = -1;
        boolean[] marked = new boolean[graphLength];
        int[] distTo = new int[graphLength];
        
        // initialize arrays 
        for (int i = 0; i < graphLength; i++) {
            marked[i] = false;
            distTo[i] = Integer.MAX_VALUE;
        }
        
        // run BFS from v
        Queue<Integer> qv = new Queue<Integer>();
        qv.enqueue(v);
        distTo[v] = 0;
        marked[v] = true;
        while (!qv.isEmpty()) {
            int curr = qv.dequeue();
            
            // quick test. if distance to ancestor from current vertex is longer than minLen
            // then no need to find distance from w -> ancestor
            // because that adds to distTo[curr]
            if (minLen < distTo[curr]) {
                break;
            }
            
            if (distFromW.hasPathTo(curr) && (distFromW.distTo(curr) + distTo[curr] < minLen)) {
                minLen = distFromW.distTo(curr) + distTo[curr];
                minAncestor = curr;
            }
            
            for (int next: g.adj(curr)) {
                if (!marked[next]) {
                    distTo[next] = distTo[curr] + 1;
                    marked[next] = true;
                    qv.enqueue(next);
                }
            }
        }
        if (flag.equals("length")) {
            if (minLen < Integer.MAX_VALUE) {
                return minLen;
            }
            else {
                return -1;
            }
        }
        return minAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    
    /**
     * Find the length of shortest ancestral path between nodes in {@code v} and nodes in {@code w}
     * @param v Some iterable list of nodes
     * @param w Some iterable list of nodes
     * @return path length between nodes in {@code v} and nodes in {@code w}, -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException("vector argument to length() is null");
        }
        
        for (int i: v) {
            if (i < 0 || i > (g.V()-1)) {
                throw new java.lang.IllegalArgumentException("some vertex in v vector, in length() doesn't exist in g");
            }
        }
        for (int i: w) {
            if (i < 0 || i > (g.V()-1)) {
                throw new java.lang.IllegalArgumentException("some vertex in w vector, in length() doesn't exist in g");
            }
        }
        return findSAP(v, w, "length");
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException("vector argument to length() is null");
        }
        for (int i : v) {
            if (i < 0 || i > (g.V()-1)) {
                throw new java.lang.IllegalArgumentException("some vertex in v vector, in length() doesn't exist in g");
            }
        }
        for (int i: w) {
            if (i < 0 || i > (g.V()-1)) {
                throw new java.lang.IllegalArgumentException("some vertex in w vector, in length() doesn't exist in g");
            }
        }
        return findSAP(v, w, "ancestor");
    }

    private int findSAP(Iterable<Integer> v, Iterable<Integer> w, String flag) {
        // BFS search from w to every other vertex
        BreadthFirstDirectedPaths distFromW = new BreadthFirstDirectedPaths(g, w);
        
        int graphLength = g.V();
        int minLen = Integer.MAX_VALUE;
        int minAncestor = -1;
        boolean[] marked = new boolean[graphLength];
        int[] distTo = new int[graphLength];
        
        // initialize arrays 
        for (int i = 0; i < graphLength; i++) {
            marked[i] = false;
            distTo[i] = Integer.MAX_VALUE;
        }
        Queue<Integer> qv = new Queue<Integer>();
        for (int i: v) {
            qv.enqueue(i);
            marked[i] = true;
            distTo[i] = 0;
        }
        
        while (!qv.isEmpty()) {
            int curr = qv.dequeue();
            
            // quick test. if distance to ancestor from current vertex is longer than minLen
            // then no need to find distance from w -> ancestor
            // because that adds to distTo[curr]
            if (minLen < distTo[curr]) {
                break;
            }
            
            if (distFromW.hasPathTo(curr) && (distFromW.distTo(curr) + distTo[curr] < minLen)) {
                minLen = distFromW.distTo(curr) + distTo[curr];
                minAncestor = curr;
            }
            
            for (int i: g.adj(curr)) {
                if (!marked[i]) {
                    qv.enqueue(i);
                    marked[i] = true;
                    distTo[i] = distTo[curr] + 1;
                }
            }
        }
        
        if (flag.equals("length")) {
            if (minLen < Integer.MAX_VALUE) {
                return minLen;
            }
            else {
                return -1;
            }
        }
        return minAncestor;
    }
    
    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
 }