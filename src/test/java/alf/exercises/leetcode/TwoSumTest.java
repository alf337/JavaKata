package alf.exercises.leetcode;

import org.junit.Test;

import static alf.exercises.leetcode.TwoSum.twoSum;
import static org.junit.Assert.assertArrayEquals;

public class TwoSumTest {

    @Test
    public void testTwoSum() {

        int[] input = new int[]{2, 7, 5, 11};

        assertArrayEquals(new int[]{0, 1}, twoSum(input, 9));
        assertArrayEquals(new int[]{1, 2}, twoSum(input, 12));
        assertArrayEquals(new int[]{2, 3}, twoSum(input, 16));
        assertArrayEquals(new int[]{1, 3}, twoSum(input, 18));
        assertArrayEquals(new int[]{}, twoSum(input, 1));
        assertArrayEquals(new int[]{}, twoSum(input, 99));

        assertArrayEquals(new int[]{0, 2}, twoSum(new int[]{2, 4, 8, 6}, 10));

        assertArrayEquals(new int[]{1, 2}, twoSum(new int[]{1, 2, 2, 2}, 4));

    }
}