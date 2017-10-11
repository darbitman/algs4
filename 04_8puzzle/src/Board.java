import java.util.LinkedList;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] blocks;
    private final int n;
    private int hammingDistance = -1;
    private int manhattanDistance = -1;
    
    // location of empty block
    private int emptyi;
    private int emptyj;
    
    
    /* construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] b) {
        n = b.length;
        blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = b[i][j];

                // update location of empty block
                if (blocks[i][j] == 0) {
                    emptyi = i;
                    emptyj = j;
                }
            }
        }
    }
	

	private Board(int[][] b, int i1, int j1, int i2, int j2, int hammingDistance, int manhattanDistance) {
	    n = b.length;
	    
	    // copy old board blocks to new board
	    blocks = new int[n][n];
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            blocks[i][j] = b[i][j];
	            if (blocks[i][j] == 0) {
	                emptyi = i;
	                emptyj = j;
	            }
	        }
	    }
	    int swap = blocks[i1][j1];
	    blocks[i1][j1] = blocks[i2][j2];
	    blocks[i2][j2] = swap;
	    this.hammingDistance = hammingDistance;
	    this.manhattanDistance = manhattanDistance;
	}
	    

	// board dimension n
	public int dimension() {
	    return n;
	}
	
	// number of blocks out of place
	public int hamming() {
	    if (hammingDistance == -1) {
	        int distance = 0;
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                int block = blocks[i][j];
	                if (block != 0) {  // blank space doesn't count toward error
	                    int goalBlock = (i * n) + j + 1;  // convert row and col to goal block number
	                    if (block != goalBlock) {
	                        distance++;
	                    }
	                }
	            }
	        }
	        hammingDistance = distance;
	    }
	    return hammingDistance;
	}
	
	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
	    if (manhattanDistance == -1) {
	        int distance = 0;
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                int block = blocks[i][j];
	                if (block != 0) {
	                    distance += singleManhattan(block, i, j);
	                }
	            }
	        }
	        manhattanDistance = distance;
	    }
	    return manhattanDistance;
	}
	
	private int singleManhattan(int block, int i, int j) {
	    int row = (block - 1) / n;
        int col = (block - 1) % n;
        return Math.abs(row - i) + Math.abs(col - j);
	}
	    
	
	// is this board the goal board?
	public boolean isGoal() {
	    return hamming() == 0;  // if all blocks in their correct position, then goal reached
	}
	
	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
	    int r1 = 0;
	    int r2 = 0;
	    int c1 = 0;
	    int c2 = 0;
	    while (((r1 == r2) && (c1 == c2)) || blocks[r1][c1] == 0 || blocks[r2][c2] == 0) {
	        r1 = StdRandom.uniform(n);
	        r2 = StdRandom.uniform(n);
	        c1 = StdRandom.uniform(n);
	        c2 = StdRandom.uniform(n);
	    }
	    return new Board(blocks, r1, c1, r2, c2, -1, -1);
	}
	
	// does this board equal y?
	public boolean equals(Object y) {
	    if (this == y) {
	        return true;
	    }
	    if (y == null) {
	        return false;
	    }
	    if (this.getClass() != y.getClass()) {
	        return false;
	    }
	    
	    Board other = (Board) y;
	    if (other.dimension() != n) {
	        return false;
	    }
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            if (this.blocks[i][j] != other.blocks[i][j]) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors() {
	    LinkedList<Board> boardsList = new LinkedList<Board>();
	    
	    // move empty block to left
	    if (emptyj > 0) {
	        boardsList.add(swapEmpty(emptyi, emptyj - 1));
	    }
	    
	    // move empty block to the right
	    if (emptyj < this.n - 1) {
	        boardsList.add(swapEmpty(emptyi, emptyj + 1));
	    }
	    
	    // move empty block up
	    if (emptyi > 0) {
	        boardsList.add(swapEmpty(emptyi - 1, emptyj));
	    }
	    
	    // move empty block down
	    if (emptyi < this.n - 1) {
	        boardsList.add(swapEmpty(emptyi + 1, emptyj));
	    }
	    return boardsList;
	}
	
	private Board swapEmpty(int i, int j) {
	    int singleManhattanBeforeSwap = singleManhattan(blocks[i][j], i, j);
	    int singleManhattanAfterSwap = singleManhattan(blocks[i][j], emptyi, emptyj);
	    int manhattanDiff = singleManhattanAfterSwap - singleManhattanBeforeSwap;     
	    return new Board(blocks, i, j, emptyi, emptyj, -1, manhattan() + manhattanDiff);
	}
	    
	
	// string representation of this board
	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append(n).append('\n');
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            builder.append(String.format("%2d ", blocks[i][j]));
	        }
	        builder.append('\n');
	    }
	    return builder.toString();
	}
	
	// unit tests
	public static void main(String[] args) {
//	    int[][] x = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
//	    Board b = new Board(x);
//	    System.out.println(b.hamming());
//	    System.out.println(b.toString());
//	    //int[][] x = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
//	    int[][] x = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
//	    Board b = new Board(x);
//	    System.out.println(b.hamming());
//	    System.out.println(b.manhattan());
//	    System.out.println(b.isGoal());
//	    System.out.println(b.toString());
//	    //Board c = b.swapEmpty(1, 2);
//	    //System.out.println(c.toString());
//	    Board d = b.twin();
//	    System.out.println(d.toString());
	}
}
