package alf.exercises.leetcode;

/**
 * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
 *
 * Example 1:
 *
 * Input: 121
 * Output: true
 *
 * Example 2:
 *
 * Input: -121
 * Output: false
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
 *
 * Example 3:
 *
 * Input: 10
 * Output: false
 * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 */
public class PalindromeNumber {

    public boolean isPalindrome(int x) {
        return isPalindrome(String.valueOf(x));
    }


    private boolean isPalindrome(String s) {

        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }
}
