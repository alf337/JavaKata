package alf.exercises.sicp;

import org.junit.Test;

import static org.junit.Assert.*;
import static alf.exercises.sicp.Factorial.*;

public class FactorialTest {

    @Test
    public void testF1() {
        Assert.assertEquals(1, f1(1));
        Assert.assertEquals(0, f1(0));
        Assert.assertEquals(6, f1(3));
        Assert.assertEquals(720, f1(6));
        System.out.println("factorial of 6 = " + f1(6));
        System.out.println("factorial of 10 = " + f1(10));
        System.out.println("factorial of 25 = " + f1(25));
        System.out.println("factorial of 50 = " + f1(50));
    }

    @Test
    public void testF5() {
        Assert.assertEquals(1, f5(1));
        Assert.assertEquals(0, f5(0));
        Assert.assertEquals(6, f5(3));
        Assert.assertEquals(720, f5(6));
        System.out.println("factorial of 6 = " + f5(6));
        System.out.println("factorial of 10 = " + f5(10));
        System.out.println("factorial of 25 = " + f5(25));
        System.out.println("factorial of 50 = " + f5(50));
    }

    @Test
    public void testF2() {
        Assert.assertEquals(1, f2(1));
        Assert.assertEquals(0, f2(0));
        Assert.assertEquals(6, f2(3));
        Assert.assertEquals(720, f2(6));
        System.out.println("factorial of 3 = " + f2(3));
        System.out.println("factorial of 10 = " + f2(10));
        System.out.println("factorial of 25 = " + f2(25));
        System.out.println("factorial of 50 = " + f2(50));
    }

    @Test
    public void testF3() {
        Assert.assertEquals(1, f3(1));
        Assert.assertEquals(0, f3(0));
        Assert.assertEquals(6, f3(3));
        Assert.assertEquals(720, f3(6));
        System.out.println("factorial of 3 = " + f3(3));
        System.out.println("factorial of 10 = " + f3(10));
        System.out.println("factorial of 25 = " + f3(25));
        System.out.println("factorial of 50 = " + f3(50));
    }

    @Test
    public void testF4() {
        Assert.assertEquals(1, f4(1));
        Assert.assertEquals(0, f4(0));
        Assert.assertEquals(6, f4(3));
        Assert.assertEquals(720, f4(6));
        System.out.println("factorial of 3 = " + f4(3));
        System.out.println("factorial of 10 = " + f4(10));
        System.out.println("factorial of 25 = " + f4(25));
        System.out.println("factorial of 50 = " + f4(50));
    }
}