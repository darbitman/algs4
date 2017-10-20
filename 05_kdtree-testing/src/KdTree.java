import java.util.LinkedList;
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
        private RectHV rect;

        public Node(Point2D p, int orientation, RectHV rect) {
            this.point = p;
            this.orientation = orientation;
            this.left = null;
            this.right = null;
            this.rect = rect;
        }
    }

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNull(p, "insert() has null argument");
        if (this.root == null) {
            this.root = new Node(p, this.X, new RectHV(0, 0, 1, 1));
        } else {
            Node parent = findParent(p);
            Point2D parentPoint = parent.point;
            RectHV parentRect = parent.rect;

            // add new node if point isn't equal
            if (!p.equals(parent.point)) {
                if (parent.orientation == this.X) {
                    if (p.x() <= parentPoint.x()) {
                        parent.left = new Node(p, this.Y, new RectHV(parentRect.xmin(), parentRect.ymin(), parentPoint.x(), parentRect.ymax()));
                    }
                    else {
                        parent.right = new Node(p, this.Y, new RectHV(parentPoint.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax()));
                    }
                    else {
                        if (p.y() <= parentPoint.y()) {
                            parent.left = new Node(p, this.X, new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), parentPoint.y()));
                        }
                        else {
                            parent.right = new Node(p, this.X, new RectHV(parentRect.xmin(), parentPoint.y(), parentRect.xmax(), parentRect.ymax()));
                        }
                    }
                }
                this.size++;
            }
        }
    }

    private Node findParent(Point2D p) {
        checkNull(p, "findParent() has null argument");
        Node currentNode = root;
        Node parent = currentNode;
        while (currentNode != null && !p.equals(currentNode.point)) {
            parent = currentNode;

            // left-right orientation
            if (currentNode.orientation == this.X) {
                if (p.x() <= currentNode.point.x()) {
                    currentNode = currentNode.left;
                } else {
                    currentNode = currentNode.right;
                }
            }

            // top-bottom orientation
            else {
                if (p.y() <= currentNode.point.y()) {
                    currentNode = currentNode.left;
                } else {
                    currentNode = currentNode.right;
                }
            }
        }

        // point not found, return parent
        if (currentNode == null) {
            return parent;
        }
        // point found
        else {
            return currentNode;
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
        } else if (p.x() == n.point.x() && p.y() == n.point.y()) {
            return true;
        } else if (n.orientation == this.X) {
            if (p.x() < n.point.x()) {
                return contains(p, n.left);
            } else if (p.x() > n.point.x()) {
                return contains(p, n.right);
            }
        } else if (n.orientation == this.Y) {
            if (p.y() < n.point.y()) {
                return contains(p, n.left);
            } else if (p.y() > n.point.y()) {
                return contains(p, n.right);
            }
        }
        return false;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect, "range() has null argument");
        LinkedList<Point2D> pointsInsideRect = new LinkedList<Point2D>();
        range(root, rect, pointsInsideRect);
        return pointsInsideRect;
    }

    private void range(Node n, RectHV rect, LinkedList<Point2D> list) {
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        return null;
    }

    private void checkNull(Object o, String msg) {
        if (o == null) {
            throw new java.lang.IllegalArgumentException(msg);
        }
    }

    public static void main(String[] args) {

    }
}