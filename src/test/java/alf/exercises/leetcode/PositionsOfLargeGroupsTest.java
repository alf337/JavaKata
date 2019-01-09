package alf.exercises.leetcode;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PositionsOfLargeGroupsTest {

    PositionsOfLargeGroups ex = new PositionsOfLargeGroups();

    @Test
    public void largeGroupPositions() {
        assertEquals(Arrays.asList(Arrays.asList(3, 6)), ex.largeGroupPositions("abbxxxxzzy"));
        assertTrue(ex.largeGroupPositions("abc").isEmpty());

        assertEquals(Arrays.asList(
                Arrays.asList(3, 5),
                Arrays.asList(6, 9),
                Arrays.asList(12, 14)),
                ex.largeGroupPositions("abcdddeeeeaabbbcd"));

        assertEquals(Arrays.asList(
                Arrays.asList(0,2),
                Arrays.asList(4,6),
                Arrays.asList(7,9)),
                ex.largeGroupPositions("nnnhaaannnm"));
    }

    @Test
    public void getGroups() {
        System.out.println(ex.getGroups("abbxxxxzzy"));
    }
}