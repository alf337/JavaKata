package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;


public class PalindromeNumberTest {

    PalindromeNumber ex = new PalindromeNumber();

    @Test
    public void isPalindrome() {
        assertTrue(ex.isPalindrome(121));
        assertFalse(ex.isPalindrome(-121));
        assertFalse(ex.isPalindrome(10));
    }
}