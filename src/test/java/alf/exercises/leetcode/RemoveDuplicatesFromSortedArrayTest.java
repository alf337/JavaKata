package alf.exercises.leetcode;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RemoveDuplicatesFromSortedArrayTest {

    RemoveDuplicatesFromSortedArray ex = new RemoveDuplicatesFromSortedArray();

    @Test
    public void removeDuplicates() {
        int[] input = new int[]{1,1,2};
        assertEquals(2, ex.removeDuplicates(input));
        assertEquals(1, input[0]);
        assertEquals(2, input[1]);
    }

    @Test
    public void removeDuplicates2() {
        int[] input = new int[]{0,0,1,1,1,2,2,3,3,4};
        assertEquals(5, ex.removeDuplicates(input));
        assertEquals(0, input[0]);
        assertEquals(1, input[1]);
        assertEquals(2, input[2]);
        assertEquals(3, input[3]);
        assertEquals(4, input[4]);
    }
}