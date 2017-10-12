import java.lang.*;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Point2D;

public class KdTree {
    Node root;
    
    private class Node {
        private Point2D point;
        private int orientation;
        private Node left;
        private Node right;
        
        public Node(Point2D p, int orientation, Node left, Node right) {
            this.point = p;
            this.orientation = orientation;
            this.left = left;
            this.right = right;
        }
        
        public Node(Point2D p, int orientation) {
            this.point = p;
            this.orientation = orientation;
            this.left = null;
            this.right = null;
        }
    }

    public KdTree() {
        root = null;
        
        // TODO Auto-generated constructor stub
    }

    private void checkNull(Object o, String msg) {
        if (o == null) {
            throw new java.lang.IllegalArgumentException(msg);
        }
    }
    
    public static void main(String[] args) {
        KdTree x = new KdTree();
        
    }
}