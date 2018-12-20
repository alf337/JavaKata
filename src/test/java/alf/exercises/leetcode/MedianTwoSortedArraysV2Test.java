package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MedianTwoSortedArraysV2Test {

    MedianTwoSortedArraysV2 ex = new MedianTwoSortedArraysV2();

    @Test
    public void testMedian() {

        assertEquals(2.0, ex.median(new int[]{1,3}, new int[]{2}), 0.001);

        assertEquals(2.0, ex.median(new int[]{}, new int[]{2}), 0.001);

        assertEquals(2.5, ex.median(new int[]{1,2}, new int[]{3,4}), 0.001);

        assertEquals(2.0, ex.median(new int[]{1,2}, new int[]{2,9}), 0.001);

    }
}