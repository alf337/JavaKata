package alf.exercises.leetcode.sudoku;

import java.util.Objects;

/**
 * Position class
 */
public class Pos implements Comparable<Pos> {
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