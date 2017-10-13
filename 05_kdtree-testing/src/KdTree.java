import java.lang.*;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Point2D;

public class KdTree {
    private Node root;
    private int size;
    private final int X = 1;
    private final int Y = 2;

    private class Node {
        private Point2D point;
        private int orientation;
        private Node left;
        private Node right;

        public Node(Point2D p, int orientation) {
            this.point = p;
            this.orientation = orientation;
            this.left = null;
            this.right = null;
        }
    }

    public KdTree() {
        this.root = null;
        this.size = 0;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNull(p, "insert() has null argument");
        add(p, this.root, this.root.orientation);
    }

    private void add(Point2D p, Node n, int orientation) {
        if (n == null) {
            n = new Node(p, orientation);
            this.size++;
        }
        if (p.x() == n.point.x() && p.y() == n.point.y()) {
            return;
        }
        if (orientation == this.X) {
            if (p.x() < n.point.x()) {
                add(p, n.left, this.Y);
            }
            else {
                add(p, n.right, this.Y);
            }
        }
        else {
            if (p.y() < n.point.y()) {
                add(p, n.left, this.X);
            }
            else {
                add(p, n.right, this.X);
            }
        }
    }
        

    private void checkNull(Object o, String msg) {
        if (o == null) {
            throw new java.lang.IllegalArgumentException(msg);
        }
    }

    public static void main(String[] args) {

    }
}