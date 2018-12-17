package alf.exercises.leetcode.sudoku;

import java.util.Arrays;

/**
 * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
 * <p>
 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 * Note:
 * A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 * Only the filled cells need to be validated according to the mentioned rules.
 * The given board contain only digits 1-9 and the character '.'.
 * The given board size is always 9x9.
 */
public class SudokuValidator {

    private static final char[] VALID_CHARS = new char[]{'.', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final int[] GRID_ROW_OFFSET = new int[]{0, 0, 0, 3, 3, 3, 6, 6, 6};
    private static final int[] GRID_COL_OFFSET = new int[]{0, 3, 6, 0, 3, 6, 0, 3, 6};

    public boolean isValidSudoku(char[][] board) {

        if (board == null || board.length != 9) return false;
        if (!isValidCharacters(board)) return false;
        if (!isValidRows(board)) return false;
        if (!isValidColumns(board)) return false;
        if (!isValidGrids(board)) return false;

        return true;
    }

    private boolean isValidRows(char[][] board) {

        for (char[] row : board) {
            if (row == null || row.length != 9) return false;
            if (hasDuplicates(row)) return false;
        }
        return true;
    }

    private boolean isValidColumns(char[][] board) {

        for (int col = 0; col < 9; col++) {
            char[] column = getColumn(board, col);
            if (hasDuplicates(column)) return false;
        }
        return true;
    }

    protected char[] getColumn(char[][] board, int col) {
        char[] column = new char[9];
        for (int row = 0; row < 9; row++) {
            column[row] = board[row][col];
        }
        return column;
    }

    private boolean isValidGrids(char[][] board) {

        for (int g = 0; g < 9; g++) {
            char[] grid = getGrid(board, g);
            if (hasDuplicates(grid)) return false;
        }
        return true;
    }

    protected char[] getGrid(char[][] board, int g) {
        int rowStart = GRID_ROW_OFFSET[g];
        int colStart = GRID_COL_OFFSET[g];
        char[] grid = new char[9];
        int gridIndex = 0;

        for (int row = rowStart; row < rowStart + 3; row++) {
            for (int col = colStart; col < colStart + 3; col++) {
                grid[gridIndex] = board[row][col];
                gridIndex++;
            }
        }
        return grid;
    }

    protected boolean isValidCharacters(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (0 > Arrays.binarySearch(VALID_CHARS, board[row][col])) return false;
            }
        }
        return true;
    }

    protected boolean hasDuplicates(char[] chars) {
        int[] count = new int[10];
        for (char c : chars) {
            if ('.' != c) {
                int digit = c - '1';
                count[digit]++;
                if (count[digit] > 1) return true;
            }
        }
        return false;
    }
}
