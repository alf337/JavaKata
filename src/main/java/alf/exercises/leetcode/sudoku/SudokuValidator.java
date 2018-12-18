package alf.exercises.leetcode.sudoku;

import java.util.Arrays;
import java.util.List;

public class SudokuValidator {

    private static final char[] VALID_CHARS = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public boolean isValidSudoku(Board board) {

        if (board == null || board.size() != 81) return false;
        if (!isValidCharacters(board)) return false;
        if (!isValidRows(board)) return false;
        if (!isValidColumns(board)) return false;
        if (!isValidGrids(board)) return false;

        return true;
    }

    private boolean isValidRows(Board board) {
        for (int r = 1; r < 10; r++) {
            List<Cell> row = board.getRow(r);
            if (row.size() != 9) return false;
            if (hasDuplicates(row)) return false;
        }
        return true;
    }

    private boolean isValidColumns(Board board) {
        for (int c = 1; c < 10; c++) {
            List<Cell> column = board.getColumn(c);
            if (column.size() != 9) return false;
            if (hasDuplicates(column)) return false;
        }
        return true;
    }

    private boolean isValidGrids(Board board) {
        for (int g = 1; g < 10; g++) {
            List<Cell> grid = board.getGrid(g);
            if (grid.size() != 9) return false;
            if (hasDuplicates(grid)) return false;
        }
        return true;
    }

    protected boolean isValidCharacters(Board board) {
        for (Cell cell : board.getAllCells()) {
            if(cell.val.isPresent()) {
                if (0 > Arrays.binarySearch(VALID_CHARS, cell.val.get())) return false;
            }
        }
        return true;
    }

    protected boolean hasDuplicates(List<Cell> cellList) {
        int[] count = new int[10];
        for (Cell cell : cellList) {
            if (cell.val.isPresent()) {
                int digit = cell.val.get() - '1';
                count[digit]++;
                if (count[digit] > 1) return true;
            }
        }
        return false;
    }
}
