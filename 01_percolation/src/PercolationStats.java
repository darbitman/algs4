import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] percolationFraction;
    private final int experimentCount;
    public PercolationStats(int n, int t)    // perform trials independent experiments on an n-by-n grid
    {
        percolationFraction = new double[t];
        experimentCount = t;
        for (int i = 0; i < t; i++)
        {
            Percolation per = new Percolation(n);
            while (!per.percolates())
            {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!per.isOpen(row, col))
                {
                    per.open(row, col);
                }
            }
            percolationFraction[i] = (double) per.numberOfOpenSites() / (n * n);
        }
    }
   
    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(percolationFraction);
    }
    
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(percolationFraction);
    }
    
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean() - (1.96 * stddev() / Math.sqrt(experimentCount));
    }
    
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean() + (1.96 * stddev() / Math.sqrt(experimentCount));
    }

    public static void main(String[] args)        // test client (described below)
    {
        int n = 250;
        int t = 1000;
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("mean        = " + ps.mean());
        System.out.println("stddev      = " + ps.stddev());
    }
}