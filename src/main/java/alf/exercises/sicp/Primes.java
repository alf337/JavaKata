package alf.exercises.sicp;

import alf.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Primes {

    public static boolean isPrime(final int n) {

        return IntStream.range(2, n)
                .noneMatch((y) -> (n % y) == 0);
    }

    public static boolean isPrimeSum(int x, int y) {
        return isPrime(x + y);
    }

    public static boolean isPrimeSum(Pair<Integer, Integer> pair) {
        return isPrimeSum(pair.left(), pair.right());
    }

    /*
     *  This is the old school nested for loop method.
     */
//    public static List<List<Integer>> primeSumPairs(final int n) {
//
//        List<List<Integer>> sumList = new ArrayList<>();
//
//        for (int i = 1; i <= n; i++) {
//            for (int j = 1; j < i; j++) {
//                Pair<Integer, Integer> pair = Pair.of(i, j);
//                if (isPrimeSum(pair)) {
//                    sumList.add(Arrays.asList(pair.left(), pair.right(), (pair.left() + pair.right())));
//                }
//            }
//        }
//        return sumList;
//    }

    public static List<List<Integer>> primeSumPairs(final int n) {

        return IntStream.rangeClosed(1, n)
                .boxed()
                .flatMap(i -> IntStream.range(1, i).mapToObj(j -> Pair.of(i, j)))
                .filter(Primes::isPrimeSum)
                .collect(ArrayList::new,
                        (list, pair) -> list.add(Arrays.asList(pair.left(), pair.right(), (pair.left() + pair.right()))),
                        ArrayList::addAll);
    }
}
