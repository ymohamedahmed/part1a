package uk.ac.cam.ym346.Algorithms.Tick2;

import java.util.Arrays;

public class LCSBottomUp extends LCSFinder {

    public LCSBottomUp(String s1, String s2) {
        super(s1, s2);
    }

    public static void main(String[] args) {
//        LCSBottomUp l = new LCSBottomUp("xaxbxcxd","aabbccdd");
//        LCSBottomUp l = new LCSBottomUp("XMJYAUZ", "MZJAWXU");
//        LCSBottomUp l = new LCSBottomUp("ABBA", "CACA");
//        LCSBottomUp l = new LCSBottomUp("","");
        LCSBottomUp l = new LCSBottomUp( "ABXC","AXBX");
        int length = l.getLCSLength();
        System.out.println("length = " + length);
        for (int[] i : l.getLCSLengthTable()) {
            System.out.println(Arrays.toString(i));
        }
        String lcs = l.getLCSString();
        System.out.println("lcs = " + lcs);
    }

    @Override
    public int getLCSLength() {
        char[] s1Array = mString1.toCharArray();
        char[] s2Array = mString2.toCharArray();
        mTable = new int[s1Array.length][s2Array.length];
        for (int i = 0; i < s1Array.length; i++) {
            for (int j = 0; j < s2Array.length; j++) {
                int result = 0;
                // If they end in the same character
                if (Character.compare(s1Array[i], s2Array[j]) == 0) {
                    result = (i == 0 || j == 0) ? 1 : getLCSLengthTable()[i - 1][j - 1] + 1;
                }
                // Otherwise find the maximum with one character removed from either string
                else {
                    result = Math.max((i == 0) ? 0 : getLCSLengthTable()[i - 1][j], (j == 0) ? 0 : getLCSLengthTable()[i][j - 1]);
                }
                getLCSLengthTable()[i][j] = result;
            }
        }
        return (s1Array.length == 0 || s2Array.length == 0) ? 0 : getLCSLengthTable()[s1Array.length - 1][s2Array.length - 1];
    }

    @Override
    public String getLCSString() {
        String result = "";
        char[] s1Array = mString1.toCharArray();
        char[] s2Array = mString2.toCharArray();
        // Find the LCS Length without recomputing
        int value = (s1Array.length == 0 || s2Array.length == 0) ? 0 : getLCSLengthTable()[s1Array.length - 1][s2Array.length - 1];
        int y = s1Array.length - 1;
        int x = s2Array.length - 1;
        // Want to move as far left and up as we can without reaching the next number down
        boolean reachedFurthestLeft = false;
        boolean reachedFurthestUp = false;
        while (value > 0) {
            while (!reachedFurthestLeft || !reachedFurthestUp) {
                // If reached edge or the next number down
                reachedFurthestLeft = x == 0 || getLCSLengthTable()[y][x - 1] == value - 1;
                reachedFurthestUp = y == 0 || getLCSLengthTable()[y-1][x] == value - 1;
                if(!reachedFurthestLeft){
                    x--;
                }
                if(!reachedFurthestUp) {
                    y--;
                }
            }
            result = s1Array[y] + result;
            value--;
            reachedFurthestLeft = x == 0;
            reachedFurthestUp = y == 0;
        }
        return result;
    }
}
