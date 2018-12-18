package alf.exercises.leetcode.sudoku;

import java.util.List;
import java.util.Set;

public class SudokuSolver {

    public char[][] solve(char[][] boardArray) {

        Board board = Board.of(boardArray);
        board.print("Begin");
        int attempts = solve(board);

        board.print();
        System.out.println("Finished attempts = " + attempts
                        + (board.isComplete() ? "" : " NOT Complete !!"));

        return board.asArray();
    }

    protected int solve(Board board) {
        int attempts = 0;
        long prevRemaining = Integer.MAX_VALUE;

        while (!board.isComplete() && board.remaining() < prevRemaining) {

            prevRemaining = board.remaining();
            attempts++;

            evalAllRows(board);
            evalAllColumns(board);
            evalAllGrids(board);

            board.print("After attempt: " + attempts);
        }
        return attempts;
    }

    protected void evalAllRows(Board board) {
        for (int r = 1; r < 10; r++) {
            evalRow(board, r);
        }
    }

    protected void evalRow(Board board, int r) {

        List<Cell> row = board.getRow(r);
        Set<Character> missingForRow = board.getMissingForRow(r);
        for (Character missing : missingForRow) {
            for(Cell cell : row) {
                if (!cell.val.isPresent()) {
                    List<Cell> column = board.getColumn(cell.pos.col);
                    if (!containsValue(column, missing)) {
                        List<Cell> grid = board.getGrid(cell.pos);
                        if (!containsValue(grid, missing)) {
                            cell.maybe.add(missing);
                        }
                    }
                }
            }
        }

        evalMaybe(row, board);
//        if (!board.isValid()) throw new RuntimeException("board invalid after eval row: " + r);
    }

    protected void evalAllColumns(Board board) {
        for (int c = 1; c < 10; c++) {
            evalColumn(board, c);
        }
    }

    protected void evalColumn(Board board, int c) {

        List<Cell> column = board.getColumn(c);
        Set<Character> missingForCol = board.getMissingForColumn(c);
        for (Character missing : missingForCol) {
            for(Cell cell : column) {
                if (!cell.val.isPresent()) {
                    List<Cell> row = board.getRow(cell.pos.row);
                    if (!containsValue(row, missing)) {
                        List<Cell> grid = board.getGrid(cell.pos);
                        if (!containsValue(grid, missing)) {
                            cell.maybe.add(missing);
                        }
                    }
                }
            }
        }

        evalMaybe(column, board);
//        if (!board.isValid()) throw new RuntimeException("board invalid after eval column: " + c);
    }

    protected void evalAllGrids(Board board) {
        for (int g = 1; g < 10; g++) {
            evalGrid(board, g);
        }
    }

    protected void evalGrid(Board board, int g) {

        List<Cell> grid = board.getGrid(g);
        Set<Character> missingForGrid = board.getMissingForGrid(g);
        for (Character missing : missingForGrid) {
            for(Cell cell : grid) {
                if (!cell.val.isPresent()) {
                    List<Cell> row = board.getRow(cell.pos.row);
                    if (!containsValue(row, missing)) {
                        List<Cell> column = board.getColumn(cell.pos.col);
                        if (!containsValue(column, missing)) {
                            cell.maybe.add(missing);
                        }
                    }
                }
            }
        }

        evalMaybe(grid, board);
//        if (!board.isValid()) throw new RuntimeException("board invalid after eval grid: " + g);
    }

    private boolean containsValue(List<Cell> cellList, Character value) {
        for (Cell c : cellList) {
            if (c.val.isPresent() && c.val.get().equals(value)) {
                return true;
            }
        }
        return false;
    }

    private void evalMaybe(List<Cell> cellList, Board board) {
        boolean somethingWasChanged;
        do {
            somethingWasChanged = false;
            for (Cell cell : cellList) {
                if (!cell.val.isPresent()) {

                    boolean needToClearMaybe = false;
                    for (Character maybeVal : cell.maybe) {

                        if (!isFoundInOtherMaybe(cellList, cell, maybeVal)) {
                            cell.setVal(maybeVal);
                            somethingWasChanged = true;
                            needToClearMaybe = true;
                            // remove maybe val from col, row, and grid
                            removeMaybeValFromNeighbors(maybeVal, cell, board);
                        }
                    }
                    if (needToClearMaybe) cell.maybe.clear();
                }
            }
        } while (somethingWasChanged);
    }

    private boolean isFoundInOtherMaybe(List<Cell> cellList, Cell cell, Character maybeVal) {
        // look for maybe val in other cells
        for (Cell other : cellList) {
            if (!other.equals(cell)) {
                if (other.maybe.contains(maybeVal)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeMaybeValFromNeighbors(Character maybeVal, Cell cell, Board board) {
        for (Cell other : board.getRow(cell.pos.row)) {
            if (other != cell) {
                other.maybe.remove(maybeVal);
            }
        }
        for (Cell other : board.getColumn(cell.pos.col)) {
            if (other != cell) {
                other.maybe.remove(maybeVal);
            }
        }
        for (Cell other : board.getGrid(cell.pos)) {
            if (other != cell) {
                other.maybe.remove(maybeVal);
            }
        }
    }

}
