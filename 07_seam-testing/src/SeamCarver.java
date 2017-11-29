import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture p;
    private static final double MARGIN_ENERGY = 1000.0;
    private int height, width;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        p = new Picture(picture);
        this.height = this.p.height();
        this.width = this.p.width();
    }
    
    // current picture
    public Picture picture() {
        return new Picture(this.p);
    }
    
    // width of current picture
    public int width() {
        return this.width;
    }
    
    // height of current picture
    public int height() {
        return this.height;
    }
    
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x > (width() - 1) || y > (height() - 1)) {
            throw new java.lang.IllegalArgumentException("x or y argument to energy() out of bounds");
        }
        
        // return 1000 for border pixels
        if (x == 0 || y == 0 || x == (width() - 1) || y == (height() - 1)) {
            return MARGIN_ENERGY;
        }
        else {
            Color aboveC, belowC, leftC, rightC;
            aboveC = p.get(x, y - 1);
            belowC = p.get(x, y + 1);
            leftC = p.get(x - 1, y);
            rightC = p.get(x + 1, y);
            double deltaSquareX = Math.pow(rightC.getRed() - leftC.getRed(), 2)
                    + Math.pow(rightC.getGreen() - leftC.getGreen(), 2)
                    + Math.pow(rightC.getBlue() - leftC.getBlue(), 2);
            
            double deltaSquareY = Math.pow(belowC, b)
        }
        
        return 0.0;
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }
    
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
    }
    
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
    }
}