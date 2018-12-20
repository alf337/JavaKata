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
public class ContainerWithMostWater {

    public int maxArea(int[] height) {
        int max = 0;
        List<Pair<Integer, Integer>> pairs = buildPairs(height);

        for (Pair<Integer, Integer> a : pairs) {
            for (Pair<Integer, Integer> b : pairs) {
                if (!a.equals(b)) {
                    int y = a.y() < b.y() ? a.y() : b.y();
                    int x = Math.abs(a.x() - b.x());
                    int area = x * y;
                    if (area > max) {
                        max = area;
                    }
                }
            }
        }
        return max;
    }

    private List<Pair<Integer, Integer>> buildPairs(int[] y) {
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        for (int x =0; x < y.length; x++) {
            pairs.add(Pair.of(x + 1, y[x]));
        }
        return pairs;
    }

    public static class Pair<L, R> extends AbstractMap.SimpleImmutableEntry<L, R> implements Comparable<Pair> {

        public Pair(L x, R y) {
            super(x, y);
        }

        public static <L, R> Pair<L, R> of(L x, R y) {
            return new Pair<>(x, y);
        }

        public L x() {
            return this.getKey();
        }

        public R y() {
            return this.getValue();
        }

        public String toString() {
            return String.format("[%s,%s]", x(), y());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair other = (Pair) o;
            return x() == other.x() &&
                    y() == other.y();
        }

        @Override
        public int hashCode() {
            return Objects.hash(x(), y());
        }

        @Override
        public int compareTo(Pair other) {
            return this.hashCode() - other.hashCode();
        }

    }

}
