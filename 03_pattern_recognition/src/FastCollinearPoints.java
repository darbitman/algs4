import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int numberOfSegments;
    
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        int n = points.length;
        
        Point[] pointsCopy = new Point[n];
        System.arraycopy(points, 0, pointsCopy, 0, n);
        checkDuplicates(pointsCopy); // pointsCopy is now sorted

        numberOfSegments = 0;
        segments = new LineSegment[n];

        for (int i = 0; i < n; i++) {
            /* Copy sorted points into new array, excluding the "origin" point
             * Will determine slope from the "origin" point to each of the points
             * Finally, will sort based on slope to "origin"
             */
            Point origin = pointsCopy[i];    // "origin" point
            Point[] pointsToCompare = new Point[n];
            System.arraycopy(pointsCopy, 0, pointsToCompare, 0, i);
            System.arraycopy(pointsCopy, i + 1, pointsToCompare, i, n - i - 1);
            Arrays.sort(pointsToCompare, 0, n - 1, origin.slopeOrder());
            
            int currentStartIndex = 0;  // beginning of current segment (excluding origin point)
            for (int j = 1; j < n; j++) {
                Point currentStartPoint = pointsToCompare[currentStartIndex];
                Point currentPoint = pointsToCompare[j];
                
                /* if reached the end of the array, or if the beginning point and end point don't have the same slope
                 * If they don't have the same slope, then 2nd to last point, i.e., currentPoint is the beginning of a new slope
                 * Can do this because array sorted
                 */
                if (currentPoint == null || (currentStartPoint.slopeTo(origin) != currentPoint.slopeTo(origin)) ) {
                    int segmentLength = j - currentStartIndex + 1;
                    // check to make sure that the origin is indeed the starting point of the segment
                    if (segmentLength >= 4 && currentStartPoint.compareTo(origin) > 0) {
                        segments[numberOfSegments++] = new LineSegment(origin, pointsToCompare[j - 1]);
                        
                        // if array full, double array size
                        if (segments.length == numberOfSegments) {
                            resize(numberOfSegments * 2);
                        }
                    }
                    // set new starting point to current point that failed the collinear test
                    currentStartIndex = j;
                }
            }
        }
    }
    
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    public LineSegment[] segments() {
        LineSegment[] segmentsToReturn = new LineSegment[numberOfSegments];
        System.arraycopy(segments, 0, segmentsToReturn, 0, numberOfSegments);
        return segmentsToReturn;
    }
    
    private void resize(int newSize) {
        LineSegment[] copy = new LineSegment[newSize];
        System.arraycopy(segments, 0, copy, 0, newSize / 2);
        segments = copy;
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
        /* Sort points, to speed up duplicate checking process ( n^2 -> n)
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