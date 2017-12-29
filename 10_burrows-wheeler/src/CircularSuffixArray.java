import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private final int length;
    private final String catString;
    private CircString[] circStringArray;
    
    private class CircString {
        private int begin;
        private int index;
    }
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new java.lang.IllegalArgumentException("argument to constructor is null");
        }
        length = s.length();
        catString = s + s.substring(0, length - 1);
        circStringArray = new CircString[length];
        for (int i = 0; i < length; i++) {
            circStringArray[i] = new CircString();
            circStringArray[i].begin = i;
            circStringArray[i].index = i;
        }
        
        sort();
    }
    
    private void sort() {
        int R = 256;
        CircString[] auxCircStringArray = new CircString[length];
        for (int d = length - 1; d >= 0; d--) {  // LSD radix sort
            int[] count = new int[R + 1];
            for (int i = 0; i < length; i++) {  // update counts for all entries at position d in string array
                count[catString.substring(circStringArray[i].begin, circStringArray[i].begin + length).charAt(d) + 1]++;
            }
            for (int r = 0; r < R; r++) {  // update cumulates
                count[r + 1] += count[r];
            }
            for (int i = 0; i < length; i++) {  // move items into aux array
                auxCircStringArray[count[catString.substring(circStringArray[i].begin, circStringArray[i].begin + length).charAt(d)]++] = circStringArray[i];
            }
            for (int i = 0; i < length; i++) {  // copy from aux array into original array
                circStringArray[i] = auxCircStringArray[i];
            }
        }
    }
    
    // length of s
    public int length() {
        return this.length;
    }
    
    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length) {
            throw new java.lang.IllegalArgumentException("index() argument i is out of bounds");
        }
        return circStringArray[i].index;
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray testCSA = new CircularSuffixArray("CADABRA!ABRA");
        for (int i = 0; i < testCSA.length(); i++) {
            StdOut.println(testCSA.index(i));
        }
    }
}
