package alf.exercises.sicp;

import org.junit.Test;

import static org.junit.Assert.*;
import static alf.exercises.sicp.SquareRoot.*;

public class SquareRootTest {

    @Test
    public void testSqrt() {
        System.out.println(Math.sqrt(2));
        System.out.println(Math.sqrt(0.01));
        System.out.println(sqrt(2));
        System.out.println(sqrt(2L));
        System.out.println(sqrt(2.0));
        System.out.println(sqrt(0));
        Assert.assertEquals(1.4142, sqrt(2), 0.0001);
        Assert.assertEquals(10.0, sqrt(100), 0.0001);
        Assert.assertEquals(9.995, sqrt(99.9), 0.001);
        Assert.assertEquals(0.0, sqrt(0), 0.0001);
        Assert.assertEquals(0.10, sqrt(0.01), 0.001);
        Assert.assertEquals(0.10, Math.sqrt(0.01), 0.0001);
        Assert.assertEquals(0.0, sqrt(null), 0.0001);
    }

    @Test
    public void testAvg() {
        System.out.println(avg(6, 4));
        Assert.assertEquals(5.0, avg(6, 4), 0.0001);
        Assert.assertEquals(5.0, avg(5, 5, 5), 0.0001);
        Assert.assertEquals(0.0, avg(), 0.0001);
        Assert.assertEquals(0.0, avg(0), 0.0001);
        Assert.assertEquals(1.0, avg(1), 0.0001);
    }
}