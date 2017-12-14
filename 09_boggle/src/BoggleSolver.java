public class BoggleSolver {
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
    }
    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        return null;
    }
    
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int wordLength = word.length();
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
        return 0;
    }
    
    public static void main(String[] args) {
        // test code here
    }

}