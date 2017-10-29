import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int totalPoints;

    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        checkDuplicates(pointsCopy);
        
        this.totalPoints = points.length;
        
        Point[] sortedPoints = Arrays.copyOf(points, totalPoints);
        Arrays.sort(sortedPoints);
        
        ArrayList<LineSegment> collinearSegments = new ArrayList<LineSegment>();
        
        
        for (int i = 0; i < totalPoints - 3; i++) {
            for (int j = i + 1; j < totalPoints - 2; j++) {
                double slopeToQ = sortedPoints[i].slopeTo(sortedPoints[j]);
                for (int k = j + 1; k < totalPoints - 1; k++) {
                    double slopeToR = sortedPoints[i].slopeTo(sortedPoints[k]);
                    if (slopeToQ == slopeToR) {
                        for (int m = k + 1; m < totalPoints; m++) {
                            double slopeToS = sortedPoints[i].slopeTo(sortedPoints[m]);
                            if (slopeToS == slopeToR) {
                               collinearSegments.add(new LineSegment(sortedPoints[i], sortedPoints[m]));
                            }
                        }
                    }
                }
            }
        }
        segments = collinearSegments.toArray(new LineSegment[collinearSegments.size()]);
    }

    public int numberOfSegments() {
        return this.segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }
    
    private void checkNull(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("points is null");
        }
        for (Point p: points) {
            if (p == null) {
                throw new java.lang.IllegalArgumentException("some point is null");
            }
        }
    }
    
    private void checkDuplicates(Point[] points) {
        /* Sort points, to speed up duplicate checking process ( n^2 -> n*lg(n) )
         * After items are sorted, duplicates are neighbors, so check neighbors        
         */
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            Point a = points[i];
            Point b = points[i + 1];
            if (a.compareTo(b) == 0) {
                throw new java.lang.IllegalArgumentException("duplicate points");
            }
        }
    }
}