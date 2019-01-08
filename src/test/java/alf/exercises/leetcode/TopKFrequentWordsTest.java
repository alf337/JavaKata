package alf.exercises.leetcode;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TopKFrequentWordsTest {

    TopKFrequentWords ex = new TopKFrequentWords();

    @Test
    public void topKFrequent1() {
        assertEquals(Arrays.asList("i", "love"),
                ex.topKFrequent(new String[]{"i", "love", "leetcode", "i", "love", "coding"}
                , 2));
    }

    @Test
    public void topKFrequent2() {
        assertEquals(Arrays.asList("the", "is", "sunny", "day"),
                ex.topKFrequent(new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}
                , 4));
    }

    @Test
    public void topKFrequent2b() {
        assertEquals(Arrays.asList("the", "is"),
                ex.topKFrequent(new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}
                , 2));
    }

    @Test
    public void countWords() {
        System.out.println(ex.countWords(new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}));
    }

    @Test
    public void sortByCount() {
        System.out.println(ex.sortByCount(ex.countWords(new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"})));

        System.out.println(ex.sortByCount(ex.countWords(new String[]{"i", "love", "leetcode", "i", "love", "coding"})));
    }
}