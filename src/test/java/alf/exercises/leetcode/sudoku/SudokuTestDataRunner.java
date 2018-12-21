package alf.exercises.leetcode.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class SudokuTestDataRunner {

    private static SudokuSolver solver = new SudokuSolver();

    public static void main(String[] args) {

        int fail = 0;
        int error = 0;
        int skip = 0;
        int pass = 0;
        int total = 0;
        long startTime = System.currentTimeMillis();

        BufferedReader reader = null;

        try {
//            reader = openFile("/home/alan/Downloads/sudoku_test_data.csv");
//            reader = openFile("/home/alan/Downloads/sudoku_test_small.csv");

//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545401541815.sudoku.data.txt");
            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545401712949.sudoku.data.txt");
//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545401725108.sudoku.data.txt");

//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545403922433.sudoku.data.txt");
//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545403936689.sudoku.data.txt");
//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545403946913.sudoku.data.txt");

            Optional<String> input = readLine(reader);

            while (input.isPresent() && total < 10) {
                total++;

                try {
                    int r = processInput(input.get());

                    switch (r) {
                        case 1:
                            pass++;
                            break;
                        case -1:
                            fail++;
                            break;
                        case 0:
                            skip++;
                            break;
                        default:
                            System.out.println("Unexpected return code " + r);
                    }

                } catch (RuntimeException re) {
                    error++;
                    System.out.println("Error puzzle number: " + total + ", " + re.getMessage());
                }

                if (total % 1000 == 0) showCounts(total, pass, fail, error, skip, startTime);

                input = readLine(reader);
            }

        } finally {
            closeFile(reader);
        }

        showCounts(total, pass, fail, error, skip, startTime);
    }

    private static int processInput(String input) {

        if (input.length() < 81) {
            System.out.println("Skipping: " + input);
            return 0;
        }

        String[] split = input.split(",");

        if (split.length == 2) {
            return processPuzzleWithSolution(split[0], split[1]);
        } else {

            if (split.length == 1) {
                return processPuzzle(split[0]);
            } else {
                System.out.println("Skipping: " + input);
                return 0;
            }
        }
    }

    /*
     * these are the puzzles download from https://www.kaggle.com/bryanpark/sudoku
     */
    private static int processPuzzleWithSolution(String puzzle, String solution) {

        String result = solver.solve(puzzle);

        if (solution.equals(result)) {
            return 1;
        } else {
            System.out.println("\n******* incorrect *********");
            System.out.println(puzzle);
            System.out.println(solution);
            System.out.println(result);
            System.out.println();
            return -1;
        }
    }

    /*
     * these are the puzzles produced by the twenty lemon generator
     */
    private static int processPuzzle(String puzzle) {

        String result = solver.solve(puzzle);

        if (result.contains("0")) {
            System.out.println("\n* incomplete *");
            System.out.println(puzzle);
            System.out.println(result);
            System.out.println();
            return -1;
        } else {
            return 1;
        }
    }


    private static void showCounts(int total, int pass, int fail, int error, int skip, long startTime) {

        System.out.println("Total: " + total
                + "  Pass: " + pass
                + "  Fail: " + fail
                + "  Error: " + error
                + "  Skip: " + skip
                + "  Time: " + (System.currentTimeMillis() - startTime) / 1000);
    }

    private static BufferedReader openFile(String filename) {

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filename));
            return br;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void closeFile(BufferedReader br) {
        try {
            if (br != null) br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Optional<String> readLine(BufferedReader br) {

        try {
            if (br != null) {
                String s = br.readLine();
                if (s != null) {
                    return Optional.of(s);
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

