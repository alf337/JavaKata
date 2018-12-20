package alf.exercises.leetcode.sudoku;

import java.util.*;

/**
 * This includes everything so that it can be run on the Leetcode website.
 */
public class SudokuAllInclusive {

    public void solveSudoku(char[][] board) {

        char[][] result = solve(board);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                board[i][j] = result[i][j];
            }
        }
    }


    public char[][] solve(char[][] boardArray) {

        Board board = Board.of(boardArray);
        board.print("Begin");
        board.isValid();

        board = solve(board);

        board.print();
        System.out.println("Finished" + (board.isComplete() ? "" : " NOT Complete !!"));

        return board.asArray();
    }

    protected Board solve(Board board) {

        computeMaybeAllRows(board);
        computeMaybeAllColumns(board);
        computeMaybeAllGrids(board);

        processAllMaybe(board);

        if (!board.isComplete()) {
            board = randomGuess(board);
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



    public static class Board {

        private Map<Pos, Cell> cellMap;
        private SudokuValidator validator;

        private static final Set<Character> VALID_VALUES = Collections.unmodifiableSet(new TreeSet<>(
                Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9')));

        private static final int[] GRID_ROW_OFFSET = new int[]{0, 1, 1, 1, 4, 4, 4, 7, 7, 7};
        private static final int[] GRID_COL_OFFSET = new int[]{0, 1, 4, 7, 1, 4, 7, 1, 4, 7};
        private static final int[][] GRID_BY_POS = new int[10][10];
        static {
            GRID_BY_POS[0] = new int[]{0,0,0,0,0,0,0,0,0,0};
            GRID_BY_POS[1] = new int[]{0,1,1,1,2,2,2,3,3,3};
            GRID_BY_POS[2] = new int[]{0,1,1,1,2,2,2,3,3,3};
            GRID_BY_POS[3] = new int[]{0,1,1,1,2,2,2,3,3,3};
            GRID_BY_POS[4] = new int[]{0,4,4,4,5,5,5,6,6,6};
            GRID_BY_POS[5] = new int[]{0,4,4,4,5,5,5,6,6,6};
            GRID_BY_POS[6] = new int[]{0,4,4,4,5,5,5,6,6,6};
            GRID_BY_POS[7] = new int[]{0,7,7,7,8,8,8,9,9,9};
            GRID_BY_POS[8] = new int[]{0,7,7,7,8,8,8,9,9,9};
            GRID_BY_POS[9] = new int[]{0,7,7,7,8,8,8,9,9,9};
        }

        private Map<Integer, List<Cell>> rowCache = new HashMap<>();
        private Map<Integer, List<Cell>> columnCache = new HashMap<>();
        private Map<Integer, List<Cell>> gridCache = new HashMap<>();


        private Board() {
            this.cellMap = new TreeMap<>();
            this.validator = new SudokuValidator();
        }

        public static Board of(char[][] boardArray) {
            Board board = new Board();

            for (int i = 0; i < boardArray.length; i++) {
                for (int j = 0; j < boardArray[i].length; j++) {
                    board.add(Cell.of(i + 1, j + 1, boardArray[i][j]));
                }
            }

            return board;
        }

        public int size() {
            return cellMap.values().size();
        }

        public void add(Cell cell) {
            if (cellMap.containsKey(cell.pos)) throw new IllegalArgumentException("already exists");
            cellMap.put(cell.pos, cell);
        }

        public Cell getCell(Pos pos) {
            if (cellMap.containsKey(pos)) {
                return cellMap.get(pos);
            } else {
                throw new IllegalArgumentException("not found");
            }
        }

        public Cell getCell(int row, int col) {
            return getCell(Pos.of(row, col));
        }

        public Collection<Cell> getAllCells() {
            return cellMap.values();
        }

        public List<Cell> getRow(int row) {
            if (rowCache.containsKey(row)) {
                return rowCache.get(row);
            } else {
                List<Cell> cells = new ArrayList<>();
                for (int col = 1; col < 10; col++) {
                    cells.add(getCell(row, col));
                }
                rowCache.put(row, cells);
                return cells;
            }
        }

        public List<Cell> getColumn(int col) {
            if (columnCache.containsKey(col)) {
                return columnCache.get(col);
            } else {
                List<Cell> cells = new ArrayList<>();
                for (int row = 1; row < 10; row++) {
                    cells.add(getCell(row, col));
                }
                columnCache.put(col, cells);
                return cells;
            }
        }

        public List<Cell> getGrid(Pos pos) {
            return getGrid(GRID_BY_POS[pos.row][pos.col]);
        }

        public List<Cell> getGrid(int g) {
            if (gridCache.containsKey(g)) {
                return gridCache.get(g);
            } else {
                List<Cell> cells = new ArrayList<>();
                int rowStart = GRID_ROW_OFFSET[g];
                int colStart = GRID_COL_OFFSET[g];

                for (int row = rowStart; row < rowStart + 3; row++) {
                    for (int col = colStart; col < colStart + 3; col++) {
                        cells.add(getCell(row, col));
                    }
                }
                gridCache.put(g, cells);
                return cells;
            }
        }

        public Set<Character> getMissingForRow(int row) {
            Set<Character> missing = copyValidValues();
            for (Cell cell : getRow(row)) {
                cell.val.ifPresent(missing::remove);
            }
            return missing;
        }

        public Set<Character> getMissingForColumn(int col) {
            Set<Character> missing = copyValidValues();
            for (Cell cell : getColumn(col)) {
                cell.val.ifPresent(missing::remove);
            }
            return missing;
        }

        public Set<Character> getMissingForGrid(int g) {
            Set<Character> missing = copyValidValues();
            for (Cell cell : getGrid(g)) {
                cell.val.ifPresent(missing::remove);
            }
            return missing;
        }

        public Set<Character> copyValidValues() {
            return VALID_VALUES.stream().collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
        }

        public char[][] asArray() {
            char[][] boardArray = new char[9][9];

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    Cell cell = getCell(i + 1, j + 1);
                    boardArray[i][j] = cell.val.orElse('.');
                }
            }
            return boardArray;
        }

        public long remaining() {
            return cellMap.values().stream()
                    .filter(cell -> !cell.val.isPresent())
                    .count();
        }

        public boolean isComplete() {
            return isValid() && remaining() == 0;
        }

        public boolean isValid() {
            if (validator.isValidSudoku(this)) {
                return true;
            } else {
                print("!! Board is invalid !!");
                print();
                throw new RuntimeException("Invalid board");
            }
        }

        public Board deepCopy() {
            Board duplicate = new Board();
            for (Cell original : getAllCells()) {
                duplicate.add(original.deepCopy());
            }
            return duplicate;
        }

        /*
         * print without maybe info
         */
        public void print() {
            char[][] boardArray = asArray();
            StringBuilder sb = new StringBuilder();
            sb.append("\n -------------------------\n");

            for (int i = 0; i < boardArray.length; i++) {
                for (int j = 0; j < boardArray[i].length; j++) {
                    if (j % 3 == 0) sb.append(" |");
                    sb.append(' ').append(boardArray[i][j]);
                }
                sb.append(" |");
                if ((i + 1) % 3 == 0) sb.append("\n -------------------------");
                sb.append('\n');
            }

            System.out.println(sb.toString());
        }

        public void print(String msg) {
            StringBuilder sb = new StringBuilder();
            if (!msg.isEmpty()) {
                sb.append(msg).append(", remaining: ").append(remaining());
            } else {
                sb.append("remaining: ").append(remaining());
            }

            long maxMaybeSize = maxMaybeSize();
            horizontalLine(sb, maxMaybeSize);
            sb.append('\n');

            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 10; j++) {
                    if ((j - 1) % 3 == 0) sb.append(" |");
                    Cell cell = cellMap.get(Pos.of(i, j));
                    sb.append(' ').append(cell.val.orElse('.'));


                    if (cell.maybe.isEmpty()) {
                        for (int k = 0; k < maxMaybeSize; k++) {
                            sb.append(' ');
                        }
                    } else {
                        for (Character m : cell.maybe) {
                            sb.append(m);
                        }
                        for (int k = 0; k < (maxMaybeSize - cell.maybe.size()); k++) {
                            sb.append(' ');
                        }
                    }
                }
                sb.append(" |");
                if (i % 3 == 0) horizontalLine(sb, maxMaybeSize);
                sb.append('\n');
            }

            System.out.println(sb.toString());
        }

        private void horizontalLine(StringBuilder sb, long maxMaybeSize) {
            sb.append("\n -------------------------");
            for (int i = 0; i < (maxMaybeSize * 9); i++) {
                sb.append('-');
            }
        }

        private long maxMaybeSize() {
            return cellMap.values().stream()
                    .map(c -> c.maybe.size())
                    .max(Comparator.comparing(Integer::valueOf))
                    .orElse(0);
        }

        public void printMaybeByValue() {
            for (Character c : copyValidValues()) {
                StringBuilder sb = new StringBuilder("Maybe: " + c);

                long maxMaybeSize = 1;
                horizontalLine(sb, maxMaybeSize);
                sb.append('\n');

                for (int i = 1; i < 10; i++) {
                    for (int j = 1; j < 10; j++) {
                        if ((j - 1) % 3 == 0) sb.append(" |");
                        Cell cell = cellMap.get(Pos.of(i, j));

                        if (cell.val.isPresent() && cell.val.get().equals(c)) {
                            sb.append(' ').append(cell.val.get()).append(' ');
                        } else {
                            if (cell.maybe.contains(c)) {
                                sb.append(" ? ");
                            } else {
                                sb.append(" . ");
                            }
                        }
                    }
                    sb.append(" |");
                    if (i % 3 == 0) horizontalLine(sb, maxMaybeSize);
                    sb.append('\n');
                }
                System.out.println(sb.toString());
            }
        }
    }

    public static class Cell implements Comparable<Cell> {

        Pos pos;
        Optional<Character> val;
        TreeSet<Character> maybe;

        private Cell(Pos pos, Optional<Character> val) {
            this.pos = pos;
            this.val = val;
            this.maybe = new TreeSet<>();
        }

        public static Cell of(int row, int col, char c) {
            return new Cell(
                    Pos.of(row, col),
                    c == '.' ? Optional.empty() : Optional.of(c));
        }

        public void setVal(Character c) {
            if (c == null) {
                this.val = Optional.empty();
            } else {
                this.val = Optional.of(c);
                this.maybe.clear();
            }
        }

        public Cell deepCopy() {
            char dupVal = this.val.orElse('.');
            Cell dupCell = Cell.of(this.pos.row, this.pos.col, dupVal);
            dupCell.maybe.addAll(this.maybe);
            return dupCell;
        }


        public String toString() {
            return "cell:" + pos.toString() + " val=" + val.orElse('?') +
                    (maybe.isEmpty() ? "" : " " + maybe.toString());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell other = (Cell) o;
            return this.pos.equals(other.pos);
        }

        @Override
        public int hashCode() {
            return pos.hashCode();
        }

        @Override
        public int compareTo(Cell other) {
            return this.hashCode() - other.hashCode();
        }
    }


    /**
     * Position class
     */
    public static class Pos implements Comparable<Pos> {
        int row;
        int col;

        private Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public static Pos of(int row, int col) {
            return new Pos(row, col);
        }

        public String toString() {
            return "(" + row + ", " + col + ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos other = (Pos) o;
            return row == other.row &&
                    col == other.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public int compareTo(Pos other) {
            return this.hashCode() - other.hashCode();
        }
    }


    public static class SudokuValidator {

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
                    if (count[digit] > 1) {
                        System.out.println("*** duplicate value found: " + cell);
                        System.out.println("*** cell list: " + cellList);
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
