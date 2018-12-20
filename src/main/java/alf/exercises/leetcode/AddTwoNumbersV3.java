package alf.exercises.leetcode;

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example:
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 */
public class AddTwoNumbersV3 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        return add(l1, l2);
    }

    /**
     * This solution V3 was "accepted" by Leet Code. V2 failed on very large numbers.
     */
    public ListNode add(ListNode l1, ListNode l2) {
        int temp = l1.val + l2.val;
        int digit = temp % 10;
        int carry = temp / 10;

        if (l1.next == null) {
            if (l2.next == null) {
                if (carry == 0) {
                    return new ListNode(digit);
                } else {
                    ListNode l3Next = new ListNode(carry);
                    ListNode l3 = new ListNode(digit);
                    l3.next = l3Next;
                    return l3;
                }

            } else {
                // this.nex is null AND other.next is not null
                ListNode l3Next = add(l2.next, new ListNode(carry));
                ListNode l3 = new ListNode(digit);
                l3.next = l3Next;
                return l3;

            }
        } else {
            // l1.next is not null
            if (l2.next == null) {
                ListNode l3Next = add(l1.next, new ListNode(carry));
                ListNode l3 = new ListNode(digit);
                l3.next = l3Next;
                return l3;

            } else {
                // l1.next is not null AND l2.next is not null
                ListNode l3Next = add(add(l1.next, l2.next), new ListNode(carry));
                ListNode l3 = new ListNode(digit);
                l3.next = l3Next;
                return l3;
            }
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
