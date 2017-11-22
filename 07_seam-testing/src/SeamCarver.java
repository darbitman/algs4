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
        return null;
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
        
        // border pixel energy
        if (x == 0 || y == 0 || y == (height() - 1) || x == (width() - 1)) {
            return 1000.0;
        }


        // colors and energies above/below/left/right of the pixel at (x,y)
        Color aboveC, belowC, leftC, rightC;
        double aboveE, belowE, leftE, rightE;
        aboveC = p.get(x, y);
        
        
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