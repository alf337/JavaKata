package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class MinimumPathSumTest {

    MinimumPathSum exercise = new MinimumPathSum();

    @Test
    public void minPathSum1x1() {
        assertEquals(1, exercise.minPathSum(new int[][]{new int[]{1}}));
    }

    @Test
    public void minPathSum2x1() {

        int[][] input = new int[][]{
                new int[]{1},
                new int[]{2}};

        assertEquals(3, exercise.minPathSum(input));
    }

    @Test
    public void minPathSum1x2() {

        int[][] input = new int[][]{
                new int[]{1, 2}};

        assertEquals(3, exercise.minPathSum(input));
    }

    @Test
    public void minPathSum2x2() {

        int[][] input = new int[][]{
                new int[]{1, 2},
                new int[]{3, 4}};

        assertEquals(7, exercise.minPathSum(input));

        System.out.println("\n ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");

        input = new int[][]{
                new int[]{1, 3},
                new int[]{2, 4}};

        assertEquals(7, exercise.minPathSum(input));
    }

    @Test
    public void minPathSum3x3() {

        int[][] input = new int[][]{
                new int[]{1,3,1},
                new int[]{1,5,1},
                new int[]{4,2,1}};

        assertEquals(7, exercise.minPathSum(input));
//
//        input = new int[][]{
//                new int[]{1,3,5},
//                new int[]{1,1,1},
//                new int[]{4,2,1}};
//
//        assertEquals(5, exercise.minPathSum(input));
//
//        input = new int[][]{
//                new int[]{1,5,1},
//                new int[]{1,3,1},
//                new int[]{4,2,1}};
//
//        assertEquals(7, exercise.minPathSum(input));
    }

    @Test
    public void minPathSum5x6() {

        int[][] input = new int[][]{
                new int[]{1,1,1,1,5,1},
                new int[]{1,3,1,1,1,8},
                new int[]{1,1,1,2,1,1},
                new int[]{4,1,7,1,9,1},
                new int[]{1,8,1,1,1,1}};

        assertEquals(10, exercise.minPathSum(input));
    }

    @Test
    public void subGridDown() {

        int[][] input = new int[][]{
                new int[]{1,3,1},
                new int[]{1,5,1},
                new int[]{4,2,1}};

        int[][] expected = new int[][]{
                new int[]{1,5,1},
                new int[]{4,2,1}};

        assertArrayEquals(expected, exercise.subGridDown(input));
    }

    @Test
    public void subGridRight() {

        int[][] input = new int[][]{
                new int[]{1,3,1},
                new int[]{1,5,1},
                new int[]{4,2,1}};

        int[][] expected = new int[][]{
                new int[]{3,1},
                new int[]{5,1},
                new int[]{2,1}};

        assertArrayEquals(expected, exercise.subGridRight(input));
    }
}