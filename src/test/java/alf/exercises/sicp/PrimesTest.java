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

        assertTrue(Primes.isPrime(1));
        assertTrue(Primes.isPrime(2));
        assertTrue(Primes.isPrime(3));
        assertFalse(Primes.isPrime(4));
        assertTrue(Primes.isPrime(5));
        assertFalse(Primes.isPrime(10));
        assertTrue(Primes.isPrime(11));
    }

    @Test
    public void isPrimeSum() {

        assertTrue(Primes.isPrimeSum(1, 2));
        assertFalse(Primes.isPrimeSum(1, 3));
    }

    @Test
    public void isPrimeSum_pair() {

        assertTrue(Primes.isPrimeSum(new Pair<>(1, 2)));
        assertFalse(Primes.isPrimeSum(new Pair<>(5, 5)));
    }

    @Test
    public void primeSumPairs() {
        List<List<Integer>> result = Primes.primeSumPairs(6);
        System.out.println(result);
        assertEquals(7, result.size());
        assertEquals(Arrays.asList(2, 1, 3), result.get(0));
        assertEquals(Arrays.asList(6, 5, 11), result.get(result.size() - 1));
    }
}