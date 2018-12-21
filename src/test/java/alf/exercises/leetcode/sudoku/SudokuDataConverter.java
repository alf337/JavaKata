package alf.exercises.leetcode.sudoku;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Convert data from the "twentylemon" Sudoku Generator into test data runner format.
 */
public class SudokuDataConverter {

    public static void main(String[] args) {

        int linesRead = 0;
        int dataLines = 0;
        int skip = 0;
        int puzzleCount = 0;

        List<String> puzzles = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        BufferedReader reader = null;

        try {
//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545398662593.sudoku.txt");
//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545398817982.sudoku.txt");
//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545398897479.sudoku.txt");

//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545403786390.sudoku.txt");
//            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545403786390.sudoku.txt");
            reader = openFile("/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/1545403810053.sudoku.txt");

            Optional<String> input = readLine(reader);

            while (input.isPresent()) {
                linesRead++;
                int r = processInput(input.get(), sb);

                switch (r) {
                    case 1:
                        puzzles.add(sb.toString());
                        sb = new StringBuilder();
                        puzzleCount++;
                        break;
                    case -1:
                        skip++;
                        break;
                    case 0:
                        dataLines++;
                        break;
                    default:
                        System.out.println("Unexpected return code " + r);
                }

                input = readLine(reader);
            }

        } finally {
            closeFile(reader);
        }

        showCounts(linesRead, dataLines, skip, puzzleCount);

        writeOutput(puzzles);
    }

    private static int processInput(String input, StringBuilder sb) {

        if (input.length() < 2) {
            System.out.println("end of puzzle: " + input);
            return 1;
        }

        if (input.contains("-")) {
            System.out.println("sep: " + input);
            return -1;
        } else {
            System.out.println("data: " + input);
            sb.append(input.replace('.', '0').replace("|", "").replace(" ", ""));
            return 0;
        }
    }

    private static void showCounts(int lines, int data, int skip, int puzzles) {

        System.out.println("Total: " + lines
                + "  Data: " + data
                + "  Skip: " + skip
                + "  Puzzles: " + puzzles);

        if (lines != (data + skip + puzzles)) throw new RuntimeException("invalid count");
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

    private static void writeOutput(List<String> puzzles) {

        int outCount = 0;
        BufferedWriter writer = null;
        String filename = "/home/alan/IdeaProjects/sudoku-generator/SudokuGenerator/dist/"
                + System.currentTimeMillis() + ".sudoku.data.txt";
        System.out.println("Output file name: " + filename);

        try {
            writer = new BufferedWriter(new FileWriter(filename));

            for (String output : puzzles) {
                writer.write(output);
                writer.newLine();
                outCount++;
            }

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Puzzles written: " + outCount);
            try {
                if (writer != null) writer.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}

