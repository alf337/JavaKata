package alf.exercises.sicp;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Factorial {

    public static long f1(Number number) {
        int n = number.intValue();
        if (n == 0) return 0L;
        long product = 1L;

        for (int i = 1; i <= n; i++) {
            product = product * i;
        }

        return product;
    }

    // ----------------------------------------

    public static long f2(Number number) {
        return f2Iter(number.intValue());
    }

    private static long f2Iter(int n) {
        if (n == 0) return 0L;
        if (n == 1) return 1L;
        return n * f2Iter(n - 1);
    }

    //---------------------------------

    public static long f3(Number number) {
        return f3Iter(1L, 1, number.intValue());
    }

    private static long f3Iter(long product, int count, int n) {
        if (n == 0) return 0L;
        if (count > n) return product;
        return f3Iter(product * count, count + 1, n);
    }

    //---------------------------------

    private interface TriFunction {
        long apply(long p, int c, int n);
    }

    public static long f4(Number number) {

        TriFunction triFn = new TriFunction() {
            @Override
            public long apply(long product, int count, int n) {
                if (n == 0) return 0L;
                if (count > n) return product;
                return apply(product * count, count + 1, n);
            }
        };

        return triFn.apply(1L, 1, number.intValue());
    }

    // ----------------------------------------
    public static long f5(Number number) {
        int n = number.intValue();
        if (n == 0) return 0L;

        return LongStream.range(1, n + 1)
                .reduce(1, (product, count) -> product * count);
    }

}
