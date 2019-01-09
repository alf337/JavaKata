package alf.exercises.leetcode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * You have a number of envelopes with widths and heights given as a pair of integers (w, h). One envelope can fit into another if and only if both the width and height of one envelope is greater than the width and height of the other envelope.
 *
 * What is the maximum number of envelopes can you Russian doll? (put one inside other)
 *
 * Note:
 * Rotation is not allowed.
 *
 * Example:
 *
 * Input: [[5,4],[6,4],[6,7],[2,3]]
 * Output: 3
 * Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 */
public class RussianDollEnvelopesV1 {
    public int maxEnvelopes(int[][] envelopes) {

        if (envelopes == null || envelopes.length == 0) return 0;

        List<Env> envList = new ArrayList<>();
        int maxNest = 1;

        for (int i = 0; i < envelopes.length; i++) {
            if (envelopes[i].length == 2) {
                envList.add(Env.of(envelopes[i][0], envelopes[i][1]));
            }
        }

        if (envList.size() < 1) return 0;

        // sort by height then width

        envList.sort((o1, o2) -> {
                    int w = Integer.compare(o1.height, o2.height);
                    if (w == 0) {
                        return Integer.compare(o1.width, o2.width);
                    } else {
                        return w;
                    }
                }
        );

        System.out.println(envList);

        for (int skip = 0; skip < envList.size() - 1; skip++) {
            int nest = nest1(skip, envList);
            if (nest > maxNest) {
                maxNest = nest;
            }
        }


        // sort by width then height --------------------------------------------------------------------

        envList.sort((o1, o2) -> {
            int w = Integer.compare(o1.width, o2.width);
            if (w == 0) {
                return Integer.compare(o1.height, o2.height);
            } else {
                return w;
            }
        });

        System.out.println(envList);

        for (int skip = 0; skip < envList.size() - 1; skip++) {
            int nest = nest2(skip, envList);
            if (nest > maxNest) {
                maxNest = nest;
            }
        }

        return maxNest;
    }

    private int nest1(int skip, List<Env> envList) {

        int count = 1;
        Iterator<Env> envIterator = envList.iterator();
        Env prev = envIterator.next();
        for (int i = 0; i < skip; i++) {
            if (envIterator.hasNext()) {
                prev = envIterator.next();
            }
        }

        while (envIterator.hasNext()) {
            Env env = envIterator.next();

            if (env.height > prev.height && env.width > prev.width) {
                count++;
                prev = env;
            }
        }
        System.out.println("nest1: skip=" + skip + ", count=" + count);
        return count;
    }

    private int nest2(int skip, List<Env> envList) {

        int count = 1;
        Iterator<Env> envIterator = envList.iterator();
        Env prev = envIterator.next();
        for (int i = 0; i < skip; i++) {
            if (envIterator.hasNext()) {
                prev = envIterator.next();
            }
        }

        while (envIterator.hasNext()) {
            Env env = envIterator.next();

            if (env.width > prev.width && env.height > prev.height) {
                count++;
                prev = env;
            }
        }
        System.out.println("nest2: skip=" + skip + ", count=" + count);
        return count;
    }

    public static class Env {
        int height;
        int width;

        private Env(int h, int w) {
            this.height = h;
            this.width = w;
        }

        public static Env of(int h, int w) {
            return new Env(h, w);
        }

       @Override
        public String toString() {
            return "{" +
                    height +
                    ", " + width +
                    "}\n";
        }
    }
}
