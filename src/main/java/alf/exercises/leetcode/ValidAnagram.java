package alf.exercises.leetcode;

import java.util.List;
import java.util.stream.Collectors;

public class ValidAnagram {

    public boolean isAnagram(String s, String t) {

        if (s == null || t == null || s.length() != t.length()) return false;

        List<Character> tlist = t.chars()
                .mapToObj( ch -> (char) ch)
                .collect(Collectors.toList());

        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (tlist.contains(c)) {
                tlist.remove(c);
            } else {
                return false;
            }
        }
        return true;
    }
}
