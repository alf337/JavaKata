package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZigZagConversionTest {

    ZigZagConversion ex = new ZigZagConversion();

    @Test
    public void convert() {

        assertEquals("PAHNAPLSIIGYIR", ex.convert("PAYPALISHIRING", 3));
        assertEquals("PINALSIGYAHRPI", ex.convert("PAYPALISHIRING", 4));

        assertEquals("AB", ex.convert("AB", 1));


    }
}