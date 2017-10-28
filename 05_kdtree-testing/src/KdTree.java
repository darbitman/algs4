// import java.io.File;
// import java.io.FileNotFoundException;
// import java.util.Scanner;

import java.util.LinkedList;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final int X_DIR = 1;
    private static final int Y_DIR = 2;
    private Node root;
    private int size;
    private Point2D closest;

    private class Node {
        private final Point2D point;
        private final int orientation;
        private final RectHV rect;
        private Node left;
        private Node right;

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
            this.root = new Node(p, KdTree.X_DIR, new RectHV(0, 0, 1, 1));
            this.size++;
        } else {
            Node parent = findParent(p);
            Point2D parentPoint = parent.point;
            RectHV parentRect = parent.rect;

            // add new node if point isn't equal
            if (!p.equals(parent.point)) {
                if (parent.orientation == KdTree.X_DIR) {
                    if (p.x() <= parentPoint.x()) {
                        parent.left = new Node(p, KdTree.Y_DIR,
                                new RectHV(parentRect.xmin(), parentRect.ymin(), parentPoint.x(), parentRect.ymax()));
                    } else {
                        parent.right = new Node(p, KdTree.Y_DIR,
                                new RectHV(parentPoint.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax()));
                    }
                } else {
                    if (p.y() <= parentPoint.y()) {
                        parent.left = new Node(p, KdTree.X_DIR,
                                new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), parentPoint.y()));
                    } else {
                        parent.right = new Node(p, KdTree.X_DIR,
                                new RectHV(parentRect.xmin(), parentPoint.y(), parentRect.xmax(), parentRect.ymax()));
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
            if (currentNode.orientation == KdTree.X_DIR) {
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
        if (isEmpty()) {
            return false;
        }
        // findParent returns the node that contains the point IF it contains the point
        Node parent = findParent(p);
        return p.equals(parent.point);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect, "range() has null argument");
        LinkedList<Point2D> pointsInsideRect = new LinkedList<Point2D>();
        range(rect, root, pointsInsideRect);
        return pointsInsideRect;
    }

    private void range(RectHV rect, Node n, LinkedList<Point2D> list) {
        if (n == null) {
            return;
        }
        if (rect.contains(n.point)) {
            list.add(n.point);
        }
        if (n.left != null && rect.intersects(n.left.rect)) {
            range(rect, n.left, list);
        }
        if (n.right != null && rect.intersects(n.right.rect)) {
            range(rect, n.right, list);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNull(p, "nearest() has null argument");
        if (isEmpty()) {
            return null;
        } else {
            Point2D closest = root.point;
            nearest(root, p);
            return closest;
        }
    }

    private void nearest(Node n, Point2D p) {
        double currentDistance = closest.distanceSquaredTo(p);

        // if inside node's rectangle or if rectangle is close to point
        if (n != null && n.rect.distanceSquaredTo(p) < currentDistance) {
            if (p.distanceSquaredTo(n.point) < currentDistance) {
                closest = n.point;
            }
            if (n.orientation == KdTree.X_DIR) {
                if (p.x() <= n.point.x()) {
                    nearest(n.left, p);
                    nearest(n.right, p);
                }
                else {
                    nearest(n.right, p);
                    nearest(n.left, p);
                }
            }
            else {
                if (p.y() <= n.point.y()) {
                    nearest(n.left, p);
                    nearest(n.right, p);
                }
                else {
                    nearest(n.right, p);
                    nearest(n.left, p);
                }
            }
        }
    }

    private void checkNull(Object o, String msg) {
        if (o == null) {
            throw new java.lang.IllegalArgumentException(msg);
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return this.size;
    }

    public void draw() {
        if (isEmpty()) {
            return;
        } else {
            draw(this.root);
        }
    }

    private void draw(Node n) {
        if (n != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(n.point.x(), n.point.y());
            if (n.orientation == KdTree.X_DIR) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.point.x(), n.rect.ymin(), n.point.x(), n.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.point.y());
            }
            draw(root.left);
            draw(root.right);
        }
    }

    public static void main(String[] args) {
        // PointSET points = new PointSET();
        // try {
        // File inputFile = new
        // File("C:\\Users\\Dmitriy\\eclipse-workspace\\algs4\\05_kdtree-testing\\src\\input100K.txt");
        // Scanner sc = new Scanner(inputFile);
        // while (sc.hasNext()) {
        // points.insert(new Point2D(Double.parseDouble(sc.next()),
        // Double.parseDouble(sc.next())));
        // }
        // sc.close();
        // } catch (FileNotFoundException e) {
        // System.out.println("File not found");
        // }
        // points.draw();
    }
}