package alf.exercises.sicp;

import java.util.OptionalDouble;
import java.util.stream.Stream;

public class SquareRoot {

    public static double sqrt(Number n) {

        if (n == null || n.doubleValue() == 0.0) return 0.0;
        return sqrtIter(1.0, n.doubleValue());
    }

    private static double sqrtIter(double guess, double x) {
        if (goodEnough(guess, x)) {
            return guess;
        } else {
            return sqrtIter(improve(guess, x), x);
        }
    }

    private static boolean goodEnough(double guess, double x) {
        return approxEqual(Math.pow(guess, 2), x, 0.001);
    }

    private static boolean approxEqual(double a, double b, double tolerance) {
        return (Math.abs(a - b) < tolerance);
    }

    private static double improve(double guess, double x) {
        return avg(guess, (x / guess));
    }

    protected static double avg(Number... n) {

        OptionalDouble average = Stream.of(n)
                .mapToDouble(Number::doubleValue)
                .average();

        return average.orElse(0.0);
    }
}
