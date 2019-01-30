package alf.exercises.leetcode;

/**
 * Implement pow(x, n), which calculates x raised to the power n (xn).
 *
 * Example 1:
 *
 * Input: 2.00000, 10
 * Output: 1024.00000
 *
 * Example 2:
 *
 * Input: 2.10000, 3
 * Output: 9.26100
 *
 * Example 3:
 *
 * Input: 2.00000, -2
 * Output: 0.25000
 * Explanation: 2-2 = 1/22 = 1/4 = 0.25
 *
 * Note:
 *
 *     -100.0 < x < 100.0
 *     n is a 32-bit signed integer, within the range [−231, 231 − 1]
 */
public class PowXn {
    public double myPow(double x, int n) {

        if (n == 0) {
            return 1.0;
        } else {

            if (Math.abs(1.00000 - x) < 0.000001) {
                return x;
            }

            if (Math.abs(1.00000 + x) < 0.000001) {
                if (n % 2 == 0) {
                    return 1.00000;
                } else {
                    return -1.00000;
                }
            }

            if (n > 0) {
                double result = 1.0;

                for (int i = 0; i < n; i++) {
                    result *= x;
                }

                return result;
            } else {

                // n < 0
                double result = 1.0;

                for (int i = 0; i > n; i--) {
                    result *= x;
                }

                return 1/result;
            }
        }
    }

    public double myPow_v1(double x, int n) {
        return Math.pow(x, Double.valueOf(n));
    }
}
