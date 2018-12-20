package alf.exercises.leetcode;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LetterCombinationsOfPhoneNumberTest {

    LetterCombinationsOfPhoneNumber ex = new LetterCombinationsOfPhoneNumber();

    @Test
    public void letterCombinations() {

        assertTrue(ex.letterCombinations("").isEmpty());

        assertEquals(Arrays.asList("a", "b", "c"),
            ex.letterCombinations("2"));

        assertEquals(Arrays.asList("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"),
            ex.letterCombinations("23"));

        assertEquals(Arrays.asList("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"),
            ex.letterCombinations("1203A"));

        assertEquals(Arrays.asList("pa", "pb", "pc", "qa", "qb", "qc", "ra", "rb", "rc", "sa", "sb", "sc"),
            ex.letterCombinations("72"));

        assertEquals(Arrays.asList("adg","adh","adi","aeg","aeh","aei","afg","afh","afi","bdg","bdh","bdi","beg","beh",
                "bei","bfg","bfh","bfi","cdg","cdh","cdi","ceg","ceh","cei","cfg","cfh","cfi"),
            ex.letterCombinations("234"));
    }
}