package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class NQueensV3Test {

    NQueensV3 ex = new NQueensV3();

    @Test
    public void totalNQueens() {

        assertEquals(1, ex.totalNQueens(1));
        assertEquals(0, ex.totalNQueens(2));
        assertEquals(0, ex.totalNQueens(3));
        assertEquals(2, ex.totalNQueens(4));
        assertEquals(10,ex.totalNQueens(5));
        assertEquals(4,ex.totalNQueens(6));
        assertEquals(92,ex.totalNQueens(8));
    }

    @Test
    public void solveNQueens() {

        ex.solveNQueens(4);
    }
}