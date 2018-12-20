package alf.exercises.leetcode;

import java.util.Arrays;

/**
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 *
 * Example 1:
 *
 * Input: [1,2,3,4,5,6,7] and k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 *
 * Example 2:
 *
 * Input: [-1,-100,3,99] and k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 */
public class RotateArray {
    public void rotate(int[] nums, int k) {
        int[] result = rotateInner(nums, k);
        for (int i = 0; i < nums.length; i++) {
            nums[i] = result[i];
        }
    }

    protected int[] rotateInner(int[] nums, int k) {
        if (k == 0) {
            return nums;
        } else {
            return rotateInner(rotateOne(nums), k - 1);
        }
    }

    private int[] rotateOne(int[] nums) {
        int[] result = new int[nums.length];
        result[0] = nums[nums.length - 1];
        for (int i = 1; i < nums.length; i++) {
            result[i] = nums[i - 1];
        }
        return result;
    }


    protected int[] rotateClojure(int[] nums, int k) {

        if (k == 0) {
            return nums;
        } else {
            return rotateClojure(cons(last(nums), butLast(nums)), k - 1);
        }
    }

    private int last(int[] y) {
        return y[y.length - 1];
    }

    private int[] butLast(int[] y) {
        int[] result = new int[y.length - 1];
        for (int i = 0; i < y.length - 1; i++) {
            result[i] = y[i];
        }
        return result;
    }

    private int[] cons(int x, int[] y) {
        int[] result = new int[y.length + 1];
        result[0] = x;
        for (int i = 1, j=0; j < y.length; i++,j++) {
            result[i] = y[j];
        }
        return result;
    }
}
