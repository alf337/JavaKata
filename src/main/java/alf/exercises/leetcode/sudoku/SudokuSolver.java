package alf.exercises.leetcode.sudoku;

import java.util.*;

public class SudokuSolver {

    public char[][] solve(char[][] boardArray) {

        Board board = Board.of(boardArray);
        board.print("Begin");
        board.isValid();

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
//            evalAllExclusivePairs(board);
//            evalAllHiddenPairs(board);

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
    }

    private void evalMaybe(List<Cell> cellList, Board board) {
        for (Cell cell : cellList) {
            if (!cell.val.isPresent()) {

                List<Character> copyOfMaybe = new ArrayList<>(cell.maybe);
                for (Character maybeChar : copyOfMaybe) {

                    if (!isFoundInOtherMaybe(cellList, cell, maybeChar)) {
                        cell.setVal(maybeChar);
                        cell.maybe.clear();

                        // remove maybe val from col, row, and grid
                        removeMaybeValFromNeighbors(maybeChar, cell, board);
                    }
                }
            }
        }
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

    private void evalAllExclusivePairs(Board board) {
        for (int row = 1; row < 9; row++) {
            evalExclusivePairs(board.getRow(row), board);
            board.isValid();
        }
        for (int col = 1; col < 9; col++) {
            evalExclusivePairs(board.getColumn(col), board);
            board.isValid();
        }
        for (int g = 1; g < 9; g++) {
            evalExclusivePairs(board.getGrid(g), board);
            board.isValid();
        }
    }

    private void evalExclusivePairs(List<Cell> cellList, Board board) {
        for (Map.Entry<Set<Character>, TreeSet<Pos>> entry : getExclusivePairs(cellList).entrySet()) {
            Pos posA = entry.getValue().first();
            Pos posB = entry.getValue().last();

            if (posA.row == posB.row) {
                // remove from other maybes in same row
                List<Cell> row = board.getRow(entry.getValue().first().row);
                for (Cell rowCell : row) {
                    if (!rowCell.pos.equals(posA) && !rowCell.pos.equals(posB)) {
                        for (Character p : entry.getKey()) {
                            rowCell.maybe.remove(p);
                        }
                    }
                }
            }

            if (posA.col == posB.col) {
                // remove from other maybes in same column
                List<Cell> column = board.getColumn(entry.getValue().first().col);
                for (Cell colCell : column) {
                    if (!colCell.pos.equals(posA) && !colCell.pos.equals(posB)) {
                        for (Character p : entry.getKey()) {
                            colCell.maybe.remove(p);
                        }
                    }
                }
            }
        }
    }

    private Map<Set<Character>, TreeSet<Pos>> getExclusivePairs(List<Cell> cellList) {
        Map<Set<Character>, TreeSet<Pos>> pairs = new HashMap<>();

        for (Cell cell : cellList) {
            if (cell.maybe.size() == 2) {
                Set<Character> pair = cell.maybe;
                if (pairs.containsKey(pair)) {
                    pairs.get(pair).add(cell.pos);
                } else {
                    TreeSet<Pos> posSet = new TreeSet<>();
                    posSet.add(cell.pos);
                    pairs.put(pair, posSet);
                }
            }
        }

        // keep only those which are exclusive pairs
        Map<Set<Character>, TreeSet<Pos>> exclusivePairs = new HashMap<>();
        for (Map.Entry<Set<Character>, TreeSet<Pos>> entry : pairs.entrySet()) {
            if (entry.getValue().size() == 2) {
                exclusivePairs.put(entry.getKey(), entry.getValue());
            }
        }
        return exclusivePairs;
    }

    private void evalAllHiddenPairs(Board board) {
        for (int row = 1; row < 9; row++) {
            evalHiddenPairs(board.getRow(row), board);
            board.isValid();
        }
        for (int col = 1; col < 9; col++) {
            evalHiddenPairs(board.getColumn(col), board);
            board.isValid();
        }
        for (int g = 1; g < 9; g++) {
            evalHiddenPairs(board.getGrid(g), board);
            board.isValid();
        }
    }

    protected void evalHiddenPairs(List<Cell> cellList, Board board) {

        for (Map.Entry<TreeSet<Character>, TreeSet<Pos>> entry : getHiddenPairs(cellList).entrySet()) {
            for (Cell cell : cellList) {
                if (cell.pos.equals(entry.getValue().first()) || cell.pos.equals(entry.getValue().last())) {
                    cell.maybe = entry.getKey();
                }
            }
        }
    }

    protected Map<TreeSet<Character>, TreeSet<Pos>> getHiddenPairs(List<Cell> cellList) {

        Map<Character, TreeSet<Pos>> charactersPositions = new HashMap<>();
        for (Cell cell : cellList) {
            for (Character m : cell.maybe) {
                if (charactersPositions.containsKey(m)) {
                    charactersPositions.get(m).add(cell.pos);
                } else {
                    TreeSet<Pos> posSet = new TreeSet<>();
                    posSet.add(cell.pos);
                    charactersPositions.put(m, posSet);
                }
            }
        }

        Map<Pos, TreeSet<Character>> positionCharacters = new HashMap<>();
        for (Map.Entry<Character, TreeSet<Pos>> entry : charactersPositions.entrySet()) {
            if (entry.getValue().size() == 2) {
                Character m = entry.getKey();
                for (Pos pos : entry.getValue()) {
                    if (positionCharacters.containsKey(pos)) {
                        positionCharacters.get(pos).add(m);
                    } else {
                        TreeSet<Character> characterSet = new TreeSet<>();
                        characterSet.add(m);
                        positionCharacters.put(pos, characterSet);
                    }
                }
            }
        }

        Map<TreeSet<Character>, TreeSet<Pos>> hiddenPairs = new HashMap<>();
        for (Map.Entry<Pos, TreeSet<Character>> entry : positionCharacters.entrySet()) {
            if (entry.getValue().size() == 2) {
                TreeSet<Character> hiddenPairKey = entry.getValue();
                if (hiddenPairs.containsKey(hiddenPairKey)) {
                    hiddenPairs.get(hiddenPairKey).add(entry.getKey());
                } else {
                    TreeSet<Pos> posTreeSet = new TreeSet<>();
                    posTreeSet.add(entry.getKey());
                    hiddenPairs.put(hiddenPairKey, posTreeSet);
                }
            }
        }

        return hiddenPairs;
    }

    private boolean containsValue(List<Cell> cellList, Character value) {
        for (Cell c : cellList) {
            if (c.val.isPresent() && c.val.get().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
