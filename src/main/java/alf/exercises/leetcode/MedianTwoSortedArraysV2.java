package alf.exercises.leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * <p>
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 * <p>
 * You may assume nums1 and nums2 cannot be both empty.
 * <p>
 * Example 1:
 * <p>
 * nums1 = [1, 3]
 * nums2 = [2]
 * <p>
 * The median is 2.0
 * <p>
 * Example 2:
 * <p>
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * <p>
 * The median is (2 + 3)/2 = 2.5
 */
public class MedianTwoSortedArraysV2 {

    /**
     * Version 2 is faster than the first.
     */
    public double median(int[] nums1, int[] nums2) {

        int[] merged = merge(nums1, nums2);

        return compute(findMiddleElement(merged));
    }

    private int[] merge(int[] a, int[] b) {

//        System.out.println(Arrays.toString(a));
//        System.out.println(Arrays.toString(b));

        int[] c = new int[(a.length + b.length)];
        int ax = 0;
        int bx = 0;
        int cx = 0;
        boolean aHasMore = hasMore(a, ax);
        boolean bHasMore = hasMore(b, bx);

        while (aHasMore || bHasMore) {

            if (aHasMore && bHasMore) {
                //both
                if (a[ax] < b[bx]) {
                    c[cx++] = a[ax++];
                } else {
                    c[cx++] = b[bx++];
                }

            } else {
                if (aHasMore) {
                    // only a
                    c[cx++] = a[ax++];
                } else {
                    // only b
                    c[cx++] = b[bx++];
                }
            }
            aHasMore = hasMore(a, ax);
            bHasMore = hasMore(b, bx);
        }

//        System.out.println("merged " + Arrays.toString(c));
        return c;
    }

    private boolean hasMore(int[] array, int index) {
        return index < array.length;
    }

    private int[] findMiddleElement(int[] list) {
//        System.out.println("find middle " + Arrays.toString(list));
        if (list.length == 1) return list;
        if (list.length % 2 == 0) {
            // even, pull out two middle elements
            int m = list.length / 2;
            return new int[]{list[m - 1], list[m]};
        } else {
            // odd, pull one middle element
            int m = list.length / 2;
            return new int[]{list[m]};
        }
    }

    private double compute(int[] list) {
//        System.out.println("compute " + Arrays.toString(list));
        if (list.length == 1) {
            return (double) list[0];
        } else {
            return ((list[0] + list[1]) / 2.0);
        }
    }

}
