package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class MinimumIncrementToMakeUniqueArrayTest {

    MinimumIncrementToMakeUniqueArray ex = new MinimumIncrementToMakeUniqueArray();

    @Test
    public void minIncrementForUnique() {
        assertEquals(1, ex.minIncrementForUnique(new int[]{1,2,2}));
    }

    @Test
    public void minIncrementForUnique2() {
        assertEquals(6, ex.minIncrementForUnique(new int[]{3,2,1,2,1,7}));
    }
}