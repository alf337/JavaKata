package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class SingleNumberTest {

    SingleNumber ex = new SingleNumber();

    @Test
    public void singleNumber() {

        assertEquals(3, ex.singleNumber(new int[]{2,2,3,2}));
        assertEquals(99, ex.singleNumber(new int[]{0,1,0,1,0,1,99}));
    }
}