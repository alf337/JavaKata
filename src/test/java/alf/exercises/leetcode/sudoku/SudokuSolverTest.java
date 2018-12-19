package alf.exercises.leetcode.sudoku;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SudokuSolverTest {

    SudokuSolver solver = new SudokuSolver();

    @Test
    public void easy1() {
        char[][] input = new char[9][9];
        input[0] = new char[]{'.', '5', '3', '.', '8', '.', '2', '7', '6'};
        input[1] = new char[]{'6', '7', '.', '5', '.', '.', '9', '8', '.'};
        input[2] = new char[]{'.', '.', '.', '.', '.', '7', '.', '.', '.'};
        input[3] = new char[]{'.', '4', '.', '.', '.', '.', '.', '.', '5'};
        input[4] = new char[]{'7', '.', '.', '4', '5', '1', '.', '.', '2'};
        input[5] = new char[]{'3', '.', '.', '.', '.', '.', '.', '4', '.'};
        input[6] = new char[]{'.', '.', '.', '9', '.', '.', '.', '.', '.'};
        input[7] = new char[]{'.', '8', '6', '.', '.', '2', '.', '5', '7'};
        input[8] = new char[]{'1', '3', '4', '.', '7', '.', '8', '2', '.'};

        assertTrue(isComplete(solver.solve(input)));
    }

    @Test
    public void easy2() {

        assertTrue(isComplete(solver.solve(parse(""
                + "53..7...."
                + "6..195..."
                + ".98....6."
                + "8...6...3"
                + "4..8.3..1"
                + "7...2...6"
                + ".6....28."
                + "...419..5"
                + "....8..79"))));
    }

    @Test
    public void soleCandidate() {

        char[][] result =solver.solve(parse(""
                + ".....1..."
                + "........."
                + ".....6..."
                + "...4....."
                + "....8...."
                + "2.9.....7"
                + "........."
                + ".....3..."
                + "........."));

        assertEquals('5', result[6-1][6-1]);
    }

    @Test
    public void uniqueCandidate() {

        char[][] result =solver.solve(parse(""
                + "..4......"
                + "........."
                + "........."
                + "........."
                + ".4......."
                + "........."
                + "5........"
                + "........."
                + ".....4..."));

        assertEquals('4', result[8-1][1-1]);
    }

    @Test
    public void blockAndColumnRowInteraction() {

        char[][] result =solver.solve(parse(""
                + "....7...."
                + "........."
                + "........."
                + "...2.1..."
                + "........."
                + "...9.6..."
                + "........."
                + "........."
                + "........."));
    }

    @Test
    public void medium1() {

        assertTrue(isComplete(solver.solve(parse(""
                + ".5......."
                + ".2736...5"
                + "..9...426"
                + "3...5..9."
                + ".4.697.1."
                + ".7..8...4"
                + "284...6.."
                + "6...4175."
                + ".......4."))));
    }

    @Test
    public void medium2() {

        assertTrue(isComplete(solver.solve(parse(""
                + "..6..1..."
                + "..5763.9."
                + "..859...6"
                + "6......53"
                + "8.......1"
                + "45......9"
                + "9...861.."
                + ".6.1498.."
                + "...3..9.."))));
    }

    @Test
    public void hard1() {

        assertTrue(isComplete(solver.solve(parse(""
                + "..9748..."
                + "7........"
                + ".2.1.9..."
                + "..7...24."
                + ".64.1.59."
                + ".98...3.."
                + "...8.3.2."
                + "........6"
                + "...2759.."))));
/*
        expected result
                [["5","1","9","7","4","8","6","3","2"],
                ["7","8","3","6","5","2","4","1","9"],
                ["4","2","6","1","3","9","8","7","5"],
                ["3","5","7","9","8","6","2","4","1"],
                ["2","6","4","3","1","7","5","9","8"],
                ["1","9","8","5","2","4","3","6","7"],
                ["9","7","5","8","6","3","1","2","4"],
                ["8","3","2","4","9","1","7","5","6"],
                ["6","4","1","2","7","5","9","8","3"]]
*/
    }

    @Test
    public void hard2() {

        assertTrue(isComplete(solver.solve(parse(""
                + "5.7.....3"
                + "..4.1...."
                + ".3.87.1.."
                + "4....86.."
                + "8.......5"
                + "..29....7"
                + "..1.96.4."
                + "....3.7.."
                + "7.....2.9"))));
    }

    @Test
    public void testGetHiddenPairs() {
        List<Cell> cellList = new ArrayList<>();
        cellList.add(Cell.of(1, 1, '4'));
        cellList.add(Cell.of(2, 1, '8'));
        cellList.add(Cell.of(3, 1, '1'));
        cellList.add(Cell.of(4, 1, '.'));
        cellList.add(Cell.of(5, 1, '.'));
        cellList.add(Cell.of(6, 1, '.'));
        cellList.add(Cell.of(7, 1, '.'));
        cellList.add(Cell.of(8, 1, '.'));
        cellList.add(Cell.of(9, 1, '.'));

        cellList.get(4 - 1).maybe.addAll(Arrays.asList('2','3'));
        cellList.get(5 - 1).maybe.addAll(Arrays.asList('2','3','5','6','7'));
        cellList.get(6 - 1).maybe.addAll(Arrays.asList('2','3','5','6','7'));
        cellList.get(7 - 1).maybe.addAll(Arrays.asList('2','9'));
        cellList.get(8 - 1).maybe.addAll(Arrays.asList('2','3','5'));
        cellList.get(9 - 1).maybe.addAll(Arrays.asList('2','3','9'));

        Map<TreeSet<Character>, TreeSet<Pos>> hiddenPairs = solver.getHiddenPairs(cellList);
        assertEquals(1, hiddenPairs.size());
        TreeSet<Character> expectedKey = new TreeSet<>(Arrays.asList('6','7'));

        assertTrue(hiddenPairs.containsKey(expectedKey));
        assertEquals(2, hiddenPairs.get(expectedKey).size());
        assertEquals(Pos.of(5, 1), hiddenPairs.get(expectedKey).first());
        assertEquals(Pos.of(6, 1), hiddenPairs.get(expectedKey).last());
    }

    @Test
    public void testGetHiddenPairs2() {
        List<Cell> cellList = new ArrayList<>();
        cellList.add(Cell.of(1, 1, '2'));
        cellList.add(Cell.of(2, 1, '9'));
        cellList.add(Cell.of(3, 1, '.'));
        cellList.add(Cell.of(4, 1, '1'));
        cellList.add(Cell.of(5, 1, '8'));
        cellList.add(Cell.of(6, 1, '7'));
        cellList.add(Cell.of(7, 1, '.'));
        cellList.add(Cell.of(8, 1, '6'));
        cellList.add(Cell.of(9, 1, '.'));

        cellList.get(3 - 1).maybe.addAll(Arrays.asList('3','4','5'));
        cellList.get(7 - 1).maybe.addAll(Arrays.asList('4','5'));
        cellList.get(9 - 1).maybe.addAll(Arrays.asList('3','4'));

        assertEquals(0, solver.getHiddenPairs(cellList).size());
    }

    @Test
    public void testEvalHiddenPairs() {
        List<Cell> cellList = new ArrayList<>();
        cellList.add(Cell.of(1, 1, '4'));
        cellList.add(Cell.of(2, 1, '8'));
        cellList.add(Cell.of(3, 1, '1'));
        cellList.add(Cell.of(4, 1, '.'));
        cellList.add(Cell.of(5, 1, '.'));
        cellList.add(Cell.of(6, 1, '.'));
        cellList.add(Cell.of(7, 1, '.'));
        cellList.add(Cell.of(8, 1, '.'));
        cellList.add(Cell.of(9, 1, '.'));

        cellList.get(4 - 1).maybe.addAll(Arrays.asList('2','3'));
        cellList.get(5 - 1).maybe.addAll(Arrays.asList('2','3','5','6','7'));
        cellList.get(6 - 1).maybe.addAll(Arrays.asList('2','3','5','6','7'));
        cellList.get(7 - 1).maybe.addAll(Arrays.asList('2','9'));
        cellList.get(8 - 1).maybe.addAll(Arrays.asList('2','3','5'));
        cellList.get(9 - 1).maybe.addAll(Arrays.asList('2','3','9'));

        solver.evalHiddenPairs(cellList, null);
        assertEquals(2, cellList.get(5 -1).maybe.size());
        assertEquals(2, cellList.get(6 -1).maybe.size());
    }

    private char[][] parse(String s) {
        if (s.length() != 81) throw new IllegalArgumentException("incorrect length " + s.length());
        char[][] board = new char[9][];
        int x = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < 9; i++) {
            char[] row = new char[9];
            for (int j = 0; j < 9; j++) {
                row[j] = chars[x++];
            }
            board[i] = row;
        }
        return board;
    }

    private boolean isComplete(char[][] chars) {
        if (chars.length != 9) throw new RuntimeException("incorrect row length " + chars.length);
        for (int i = 0; i < chars.length; i++) {
            if (chars[i].length != 9) throw new RuntimeException("incorrect column length " + chars[i].length);
            for (int j = 0; j < chars[i].length; j++) {
                if (chars[i][j] == '.') return false;
            }
        }
        return true;
    }
}