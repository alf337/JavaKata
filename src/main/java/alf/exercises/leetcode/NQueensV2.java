package alf.exercises.leetcode;

import alf.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The n-queens puzzle is the problem of placing n queens on
 * an n×n chessboard such that no two queens attack each other.
 *
 * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
 *
 * Example:
 *
 * Input: 4
 * Output: 2
 * Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
 * [
 *  [".Q..",  // Solution 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // Solution 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 */
public class NQueensV2 {

    Map<Pair, Boolean> diagonalCacheV2 = new HashMap<>();
    int boardSize = 0;

    public int totalNQueens(int n) {

        return queens(n).size();
    }

    protected List<Set<Pos>> queens(int n) {

        this.diagonalCacheV2.clear();
        this.boardSize = n;

        List<Set<Pos>> boards = queenCols(n);

//        printAllBoards(boards);
        return boards;
    }

    protected List<Set<Pos>> queenCols(int k) {

        if (k == 0) return emptyBoardList();

        return queenCols(k - 1).stream()
                .flatMap( setOfPositions ->
                    IntStream.rangeClosed(1, boardSize)
                            .mapToObj( row -> adjoinPosition(row, k, setOfPositions)))
                .filter( setOfPositions -> isSafe(k, setOfPositions))
//                .filter(this::isSafeV2)
                .collect(Collectors.toList());
    }

    protected boolean isSafe(int k, Set<Pos> positions) {

        Pos pos = positions.stream()
                .filter( p -> p.col == k)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        Set<Pos> others = positions.stream()
                .filter( p -> p.col != k)
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


    protected boolean isSafeV2(Set<Pos> positions) {

        return positions.stream()
                .flatMap( a -> positions.stream()
                        .filter( b -> !a.equals(b)) // exclude self
                        .filter( b -> isConflict(a, b))
                )
                .noneMatch( x -> true);
    }

    protected boolean isConflict(Pos a, Pos b) {
        return (a.row == b.row
                || a.col == b.col
                || isConflictDiagonal(a, b));
    }

    protected boolean isConflictDiagonal(Pos a, Pos b) {
        Pair<Pos, Pos> posPair = Pair.of(a, b);

        if (diagonalCacheV2.containsKey(posPair)) {
            return diagonalCacheV2.get(posPair);
        }

        boolean result;

        if (a.row > b.row && a.col > b.col) {
            result = checkNorthWest(a, b);

        } else if (a.row > b.row && a.col < b.col) {
            result = checkNorthEast(a, b);

        } else if (a.row < b.row && a.col > b.col) {
            result = checkSouthWest(a, b);

        } else {
            result = checkSouthEast(a, b);
        }

        diagonalCacheV2.put(posPair, result);
        return result;
    }

    private boolean checkNorthWest(Pos a, Pos b) {
        int r = a.row - 1;
        int c = a.col - 1;
        while (r >= b.row && c >= b.col) {
            if (r == b.row && c == b.col) return true;
            r--;
            c--;
        }
        return false;
    }

    private boolean checkNorthEast(Pos a, Pos b) {
        int r = a.row - 1;
        int c = a.col + 1;
        while (r >= b.row && c <= b.col) {
            if (r == b.row && c == b.col) return true;
            r--;
            c++;
        }
        return false;
    }

    private boolean checkSouthWest(Pos a, Pos b) {
        int r = a.row + 1;
        int c = a.col - 1;
        while (r <= b.row && c >= b.col) {
            if (r == b.row && c == b.col) return true;
            r++;
            c--;
        }
        return false;
    }

    private boolean checkSouthEast(Pos a, Pos b) {
        int r = a.row + 1;
        int c = a.col + 1;
        while (r <= b.row && c <= b.col) {
            if (r == b.row && c == b.col) return true;
            r++;
            c++;
        }
        return false;
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
        IntStream.rangeClosed(1, boardSize).forEach( i -> sb.append(i).append(' '));
        sb.append("\n  |-");
        IntStream.rangeClosed(1, boardSize).forEach( i -> sb.append("--"));
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
        IntStream.rangeClosed(1, boardSize).forEach( i -> sb.append("--"));
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
                    col == other.col ;
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