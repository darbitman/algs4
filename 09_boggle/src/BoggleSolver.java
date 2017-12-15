import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    private final TrieAlphabet dict;  //  dictionary of valid words
    private boolean[][] visited;  // mark tiles that are already part of given word
    private int[][] aux;  // delta coordinates for both i and j to correspond to relative moves
    private StringBuilder word;  // keep track of current word
    private SET<String> validWords;  // words found on boggle board given dictionary to return to client
    private TrieAlphabet validWordsTrie;  // faster to search this than SET
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        dict = new TrieAlphabet();
        for (String s: dictionary) {
            dict.put(s);
        }
        setupAuxArray();
    }
    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int bCols = board.cols();
        int bRows = board.rows();
        visited = new boolean[bRows][bCols];
        // initialize visited grid to "unvisited"
        for (int i = 0; i < bRows; i++) {
            for (int j = 0; j < bCols; j++) {
                visited[i][j] = false;
            }
        }
        
        validWords = new SET<String>();
        validWordsTrie = new TrieAlphabet();
        // start word search
        word = new StringBuilder();
        for (int i = 0; i < bRows; i++) {
            for (int j = 0; j < bCols; j++) {
                addLetter(board.getLetter(i, j), i, j);
                findWord(board, i, j);
                removeLetter(i, j);
            }
        }
        return validWords;
    }
    
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String s) {
        int wordLength = s.length();
        if (dict.contains(s)) {
            if (wordLength >= 8) {
                return 11;
            }
            if (wordLength == 7) {
                return 5;
            }
            if (wordLength == 6) {
                return 3;
            }
            if (wordLength == 5) {
                return 2;
            }
            if (wordLength >= 3) {
                return 1;
            }
        }
        return 0;
    }
    
    private void findWord(BoggleBoard board, int i, int j) {
        // loop through all 8 moves you can make from any square
        for (int n = 0; n < 8; n++) {
            int newI = i + aux[n][0];
            int newJ = j + aux[n][1];
            
            // if move is valid and if it hasn't been visited before
            if (newI >= 0 && newI < board.rows() && newJ >= 0 && newJ < board.cols() && !visited[newI][newJ]) {
                addLetter(board.getLetter(newI, newJ), newI, newJ);
                
                // found valid word in dictionary
                if (word.length() > 2 && dict.contains(word.toString()) && !validWordsTrie.contains(word.toString())) {
                    validWords.add(word.toString());
                    validWordsTrie.put(word.toString());
                }
                if (dict.hasPrefix(word)) {
                    findWord(board, newI, newJ);  // recursive call
                }
                removeLetter(newI, newJ);
            }
        }
    }
    
    private void addLetter(char c, int i, int j) {
        if (c == 'Q') {
            word.append("QU");
        }
        else {
            word.append(c);
        }
        visited[i][j] = true;
    }
    
    private void removeLetter(int i, int j) {
        if (word.length() > 1 && word.charAt(word.length() - 2) == 'Q') {
            word.delete(word.length() - 2, word.length());
        }
        else {
            word.delete(word.length() - 1, word.length());
        }
        visited[i][j] = false;
    }
    
    /*
     * 0 1 2
     * 3   4
     * 5 6 7
     * These are the valid moves
     * Create 8x2 array to correspond the moves you can make (-1, 0, +1)
     * for both, i and j, relative to current position
     */
    private void setupAuxArray() {
        aux = new int[8][2];
        aux[0][0] = -1;
        aux[1][0] = 0;
        aux[2][0] = 1;
        aux[3][0] = -1;
        aux[4][0] = 1;
        aux[5][0] = -1;
        aux[6][0] = 0;
        aux[7][0] = 1;
        aux[0][1] = -1;
        aux[1][1] = -1;
        aux[2][1] = -1;
        aux[3][1] = 0;
        aux[4][1] = 0;
        aux[5][1] = 1;
        aux[6][1] = 1;
        aux[7][1] = 1;
    }
    
    public static void main(String[] args) {
        String filename = args[0];
        StdOut.println("4-by-4 board from file " + filename + ":");
        BoggleBoard board4 = new BoggleBoard(filename);
        StdOut.println(board4);
        StdOut.println();
        
        In inDict = new In(args[1]);
        String[] dict;
        Bag<String> bagDict = new Bag<String>();

        while (inDict.hasNextLine())
            bagDict.add(inDict.readLine());
        dict = new String[bagDict.size()];
        int i = 0;
        for (String entry : bagDict) {
            dict[i++] = entry;
        }

        BoggleSolver boggleSolver = new BoggleSolver(dict);
        boggleSolver.getAllValidWords(board4);
    }

}