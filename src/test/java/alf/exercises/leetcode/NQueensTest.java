package alf.exercises.leetcode;

import org.junit.Test;

import java.util.Collection;

import static alf.exercises.leetcode.NQueens.*;
import static org.junit.Assert.*;

public class NQueensTest {

    NQueens ex = new NQueens();

    @Test
    public void totalNQueens() {

        assertEquals(1, ex.totalNQueens(1));
        assertEquals(0, ex.totalNQueens(2));
        assertEquals(0, ex.totalNQueens(3));
        assertEquals(2, ex.totalNQueens(4));
        assertEquals(10, ex.totalNQueens(5));
    }

    @Test
    public void testBoard() {
        Board b = new Board(2);
        System.out.println("\nBoard=" + b);

        System.out.println("\nRow=" + b.getRow(1));
        System.out.println("\nCol=" + b.getColumn(0));
        System.out.println("\nLoc=" + b.getLoc(1,1));

        b.setOccupied(1,1);
        System.out.println("\nBoard=" + b);
        System.out.println("\nLoc=" + b.getLoc(1,1));
    }

    @Test
    public void testDiagonals() {
        Board b = new Board(5);
        System.out.println(b.getDiagonals(b.getLoc(2, 2)));
        System.out.println(b.getDiagonals(b.getLoc(3, 0)));
        System.out.println(b.getDiagonals(b.getLoc(3, 4)));
    }

    @Test
    public void testAttacks() {
        Board b = new Board(5);
        Loc l22 = b.setOccupied(2,2);
        Collection<Loc> att = b.getAttacks(l22);
        System.out.println("Attacks for " + l22 + " -> " + att);
        assertEquals(16, att.size());
        assertFalse(att.contains(b.getLoc(2,2)));
        assertTrue(att.contains(b.getLoc(0,0)));
        assertTrue(att.contains(b.getLoc(0,2)));
        assertTrue(att.contains(b.getLoc(0,4)));
        assertTrue(att.contains(b.getLoc(1,1)));
        assertTrue(att.contains(b.getLoc(1,2)));
        assertTrue(att.contains(b.getLoc(1,3)));
        assertTrue(att.contains(b.getLoc(2,0)));
        assertTrue(att.contains(b.getLoc(2,1)));
        assertTrue(att.contains(b.getLoc(2,3)));
        assertTrue(att.contains(b.getLoc(2,4)));
        assertTrue(att.contains(b.getLoc(3,1)));
        assertTrue(att.contains(b.getLoc(3,2)));
        assertTrue(att.contains(b.getLoc(3,3)));
        assertTrue(att.contains(b.getLoc(4,0)));
        assertTrue(att.contains(b.getLoc(4,2)));
        assertTrue(att.contains(b.getLoc(4,4)));

        System.out.println(b);
    }

    @Test
    public void testGetAllAttacked() {
        Board b = new Board(4);
        System.out.println(b);
        Loc l10 = b.setOccupied(0,1);
        System.out.println(b);

        Loc l20 = b.setOccupied(2,0);
        System.out.println(b);
    }
}