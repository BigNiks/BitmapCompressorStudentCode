/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Niko Madriz
 */
public class BitmapCompressor {

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {

        // TODO: complete compress()
        String encrypted = BinaryStdIn.readString();
        int numZero = 0;
        int numOne = 0;
        boolean isOne = false;
        int n = encrypted.length();
        int i = 0;
        while (i < n) {
            if (encrypted.substring(i, i + 1).equals("1")) {
                isOne = true;
                numZero = 0;
            }
            if (!isOne && numOne == 0) {
                numZero++;
            }
            else if (encrypted.substring(i, i + 1).equals("0") && isOne){
                isOne = false;
                numOne = 0;
                BinaryStdOut.write(numOne);
                numZero++;
            }
            if (isOne) {
                BinaryStdOut.write(numZero);
                numOne++;
            }
            i++;
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {

        // TODO: complete expand()
        int i = 0;
        int numWrites = 0;
        int n = BinaryStdIn.readInt();
        boolean isOne = false;
        while (i < n) {
            numWrites = BinaryStdIn.readInt(8);
            if (!isOne) {
                for (int j = 0; j < numWrites; j++) {
                    BinaryStdOut.write(0);
                }
            }
            else if (isOne) {
                for (int j = 0; j < numWrites; j++) {
                    BinaryStdOut.write(1);
                }
                isOne = false;
            }
            isOne = true;
            i++;
        }

        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}