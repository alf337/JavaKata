package alf.exercises.leetcode.sudoku;

import java.util.*;

public class SudokuSolver {

    public char[][] solve(char[][] boardArray) {

        Board board = Board.of(boardArray);
        board.print("Begin");
        board.isValid();

        board = solve(board);

        board.print();
        System.out.println("Finished" + (board.isComplete() ? "" : " NOT Complete !!"));

        return board.asArray();
    }

    public String solve(String boardString) {

        Board board = Board.of(boardString);
        board.isValid();
//        board.print("");

        Board result = solve(board);
        return result.asString();
    }

    protected Board solve(Board board) {

        long remainingAtStart = board.remaining();
        computeMaybeAllRows(board);
        computeMaybeAllColumns(board);
        computeMaybeAllGrids(board);

        processAllMaybe(board);

        if (!board.isComplete()) {
            board = randomGuess(board);
            if (board.isComplete()) {
                System.out.println("!! Hard Puzzle Solved " + remainingAtStart);
            } else {
                System.out.println("!!!! Not Solved " + remainingAtStart);
            }
        }

//        if (!board.isComplete()) board.printMaybeByValue();
        return board;
    }

    protected void computeMaybeAllRows(Board board) {
        for (int r = 1; r < 10; r++) {
            computeMaybeRow(board, r);
            //board.isValid();
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
            //board.isValid();
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
            //board.isValid();
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
                numberOfChanges += gridRowColumnInteraction(board.getGrid(g), board);
            }

            numberOfChanges += evalAllPairs(board);

        } while (numberOfChanges > 0);
    }

    private int processSingletons(Board board) {
        int changeCount = 0;
        boolean changeOccurred;
        do {
            changeOccurred = false;
            for (Cell cell : board.getAllCells()) {
                if (!cell.val.isPresent()) {
                    if (cell.maybe.size() == 1) {
                        Character maybeVal = cell.maybe.first();
                        changeOccurred = true;
                        changeCount++;
                        cell.setVal(maybeVal);
                        removeMaybeValFromNeighbors(maybeVal, cell, board);
                    }
                }
            }
        } while (changeOccurred);
        //board.isValid();
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
                            removeMaybeValFromNeighbors(maybeChar, cell, board);
                        }
                    }
                }
            }
        } while (changeOccured);
        //board.isValid();
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
                if (other.maybe.contains(maybeVal)) {
                    if (other.maybe.size() > 1) {
                        other.maybe.remove(maybeVal);
                    } else {
                        throw new RuntimeException("Removing last maybe from neighbor: " + other + " at: " + cell);
                    }
                }
            }
        }
        for (Cell other : board.getColumn(cell.pos.col)) {
            if (other != cell) {
                if (other.maybe.contains(maybeVal)) {
                    if (other.maybe.size() > 1) {
                        other.maybe.remove(maybeVal);
                    } else {
                        throw new RuntimeException("Removing last maybe from neighbor: " + other + " at: " + cell);
                    }
                }
            }
        }
        for (Cell other : board.getGrid(cell.pos)) {
            if (other != cell) {
                if (other.maybe.contains(maybeVal)) {
                    if (other.maybe.size() > 1) {
                        other.maybe.remove(maybeVal);
                    } else {
                        throw new RuntimeException("Removing last maybe from neighbor: " + other + " at: " + cell);
                    }
                }
            }
        }
    }

    protected int gridRowColumnInteraction(List<Cell> cellList, Board board) {
        int changeCount = 0;
        boolean changeOccured;
        do {
            changeOccured = false;
            for (Character c : board.copyValidValues()) {
                TreeSet<Pos> maybeCharPosSet = findMaybeCharacterPositions(c, cellList);
                if (2 == maybeCharPosSet.size()) {

                    if (maybeCharPosSet.first().row == maybeCharPosSet.last().row) {
                        // same row, remove maybe char from others in same row
                        for (Cell rowCell : board.getRow(maybeCharPosSet.first().row)) {
                            if (!maybeCharPosSet.contains(rowCell.pos)) {
                                if (rowCell.maybe.contains(c)) {
                                    rowCell.maybe.remove(c);
                                    changeOccured = true;
                                    changeCount++;
                                }
                            }
                        }
                    }

                    if (maybeCharPosSet.first().col == maybeCharPosSet.last().col) {
                        // same col
                        for (Cell colCell : board.getColumn(maybeCharPosSet.first().col)) {
                            if (!maybeCharPosSet.contains(colCell.pos)) {
                                if (colCell.maybe.contains(c)) {
                                    colCell.maybe.remove(c);
                                    changeOccured = true;
                                    changeCount++;
                                }
                            }
                        }
                    }
                }
            }

        } while (changeOccured);
        return changeCount;
    }

    private TreeSet<Pos> findMaybeCharacterPositions(Character c, List<Cell> cellList) {
        TreeSet<Pos> result = new TreeSet<>();
        for (Cell cell : cellList) {
            if (cell.maybe.contains(c)) {
                result.add(cell.pos);
            }
        }
        return result;
    }

    private int evalAllPairs(Board board) {
        int totalChangeCount = 0;
        int loopChangeCount;
        do {
            loopChangeCount = 0;
            for (int row = 1; row < 10; row++) {
                loopChangeCount += evalHiddenPairs(board.getRow(row));
                loopChangeCount += evalExclusivePairs(board.getRow(row), board);
                //board.isValid();
            }
            for (int col = 1; col < 10; col++) {
                loopChangeCount += evalHiddenPairs(board.getColumn(col));
                loopChangeCount += evalExclusivePairs(board.getColumn(col), board);
                //board.isValid();
            }
            for (int g = 1; g < 10; g++) {
                loopChangeCount += evalHiddenPairs(board.getGrid(g));
                loopChangeCount += evalExclusivePairs(board.getGrid(g), board);
                //board.isValid();
            }
            totalChangeCount += loopChangeCount;

        } while (loopChangeCount > 0);
        return totalChangeCount;
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
                                cell.maybe = new TreeSet<>(entry.getKey());
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
                Set<Character> pair = new TreeSet<>(cell.maybe);
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

    /**
     * Try randomly setting a value to see if we can break through the log jam.
     */
    private Board randomGuess(Board originalBoard) {
//        originalBoard.print("Original board before Random Guess");
        for (Cell pick :  pickSome(originalBoard)) {
//            System.out.println("PICK cell = " + pick);
            for (Character pickChar : pick.maybe) {
                try {
                    Board dupBoard = originalBoard.deepCopy();

                    Cell guessCell = dupBoard.getCell(pick.pos);
                    guessCell.setVal(pickChar);
                    removeMaybeValFromNeighbors(pickChar, guessCell, dupBoard);
//                    dupBoard.print("START random guess: " + guessCell);

                    dupBoard.isValid();
                    processAllMaybe(dupBoard);
                    dupBoard.isValid();
//                    dupBoard.print("After Random guess " + guessCell);
                    if (dupBoard.isComplete()) return dupBoard;

                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }
        }
        return originalBoard;
    }

    private List<Cell> pickSome(Board b) {
        List<Cell> picked = new ArrayList<>();
        for (Cell cell : b.getAllCells()) {
            if (cell.maybe.size() == 2) {
                picked.add(cell);
            }
        }
        return picked;
    }
}
