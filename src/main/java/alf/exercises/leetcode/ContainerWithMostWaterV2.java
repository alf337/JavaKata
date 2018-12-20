package alf.exercises.leetcode;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical
 * lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
 * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 * Note: You may not slant the container and n is at least 2.
 * Example:
 * Input: [1,8,6,2,5,4,8,3,7]
 * Output: 49
 */
public class ContainerWithMostWaterV2 {

    public int maxArea(int[] height) {
        int max = 0;

        for (int i = 0; i < height.length; i++) {
            for (int j = 0; j < height.length; j++) {
                if (i != j) {
                    int jx = j + 1;
                    int y = height[i] < height[j] ? height[i] : height[j];
                    int x = Math.abs(i - j);
                    int area = x * y;
                    if (area > max) {
                        max = area;
                    }
                }
            }
        }
        return max;
    }
}
