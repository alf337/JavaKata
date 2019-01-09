package alf.exercises.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * In a string S of lowercase letters, these letters form consecutive groups of the same character.
 *
 * For example, a string like S = "abbxxxxzyy" has the groups "a", "bb", "xxxx", "z" and "yy".
 *
 * Call a group large if it has 3 or more characters.  We would like the starting and ending positions of every large group.
 *
 * The final answer should be in lexicographic order.
 *
 *
 *
 * Example 1:
 *
 * Input: "abbxxxxzzy"
 * Output: [[3,6]]
 * Explanation: "xxxx" is the single large group with starting  3 and ending positions 6.
 *
 * Example 2:
 *
 * Input: "abc"
 * Output: []
 * Explanation: We have "a","b" and "c" but no large group.
 *
 * Example 3:
 *
 * Input: "abcdddeeeeaabbbcd"
 * Output: [[3,5],[6,9],[12,14]]
 *
 *
 *
 * Note:  1 <= S.length <= 1000
 */
public class PositionsOfLargeGroupsV1 {

    public List<List<Integer>> largeGroupPositions(String S) {

        List<List<Integer>> groups = getGroups(S);

        Collections.sort(groups, (o1, o2) -> o1.get(0) - o2.get(0));

        return groups;
    }

    protected List<List<Integer>> getGroups(String s) {
        List<List<Integer>> groups = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Character prev = null;
        int index = 0;
        int start = 0;

        for (Character c : s.toCharArray()) {

            if (!c.equals(prev)) {
                if (sb.length() > 2) {
                    groups.add(Arrays.asList(start, index - 1));
                }
                sb = new StringBuilder();
                start = index;
            }

            sb.append(c);

            prev = c;
            index++;
        }

        if (sb.length() > 2) {
            groups.add(Arrays.asList(start, index - 1));
        }

        return groups;
    }
}
