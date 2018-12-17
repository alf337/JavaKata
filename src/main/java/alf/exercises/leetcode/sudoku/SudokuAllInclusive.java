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
        int attempts = solve(board);
        board.print("Finished, attempts = " + attempts);

        return board.asArray();
    }

    protected int solve(Board board) {
        int attempts = 0;
        while (attempts < 10 && !board.isComplete()) {

            evalAllRows(board);
            evalAllColumns(board);
            evalAllGrids(board);
            attempts++;
            System.out.println("after attempt: " + attempts + " remaining:" + board.remaining());
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

    //************************************8
    public static class Board {

        private Map<Pos, Cell> map;
        private SudokuValidator validator;

        private static final Set<Character> ALL_VALUES = Collections.unmodifiableSet(new TreeSet<>(
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
            this.map = new TreeMap<>();
            this.validator = new SudokuValidator();
        }

        public static Board of(char[][] boardArray) {
            Board board = new Board();
            if (!board.validator.isValidSudoku(boardArray)) throw new IllegalArgumentException("invalid board");

            for (int i = 0; i < boardArray.length; i++) {
                for (int j = 0; j < boardArray[i].length; j++) {
                    board.add(Cell.of(i + 1, j + 1, boardArray[i][j]));
                }
            }

            return board;
        }

        public void add(Cell cell) {
            if (map.containsKey(cell.pos)) throw new IllegalArgumentException("already exists");
            map.put(cell.pos, cell);
        }

        public Cell getCell(Pos pos) {
            if (map.containsKey(pos)) {
                return map.get(pos);
            } else {
                throw new IllegalArgumentException("not found");
            }
        }

        public Cell getCell(int row, int col) {
            return getCell(Pos.of(row, col));
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
            Set<Character> missing = copyAllValues();
            for (Cell cell : getRow(row)) {
                cell.val.ifPresent(missing::remove);
            }
            return missing;
        }

        public Set<Character> getMissingForColumn(int col) {
            Set<Character> missing = copyAllValues();
            for (Cell cell : getColumn(col)) {
                cell.val.ifPresent(missing::remove);
            }
            return missing;
        }

        public Set<Character> getMissingForGrid(int g) {
            Set<Character> missing = copyAllValues();
            for (Cell cell : getGrid(g)) {
                cell.val.ifPresent(missing::remove);
            }
            return missing;
        }

        private Set<Character> copyAllValues() {
            return ALL_VALUES.stream().collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
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

        public void print() {
            print("");
        }

        public long remaining() {
            return map.values().stream()
                    .filter(cell -> !cell.val.isPresent())
                    .count();
        }

        public boolean isComplete() {
            return remaining() == 0;
        }

        public boolean isValid() {
            return validator.isValidSudoku(this.asArray());
        }

        public void print(String msg) {
            char[][] boardArray = asArray();
            StringBuilder sb = new StringBuilder();
            if (!msg.isEmpty()) {
                sb.append(msg).append(", remaining: " + remaining());
            } else {
                sb.append("remaining: " + remaining());
            }
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
    }

    //************************************8

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
            }
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

    //************************************8
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
    //************************************8
    public static class SudokuValidator {

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
}
