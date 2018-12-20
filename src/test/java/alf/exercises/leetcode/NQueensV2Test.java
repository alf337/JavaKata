package alf.exercises.leetcode;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static alf.exercises.leetcode.NQueensV2.*;
import static org.junit.Assert.*;

public class NQueensV2Test {
    NQueensV2 ex = new NQueensV2();

    @Test
    public void totalNQueens0() {
        assertEquals(1, ex.totalNQueens(0));
        List<Set<Pos>> result = ex.queenCols(0);
        assertTrue(result.get(0).isEmpty());
    }
    @Test
    public void totalNQueens() {

        assertEquals(1,ex.totalNQueens(1));
        assertEquals(0,ex.totalNQueens(2));
        assertEquals(0,ex.totalNQueens(3));
        assertEquals(2,ex.totalNQueens(4));
        assertEquals(10,ex.totalNQueens(5));
        assertEquals(4,ex.totalNQueens(6));
        assertEquals(92,ex.totalNQueens(8));
    }

    @Test
    public void isSafe() {

        ex.boardSize = 4;
        assertTrue(ex.isSafe(4, positions(1, 2, 3, 1, 2, 4)));
        assertFalse(ex.isSafe(4, positions(1, 2, 3, 3, 2, 4)));
    }

    @Test
    public void isSafeRow() {

        assertTrue(ex.isSafeRow(Pos.of(3, 3), positions(1, 1, 2, 2)));
        assertFalse(ex.isSafeRow(Pos.of(3, 3), positions(1, 1, 3, 2)));
    }
    @Test
    public void isSafeColumn() {

        assertTrue(ex.isSafeColumn(Pos.of(3, 3), positions(1, 1, 2, 2)));
        assertFalse(ex.isSafeColumn(Pos.of(3, 3), positions(1, 1, 2, 3)));
    }

    @Test
    public void isSafeDiagonal() {

        ex.boardSize = 4;
        assertTrue(ex.isSafeDiagonal(Pos.of(2, 4), positions(1, 2, 4, 3)));
        assertFalse(ex.isSafeDiagonal(Pos.of(2, 4), positions(1, 2, 3, 3)));
        assertFalse(ex.isSafeDiagonal(Pos.of(2, 2), positions(3, 1)));
        assertFalse(ex.isSafeDiagonal(Pos.of(3, 3), positions(4, 2)));
        ex.boardSize = 6;
        assertFalse(ex.isSafeDiagonal(Pos.of(5, 2), positions(6, 1)));
        assertFalse(ex.isSafeDiagonal(Pos.of(1, 4), positions(3, 2)));
    }

    @Test
    public void generateDiagonals() {

        ex.boardSize = 4;
        Set<Pos> diags = ex.generateDiagonals(Pos.of(2, 4));
        System.out.println(diags);
        assertEquals(3, diags.size());
        assertTrue(diags.contains(Pos.of(1, 3)));
        assertTrue(diags.contains(Pos.of(3, 3)));
        assertTrue(diags.contains(Pos.of(4, 2)));

        ex.boardSize = 6;
        diags = ex.generateDiagonals(Pos.of(5, 2));
        System.out.println(diags);
        assertTrue(diags.contains(Pos.of(4, 1)));
        assertTrue(diags.contains(Pos.of(6, 1)));
    }

    Set<Pos> positions(int... p) {
        Set<Pos> board = new HashSet<>();
        for (int i = 0; i < p.length; i=i+2) {
            board.add(Pos.of(p[i], p[i+1]));
        }
        return board;
    }

    @Test
    public void emptyBoardList() {
        List<Set<Pos>> result = ex.emptyBoardList();
        assertEquals("java.util.ArrayList", result.getClass().getName());
        assertEquals(1, result.size());
        assertTrue(result.get(0).isEmpty());
    }

    @Test
    public void adjoinPosition() {
        Set<Pos> r1 = ex.adjoinPosition(1, 2, ex.emptyBoardList().get(0));
        System.out.println(r1);
        assertEquals(1, r1.size());
        assertTrue(r1.contains(Pos.of(1, 2)));

        Set<Pos> r2 = ex.adjoinPosition(2, 3, r1);
        System.out.println(r2);
        assertEquals(2, r2.size());
        assertTrue(r2.contains(Pos.of(1, 2)));
        assertTrue(r2.contains(Pos.of(2, 3)));
    }

    @Test
    public void isConflictDiagonal() {

        assertTrue(ex.isConflictDiagonal(Pos.of(1,3), Pos.of(2,2)));
        assertTrue(ex.isConflictDiagonal(Pos.of(1,3), Pos.of(2,4)));
        assertTrue(ex.isConflictDiagonal(Pos.of(1,3), Pos.of(3,5)));
        assertTrue(ex.isConflictDiagonal(Pos.of(1,3), Pos.of(3,1)));
        assertTrue(ex.isConflictDiagonal(Pos.of(3,4), Pos.of(1,2)));

        assertFalse(ex.isConflictDiagonal(Pos.of(1,3), Pos.of(2,1)));
        assertFalse(ex.isConflictDiagonal(Pos.of(2,1), Pos.of(1,3)));
        assertFalse(ex.isConflictDiagonal(Pos.of(1,3), Pos.of(3,2)));
        assertFalse(ex.isConflictDiagonal(Pos.of(1,3), Pos.of(4,4)));
        assertFalse(ex.isConflictDiagonal(Pos.of(4,3), Pos.of(1,1)));
        assertFalse(ex.isConflictDiagonal(Pos.of(4,3), Pos.of(2,4)));
    }

    @Test
    public void isSafeV2() {

        assertTrue(ex.isSafeV2(positions(1,3, 2,1)));
        assertFalse(ex.isSafeV2(positions(1,3, 2,3)));
        assertFalse(ex.isSafeV2(positions(1,3, 1,1)));

        // diagonals
        assertFalse(ex.isSafeV2(positions(1,3, 2,2)));
        assertFalse(ex.isSafeV2(positions(1,3, 2,4)));
        assertFalse(ex.isSafeV2(positions(1,3, 3,5)));
        assertFalse(ex.isSafeV2(positions(1,3, 3,1)));
        assertFalse(ex.isSafeV2(positions(3,4, 1,2)));

        assertTrue(ex.isSafeV2(positions(1,3, 3,2)));
        assertTrue(ex.isSafeV2(positions(1,3, 4,4)));
        assertTrue(ex.isSafeV2(positions(4,3, 1,1)));
        assertTrue(ex.isSafeV2(positions(4,3, 2,4)));
    }

}