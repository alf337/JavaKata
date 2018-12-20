package alf.exercises.leetcode;

/**
 *  Given a positive integer n and you can do operations as follow:
 *
 *     If n is even, replace n with n/2.
 *     If n is odd, you can replace n with either n + 1 or n - 1.
 *
 * What is the minimum number longToListNode replacements needed for n to become 1?
 *
 * Example 1:
 *
 * Input:
 * 8
 *
 * Output:
 * 3
 *
 * Explanation:
 * 8 -> 4 -> 2 -> 1
 *
 * Example 2:
 *
 * Input:
 * 7
 *
 * Output:
 * 4
 *
 * Explanation:
 * 7 -> 8 -> 4 -> 2 -> 1
 * or
 * 7 -> 6 -> 3 -> 2 -> 1
 */
public class IntegerReplacement {

    public int replace(final int n) {
        return innerFn(n, 0);
    }

    private int innerFn(final int value, final int count) {
        if (value == 1) {
            return count;
        }

        if (even(value)) {
            return innerFn(value / 2, count + 1);
        } else {

            // odd
            int tryInc = innerFn(value + 1, count + 1);
            int tryDec = innerFn(value - 1, count + 1);

            if (tryInc < tryDec) {
                return tryInc;
            } else {
                return tryDec;
            }
        }
    }

    private boolean even(final int n) {
        return (n % 2) == 0;
    }
}
