package alf.exercises.leetcode.sudoku;

import java.util.Optional;
import java.util.TreeSet;

public class Cell implements Comparable<Cell> {

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
                c == '.' || c == '0' ? Optional.empty() : Optional.of(c));
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
