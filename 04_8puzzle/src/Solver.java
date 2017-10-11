import java.util.LinkedList;
import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private int numMoves;
    private boolean isSolvable;
    private LinkedList<Board> solution;
    
    private class SearchNode {
        private SearchNode prevSearchNode;
        private Board board;
        private int moves;
        
       public SearchNode(Board b) {
           this(b, null, 0);
       }
       
       private SearchNode(Board b, SearchNode p, int m) {
           this.board = b;
           this.prevSearchNode = p;
           this.moves = m;
       }
       
       public SearchNode(Board b, SearchNode p) {
           this(b, p, p.moves + 1);
       }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException("null initial board");
        }
        
        Comparator<SearchNode> comparator = new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode node1, SearchNode node2) {
                // find total distance from node (manhattan() + moves to get to this board)
                int distance1 = node1.moves + node1.board.manhattan();
                int distance2 = node2.moves + node2.board.manhattan();
                if (distance1 == distance2) {
                    return node1.board.manhattan() - node2.board.manhattan();
                }
                return distance1 - distance2;
            }
        };
        
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>(comparator);
        MinPQ<SearchNode> minPQAlt = new MinPQ<SearchNode>(comparator);
        
        SearchNode searchNode = new SearchNode(initial);
        SearchNode searchNodeAlt = new SearchNode(initial.twin());
        
        while (!searchNode.board.isGoal() && !searchNodeAlt.board.isGoal()) {
            for (Board b: searchNode.board.neighbors()) {
                // insert new neighbor board if initial board or if previous board is a new neighbor
                if (searchNode.prevSearchNode == null || !b.equals(searchNode.prevSearchNode.board)) {
                    minPQ.insert(new SearchNode(b, searchNode));
                }
            }
            searchNode = minPQ.delMin();  // delete minimum-distance node and update current search node to compute the next move
            
            for (Board b: searchNodeAlt.board.neighbors()) {
                // insert new neighbor board if initial board or if previous board is a new neighbor
                if (searchNodeAlt.prevSearchNode == null || !b.equals(searchNodeAlt.prevSearchNode.board)) {
                    minPQAlt.insert(new SearchNode(b, searchNodeAlt));
                }
            }
            searchNodeAlt = minPQAlt.delMin();  // delete minimum-distance node and update current search node to compute the next move
        }
        
        if (searchNode.board.isGoal()) {
            isSolvable = true;
            numMoves = searchNode.moves;
            solution = new LinkedList<Board>();
            SearchNode n = searchNode;
            while (n != null) {
                solution.addFirst(n.board);
                n = n.prevSearchNode;
            }
        }
        else {
            isSolvable = false;
            numMoves = -1;
            solution = null;
        }
    }
    
    // is the initial board solveable?
    public boolean isSolvable() {
        return isSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return numMoves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }
    
    // solve a slider puzzle
    public static void main(String[] args) {
//        //Solver s = new Solver(null);
//        int[][] x = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
//        Board b = new Board(x);
//        Solver s = new Solver(b);
    }
}
