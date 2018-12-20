package alf.exercises.leetcode.sudoku;

public class SudokuTestGeneratorRunner {

    private static SudokuSolver solver = new SudokuSolver();
    private static SudokuGenerator generator = new SudokuGenerator(9, 20);

    public static void main(String[] args) {

        int fail = 0;
        int skip = 0;
        int pass = 0;
        int total = 0;
        long startTime = System.currentTimeMillis();

        while (total < 1000) {
            for (int missing = 20; missing < 60; missing++) {
                total++;
                int r = processInput(generator.generate(missing));

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

                if (total % 10 == 0) showCounts(total, pass, fail, skip, startTime);
            }
        }

        showCounts(total, pass, fail, skip, startTime);
    }

    private static int processInput(String input) {

        if (input.length() < 81) {
            System.out.println("Skipping: " + input);
            return 0;
        }

       String result = solver.solve(input);

        if (result.contains("0")) {
//            System.out.println("\n* Incomplete *");
//            System.out.println(input);
//            System.out.println(result);
//            System.out.println();
            return -1;
        } else {
            return 1;
        }
    }

    private static void showCounts(int total, int pass, int fail, int skip, long startTime) {

        System.out.println("Total: " + total
                + "  Pass: " + pass
                + "  Fail: " + fail
                + "  Skip: " + skip
                + "  Time: " + (System.currentTimeMillis() - startTime) / 1000);
    }
}

