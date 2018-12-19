package alf.exercises.leetcode.sudoku;

import java.util.*;

public class SudokuSolver {

    public char[][] solve(char[][] boardArray) {

        Board board = Board.of(boardArray);
        board.print("Begin");
        board.isValid();

        solve(board);

        board.print();
        System.out.println("Finished" + (board.isComplete() ? "" : " NOT Complete !!"));

        return board.asArray();
    }

    protected void solve(Board board) {

        computeMaybeAllRows(board);
        computeMaybeAllColumns(board);
        computeMaybeAllGrids(board);


        board.print("before first maybe process");
        processAllMaybe(board);
        if (board.isComplete()) return;

        board.print("after first maybe process");

        evalAllPairs(board);
        board.print("after eval all hidden pairs");
        processAllMaybe(board);
        if (board.isComplete()) return;

        board.print("after second maybe process");
    }

    protected void computeMaybeAllRows(Board board) {
        for (int r = 1; r < 10; r++) {
            computeMaybeRow(board, r);
            board.isValid();
        }
    }

    protected void computeMaybeRow(Board board, int r) {

        List<Cell> row = board.getRow(r);
        Set<Character> missingForRow = board.getMissingForRow(r);
        for (Character missing : missingForRow) {
            for(Cell rowCell : row) {
                if (!rowCell.val.isPresent()) {
                    List<Cell> column = board.getColumn(rowCell.pos.col);
                    if (!containsValue(column, missing)) {
                        List<Cell> grid = board.getGrid(rowCell.pos);
                        if (!containsValue(grid, missing)) {
                            rowCell.maybe.add(missing);
                        }
                    }
                }
            }
        }
    }

    protected void computeMaybeAllColumns(Board board) {
        for (int c = 1; c < 10; c++) {
            computeMaybeColumn(board, c);
            board.isValid();
        }
    }

    protected void computeMaybeColumn(Board board, int c) {

        List<Cell> column = board.getColumn(c);
        Set<Character> missingForCol = board.getMissingForColumn(c);
        for (Character missing : missingForCol) {
            for(Cell columnCell : column) {
                if (!columnCell.val.isPresent()) {
                    List<Cell> row = board.getRow(columnCell.pos.row);
                    if (!containsValue(row, missing)) {
                        List<Cell> grid = board.getGrid(columnCell.pos);
                        if (!containsValue(grid, missing)) {
                            columnCell.maybe.add(missing);
                        }
                    }
                }
            }
        }
    }

    protected void computeMaybeAllGrids(Board board) {
        for (int g = 1; g < 10; g++) {
            computeMaybeGrid(board, g);
            board.isValid();
        }
    }

    protected void computeMaybeGrid(Board board, int g) {

        List<Cell> grid = board.getGrid(g);
        Set<Character> missingForGrid = board.getMissingForGrid(g);
        for (Character missing : missingForGrid) {
            for(Cell gridCell : grid) {
                if (!gridCell.val.isPresent()) {
                    List<Cell> row = board.getRow(gridCell.pos.row);
                    if (!containsValue(row, missing)) {
                        List<Cell> column = board.getColumn(gridCell.pos.col);
                        if (!containsValue(column, missing)) {
                            gridCell.maybe.add(missing);
                        }
                    }
                }
            }
        }
    }

    private void processAllMaybe(Board board) {
        int numberOfChanges;
        do {
            numberOfChanges = processSingletons(board);

            for (int row = 1; row < 10; row++) {
                numberOfChanges += processMaybeForGroup(board.getRow(row), board);
            }
            for (int col = 1; col < 10; col++) {
                numberOfChanges += processMaybeForGroup(board.getColumn(col), board);
            }
            for (int g = 1; g < 10; g++) {
                numberOfChanges += processMaybeForGroup(board.getGrid(g), board);
            }
        } while (numberOfChanges > 0);
    }

    private int processSingletons(Board board) {
        int changeCount = 0;
        boolean changeOccurred;
        do {
            changeOccurred = false;
            for (Cell cell : board.getAllCells()) {
                if (cell.val.isPresent()) {
                    if (!cell.maybe.isEmpty()) throw new RuntimeException("??");
                } else {
                    if (cell.maybe.size() == 1) {
                        Character maybeVal = cell.maybe.first();
                        changeOccurred = true;
                        changeCount++;
                        cell.setVal(maybeVal);
                        cell.maybe.clear();
                        removeMaybeValFromNeighbors(maybeVal, cell, board);
                    }
                }
            }
        } while (changeOccurred);
        board.isValid();
        return changeCount;
    }

    private int processMaybeForGroup(List<Cell> cellList, Board board) {
        int changeCount = 0;
        boolean changeOccured;
        do {
            changeOccured = false;
            for (Cell cell : cellList) {
                if (!cell.val.isPresent()) {

                    List<Character> copyOfMaybe = new ArrayList<>(cell.maybe);
                    for (Character maybeChar : copyOfMaybe) {

                        if (!isFoundInOtherMaybe(cellList, cell, maybeChar)) {
                            changeOccured = true;
                            changeCount++;
                            cell.setVal(maybeChar);
                            cell.maybe.clear();
                            removeMaybeValFromNeighbors(maybeChar, cell, board);
                        }
                    }
                }
            }
        } while (changeOccured);
        board.isValid();
        return changeCount;
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

    private void evalAllPairs(Board board) {
        int numberOfChanges;
        do {
            numberOfChanges = 0;
            for (int row = 1; row < 10; row++) {
                numberOfChanges += evalHiddenPairs(board.getRow(row));
                numberOfChanges += evalExclusivePairs(board.getRow(row), board);
                board.isValid();
            }
            for (int col = 1; col < 10; col++) {
                numberOfChanges += evalHiddenPairs(board.getColumn(col));
                numberOfChanges += evalExclusivePairs(board.getColumn(col), board);
                board.isValid();
            }
            for (int g = 1; g < 10; g++) {
                numberOfChanges += evalHiddenPairs(board.getGrid(g));
                numberOfChanges += evalExclusivePairs(board.getGrid(g), board);
                board.isValid();
            }

        } while (numberOfChanges > 0);
    }

    protected int evalHiddenPairs(List<Cell> cellList) {
        int changeCount = 0;
        boolean changeOccurred;
        do {
            changeOccurred = false;
            Map<TreeSet<Character>, TreeSet<Pos>> hiddenPairs = getHiddenPairs(cellList);
            for (Map.Entry<TreeSet<Character>, TreeSet<Pos>> entry : hiddenPairs.entrySet()) {
                for (Cell cell : cellList) {
                    if (!cell.val.isPresent()) {
                        if (cell.pos.equals(entry.getValue().first()) || cell.pos.equals(entry.getValue().last())) {
                            if (!cell.maybe.equals(entry.getKey())) {
                                cell.maybe = entry.getKey();
                                changeOccurred = true;
                                changeCount++;
                            }
                        }
                    }
                }
            }
        } while (changeOccurred);
        return changeCount;
    }

    protected Map<TreeSet<Character>, TreeSet<Pos>> getHiddenPairs(List<Cell> cellList) {

        Map<Character, TreeSet<Pos>> characterPositions = new HashMap<>();
        for (Cell cell : cellList) {
            for (Character m : cell.maybe) {
                if (characterPositions.containsKey(m)) {
                    characterPositions.get(m).add(cell.pos);
                } else {
                    TreeSet<Pos> posSet = new TreeSet<>();
                    posSet.add(cell.pos);
                    characterPositions.put(m, posSet);
                }
            }
        }

        // keep only those char which are found in exactly two positions
        Map<Character, TreeSet<Pos>> characterPositionsTwo = new HashMap<>();
        for (Map.Entry<Character, TreeSet<Pos>> entry : characterPositions.entrySet()) {
            if (entry.getValue().size() == 2) {
                characterPositionsTwo.put(entry.getKey(), entry.getValue());
            }
        }

        Map<TreeSet<Character>, TreeSet<Pos>> hiddenPairs = new HashMap<>();
        for (Map.Entry<Character, TreeSet<Pos>> thisEntry : characterPositionsTwo.entrySet()) {

            Character thisCharKey = thisEntry.getKey();
            TreeSet<Pos> thisPairOfPos = thisEntry.getValue();

            for (Map.Entry<Character, TreeSet<Pos>> otherEntry : characterPositionsTwo.entrySet()) {
                if (!thisCharKey.equals(otherEntry.getKey())) {
                    if (thisPairOfPos.equals(otherEntry.getValue())) {
                        // add to output map
                        TreeSet<Character> hiddenPairKey = new TreeSet<>(Arrays.asList(thisCharKey, otherEntry.getKey()));
                        hiddenPairs.put(hiddenPairKey, thisPairOfPos);
                    }
                }
            }
        }

        return hiddenPairs;
    }

/*
    private int evalAllExclusivePairs(Board board) {
        int changeCount = 0;
        boolean changeOccurred;
        do {
            int prevChangeCount = changeCount;
            for (int row = 1; row < 10; row++) {
                changeCount += evalExclusivePairs(board.getRow(row), board);
                board.isValid();
            }
            for (int col = 1; col < 10; col++) {
                changeCount += evalExclusivePairs(board.getColumn(col), board);
                board.isValid();
            }
            for (int g = 1; g < 10; g++) {
                changeCount += evalExclusivePairs(board.getGrid(g), board);
                board.isValid();
            }

            changeOccurred = (changeCount - prevChangeCount) > 0;

        } while (changeOccurred);
        return changeCount;
    }
*/

    private int evalExclusivePairs(List<Cell> cellList, Board board) {
        int numberOfChanges = 0;
        boolean changeOccurred;
        do {
            changeOccurred = false;
            for (Map.Entry<Set<Character>, TreeSet<Pos>> entry : getExclusivePairs(cellList).entrySet()) {
                Pos posA = entry.getValue().first();
                Pos posB = entry.getValue().last();

                if (posA.row == posB.row) {
                    // remove from other maybes in same row
                    List<Cell> row = board.getRow(entry.getValue().first().row);
                    for (Cell rowCell : row) {
                        if (!rowCell.val.isPresent()) {
                            if (!rowCell.pos.equals(posA) && !rowCell.pos.equals(posB)) {
                                for (Character p : entry.getKey()) {
                                    if (rowCell.maybe.contains(p)) {
                                        rowCell.maybe.remove(p);
                                        changeOccurred = true;
                                        numberOfChanges++;
                                    }
                                }
                            }
                        }
                    }
                }

                if (posA.col == posB.col) {
                    // remove from other maybes in same column
                    List<Cell> column = board.getColumn(entry.getValue().first().col);
                    for (Cell colCell : column) {
                        if (!colCell.val.isPresent()) {
                            if (!colCell.pos.equals(posA) && !colCell.pos.equals(posB)) {
                                for (Character p : entry.getKey()) {
                                    if (colCell.maybe.contains(p)) {
                                        colCell.maybe.remove(p);
                                        changeOccurred = true;
                                        numberOfChanges++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } while (changeOccurred);
        return numberOfChanges;
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

    private boolean containsValue(List<Cell> cellList, Character value) {
        for (Cell c : cellList) {
            if (c.val.isPresent() && c.val.get().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
