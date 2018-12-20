package alf.exercises.leetcode;

/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 * <p>
 * Example 1:
 * <p>
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 * <p>
 * Example 2:
 * <p>
 * Input: "cbbd"
 * Output: "bb"
 */
public class LongestPalindromicSubstring {

    public String longestPalindrome(String s) {
        if (isPalindrome(s)) return s;
        String longest = "";

        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = i + 1; j < s.length(); j++) {
                String temp = s.substring(i, j);
                if (isPalindrome(temp)) {
                    if (temp.length() > longest.length()) {
                        longest = temp;
                    }
                }
            }
        }

        for (int j = s.length(); j >= 1; j--) {
            for (int i = j - 1; i >= 0; i--) {
                String temp = s.substring(i, j);
                if (isPalindrome(temp)) {
                    if (temp.length() > longest.length()) {
                        longest = temp;
                    }
                }
            }
        }

        return longest;
    }

    protected boolean isPalindrome(String s) {

        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }
}
