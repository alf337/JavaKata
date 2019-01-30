package alf.exercises.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Given an array of integers A, a move consists of choosing any A[i], and incrementing it by 1.
 *
 * Return the least number of moves to make every value in A unique.
 *
 *
 *
 * Example 1:
 *
 * Input: [1,2,2]
 * Output: 1
 * Explanation:  After 1 move, the array could be [1, 2, 3].
 *
 * Example 2:
 *
 * Input: [3,2,1,2,1,7]
 * Output: 6
 * Explanation:  After 6 moves, the array could be [3, 4, 1, 2, 5, 7].
 * It can be shown with 5 or less moves that it is impossible for the array to have all unique values.
 *
 *
 *
 * Note:
 *
 *     0 <= A.length <= 40000
 *     0 <= A[i] < 40000
 */
public class MinimumIncrementToMakeUniqueArray {

    public int minIncrementForUnique(int[] A) {
        int count = 0;
        Set<Integer> integerSet = new HashSet<>();

        for (Integer i : A) {
            while (integerSet.contains(i)) {
                count++;
                i++;
            }
            integerSet.add(i);
        }

        return count;
    }
}