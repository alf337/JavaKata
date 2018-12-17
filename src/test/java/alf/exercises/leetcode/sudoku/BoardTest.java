package alf.exercises.leetcode.sudoku;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

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
    public void print() {
        Board board = Board.of(input);
        board.print();
    }

    @Test
    public void getRow() {
        Board board = Board.of(input);
//        System.out.println(board.getRow(2));
        assertEquals(9, board.getRow(2).size());
        assertEquals(Character.valueOf('6'), board.getRow(2).get(0).val.orElse('?'));
        assertEquals(Character.valueOf('8'), board.getRow(2).get(7).val.orElse('?'));
        assertFalse(board.getRow(2).get(8).val.isPresent());
    }

    @Test
    public void getColumn() {
        Board board = Board.of(input);
//        System.out.println(board.getColumn(2));
        assertEquals(9, board.getColumn(2).size());
        assertEquals(Character.valueOf('5'), board.getColumn(2).get(0).val.orElse('?'));
        assertEquals(Character.valueOf('4'), board.getColumn(2).get(3).val.orElse('?'));
        assertFalse(board.getColumn(2).get(2).val.isPresent());
    }

    @Test
    public void getGrid() {
        Board board = Board.of(input);
//        System.out.println(board.getGrid(8));
        assertEquals(9, board.getGrid(8).size());
        assertEquals(Character.valueOf('9'), board.getGrid(8).get(0).val.orElse('?'));
        assertEquals(Character.valueOf('7'), board.getGrid(8).get(7).val.orElse('?'));
        assertFalse(board.getGrid(8).get(8).val.isPresent());

        Pos pos = Pos.of(7, 4);
        System.out.println(board.getGrid(pos));
        assertEquals(9, board.getGrid(pos).size());
        assertEquals(Character.valueOf('9'), board.getGrid(pos).get(0).val.orElse('?'));
        assertEquals(Character.valueOf('7'), board.getGrid(pos).get(7).val.orElse('?'));
        assertFalse(board.getGrid(pos).get(8).val.isPresent());
    }
}