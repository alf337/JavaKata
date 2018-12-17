package alf.exercises.leetcode.sudoku;

import java.util.*;

public class Board {

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
