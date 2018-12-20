package alf.exercises.leetcode;

import org.junit.Test;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class IntegerReplacementTest {

    IntegerReplacement ex = new IntegerReplacement();

    @Test
    public void testReplace() {

        assertEquals(2, ex.replace(3));
        assertEquals(3, ex.replace(6));
        assertEquals(3, ex.replace(8));
        assertEquals(4, ex.replace(7));
        assertEquals(4, ex.replace(9));
        assertEquals(4, ex.replace(10));
        assertEquals(5, ex.replace(17));
        assertEquals(6, ex.replace(19));
        assertEquals(7, ex.replace(29));
        assertEquals(9, ex.replace(119));
        assertEquals(9, ex.replace(121));
    }
}