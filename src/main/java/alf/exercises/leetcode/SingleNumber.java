package alf.exercises.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a non-empty array of integers, every element appears three times except for one, which appears exactly once. Find that single one.
 *
 * Note:
 *
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 *
 * Example 1:
 *
 * Input: [2,2,3,2]
 * Output: 3
 *
 * Example 2:
 *
 * Input: [0,1,0,1,0,1,99]
 * Output: 99
 */
public class SingleNumber {
    public int singleNumber(int[] nums) {

        Map<Integer, Integer> count = new HashMap<>();

        for (int i : nums) {
            if (count.containsKey(i)) {
                if (count.get(i).equals(2)) {
                    count.remove(i);
                } else {
                    count.put(i, count.get(i) + 1);
                }
            } else {
                count.put(i, 1);
            }
        }


        return count.keySet().iterator().next();
    }
}
