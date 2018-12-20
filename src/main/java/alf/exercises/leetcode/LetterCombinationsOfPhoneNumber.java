package alf.exercises.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LetterCombinationsOfPhoneNumber {
    public List<String> letterCombinations(String digits) {

        List<String> result = new ArrayList<>();
        if (digits.isEmpty()) return result;

        char[][] charGroups = initCharGroups(digits);
        int[] index = initIndexes(charGroups);
        int[] max = initMax(charGroups);

        while (index[0] < max[0]) {

            StringBuilder sb = new StringBuilder();
            for (int g = 0; g < charGroups.length; g++) {
                sb.append(charGroups[g][index[g]]);
            }
            result.add(sb.toString());
            incrementIndex(index, max);
        }

        return result;
    }

    private void incrementIndex(int[] index, int[] max) {
        int g = index.length - 1;
        boolean done = false;
        while (!done) {
            index[g] += 1;
            if (index[g] >= max[g]) {
                if (g == 0) {
                    done = true;
                } else {
                    index[g] = 0;
                    g -= 1;
                }
            } else {
                done = true;
            }
        }
    }

    private char[][] initCharGroups(String digits) {
        char[] charDigits = digits.toCharArray();
        char[][] charGroups = new char[charDigits.length][];

        for (int d = 0; d < charDigits.length; d++) {
            charGroups[d] = PhonePad.getChars(charDigits[d]);
        }

        return removeEmptyGroups(charGroups);
    }

    private char[][] removeEmptyGroups(char[][] charGroups) {
        int emptyGroups = 0;
        for (char[] charGroup : charGroups) {
            if (charGroup.length == 0) {
                emptyGroups++;
            }
        }

        if (emptyGroups > 0) {
            char[][] copyCharGroups = new char[charGroups.length - emptyGroups][];
            int i = 0;
            for (char[] charGroup : charGroups) {
                if (charGroup.length != 0) {
                    copyCharGroups[i] = charGroup;
                    i++;
                }
            }
            return copyCharGroups;
        } else {
            return charGroups;
        }
    }

    private int[] initIndexes(char[][] charGroups) {
        int[] index = new int[charGroups.length];
        Arrays.fill(index, 0);
        return index;
    }

    private int[] initMax(char[][] charGroups) {
        int[] max = new int[charGroups.length];
        for (int i = 0; i < charGroups.length; i++) {
            max[i] = charGroups[i].length;
        }
        return max;
    }

    enum PhonePad {

        ZERO('0', ""),
        ONE('1', ""),
        TWO('2', "abc"),
        THREE('3', "def"),
        FOUR('4', "ghi"),
        FIVE('5', "jkl"),
        SIX('6', "mno"),
        SEVEN('7', "pqrs"),
        EIGHT('8', "tuv"),
        NINE('9', "wxyz"),
        UNKNOWN('?', "");

        private char digit;
        private char[] chars;

        PhonePad(char digit, String s) {
            this.digit = digit;
            this.chars = s.toCharArray();
        }

        static char[] getChars(char d) {
            for (PhonePad p : PhonePad.values()) {
                if (p.digit == d) return p.chars;
            }
            return PhonePad.UNKNOWN.chars;
        }
    }
}
