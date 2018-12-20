package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongestSubstringWithoutRepeatCharactersTest {

    LongestSubstringWithoutRepeatCharacters ex = new LongestSubstringWithoutRepeatCharacters();

    @Test
    public void length() {

        assertEquals(3, ex.length("abcabcbb"));
        assertEquals(1, ex.length("bbbbb"));
        assertEquals(3, ex.length("pwwkew"));
    }

    @Test
    public void testHasDuplicates() {
        assertFalse(ex.hasDuplicates(""));
        assertFalse(ex.hasDuplicates("a"));
        assertFalse(ex.hasDuplicates("ab"));
        assertTrue(ex.hasDuplicates("aa"));
        assertTrue(ex.hasDuplicates("aba"));

        assertFalse(ex.hasDuplicates("abcdefghij"));
    }
}