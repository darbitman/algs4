import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;

public class SAP {
    private Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        g = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v > g.V() - 1 || w > g.V() - 1) {
            throw new java.lang.IndexOutOfBoundsException("length() called with a vertex not in digraph");
        }
        return findSAP(v, w, "length");
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v > g.V() - 1 || w > g.V() - 1) {
            throw new java.lang.IndexOutOfBoundsException("length() called with a vertex not in digraph");
        }
        return findSAP(v, w, "ancestor");
    }
    
    private int findSAP(int v, int w, String flag) {
        // BFS search from w to every other vertex
        BreadthFirstDirectedPaths distFromW = new BreadthFirstDirectedPaths(g, w);
        
        int graphLength = g.V();
        int minLen = Integer.MAX_VALUE;
        int minAncestor = -1;
        Boolean[] marked = new Boolean[graphLength];
        int[] distTo = new int[graphLength];
        
        // initialize arrays 
        for (int i = 0; i < graphLength; i++) {
            marked[i] = false;
            distTo[i] = Integer.MAX_VALUE;
        }
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
            
            for (int next : g.adj(curr)) {
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
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0;
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
 }