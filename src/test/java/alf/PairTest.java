package alf;

import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {

    @Test
    public void getters() {
        Pair<Integer, Integer> pair = new Pair<>(1, 2);
        assertEquals(1, pair.left().intValue());
        assertEquals(2, pair.right().intValue());
    }

    @Test
    public void ofFactory() {
        assertEquals("left", Pair.of("left", "right").left());
        assertEquals(2, Pair.of(1, 2).right().intValue());
    }

    @Test
    public void testEquals() {
        Pair<Integer, Integer> pairA = new Pair<>(1, 2);
        Pair<Integer, Integer> pairB = new Pair<>(1, 2);
        Pair<Integer, Integer> pairC = new Pair<>(2, 1);

        assertEquals(pairA, pairB);
        assertNotEquals(pairA, pairC);
    }

    @Test
    public void testHashCode() {
        Pair<Integer, Integer> pairA = new Pair<>(1, 2);
        Pair<Integer, Integer> pairB = new Pair<>(1, 2);
        Pair<Integer, Integer> pairC = new Pair<>(2, 1);

        assertEquals(pairA.hashCode(), pairB.hashCode());
        assertEquals(pairA.hashCode(), pairC.hashCode());
    }

    @Test
    public void testLinkedList() {
        Pair last = Pair.of("last", null);
        Pair three = Pair.of("three", last);
        Pair two = Pair.of("two", three);
        Pair one = Pair.of("one", two);

        System.out.println(one);
    }

}