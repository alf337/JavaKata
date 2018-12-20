package alf.exercises.leetcode;

import org.junit.Test;

import static alf.exercises.leetcode.AddTwoNumbers.ListNode;
import static alf.exercises.leetcode.AddTwoNumbers.addTwoNumbers;
import static org.junit.Assert.*;

public class AddTwoNumbersTest {

    @Test
    public void testAddTwoNumbers() {
        ListNode l = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode m = new ListNode(5, new ListNode(6, new ListNode(4)));

        System.out.println(addTwoNumbers(l, m));
        assertEquals(807, addTwoNumbers(l, m).asInt());
        assertEquals(ListNode.of(807), addTwoNumbers(l, m));
    }

    @Test
    public void testAddTwoNumbers_withNegative() {
        ListNode l = ListNode.of(12);
        ListNode m = ListNode.of(-5);
        System.out.println(m);
        ListNode n = ListNode.of(-29);
        System.out.println(n);
        ListNode o = ListNode.of(29);

        assertEquals(7, addTwoNumbers(l, m).asInt());
        assertEquals(-17, addTwoNumbers(l, n).asInt());
        assertEquals(-34, addTwoNumbers(m, n).asInt());
        assertEquals(0, addTwoNumbers(n, o).asInt());
    }

    @Test
    public void testPlus() {
        ListNode l = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode m = new ListNode(5, new ListNode(6, new ListNode(4)));

        assertEquals(ListNode.of(807), l.plus(m));
        assertEquals(807, m.plus(l).asInt());
    }

    @Test
    public void testAdd() {
        assertEquals(ListNode.of(5), ListNode.of(2).add(ListNode.of(3)));
        assertEquals(ListNode.of(18), ListNode.of(9).add(ListNode.of(9)));
        assertEquals(ListNode.of(22), ListNode.of(11).add(ListNode.of(11)));
        assertEquals(ListNode.of(30), ListNode.of(11).add(ListNode.of(19)));
        assertEquals(ListNode.of(20), ListNode.of(11).add(ListNode.of(9)));
        assertEquals(ListNode.of(21), ListNode.of(8).add(ListNode.of(13)));
        assertEquals(ListNode.of(198), ListNode.of(99).add(ListNode.of(99)));
        assertEquals(ListNode.of(2222), ListNode.of(1111).add(ListNode.of(1111)));
        assertEquals(ListNode.of(1111), ListNode.of(1111).add(ListNode.of(0)));
        assertEquals(ListNode.of(1000), ListNode.of(999).add(ListNode.of(1)));
    }

    @Test
    public void testListNodeOf() {
        System.out.println(ListNode.of(12));
        assertEquals(3010, ListNode.of(3010).asInt());
    }

    @Test
    public void testListNode() {

        ListNode l = new ListNode(2);
        System.out.println(l);
        l.setNext(new ListNode(4));
        System.out.println(l);

        ListNode m = new ListNode(2, new ListNode(4, new ListNode(3)));
        System.out.println(m);

        try {
            new ListNode(10);
            fail("should have failed");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testAsInt() {
        ListNode m = new ListNode(2, new ListNode(4, new ListNode(3)));
        assertEquals(342, m.asInt());
    }

    @Test
    public void testHashCode() {
        ListNode l = ListNode.of(2309);
        ListNode m = ListNode.of(2309);
        System.out.println(l.hashCode());
        assertEquals(l, m);
        assertEquals(l.hashCode(), m.hashCode());

    }
}