package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerWithMostWaterTest {

    ContainerWithMostWater ex = new ContainerWithMostWater();
    ContainerWithMostWaterV2 exV2 = new ContainerWithMostWaterV2();

    @Test
    public void maxArea() {

        assertEquals(49, ex.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        assertEquals(49, exV2.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
   }
}