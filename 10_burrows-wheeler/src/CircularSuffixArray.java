import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private final int length;
    private final String catString;
    private CircString[] circStringArray;
    
    private class CircString {
        private int begin;
        private int index;
    }
    
    /**
     * Construct a new circular suffix
     * @param s string to construct circular suffix array from
     */
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
    
    /**
     * Perform LSD radix sort
     */
    private void sort() {
        int radix = 256;  // Radix R
        CircString[] auxCircStringArray = new CircString[length];
        // run sort starting at the end of the string
        for (int d = length - 1; d >= 0; d--) {  // LSD radix sort
            int[] count = new int[radix + 1];
            
            // update counts for all entries at position d in string array
            for (int i = 0; i < length; i++) {
                count[catString.substring(circStringArray[i].begin, circStringArray[i].begin + length).charAt(d) + 1]++;
            }
            
            // update cumulates
            for (int r = 0; r < radix; r++) {
                count[r + 1] += count[r];
            }
            
            // move items into aux array
            for (int i = 0; i < length; i++) {
                auxCircStringArray[count[catString.substring(circStringArray[i].begin, circStringArray[i].begin + length).charAt(d)]++] = circStringArray[i];
            }
            
            // copy from aux array back into original array
            for (int i = 0; i < length; i++) {
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
        CircularSuffixArray testCSA = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < testCSA.length(); i++) {
            StdOut.println(testCSA.index(i));
        }
    }
}
