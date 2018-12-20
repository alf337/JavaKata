package alf.exercises.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Implement atoi which converts a string to an integer.
 *
 * The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.
 *
 * The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.
 *
 * If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.
 *
 * If no valid conversion could be performed, a zero value is returned.
 *
 * Note:
 *
 *     Only the space character ' ' is considered as whitespace character.
 *     Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of representable values, INT_MAX (231 − 1) or INT_MIN (−231) is returned.
 *
 * Example 1:
 *
 * Input: "42"
 * Output: 42
 *
 * Example 2:
 *
 * Input: "   -42"
 * Output: -42
 * Explanation: The first non-whitespace character is '-', which is the minus sign.
 *              Then take as many numerical digits as possible, which gets 42.
 *
 * Example 3:
 *
 * Input: "4193 with words"
 * Output: 4193
 * Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
 *
 * Example 4:
 *
 * Input: "words and 987"
 * Output: 0
 * Explanation: The first non-whitespace character is 'w', which is not a numerical
 *              digit or a +/- sign. Therefore no valid conversion could be performed.
 *
 * Example 5:
 *
 * Input: "-91283472332"
 * Output: -2147483648
 * Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
 *              Thefore INT_MIN (−231) is returned.
 */
public class StringToIntegerAtoi {

    Set<Character> digits = new HashSet<>(Arrays.asList('9','8','7','6','5','4','3','2','1','0'));
    Set<Character> special = new HashSet<>(Arrays.asList('.',',','-','+'));

    public int atoi(String s) {

        String t3 = getDigits(s);

        try {
            return Integer.valueOf(t3);
        } catch (NumberFormatException e) {
            if (containsOnlyDigits(t3)) {
                if (t3.startsWith("-")) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            } else {
                return 0;
            }
        }
    }

    protected String getDigits(String s) {
        StringBuilder sb = new StringBuilder();
        boolean firstFound = false;
        boolean signFound = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (digits.contains(c)) {
                sb.append(c);
                firstFound = true;
            } else {
                if (c == ' ' && !firstFound) {
                    // nothing
                } else {
                    if (c == '-' || c == '+') {
                        if (firstFound || signFound) {
                            return sb.toString();
                        } else {
                            sb.append(c);
                            signFound = true;
                            firstFound = true;
                        }
                    } else {
                        return sb.toString();
                    }
                }
            }
        }
        return sb.toString();
    }

    protected boolean containsOnlyDigits(String input) {
        String s = input.startsWith("-") ? input.substring(1) : input;
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!digits.contains(s.charAt(i))) return false;
        }
        return true;
    }

/*
    protected int firstWhiteSpace(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!digits.contains(c) && !special.contains(c))  return i;
        }
        return s.length();
    }
    protected int firstNonWhiteSpace(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') return i;
        }
        return s.length();
    }
*/
}
