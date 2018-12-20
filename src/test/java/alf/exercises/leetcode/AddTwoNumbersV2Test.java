package alf.exercises.leetcode;

import org.junit.Test;
import static org.junit.Assert.*;
import static alf.exercises.leetcode.AddTwoNumbersV2.ListNode;
import static alf.exercises.leetcode.AddTwoNumbersV2.longToListNode;

public class AddTwoNumbersV2Test {

    AddTwoNumbersV2 ex = new AddTwoNumbersV2();

    public String asString(ListNode node) {
        if (node == null) {
            return "null";
        } else {
            return "" + node.val + " -> " + asString(node.next);
        }
    }


    @Test
    public void testAddTwoNumbersV2() {
        ListNode l1 = longToListNode(342);
        System.out.println(asString(l1));
        ListNode l2 = longToListNode(465);
        System.out.println(asString(l2));

        ListNode result = ex.addTwoNumbers(l1, l2);
        System.out.println(asString(result));

        assertEquals(807, ex.asLong(result));
    }
}