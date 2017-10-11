import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private TreeSet<Point2D> set;
    
    
    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return set.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNull(p, "insert() has null argument");
        set.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNull(p, "contains() has null argument");
        return set.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        for (Point2D q: this.set) {
            StdDraw.point(q.x(), q.y());
        }
    }
    
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect, "range() has null argument");
        LinkedList<Point2D> pointsInsideRect = new LinkedList<Point2D>();
        for (Point2D q: this.set) {
            if (rect.contains(q)) {
                pointsInsideRect.add(q);
            }
        }
        return pointsInsideRect;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNull(p, "nearest() has null argument");
        Point2D nearestPoint = null;
        for (Point2D q: this.set) {
            if (p.distanceTo(q) < p.distanceTo(nearestPoint) || nearestPoint == null) {
                nearestPoint = q;
            }
        }
        return nearestPoint;
    }
    
    private void checkNull(Object o, String msg) {
        if (o == null) {
            throw new java.lang.IllegalArgumentException(msg);
        }
    }
    
    public static void main(String[] args) {
        PointSET points = new PointSET();
        try {
            File inputFile = new File("C:\\Users\\Dmitriy\\eclipse-workspace\\05_kdtree-testing\\src\\input100K.txt");
            Scanner sc = new Scanner(inputFile);
            while(sc.hasNext()) {
                points.insert(new Point2D(Double.parseDouble(sc.next()), Double.parseDouble(sc.next())));
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
//        points.draw();
    }
}
