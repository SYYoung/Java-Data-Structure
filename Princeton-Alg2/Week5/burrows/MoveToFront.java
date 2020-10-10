/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;

public class MoveToFront {
    ArrayList<Character> charList;
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] charList = new char[R];
        // 0. init the sequence by making the ith character in the seq equal to the ith ASCII char
        
        // 1. read each 8-bit character c from standard input
        while (!BinaryStdIn.isEmpty()) {
            char ch = BinaryStdIn.readChar();
            BinaryStdOut.write(charList[ch]);
        }
        // 2. output 8-bit index in the seq where c appears
        // 3. move c to the front
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        // 0. init the sequence
        // 1. read each 8-bit character but treat it as an int between 0 and 255
        while (!BinaryStdIn.isEmpty()) {
            char ch = BinaryStdIn.readChar();
        }
        // 2. write the ith char in the sequence
        // 3. move that char to the front
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        MoveToFront myMove = new MoveToFront();
        if (args[0] == "-")
            myMove.encode();
        else
            myMove.decode();
    }
}
