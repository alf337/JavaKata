package alf.exercises.leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 *
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 *
 * You may assume nums1 and nums2 cannot be both empty.
 *
 * Example 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * The median is 2.0
 *
 * Example 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * The median is (2 + 3)/2 = 2.5
 */
public class MedianTwoSortedArrays {

    public double median(int[] nums1, int[] nums2) {

        List<Integer> integerList = IntStream.of(nums1)
                .parallel()
                .boxed()
                .collect(Collectors.toList());

        integerList.addAll(IntStream.of(nums2)
                .parallel()
                .boxed()
                .collect(Collectors.toList()));

        Collections.sort(integerList);

        return compute(findMiddleElement(integerList));
    }

    private List<Integer> findMiddleElement(List<Integer> list) {
        if (list.size() == 1) return list;
        if (list.size() % 2 == 0) {
            // even, pull out two middle elements
            int m = list.size() / 2;
            return list.subList(m -1, m + 1);
        } else {
            // odd, pull one middle element
            int m = list.size() / 2;
            return list.subList(m, m + 1);
        }
    }

    private double compute(List<Integer> list) {
        if (list.size() == 1) {
            return list.get(0).doubleValue();
        } else {
            return ((list.get(0).doubleValue() + list.get(1).doubleValue()) / 2.0);
        }
    }

}
