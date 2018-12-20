package alf.exercises.leetcode;

import org.junit.Test;

import static alf.exercises.leetcode.AddTwoNumbersV3.ListNode;
import static org.junit.Assert.*;

public class AddTwoNumbersV3Test {

    AddTwoNumbersV3 ex = new AddTwoNumbersV3();

    public String asString(ListNode node) {
        if (node == null) {
            return "null";
        } else {
            return "" + node.val + " -> " + asString(node.next);
        }
    }

    public ListNode longToListNode(final long x) {
        if (Math.abs(x) < 10) {
            return new ListNode(Long.valueOf(x).intValue());
        } else {
            ListNode ln = new ListNode(Long.valueOf(x % 10).intValue());
            ln.next = longToListNode(x / 10);
            return ln;
        }
    }

    public boolean isEqual(ListNode l1, ListNode l2) {
        if (l1 == l2) return true;
        if (l1 == null && l2 != null) return false;
        if (l2 == null) return false;
        return l1.val == l2.val && isEqual(l1.next, l2.next);
    }

    @Test
    public void testAddTwoNumbersV3() {
        ListNode l1 = longToListNode(342);
        System.out.println(asString(l1));
        ListNode l2 = longToListNode(465);
        System.out.println(asString(l2));

        ListNode result = ex.addTwoNumbers(l1, l2);
        System.out.println(asString(result));

        ListNode expected = longToListNode(807);

        assertTrue(isEqual(expected, result));
    }
}