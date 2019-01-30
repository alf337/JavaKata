package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class PowXnTest {

    PowXn ex = new PowXn();

    @Test
    public void myPow() {
        assertEquals(1024.00000, ex.myPow(2.00000, 10), 0.00001);
        assertEquals(9.26100, ex.myPow(2.10000, 3), 0.00001);
        assertEquals(0.25000, ex.myPow(2.00000, -2), 0.00001);
        assertEquals(1.00000, ex.myPow(2.00000, 0), 0.00001);
        assertEquals(2.00000, ex.myPow(2.00000, 1), 0.00001);
        assertEquals(1.00000, ex.myPow(1.00000, -2147483648), 0.00001);
        assertEquals(1.00000, ex.myPow(-1.00000, -2147483648), 0.00001);
        assertEquals(-1.00000, ex.myPow(-1.00000, 2147483647), 0.00001);

    }

    @Test
    public void myPow_v1() {
        assertEquals(1024.00000, ex.myPow_v1(2.00000, 10), 0.00001);
        assertEquals(9.26100, ex.myPow_v1(2.10000, 3), 0.00001);
        assertEquals(0.25000, ex.myPow_v1(2.00000, -2), 0.00001);
        assertEquals(1.00000, ex.myPow_v1(2.00000, 0), 0.00001);
        assertEquals(2.00000, ex.myPow_v1(2.00000, 1), 0.00001);
        assertEquals(1.00000, ex.myPow_v1(1.00000, -2147483648), 0.00001);
        assertEquals(1.00000, ex.myPow_v1(-1.00000, -2147483648), 0.00001);
        assertEquals(-1.00000, ex.myPow_v1(-1.00000, 2147483647), 0.00001);
    }
}