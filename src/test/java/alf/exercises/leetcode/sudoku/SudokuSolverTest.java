package alf.exercises.leetcode.sudoku;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuSolverTest {

    SudokuSolver solver = new SudokuSolver();

    char[][] input = new char[9][9];

    @Before
    public void before() {
        input[0] = new char[]{'.','5','3','.','8','.','2','7','6'};
        input[1] = new char[]{'6','7','.','5','.','.','9','8','.'};
        input[2] = new char[]{'.','.','.','.','.','7','.','.','.'};
        input[3] = new char[]{'.','4','.','.','.','.','.','.','5'};
        input[4] = new char[]{'7','.','.','4','5','1','.','.','2'};
        input[5] = new char[]{'3','.','.','.','.','.','.','4','.'};
        input[6] = new char[]{'.','.','.','9','.','.','.','.','.'};
        input[7] = new char[]{'.','8','6','.','.','2','.','5','7'};
        input[8] = new char[]{'1','3','4','.','7','.','8','2','.'};
    }

    @Test
    public void solve() {
        assertNull(solver.solve(input));
    }
}