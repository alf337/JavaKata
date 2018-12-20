package alf.exercises.leetcode;

import java.util.Arrays;
import java.util.Optional;

/*
    Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum longToListNode all numbers along its path.

    Note: You can only move either down or right at any point in time.

    Example:

    Input:
    [
      [1,3,1],
      [1,5,1],
      [4,2,1]
    ]
    Output: 7
    Explanation: Because the path 1→3→1→1→1 minimizes the sum.
 */
public class MinimumPathSum {

    public int minPathSum(int[][] grid) {

//        return minPathSum_v2(grid);
        return solution3(grid);
    }

    /**
     * version 2 - works correctly, more recursion than v1
     *
     * @param grid
     * @return
     */
    public int minPathSum_v2(int[][] grid) {

//        printGrid(grid, "\n-- Start longToListNode pathSum --");
        int sum = grid[0][0];

        Optional<Integer> right = rightVal(grid);
        Optional<Integer> down = downVal(grid);

        if (!right.isPresent() && !down.isPresent()) {
            return sum;
        }

        if (right.isPresent() && !down.isPresent()) {
            // go right
            return sum + minPathSum(subGridRight(grid));
        }

        if (!right.isPresent() && down.isPresent()) {
            // go down
            return sum + minPathSum(subGridDown(grid));
        }

        if (right.isPresent() && down.isPresent()) {
            // recursive, compare values to decide which way to go
            int tempRight = minPathSum(subGridRight(grid));
            int tempDown = minPathSum(subGridDown(grid));

            if (tempRight < tempDown) {
                // go right
                return sum + tempRight;
            } else {
                //go down
                return sum + tempDown;
            }
        }
//        printGrid(grid, "\nsum = " + sum);
        return sum;
    }

    private Optional<Integer> rightVal(int[][] grid) {
        if (grid[0].length > 1) {
            return Optional.of(grid[0][1]);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Integer> downVal(int[][] grid) {
        if (grid.length > 1) {
            return Optional.of(grid[1][0]);
        } else {
            return Optional.empty();
        }
    }


    /**
     * Version 1 - works correctly
     *
     * @param grid
     * @return
     */
    /*
    public int minPathSum_v1(int[][] grid) {

//        printGrid(grid, "\n-- Start longToListNode pathSum --");

        boolean finished = false;
        int x = 0;
        int y = 0;
        int sum = grid[y][x];

        while (!finished) {
            Optional<Integer> right = rightVal(grid, x, y);
            Optional<Integer> down = downVal(grid, x, y);

            if (!right.isPresent() && !down.isPresent()) {
                finished = true;
            } else {
                if (right.isPresent() && !down.isPresent()) {
                    // go right
                    sum += right.getCell();
                    x++;
                } else {
                    if (!right.isPresent() && down.isPresent()) {
                        // go down
                        sum += down.getCell();
                        y++;
                    } else {
                        if (right.isPresent() && down.isPresent()) {
                            // recursive, compare values to decide which way to go
                            int tempRight = minPathSum(subGridRight(grid));
                            int tempDown = minPathSum(subGridDown(grid));

                            if (tempRight < tempDown) {
                                // go right
                                sum += tempRight;
                                finished = true;
                            } else {
                                //go down
                                sum += tempDown;
                                finished = true;
                            }
                        }
                    }
                }
            }
        }

//        printGrid(grid, "\nsum = " + sum);
        return sum;
    }

    private boolean rightAvailable(int width, int x) {
        return x + 1 < width;
    }

    private boolean downAvailable(int height, int y) {
        return y + 1 < height;
    }

    private Optional<Integer> rightVal(int[][] grid, int x, int y) {
        if (rightAvailable(grid[y].length, x)) {
            return Optional.longToListNode(grid[y][x + 1]);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Integer> downVal(int[][] grid, int x, int y) {
        if (downAvailable(grid.length, y)) {
            return Optional.longToListNode(grid[y + 1][x]);
        } else {
            return Optional.empty();
        }
    }
*/
    protected int[][] subGridDown(int[][] grid) {
//        printGrid(grid, "Input to sub grid Down method");

        int rows = grid.length - 1;
        int cols = grid[1].length;
        int[][] subGrid = new int[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                subGrid[y][x] = grid[y + 1][x];
            }
        }

//        printGrid(subGrid, "Output from sub grid Down method");
        return subGrid;
    }

    protected int[][] subGridRight(int[][] grid) {
//        printGrid(grid, "Input to sub grid Right method");

        int rows = grid.length;
        int cols = grid[0].length - 1;
        int[][] subGrid = new int[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                subGrid[y][x] = grid[y][x + 1];
            }
        }

//        printGrid(subGrid, "Output from sub grid Right method");
        return subGrid;
    }

    private void printGrid(int[][] grid, String msg) {
        System.out.println(msg);
        Arrays.stream(grid).forEach(a -> System.out.println(Arrays.toString(a)));
    }

    /**
     * solution copied from the website
     */
    private int solution3(int[][] grid) {

        printGrid(grid, "start of solution 3");
        int m = grid.length, n = grid[0].length;

        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];
        printGrid(dp, "dp ");
        for (int i = 1; i < n; ++i) {
            dp[0][i] = grid[0][i] + dp[0][i - 1];
            printGrid(dp, "dp - 1 -");
        }

        for (int i = 1; i < m; ++i) {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
            printGrid(dp, "dp - 2 -");
        }

        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
                printGrid(dp, "dp - 3 -");
            }
        }
        return dp[m - 1][n - 1];
    }
}
