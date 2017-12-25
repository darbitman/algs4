import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

public class MoveToFront {
    private static final int MAX = 256;
    
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
        while (true) {
            readCharBinary = BinaryStdIn.readChar();
            if (readCharBinary == code[0]) {
                StdOut.println(readCharBinary);
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
    
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] code = new int[MAX];
        // initialize ordered sequency of 256 extended ASCII characters
        // i-th position = i-th extended ASCII
        for (int i = 0; i < MAX; i++) {
            code[i] = i;
        }
        
        int readIntBinary, first;
        while (true) {
            readIntBinary = BinaryStdIn.readByte();
            first = code[readIntBinary];
            BinaryStdOut.write(first, 8);
            for (int i = readIntBinary; i > 0 ; i--) {
                code[i] = code[i - 1];
            }
            code[0] = first;
        }
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
