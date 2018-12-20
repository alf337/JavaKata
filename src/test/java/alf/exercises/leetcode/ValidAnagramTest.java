package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidAnagramTest {

    ValidAnagram ex = new ValidAnagram();

    @Test
    public void isAnagram() {
        assertFalse(ex.isAnagram("abcde", ""));
        assertFalse(ex.isAnagram("abcde", "lmnop"));
        assertTrue(ex.isAnagram("abcde", "edcab"));
        assertTrue(ex.isAnagram("abcdec", "cedcab"));
    }
}