package alf.exercises.leetcode;

import java.util.Objects;

public class AddTwoNumbers {

    public static ListNode addTwoNumbers(ListNode one, ListNode two) {

        return one.plus(two);
    }


    /**
     * Definition for singly-linked list.
     */
    public static class ListNode {
        private int val;
        private ListNode next;

        ListNode(int x) {
            if (x > 9) throw new IllegalArgumentException();
            val = x;
        }

        ListNode(int x, ListNode n) {
            this(x);
            next = n;
        }

        public static ListNode of(final int x) {

            if (Math.abs(x) < 10) {
                return new ListNode(x);
            } else {
                return new ListNode((x % 10), ListNode.of(x / 10));
            }
        }

        public int asInt() {

            if (next == null) {
                return val;
            } else {
                return val + (10 * next.asInt());
            }

        }

        public ListNode plus(ListNode other) {
            return ListNode.of(this.asInt() + other.asInt());
        }

        public ListNode add(ListNode other) {
            int temp = this.val + other.val;
            int digit = temp % 10;
            int carry = temp / 10;

            if (next == null) {
                if (other.next == null) {
                    if (carry == 0) {
                        return new ListNode(digit);
                    } else {
                        return new ListNode(digit, new ListNode(carry));
                    }

                } else {
                    // this.nex is null AND other.next is not null
                    ListNode newNext = other.next.add(new ListNode(carry));
                    return new ListNode(digit, newNext);

                }
            } else {
                // this.next is not null
                if (other.next == null) {
                    ListNode newNext = this.next.add(new ListNode(carry));
                    return new ListNode(digit, newNext);

                } else {
                    // this.nex is not null AND other.next is not null
                    ListNode newNext = this.next.add(other.next).add(new ListNode(carry));
                    return new ListNode(digit, newNext);

                }
            }
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "" + val + " -> " + next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListNode listNode = (ListNode) o;
            return val == listNode.val &&
                    Objects.equals(next, listNode.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, next);
        }
    }

}
