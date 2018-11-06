import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	// index of starting site
    private static final int TOP = 0;
    
    // marks opened locations
    private boolean[][] opened;
    
    // grid dimension
    private final int size;
    
    private final WeightedQuickUnionUF uf;
    
    // index of ending site
    private final int BOTTOM;
    
    // keep track of the number of sites that have been opened
    private int openedSites = 0;

    /**
     * Initializes an {@code n}-by-{@code n} grid with all sites blocked.
     * @param  n single-sided dimension of an n x n grid
     */
    public Percolation(int n) {
        size = n;
        BOTTOM = size*size + 1;
        // add extra 2 elements, one for starting site and one for ending site
        uf = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[size][size];
    }

    /**
     * Open a site at row {@code r} and column {@code c}
     * @param r
     * @param c
     */
    public void open(int r, int c) {
        if (r < 1 || r > size || c < 1 || c > size)  {
            throw new java.lang.IllegalArgumentException();
        }
        opened[r - 1][c - 1] = true;
        
        // if top row, then connect to starting site
        if (r == 1) {
            uf.union(getIndex(r, c), TOP);
        }
        
        // if BOTTOM row, then connect to ending site
        if (r == size) {
            uf.union(getIndex(r, c), BOTTOM);
        }
        
        // check left
        if (c > 1 && isOpen(r, c - 1)) {
            uf.union(getIndex(r, c), getIndex(r, c - 1));
        }
        
        // check right
        if (c < size && isOpen(r, c + 1)) {
            uf.union(getIndex(r, c), getIndex(r, c + 1));
        }
        
        // check below
        if (r < size && isOpen(r+1, c)) {
            uf.union(getIndex(r, c), getIndex(r + 1, c));
        }
        
        // check above
        if (r > 1 && isOpen(r - 1, c)) {
            uf.union(getIndex(r, c), getIndex(r - 1, c));
        }
        
        // increment number of opened sites
        openedSites++;
    }

    /**
     * Check if site at row {@code row} and column {@code col} has been opened
     * @param row
     * @param col
     * @return true if site has been opened at row {@code row} and column {@code col}
     */
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException();
        }
        return opened[row-1][col-1];
    }
    
    /**
     * Checks whether the site at row {@code row} and column {@code col} is connected to starting site
     * @param row
     * @param col
     * @return true if site at row {@code row} and column {@code col} is connected to starting site
     */
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException();
        }
        return uf.connected(TOP, getIndex(row, col));
    }

    /**
     * Does the system percolate?
     * @return true if the system percolates
     */
    public boolean percolates() {
        return uf.connected(TOP, BOTTOM);
    }
    
    /**
     * Creates a flat index from a 2D index
     * @param row
     * @param col
     * @return the equivalent flat index corresponding to row {@code row} and column {@code col}
     */
    private int getIndex(int row, int col) {
        return size*(row-1) + col;
    }
    
    /**
     * Gets the number of opened sites
     * @return the number of opened sites
     */
    public int numberOfOpenSites() {
        return openedSites;
    }
}