package alf.exercises.leetcode.sudoku;

import java.util.*;

public class Board {

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
