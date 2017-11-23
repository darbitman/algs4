import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture p;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        p = picture;
    }
    
    // current picture
    public Picture pictre() {
        return p;
    }
    
    // width of current picture
    public int width() {
        return p.width();
    }
    
    // height of current picture
    public int height() {
        return p.height();
    }
    
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x > (width() - 1) || y > (height() - 1)) {
            throw new java.lang.IllegalArgumentException("x or y argument to energy() out of bounds");
        }
        
        // return 1000 for border pixels
        if (x == 0 || y == 0 || x == (width() - 1) || y == (height() - 1)) {
            return 1000.0;
        }
        else {
            Color aboveC, belowC, leftC, rightC;
            aboveC = p.get(x, y - 1);
            belowC = p.get(x, y + 1);
            leftC = p.get(x - 1, y);
            rightC = p.get(x + 1, y);
            
            double rX = (double)(rightC.getRed() - leftC.getRed());
            double gX = (double)(rightC.getGreen() - leftC.getGreen());
            double bX = (double)(rightC.getBlue() - leftC.getBlue());
            
            double rY = (double)(belowC.getRed() - aboveC.getRed());
            double gY = (double)(belowC.getGreen() - aboveC.getGreen());
            double bY = (double)(belowC.getBlue() - belowC.getBlue());
            
            double deltaXsquared = Math.pow(rX, 2.0) + Math.pow(gX, 2.0) + Math.pow(bX, 2.0);
            double deltaYsquared = Math.pow(rY, 2.0) + Math.pow(gY, 2.0) + Math.pow(bY, 2.0);
            
            return Math.sqrt(deltaXsquared + deltaYsquared);
        }
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
    
    public static void main(String[] args) {
    }
}
