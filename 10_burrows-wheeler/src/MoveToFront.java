import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int MAX = 256;
    private static final boolean DEBUG = false;
    
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] code = new int[MAX];
        // initialize ordered sequency of 256 extended ASCII characters
        // i-th position = i-th extended ASCII
        for (int i = 0; i < MAX; i++) {
            code[i] = i;
        }
        
        char readCharBinary;
        int swapTemp, swapNext;
        if (DEBUG) {
            String testInput = "ABRACADABRA!";
            int length = testInput.length();
            for (int j = 0; j < length; j++) {
                readCharBinary = testInput.charAt(j);
                if (readCharBinary == code[0]) {
                    BinaryStdOut.write(0, 8);
                }
                else {
                    swapTemp = code[0];
                    for (int i = 1; i < MAX; i++) {
                        if (readCharBinary == code[i]) {
                            code[0] = code[i];
                            code[i] = swapTemp;
                            BinaryStdOut.write(i, 8);
                            break;
                        }
                        else {
                            swapNext = code[i];
                            code[i] = swapTemp;
                            swapTemp = swapNext;
                        }
                    }
                }
            }
        }
        else {
            while (!BinaryStdIn.isEmpty()) {
                readCharBinary = BinaryStdIn.readChar();
                if (readCharBinary == code[0]) {
                    BinaryStdOut.write(0, 8);
                }
                else {
                    swapTemp = code[0];
                    for (int i = 1; i < MAX; i++) {
                        if (readCharBinary == code[i]) {
                            code[0] = code[i];
                            code[i] = swapTemp;
                            BinaryStdOut.write(i, 8);
                            break;
                        }
                        else {
                            swapNext = code[i];
                            code[i] = swapTemp;
                            swapTemp = swapNext;
                        }
                    }
                }
            }
        }
        BinaryStdOut.flush();
    }
    
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] code = new int[MAX];
        // initialize ordered sequency of 256 extended ASCII characters
        // i-th position = i-th extended ASCII
        for (int i = 0; i < MAX; i++) {
            code[i] = i;
        }
        
        int readIntBinary, first;
        while (!BinaryStdIn.isEmpty()) {
            readIntBinary = BinaryStdIn.readByte();
            first = code[readIntBinary];
            BinaryStdOut.write(first, 8);
            for (int i = readIntBinary; i > 0; i--) {
                code[i] = code[i - 1];
            }
            code[0] = first;
        }
        BinaryStdOut.flush();
    }
    
    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            MoveToFront.encode();
        }
        else {
            MoveToFront.decode();
        }
    }
}
