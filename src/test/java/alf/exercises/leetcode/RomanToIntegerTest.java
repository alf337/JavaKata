package alf.exercises.leetcode;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class RomanToIntegerTest {

    RomanToInteger ex = new RomanToInteger();

    @Test
    public void romanToInt() {
        assertEquals(1, ex.romanToInt("I"));
        assertEquals(3, ex.romanToInt("III"));
        assertEquals(5, ex.romanToInt("V"));
        assertEquals(4, ex.romanToInt("IV"));
        assertEquals(9, ex.romanToInt("IX"));
        assertEquals(58, ex.romanToInt("LVIII"));
        assertEquals(1994, ex.romanToInt("MCMXCIV"));
    }

    @Test
    public void intToRoman() {
        assertEquals("I", ex.intToRoman(1));
        assertEquals("III", ex.intToRoman(3));
        assertEquals("V", ex.intToRoman(5));
        assertEquals("IV", ex.intToRoman(4));
        assertEquals("IX", ex.intToRoman(9));
        assertEquals("LVIII", ex.intToRoman(58));
        assertEquals("MCMXCIV", ex.intToRoman(1994));

        IntStream.range(1,4000).forEach(i -> ex.intToRoman(i));
    }

    @Test
    public void intToRomanV2() {
        assertEquals("I", ex.intToRomanV2(1));
        assertEquals("III", ex.intToRomanV2(3));
        assertEquals("V", ex.intToRomanV2(5));
        assertEquals("IV", ex.intToRomanV2(4));
        assertEquals("IX", ex.intToRomanV2(9));
        assertEquals("LVIII", ex.intToRomanV2(58));
        assertEquals("MCMXCIV", ex.intToRomanV2(1994));

        IntStream.range(1,4000).forEach(i -> ex.intToRomanV2(i));
    }


}