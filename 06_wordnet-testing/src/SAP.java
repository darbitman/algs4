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
    
    private int findSAP(int v, int w, String flag) {
        BreadthFirstDirectedPaths dist2w = new BreadthFirstDirectedPaths(g, w);
        int graphLength = g.V();
        int minLen = 1 << 31;
        int minAncestor = -1;
        Boolean[] marked = new Boolean[graphLength];
        int[] distTo = new int[graphLength];
        for (int i = 0; i < graphLength; i++) {
            marked[i] = false;
            distTo[i] = 1 << 31;
        }
        Queue<Integer> qv = new Queue<Integer>();
        qv.enqueue(v);
        distTo[v] = 0;
        marked[v] = true;
        while (!qv.isEmpty()) {
            int curr = qv.dequeue();
            
            // quick test. if distance to ancestor from v is longer than minLen
            // then no need to find distance from w -> ancestor
            // because that adds to distTo[curr]
            if (minLen < distTo[curr]) {
                break;
            }
            
            if (dist2w.hasPathTo(curr) && (dist2w.distTo(curr) + distTo[curr] < minLen)) {
                minLen = dist2w.distTo(curr) + distTo[curr];
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
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return 0;
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