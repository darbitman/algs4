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
        if (this.height <= 1) {
            throw new java.lang.Error("No horizontal seams left to remove");
        }
        
        int[][] pixelTo = new int[this.width][this.height];  // stores location of previous pixel
        int[] minPath = new int[this.width];
        double[][] totalEnergyTo = new double[this.width][this.height];  // stores accumulated energies up to current pixel (including current pixel)
        
        // initialize first column
        for (int j = 0; j < this.height; j++) {
            totalEnergyTo[0][j] = energy(0, j);
            pixelTo[0][j] = -1;
        }
        
        // find the shortest path from one of the three parents
        for (int i = 1; i < this.width; i++) {
            // margin energies accumulate
            totalEnergyTo[i][0] = totalEnergyTo[i - 1][0] + energy(i, 0);
            totalEnergyTo[i][this.height - 1] = totalEnergyTo[i - 1][this.height - 1] + energy(i, this.height - 1);
            pixelTo[i][0] = 0;
            pixelTo[i][this.height - 1] = 0;
            
            // search for path
            for (int j = 1; j < this.height; j++) {
                // parent to the left/above
                if (totalEnergyTo[i - 1][j - 1] <= totalEnergyTo[i - 1][j] && 
                        totalEnergyTo[i - 1][j - 1] <= totalEnergyTo[i - 1][j + 1]) {
                    
                    totalEnergyTo[i][j] = totalEnergyTo[i - 1][j - 1] + energy(i, j);
                    pixelTo[i][j] = j - 1;
                }
                
                // parent directly to the left
                else if (totalEnergyTo[i - 1][j] <= totalEnergyTo[i - 1][j - 1] &&
                        totalEnergyTo[i - 1][j] <= totalEnergyTo[i - 1][j + 1]) {
                    
                    totalEnergyTo[i][j] = totalEnergyTo[i - 1][j] + energy(i, j);
                    pixelTo[i][j] = j;
                }
                
                // parent to the left/below
                else {
                    totalEnergyTo[i][j] = totalEnergyTo[i - 1][j] + energy(i, j);
                    pixelTo[i][j] = j + 1;
                }
            }
        }
        
        
        // Find minimum total energy endpoint
        double minTotalEnergy = totalEnergyTo[this.width - 1][0];
        int minFinal = 0;
        for (int j = 0; j < this.height; j++) {
            if (totalEnergyTo[this.width - 1][j] < minTotalEnergy) {
                minTotalEnergy = totalEnergyTo[this.width - 1][j];
                minFinal = j;
            }
        }
        
        // trace path backwards
        minPath[this.width - 1] = minFinal;
        for (int i = this.width - 2; i >= 0; i--) {
            minPath[i] = pixelTo[i][minPath[i + 1]];
        }
        return minPath;
    }
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (this.width <= 1) {
            throw new java.lang.Error("No vertical seams left");
        }
        
        int[][] pixelTo = new int[this.width][this.height];  // stores location of previous pixel
        double[][] totalEnergyTo = new double[this.width][this.height];  // stores accumulated energies up to current pixel (including current pixel)
        int[] minPath = new int[this.height];  // stores pixels that form the vertical seam
        
        // initialize first row
        for (int i = 0; i < this.width; i++) {
            totalEnergyTo[i][0] = energy(i, 0);
            pixelTo[i][0] = -1;
        }
        
        // find the shortest path from one of the three parents
        for (int j = 1; j < this.height; j++) {
            // margin energies accumulate
            totalEnergyTo[0][j] = totalEnergyTo[0][j - 1] + energy(0, j);
            totalEnergyTo[this.width - 1][j] = totalEnergyTo[this.width - 1][j - 1] + energy(this.width - 1, j);
            pixelTo[0][j] = 0;
            pixelTo[this.width - 1][j] = 0;
            
            // search for path
            for (int i = 0; i < this.width; i++) {
                // parent to the left/above
                if (totalEnergyTo[i - 1][j - 1] <= totalEnergyTo[i][j - 1] &&
                        totalEnergyTo[i - 1][j - 1] <= totalEnergyTo[i + 1][j - 1]) {
                    
                    totalEnergyTo[i][j] = totalEnergyTo[i - 1][j] + energy(i, j);
                    pixelTo[i][j] = i - 1;
                }
                
                // parent directly above
                if (totalEnergyTo[i][j - 1] <= totalEnergyTo[i - 1][j - 1] && 
                        totalEnergyTo[i][j - 1] <= totalEnergyTo[i + 1][j - 1]) {
                    
                    totalEnergyTo[i][j] = totalEnergyTo[i][j - 1] + energy(i, j);
                }
                
                // parent to the right/above
        
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