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
        add(p, this.root, this.X);
    }

    // recursive add to Kd tree.
    private void add(Point2D p, Node n, int orientation) {
        if (n == null) {
            n = new Node(p, orientation);
            this.size++;
        }
        else if (p.x() == n.point.x() && p.y() == n.point.y()) {
            return;
        }
        else if (n.orientation == this.X) {
            if (p.x() < n.point.x()) {
                add(p, n.left, this.Y);
            }
            else {
                add(p, n.right, this.Y);
            }
        }
        else if (n.orientation == this.Y) {
            if (p.y() < n.point.y()) {
                add(p, n.left, this.X);
            }
            else {
                add(p, n.right, this.X);
            }
        }
        else {
            throw new java.lang.IllegalStateException("recursive add() failed");
        }
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNull(p, "contains() has null argument");
        return contains(p, this.root); 
    }
    
    private boolean contains(Point2D p, Node n) {
        if (n == null) {
            return false;
        }
        else if (p.x() == n.point.x() && p.y() == n.point.y()) {
            return true;
        }
        else if (n.orientation == this.X) {
            if (p.x() < n.point.x()) {
                return contains(p, n.left);
            }
            else if(p.x() > n.point.x()) {
                return contains(p, n.right);
            }
        }
        else if (n.orientation == this.Y) {
            if (p.y() < n.point.y()) {
                return contains(p, n.left);
            }
            else if (p.y() > n.point.y()) {
                return contains(p, n.right);
            }
        }
        return false;
    }

    private void checkNull(Object o, String msg) {
        if (o == null) {
            throw new java.lang.IllegalArgumentException(msg);
        }
    }

    public static void main(String[] args) {

    }
}