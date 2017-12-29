import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.BinaryStdIn;

public class BurrowsWheeler {
    private static final int R = 256;  // alphabet size
    private static final boolean DEBUG = true;
    
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        
        CircularSuffixArray csa = new CircularSuffixArray(s);
        
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
            }
        }
        
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(s.charAt(csa.length() + csa.index(i) - 1));
            }
            else {
                BinaryStdOut.write(s.charAt(csa.index(i) - 1));
            }
        }
        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int[] count = new int[R + 1];  // used for count/cumulates for decoding
        Queue<Integer>[] fifoIndices = new Queue[R];
        for (int r = 0; r < R; r++) {
            fifoIndices[r] = new Queue<Integer>();
        }
        String binaryInputString;
        char binaryInputChar;
        int counter = 0;
        int length;  // length of input

        // Get input binary stream
        if (DEBUG) {
            binaryInputString = "ARD!RCAAAABB";
            length = binaryInputString.length();
            for (int i = 0; i < length; i++) {
                count[binaryInputString.charAt(i) + 1]++;   // update counts
                fifoIndices[binaryInputString.charAt(i)].enqueue(i);
            }
        }
        else {
            binaryInputString = new String();
            BinaryStdIn.readInt();
            while (!BinaryStdIn.isEmpty()) {
                binaryInputChar = BinaryStdIn.readChar();
                binaryInputString += binaryInputChar;
                count[binaryInputChar + 1]++;  // update counts
                fifoIndices[binaryInputChar].enqueue(counter++);  // array of FIFOs of indices of binaryInputChar in input binary stream
            }
            length = binaryInputString.length();
        }


        for (int r = 0; r < R; r++) {  // update cumulates
            count[r + 1] += count[r];
        }
        char[] tAux = new char[length];  // sorted first column
        for (int i = 0; i < length; i++) {
            tAux[count[binaryInputString.charAt(i)]++] = binaryInputString.charAt(i);
        }
        
        // store corresponding index for relative order
        // i < j --> next[i] < next[j]
        int[] next = new int[length];
        for (int i = 0; i < length; i++) {
            next[i] = fifoIndices[tAux[i]].dequeue();
        }

        int nextIndex = 0;
        for (int i = 0; i < length; i++) {
            nextIndex = next[nextIndex];
            BinaryStdOut.write(tAux[nextIndex]);
        }
        BinaryStdOut.flush();
    }
    
    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            BurrowsWheeler.transform();
        }
        else {
            BurrowsWheeler.inverseTransform();
        }
    }
}
