package alf.exercises.leetcode;

import java.util.Arrays;
import java.util.Optional;

/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)
 *
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 *
 * And then read line by line: "PAHNAPLSIIGYIR"
 *
 * Write the code that will take a string and make this conversion given a number of rows:
 *
 * string convert(string s, int numRows);
 *
 * Example 1:
 *
 * Input: s = "PAYPALISHIRING", numRows = 3
 * Output: "PAHNAPLSIIGYIR"
 *
 * Example 2:
 *
 * Input: s = "PAYPALISHIRING", numRows = 4
 * Output: "PINALSIGYAHRPI"
 * Explanation:
 *
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 */
public class ZigZagConversion {

    public String convert(String s, int numRows) {

        return solution1(s, numRows);
    }

    private String solution1(String s, int numRows) {

        if (numRows == 1) return s;
        System.out.println("input string: " + s + " rows: " + numRows);
        Character[][] grid = buildGrid(s, numRows);
        printGrid(grid);

        return buildResult(grid);
    }

    private Character[][] buildGrid(String s, int numRows) {

        Character[][] grid = new Character[numRows][s.length()];
        char[] chars = s.toCharArray();
        int index = 0;
        int row = 0;
        int col = 0;
        int maxRow = numRows - 1;
        int rowInc = 1;
        int colInc = 0;

        while (index < chars.length) {
            grid[row][col] = chars[index];
            index++;

            if (row == maxRow) {
                rowInc = -1;
                colInc = 1;
            } else {
                if (row == 0) {
                    rowInc = 1;
                    colInc = 0;
                }
            }

            row = row + rowInc;
            col = col + colInc;
        }
        return grid;
    }

    private String buildResult(Character[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    sb.append(grid[i][j]);
                }
            }
        }
        return sb.toString();
    }

    private void printGrid(Character[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    System.out.print(grid[i][j].toString() + " ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}