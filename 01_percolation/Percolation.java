import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private boolean[][] opened;
    private final int size;
    private final WeightedQuickUnionUF uf;
    private final int bottom;
    private int openedSites = 0;

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        size = n;
        bottom = size*size + 1;
        uf = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[size][size];
    }

    public void open(int r, int c)
    {
        if (r < 1 || r > size || c < 1 || c > size)
        {
            throw new java.lang.IllegalArgumentException();
        }
        opened[r-1][c-1] = true;
        if (r == 1)  // if TOP row, then TOP
        {
            uf.union(getIndex(r, c), TOP);
        }
        if (r == size)  // if bottom row, then bottom
        {
            uf.union(getIndex(r, c), bottom);
        }
        if (c > 1 && isOpen(r, c-1))  // check left
        {
            uf.union(getIndex(r, c), getIndex(r, c-1));
        }
        if (c < size && isOpen(r, c+1))  // check right
        {
            uf.union(getIndex(r, c), getIndex(r, c+1));
        }
        if (r < size && isOpen(r+1, c))  // check below
        {
            uf.union(getIndex(r, c), getIndex(r+1, c));
        }
        if (r > 1 && isOpen(r-1, c))  // check above
        {
            uf.union(getIndex(r, c), getIndex(r-1, c));
        }
        openedSites++;
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row < 1 || row > size || col < 1 || col > size)
        {
            throw new java.lang.IllegalArgumentException();
        }
        return opened[row-1][col-1];
    }
    
    public boolean isFull(int row, int col)
    {
        if (row < 1 || row > size || col < 1 || col > size)
        {
            throw new java.lang.IllegalArgumentException();
        }
        return uf.connected(TOP, getIndex(row, col));
    }

    public boolean percolates()             // does the system percolate?
    {
        return uf.connected(TOP, bottom);
    }
    
    private int getIndex(int row, int col)
    {
        return size*(row-1) + col;
    }
    
    public int numberOfOpenSites()
    {
        return openedSites;
    }
}