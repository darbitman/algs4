import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;

public class BurrowsWheeler {
    private 
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
        int first = BinaryStdIn.readInt();
        StringBuilder sb = new StringBuilder();  // t[]
        char readBinaryChar;
        while (!BinaryStdIn.isEmpty()) {
            readBinaryChar = BinaryStdIn.readChar();
            sb = sb.append(readBinaryChar);
        }
        int length = sb.length();               // length of t[]
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
