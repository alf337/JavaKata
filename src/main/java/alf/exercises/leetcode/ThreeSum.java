package alf.exercises.leetcode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 *
 * The solution set must not contain duplicate triplets.
 *
 * Example:
 *
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 *
 * A solution set is:
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 */
public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {

        return  IntStream.range(0, nums.length).parallel().boxed()
                .flatMap(i -> IntStream.range(i + 1, nums.length).parallel().boxed()
                        .flatMap(j -> IntStream.range(j + 1, nums.length).parallel()
                                .filter(k -> nums[i] + nums[j] + nums[k] == 0)
                                .mapToObj(k -> littleSort(nums[i], nums[j], nums[k]))
                                .map(a -> Arrays.asList(a[0], a[1], a[2]))))
                .distinct()
                .collect(Collectors.toList());
    }

    protected int[] littleSort(int x, int y, int z) {
        int[] a = new int[]{x, y, z};

        if (a[0] > a[1]) {
            int a0 = a[0];
            a[0] = a[1];
            a[1] = a0;
        }
        if (a[1] > a[2]) {
            int a1 = a[1];
            a[1] = a[2];
            a[2] = a1;
        }
        if (a[0] > a[1]) {
            int a0 = a[0];
            a[0] = a[1];
            a[1] = a0;
        }
        return a;
    }

    public List<List<Integer>> threeSumV3(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                for (int k = j+1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        int[] a = littleSort(nums[i], nums[j], nums[k]);
                        List<Integer> list = Arrays.asList(a[0], a[1], a[2]);
                        if (!result.contains(list)) result.add(list);
                    }
                }
            }
        }
        return result;
    }

    public List<List<Integer>> threeSumV2(int[] nums) {

        Set<List<Integer>> result = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                for (int k = j+1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> list = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(list);
                        result.add(list);
                    }
                }
            }
        }

        return new ArrayList<>(result);
    }

    public List<List<Integer>> threeSumV1(int[] nums) {

        Set<Triplet> result = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                for (int k = j+1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        result.add(Triplet.of(nums[i], nums[j], nums[k]));
                    }
                }
            }
        }
        return result.stream()
                .map( t -> t.list)
                .collect(Collectors.toList());

    }

    public static class Triplet {
        List<Integer> list;

        private Triplet(int a, int b, int c) {
            this.list = Arrays.asList(a, b, c);
            Collections.sort(this.list);
        }
        public static Triplet of(int a, int b, int c) {
            return new Triplet(a, b, c);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Triplet other = (Triplet) o;
            return this.list.equals(other.list);
        }

        @Override
        public int hashCode() {
            return Objects.hash(list);
        }
    }
}
