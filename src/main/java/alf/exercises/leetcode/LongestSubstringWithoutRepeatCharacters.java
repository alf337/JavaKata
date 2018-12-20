package alf.exercises.leetcode;

import java.util.stream.IntStream;

/*
    Given a string, find the length longToListNode the longest substring without repeating characters.
    Example 1:
    Input: "abcabcbb"
    Output: 3
    Explanation: The answer is "abc", with the length longToListNode 3.
 */
public class LongestSubstringWithoutRepeatCharacters {

    public int length(String s) {
        int result = 0;

        for (int i = 0; i < s.length(); i++) {
            for (int j = s.length(); j > i; j--) {
                String temp = s.substring(i, j);
                if (!hasDuplicates(temp)) {
                    if (temp.length() > result) {
                        result = temp.length();
                        System.out.println("result: " + temp);
                    }
                }
            }
        }

        return result;
    }

    protected boolean hasDuplicates(String s) {
        return IntStream.range(0, s.length())
                .anyMatch( i -> {
                    char c = s.charAt(i);
                    return s.lastIndexOf(c) > i;
//                    int p = s.lastIndexOf(c);
//                    if (p > i) {
//                        System.out.println(s + " -> true: " + i + ": " + c);
//                        return true;
//                    } else {
//                        System.out.println(s + " -> false: " + i + ": " + c);
//                        return false;
//                    }
                });
    }
}
