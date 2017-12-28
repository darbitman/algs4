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
        StringBuilder sb = new StringBuilder();  // t[]
        char readBinaryChar;
        int counter = 0;
        int length;  // length of t[]


        if (DEBUG == false) {
            BinaryStdIn.readInt();
            while (!BinaryStdIn.isEmpty()) {
                readBinaryChar = BinaryStdIn.readChar();
                sb = sb.append(readBinaryChar);
                count[readBinaryChar + 1]++;  // update counts
                fifoIndices[readBinaryChar].enqueue(counter++);
            }
            length = sb.length();
        }
        else {
            sb = sb.append("ARD!RCAAAABB");
            length = sb.length();
            for (int i = 0; i < length; i++) {
                count[sb.charAt(i) + 1]++;   // update counts
                fifoIndices[sb.charAt(i)].enqueue(i);
            }
        }


        for (int r = 0; r < R; r++) {  // update cumulates
            count[r + 1] += count[r];
        }
        char[] tAux = new char[length];
        for (int i = 0; i < length; i++) {  // sorted first column
            tAux[count[sb.charAt(i)]++] = sb.charAt(i);
        }
        
        // store corresponding index for relative order
        // i < j --> next[i] < next[j]
        int[] next = new int[length];
        for (int i = 0; i < length; i++) {
            next[i] = fifoIndices[tAux[i]].dequeue();
        }

        StringBuilder sbDecoded = new StringBuilder();
        int nextIndex = 0;
        char currentChar;
        for (int i = 0; i < length; i++) {
            nextIndex = next[nextIndex];
            currentChar = tAux[nextIndex];
            sbDecoded = sbDecoded.append(currentChar);
        }
        BinaryStdOut.write(sbDecoded.toString());
        BinaryStdOut.flush();
    }
    
    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        BurrowsWheeler.inverseTransform();
//        if (args[0].equals("-")) {
//            BurrowsWheeler.transform();
//        }
//        else {
//            BurrowsWheeler.inverseTransform();
//        }
    }
}
