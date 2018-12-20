package alf.exercises.leetcode;

public class AddTwoNumbersV2 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        long sum = asLong(l1) + asLong(l2);
        return longToListNode(sum);
    }

    public long asLong(final ListNode node) {

        if (node.next == null) {
            return node.val;
        } else {
            return node.val + (10 * asLong(node.next));
        }
    }

    public static ListNode longToListNode(final long x) {

        if (Math.abs(x) < 10) {
            return new ListNode(Long.valueOf(x).intValue());
        } else {
            ListNode ln = new ListNode(Long.valueOf(x % 10).intValue());
            ln.next = longToListNode(x / 10);
            return ln;
        }
    }


    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
