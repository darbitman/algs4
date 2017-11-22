import edu.princeton.cs.algs4.Picture;

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