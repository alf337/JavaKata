package alf.exercises.leetcode.sudoku;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuValidatorTest {

    SudokuValidator ex = new SudokuValidator();
    char[][] input = new char[9][9];

    @Before
    public void before() {
        input[0] = new char[]{'5','3','.','.','7','.','.','.','.'};
        input[1] = new char[]{'6','.','.','1','9','5','.','.','.'};
        input[2] = new char[]{'.','9','8','.','.','.','.','6','.'};
        input[3] = new char[]{'8','.','.','.','6','.','.','.','3'};
        input[4] = new char[]{'4','.','.','8','.','3','.','.','1'};
        input[5] = new char[]{'7','.','.','.','2','.','.','.','6'};
        input[6] = new char[]{'.','6','.','.','.','.','2','8','.'};
        input[7] = new char[]{'.','.','.','4','1','9','.','.','5'};
        input[8] = new char[]{'.','.','.','.','8','.','.','7','9'};
    }

    @Test
    public void allValid() {
        assertTrue(ex.isValidSudoku(input));
        assertFalse(ex.isValidSudoku(null));
    }

    @Test
    public void invalidRow() {
        input[0][6] = '3';
        assertFalse(ex.isValidSudoku(input));
    }

    @Test
    public void invalidColumn() {
        input[7][0] = '8';
        assertFalse(ex.isValidSudoku(input));
    }

    @Test
    public void invalidGrid() {
        input[5][5] = '8';
        assertFalse(ex.isValidSudoku(input));
    }

    @Test
    public void isValidCharacters() {

        assertTrue(ex.isValidCharacters(input));
        input[5][5] = 'a';
        assertFalse(ex.isValidCharacters(input));
    }

    @Test
    public void hasDuplicates() {

        assertFalse(ex.hasDuplicates(new char[]{'1','2','3'}));
        assertFalse(ex.hasDuplicates(new char[]{'1','2','.','3','.'}));
        assertTrue(ex.hasDuplicates(new char[]{'1','2','.','2','.'}));
    }

    @Test
    public void getColumn() {

        assertArrayEquals(new char[]{'5','6','.','8','4','7','.','.','.'} , ex.getColumn(input, 0));
        assertArrayEquals(new char[]{'7','9','.','6','.','2','.','1','8'} , ex.getColumn(input, 4));
    }

    @Test
    public void getGrid() {

        assertArrayEquals(new char[]{'5','3','.','6','.','.','.','9','8'} , ex.getGrid(input, 0));
        assertArrayEquals(new char[]{'.','6','.','8','.','3','.','2','.'} , ex.getGrid(input, 4));
    }
}