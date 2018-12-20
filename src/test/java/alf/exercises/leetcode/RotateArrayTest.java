package alf.exercises.leetcode;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RotateArrayTest {

    RotateArray ex = new RotateArray();

    @Test
    public void rotateArray() {
        int[] input = new int[]{3, 4};
        System.out.println("input=" + Arrays.toString(input));
        ex.rotate(input, 0);
        System.out.println("output=" + Arrays.toString(input));

        input = new int[]{3, 4};
        System.out.println("input=" + Arrays.toString(input));
        ex.rotate(input, 1);
        System.out.println("output=" + Arrays.toString(input));

        input = new int[]{1,2,3,4,5,6,7};
        System.out.println("input=" + Arrays.toString(input));
        ex.rotate(input, 3);
        System.out.println("output=" + Arrays.toString(input));

        input = new int[]{-1,-100,3,99};
        System.out.println("input=" + Arrays.toString(input));
        ex.rotate(input, 2);
        System.out.println("output=" + Arrays.toString(input));
    }

    @Test
    public void rotateClojure() {
        assertArrayEquals(new int[]{3,4}, ex.rotateClojure(new int[]{3,4}, 0));
        assertArrayEquals(new int[]{4,3}, ex.rotateClojure(new int[]{3,4}, 1));
        assertArrayEquals(new int[]{5,6,7,1,2,3,4}, ex.rotateClojure(new int[]{1,2,3,4,5,6,7}, 3));
        assertArrayEquals(new int[]{3,99,-1,-100}, ex.rotateClojure(new int[]{-1,-100,3,99}, 2));
    }

    @Test
    public void rotateInner() {
        assertArrayEquals(new int[]{3,4}, ex.rotateInner(new int[]{3,4}, 0));
        assertArrayEquals(new int[]{4,3}, ex.rotateInner(new int[]{3,4}, 1));
        assertArrayEquals(new int[]{5,6,7,1,2,3,4}, ex.rotateInner(new int[]{1,2,3,4,5,6,7}, 3));
        assertArrayEquals(new int[]{3,99,-1,-100}, ex.rotateInner(new int[]{-1,-100,3,99}, 2));
    }
}