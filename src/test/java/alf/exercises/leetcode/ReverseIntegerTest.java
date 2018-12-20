package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReverseIntegerTest {

    @Test
    public void reverse() {

        assertEquals(123, ReverseInteger.reverse(321));
        assertEquals(21, ReverseInteger.reverse(120));
        assertEquals(-123, ReverseInteger.reverse(-321));
        assertEquals(-123_456_789, ReverseInteger.reverse(-987_654_321));
        assertEquals(123456789, ReverseInteger.reverse(987_654_321));
    }
    @Test
    public void reverse2() {

        assertEquals(123, ReverseInteger.reverse2(321));
        assertEquals(21, ReverseInteger.reverse2(120));
        assertEquals(-123, ReverseInteger.reverse2(-321));
        assertEquals(-123_456_789, ReverseInteger.reverse2(-987_654_321));
        assertEquals(123456789, ReverseInteger.reverse2(987_654_321));
    }
}