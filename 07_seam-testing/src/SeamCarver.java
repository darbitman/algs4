import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture p;
    private static final double MARGIN_ENERGY = 1000.0;
    private int height, width;
    private Picture currentPicture;  // Current picture
    private int[][] pixels;  // store RGB for each pixel
    
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        p = new Picture(picture);
        this.height = this.p.height();
        this.width = this.p.width();
        this.pixels = new int[this.width][this.height];
        
        
        // populate pixel data
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                pixels[i][j] = this.p.getRGB(i, j);
            }
        }
                
    }
    
    // return current picture
    public Picture picture() {
        Color c;
        currentPicture = new Picture(this.width, this.height);
        
        // generate new picture using current pixel data
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                c = new Color(pixels[i][j]);
                currentPicture.set(i, j, c);
            }
        }
        return currentPicture;
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
            
            double deltaSquareY = Math.pow(belowC.getRed() - aboveC.getRed(), 2)
                    + Math.pow(belowC.getGreen() - aboveC.getGreen(), 2)
                    + Math.pow(belowC.getGreen() - aboveC.getGreen(), 2);
            
            return Math.sqrt(deltaSquareY + deltaSquareX);
        }
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[][] path = new int[this.width][this.height];  // contains flags for shortest path
        int[] minPath = new int[this.width];  // contains the array indices of the shortest path
        int[][] totalEnergy = new int[2][this.height];  // keep only the current column and next column data in alternating fashion
        double minTotalEnergy;

        
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