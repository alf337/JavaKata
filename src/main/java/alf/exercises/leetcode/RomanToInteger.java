package alf.exercises.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 * <p>
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * <p>
 * For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.
 * <p>
 * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:
 * <p>
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * <p>
 * Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.
 * <p>
 * Example 1:
 * <p>
 * Input: "III"
 * Output: 3
 * <p>
 * Example 2:
 * <p>
 * Input: "IV"
 * Output: 4
 * <p>
 * Example 3:
 * <p>
 * Input: "IX"
 * Output: 9
 * <p>
 * Example 4:
 * <p>
 * Input: "LVIII"
 * Output: 58
 * Explanation: L = 50, V= 5, III = 3.
 * <p>
 * Example 5:
 * <p>
 * Input: "MCMXCIV"
 * Output: 1994
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 */
public class RomanToInteger {

    public String intToRoman(int num) {
        int remain = num;
        StringBuilder sb = new StringBuilder();
        List<Numeral> sortedNumerals = Numeral.getSortedList();

        for (Numeral numeral : sortedNumerals) {
            while (remain > 0 && remain >= numeral.value) {
                sb.append(numeral.name());
                remain -= numeral.value;
            }
        }

        return sb.toString();
    }

    /*
     * string replacement method (no math)
     */
    public String intToRomanV2(int num) {

        return IntStream.range(0, num).parallel()
                .mapToObj(n -> "I")
                .reduce(String::concat)
                .orElse("")
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C")
                .replace("LXL", "XC")
                .replace("CCCCC", "D")
                .replace("CCCC", "CD")
                .replace("DD", "M")
                .replace("DCD", "CM");
    }


        public int romanToInt(String s) {
        int sum = 0;
        Numeral prev = Numeral.Z;

        for (int i = 0; i < s.length(); i++) {
            Numeral numeral = Numeral.from(s, i);

            Optional<Integer> combinedVal = numeral.combinedValue(prev);
            if (combinedVal.isPresent()) {
                sum -= prev.value;
                sum += combinedVal.get();
                prev = Numeral.Z;
            } else {
                sum += numeral.value;
                prev = numeral;
            }
        }

        return sum;
    }

    enum Numeral {

        Z(0),
        I(1),
        IV(4),
        V(5),
        IX(9),
        X(10),
        XL(40),
        L(50),
        XC(90),
        C(100),
        CD(400),
        D(500),
        CM(900),
        M(1000);

        private final int value;

        Numeral(int value) {
            this.value = value;
        }

        Optional<Integer> combinedValue(Numeral other) {
            String combined = other.name() + this.name();
            for (Numeral special : Numeral.values()) {
                if (special.name().equals(combined)) {
                    return Optional.of(special.value);
                }
            }
            return Optional.empty();
        }

        static Numeral from(String s, int index) {
            try {
                String s1 = String.valueOf(s.toUpperCase().charAt(index));
                for (Numeral n : Numeral.values()) {
                    if (n.name().equals(s1)) {
                        return n;
                    }
                }
                return Z;
            } catch (Exception e) {
                return Z;
            }
        }

        static List<Numeral> getSortedList() {
            List<Numeral> sortedList = new ArrayList<>();
            for (Numeral n : Numeral.values()) {
                sortedList.add(n);
            }
            Collections.sort(sortedList, (a, b) -> b.value - a.value);

            return sortedList;
        }
    }
}
