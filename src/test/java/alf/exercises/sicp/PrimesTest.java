package alf.exercises.sicp;

import alf.Pair;
import alf.exercises.sicp.Primes;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PrimesTest {

    @Test
    public void isPrime() {

        Assert.assertTrue(Primes.isPrime(1));
        Assert.assertTrue(Primes.isPrime(2));
        Assert.assertTrue(Primes.isPrime(3));
        Assert.assertFalse(Primes.isPrime(4));
        Assert.assertTrue(Primes.isPrime(5));
        Assert.assertFalse(Primes.isPrime(10));
        Assert.assertTrue(Primes.isPrime(11));
    }

    @Test
    public void isPrimeSum() {

        Assert.assertTrue(Primes.isPrimeSum(1, 2));
        Assert.assertFalse(Primes.isPrimeSum(1, 3));
    }

    @Test
    public void isPrimeSum_pair() {

        Assert.assertTrue(Primes.isPrimeSum(new Pair<>(1, 2)));
        Assert.assertFalse(Primes.isPrimeSum(new Pair<>(5, 5)));
    }

    @Test
    public void primeSumPairs() {
        List<List<Integer>> result = Primes.primeSumPairs(6);
        System.out.println(result);
        Assert.assertEquals(7, result.size());
        Assert.assertEquals(Arrays.asList(2, 1, 3), result.get(0));
        Assert.assertEquals(Arrays.asList(6, 5, 11), result.get(result.size() - 1));
    }
}