package alf.exercises.leetcode;

public class ReverseInteger {


    /*
     * this is the recursive version
     */
    public static int reverse(int x) {

        return reverse1(x);
    }

    private static int reverse1(int x) {

        long result = reverse1Inner(0, x);
        return result > Integer.MAX_VALUE || result < Integer.MIN_VALUE ? 0 : (int)result;
    }

    private static long reverse1Inner(long result, long n) {

        if (n == 0) {
            return result;
        }

        return reverse1Inner((result * 10) + (n % 10), (n / 10));
    }

    /*
     * This is the non-recursive version
     */
    public static int reverse2(int x) {

        long result = 0;
        int n = x;

        while (n != 0) {
            result = (result * 10) + (n % 10);
            n = (n / 10);
        }
        return result > Integer.MAX_VALUE || result < Integer.MIN_VALUE ? 0 : (int)result;
    }


}
