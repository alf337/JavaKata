package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringToIntegerAtoiTest {

    StringToIntegerAtoi ex = new StringToIntegerAtoi();

    @Test
    public void atoi() {

        assertEquals(42, ex.atoi("42"));
        assertEquals(-42, ex.atoi("   -42"));
        assertEquals(4193, ex.atoi("4193 with words"));
        assertEquals(0, ex.atoi("words and 987"));
        assertEquals(Integer.MIN_VALUE, ex.atoi("-91283472332"));
        assertEquals(Integer.MAX_VALUE, ex.atoi("91283472332"));
        assertEquals(3, ex.atoi("3.14159"));
        assertEquals(0, ex.atoi(""));
        assertEquals(0, ex.atoi("+"));
        assertEquals(0, ex.atoi("-"));
        assertEquals(0, ex.atoi("+-2"));
        assertEquals(-12, ex.atoi("  -0012a42"));
        assertEquals(0, ex.atoi("0-1"));
        assertEquals(-5, ex.atoi("-5-"));
        assertEquals(1, ex.atoi("+1"));
        assertEquals(-13, ex.atoi("-13+8"));
        assertEquals(0, ex.atoi("-  234"));
    }
}