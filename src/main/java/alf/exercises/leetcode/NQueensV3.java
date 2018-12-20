package alf.exercises.leetcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * try changing recursive call to iterative loop
 */
public class NQueensV3 {

    int boardSize = 0;

    /**
     * this one returns a pretty print format for leetcode
     */
    public List<List<String>> solveNQueens(int n) {

        this.boardSize = n;
        List<Set<Pos>> boards = queens(n);
        List<List<String>> result = formatAllBoards(boards);
        System.out.println(result);
        return result;
    }

    public int totalNQueens(int n) {

        this.boardSize = n;
        List<Set<Pos>> boards = queens(n);
        printAllBoards(boards);

        return boards.size();
    }

    protected List<Set<Pos>> queens(int n) {

        List<Set<Pos>> boards = emptyBoardList();

        for (int i = 1; i <= n; i++) {
            boards = queenCols(boards, i);
        }
        return boards;
    }

    protected List<Set<Pos>> queenCols(List<Set<Pos>> boards, int k) {

        return boards.stream()
                .flatMap(setOfPositions ->
                        IntStream.rangeClosed(1, boardSize)
                                .mapToObj(row -> adjoinPosition(row, k, setOfPositions)))
                .filter(setOfPositions -> isSafe(k, setOfPositions))
                .collect(Collectors.toList());
    }

    protected boolean isSafe(int k, Set<Pos> positions) {

        Pos pos = positions.stream()
                .filter(p -> p.col == k)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        Set<Pos> others = positions.stream()
                .filter(p -> p.col != k)
                .collect(Collectors.toSet());

        return isSafeRow(pos, others)
                && isSafeColumn(pos, others)
                && isSafeDiagonal(pos, others);
    }

    protected boolean isSafeRow(Pos pos, Set<Pos> others) {
        return others.stream()
                .noneMatch(other -> other.row == pos.row);
    }

    protected boolean isSafeColumn(Pos pos, Set<Pos> others) {
        return others.stream()
                .noneMatch(other -> other.col == pos.col);
    }

    protected boolean isSafeDiagonal(Pos pos, Set<Pos> others) {
        Set<Pos> diagonals = generateDiagonals(pos);
        return others.stream()
                .noneMatch(diagonals::contains);
    }

    protected Set<Pos> generateDiagonals(Pos pos) {

        Set<Pos> diagonals = new TreeSet<>();

        for (int r = pos.row - 1, c = pos.col - 1; r >= 1; r--, c--) {
            diagonals.add(Pos.of(r, c));
        }

        for (int r = pos.row + 1, c = pos.col - 1; r <= boardSize; r++, c--) {
            diagonals.add(Pos.of(r, c));
        }
        return diagonals;
    }

    protected List<Set<Pos>> emptyBoardList() {
        List<Set<Pos>> emptyList = new ArrayList<>();
        emptyList.add(new TreeSet<>());
        return emptyList;
    }

    protected Set<Pos> adjoinPosition(int row, int k, Set<Pos> setOfPositions) {
        Set<Pos> resultSetOfPos = new TreeSet<>(setOfPositions);
        resultSetOfPos.add(Pos.of(row, k));
        return resultSetOfPos;
    }

    private List<List<String>> formatAllBoards(List<Set<Pos>> allBoards) {
        List<List<String>> result = new ArrayList<>();
        if (allBoards.isEmpty()) {
            return result;
        } else {
            allBoards.forEach(b -> result.add(formatBoard(b)));
            return result;
        }
    }

    private List<String> formatBoard(Set<Pos> posSet) {
        List<String> rowList = new ArrayList<>();

        for (int i = 1; i <= boardSize; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j <= boardSize; j++) {
                if (posSet.contains(Pos.of(i, j))) {
                    sb.append("Q");
                } else {
                    sb.append(".");
                }
            }
            rowList.add(sb.toString());
        }
        return rowList;
    }

    private void printAllBoards(List<Set<Pos>> boards) {
        if (boards.isEmpty()) {
            System.out.println("\nboard list is empty");
        } else {
            System.out.println("\nprinting all boards");
            boards.forEach(b -> printBoard(b));
        }
    }

    private void printBoard(Set<Pos> posSet) {
        System.out.println("\n" + posSet);
        StringBuilder sb = new StringBuilder("\n    ");
        IntStream.rangeClosed(1, boardSize).forEach(i -> sb.append(i).append(' '));
        sb.append("\n  |-");
        IntStream.rangeClosed(1, boardSize).forEach(i -> sb.append("--"));
        sb.append('|');

        for (int i = 1; i <= boardSize; i++) {
            sb.append("\n").append(i).append(" | ");

            for (int j = 1; j <= boardSize; j++) {
                if (posSet.contains(Pos.of(i, j))) {
                    sb.append("Q ");
                } else {
                    sb.append(". ");
                }
            }
            sb.append('|');
        }
        sb.append("\n  |-");
        IntStream.rangeClosed(1, boardSize).forEach(i -> sb.append("--"));
        sb.append('|');
        System.out.println(sb.toString());
    }

    /**
     * Position class
     */
    public static class Pos implements Comparable<Pos> {
        int row;
        int col;

        Pos(int row, int col) {
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
}