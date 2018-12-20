package alf.exercises.sicp;

import alf.exercises.sicp.Fraction;
import org.junit.Test;

import static org.junit.Assert.*;

public class FractionTest {

    Fraction f1 = new Fraction(1, 2);
    Fraction f2 = new Fraction(2, 3);

    @Test
    public void testToString() {
        Assert.assertEquals("1/2", f1.toString());
    }

    @Test
    public void testToDouble() {
        Assert.assertEquals(0.5d, f1.toDouble(), 0.001d);
    }

    @Test
    public void testAdd() {
        Fraction f3 = f1.add(f2);
        Assert.assertEquals("7/6", f3.toString());
        Assert.assertEquals(1.1666d, f3.toDouble(), 0.0001d);

        Fraction f32 = f2.add(f1);
        Assert.assertEquals("7/6", f32.toString());
        Assert.assertEquals(1.1666d, f32.toDouble(), 0.0001d);
    }

    @Test
    public void testSubtract() {
        Fraction f4 = f2.subtract(f1);
        Assert.assertEquals("1/6", f4.toString());
        Assert.assertEquals(0.1666d, f4.toDouble(), 0.0001d);

        Fraction f44 = f1.subtract(f2);
        Assert.assertEquals("-1/6", f44.toString());
        Assert.assertEquals(-0.1666d, f44.toDouble(), 0.0001d);
    }

    @Test
    public void testMultiply() {
        Fraction f5 = f1.multiply(f2);
        Assert.assertEquals("1/3", f5.toString());
        f5.pretty();
    }

    @Test
    public void testDivide() {
        Fraction f6 = f1.divide(f2);
        Assert.assertEquals("3/4", f6.toString());
        f6.pretty();
    }

    @Test
    public void testRefactor() {
//        assertEquals("1/2", new Fraction(4, 8).toString());
//        assertEquals("3/4", new Fraction(6, 8).toString());
//        assertEquals("7/11", new Fraction(91, 143).toString());
//        assertEquals("11/7", new Fraction(143, 91).toString());
//        assertEquals("5/8", new Fraction(20, 32).toString());
//        assertEquals("8/5", new Fraction(32, 20).toString());
//        assertEquals("2", new Fraction(40, 20).toString());
//        assertEquals("1/2", new Fraction(20, 40).toString());
        Assert.assertEquals("31/17", new Fraction(31, 17).toString());

    }

}