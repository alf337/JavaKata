package alf.exercises.leetcode;

import java.util.*;
import java.util.function.Predicate;
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
public class NQueens {

    public int totalNQueens(int n) {

        Set<Board> resultSet = IntStream.range(0,n)
                .boxed()
                .flatMap( i -> IntStream.range(0, n)
                        .mapToObj( j -> findValidSolution(n, i, j)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        System.out.println(resultSet);
        return resultSet.size();
    }

    private Optional<Board> findValidSolution(int n, int startRow, int startColumn) {

        Board b = new Board(n);
        Loc currentLoc = b.setOccupied(startRow, startColumn);
        int remain = n -1;

        while (remain > 0) {
            Optional<Loc> available = nextAvailable(b, currentLoc);
            if (available.isPresent()) {
                b.setOccupied(available.get());
                currentLoc = available.get();
            } else {
                return Optional.empty();
            }
            remain--;
        }
        return Optional.of(b);
    }

    private Optional<Loc> nextAvailable(Board b, Loc l) {
        Collection<Loc> available = b.getAvailable();
        Optional<Loc> next = available.stream()
                .filter( it -> it.compareTo(l) > 0)
                .findFirst();

        if (next.isPresent()) {
            return next;
        } else {
            Optional<Loc> prev = available.stream()
                    .filter( it -> it.compareTo(l) < 0)
                    .reduce( (l1, l2) -> l2);

            if (prev.isPresent()) {
                return prev;
            } else {
                return available.stream().findFirst();
            }
        }
    }

    /**
     * Loc class
     */
    public static class Loc implements Comparable<Loc> {
        int row;
        int col;
        boolean occupied = false;
        boolean attacked = false;

        public Loc(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public static Loc of(int row, int col) {
            return new Loc(row, col);
        }

        public String toString() {
            return "[" + row + ", " + col + ", " +
                    (occupied ? "Q" : (attacked ? "* " : ".")) + ']';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Loc loc = (Loc) o;
            return row == loc.row &&
                    col == loc.col &&
                    occupied == loc.occupied &&
                    attacked == loc.attacked;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col, occupied, attacked);
        }

        @Override
        public int compareTo(Loc other) {
            return this.hashCode() - other.hashCode();
        }
    }

    /**
     * Board class
     */
    public static class Board {
        Collection<Loc> allLocations;
        int size;
        Predicate<Loc> availablePredicate = loc -> !loc.occupied && !loc.attacked;

        public Board(int size) {

            if (size < 1) throw new IllegalArgumentException("size must be >= 1");
            this.size = size;

            this.allLocations = IntStream.range(0, size)
                    .boxed()
                    .flatMap(row -> IntStream.range(0, size)
                            .mapToObj(col -> Loc.of(row, col)))
                    .collect(TreeSet::new,
                            TreeSet::add,
                            TreeSet::addAll);
        }

        public Loc getLoc(int row, int col) {
            if (col < 0 || col >= size || row < 0 || row >= size)
                throw new IllegalArgumentException("loc must be between (0, 0) and (" + (size - 1) +", " + (size - 1) +")");

            return allLocations.stream()
                    .filter( loc -> loc.col == col && loc.row == row)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("not found: " + row + ", " + col));
        }

        public Loc setOccupied(int row, int col) {
            Loc loc = getLoc(row, col);
            return setOccupied(loc);
        }

        public Loc setOccupied(Loc loc) {
            if (loc.occupied) throw new IllegalStateException("already occupied: " + loc);
            if (loc.attacked) throw new IllegalStateException("under attack: " + loc);
            loc.occupied = true;
            getAttacks(loc).forEach( att -> setAttacked(att));
            return loc;
        }

        public Loc setAttacked(int row, int col) {
            Loc loc = getLoc(row, col);
            return setAttacked(loc);
        }

        public Loc setAttacked(Loc loc) {
            if (loc.occupied) throw new IllegalStateException("already occupied: " + loc);
            loc.attacked = true;
            return loc;
        }

        public Collection<Loc> getUnoccupied() {
            return allLocations.stream()
                    .filter( loc -> !loc.occupied)
                    .collect(TreeSet::new,
                            TreeSet::add,
                            TreeSet::addAll);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("\n");
            IntStream.range(0, size)
                    .mapToObj(this::getRow)
                    .forEach( row -> {
                        row.forEach(loc -> sb.append(loc.occupied ? "Q " : (loc.attacked ? "* " : ". ")));
                        sb.append('\n');
                    });

            return sb.toString();
        }

        public Collection<Loc> getRow(int row) {
            if (row < 0 || row >= size) throw new IllegalArgumentException("row must be between 0 and " + (size - 1));
            return allLocations.stream()
                    .filter((loc) -> loc.row == row)
                    .collect(TreeSet::new,
                            TreeSet::add,
                            TreeSet::addAll);
        }

        public Collection<Loc> getColumn(int col) {
            if (col < 0 || col >= size) throw new IllegalArgumentException("col must be between 0 and " + (size - 1));
            return allLocations.stream()
                    .filter((loc) -> loc.col == col)
                    .collect(TreeSet::new,
                            TreeSet::add,
                            TreeSet::addAll);
        }

        public Collection<Loc> getDiagonals(Loc loc) {
            Collection<Loc> diagonalLocs = new TreeSet<>();
            int r = loc.row + 1;
            int c = loc.col + 1;

            while (r < size && c < size) {
                diagonalLocs.add(getLoc(r++, c++));
            }

            r = loc.row - 1;
            c = loc.col - 1;
            while (r >= 0 && c >= 0) {
                diagonalLocs.add(getLoc(r--, c--));
            }

            r = loc.row + 1;
            c = loc.col - 1;
            while (r < size && c >= 0) {
                diagonalLocs.add(getLoc(r++, c--));
            }

            r = loc.row - 1;
            c = loc.col + 1;
            while (r >= 0 && c < size) {
                diagonalLocs.add(getLoc(r--, c++));
            }
            return diagonalLocs;
        }

        public Collection<Loc> getAttacks(Loc loc) {
            Collection<Loc> locsAttacked = getDiagonals(loc);
            locsAttacked.addAll(getRow(loc.row));
            locsAttacked.addAll(getColumn(loc.col));
            locsAttacked.remove(loc);
            return locsAttacked;
        }

        public Collection<Loc> getWithFilter(Predicate<Loc> predicate) {
            return allLocations.stream()
                    .filter(predicate)
                    .collect(TreeSet::new,
                            TreeSet::add,
                            TreeSet::addAll);
        }

        public Collection<Loc> getAvailable() {
            return getWithFilter(availablePredicate);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Board board = (Board) o;
            return allLocations.equals(board.allLocations);
        }

        @Override
        public int hashCode() {
            return Objects.hash(allLocations);
        }
    }
}
