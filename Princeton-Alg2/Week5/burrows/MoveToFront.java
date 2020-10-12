/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


public class MoveToFront {
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] charList = new char[R];
        // 0. init the sequence by making the ith character in the seq equal to the ith ASCII char
        for (int i = 0; i < R; i++) {
            charList[i] = (char) i;
        }

        // 1. read each 8-bit character c from standard input
        // 2. output 8-bit index in the seq where c appears
        // 3. move c to the front
        // 4. update the index of c, and all char before c
        /*
        String s = "ABRACADABRA!";
        for (int j = 0; j < s.length(); j++) {
        char ch = s.charAt(j);
         */
        while (!BinaryStdIn.isEmpty()) {
            char ch = BinaryStdIn.readChar();
            int pos = search(ch, charList);
            BinaryStdOut.write(pos, 8);
            System.arraycopy(charList, 0, charList, 1, pos);
            charList[0] = ch;
        }

        BinaryStdOut.close();
    }

    private static int search(char ch, char[] charList) {
        for (int i = 0; i < charList.length; i++) {
            if (charList[i] == ch)
                return i;
        }
        return charList.length;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] charList = new char[R];
        // 0. init the sequence by making the ith character in the seq equal to the ith ASCII char
        for (int i = 0; i < R; i++) {
            charList[i] = (char) i;
        }
        // 1. read each 8-bit character c from standard input
        // 2. output 8-bit index in the seq where c appears
        // 3. move c to the front
        // 4. update the index of c, and all char before c
        /*
        int[] inputList = {
                0x41, 0x42, 0x52, 0x02, 0x44, 0x01, 0x45, 0x01, 0x04, 0x04, 0x02, 0x26
        };
        //for (int j = 0; j < inputList.length; j++) {
            //int pos = inputList[j];
         */
        while (!BinaryStdIn.isEmpty()) {
            int pos = BinaryStdIn.readInt(8);
            BinaryStdOut.write(charList[pos], 8);
            char tmp = charList[pos];
            System.arraycopy(charList, 0, charList, 1, pos);
            charList[0] = tmp;
        }
        
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-"))
            MoveToFront.encode();
        else
            MoveToFront.decode();
    }
}
