package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidIPAddressTest {

    ValidIPAddress ex = new ValidIPAddress();

    @Test
    public void validIPAddress() {
    }

    @Test
    public void isValidIPv4() {
        assertFalse(ex.isValidIPv4(null));
        assertFalse(ex.isValidIPv4(""));
        assertFalse(ex.isValidIPv4("172.16.254.01"));
        assertFalse(ex.isValidIPv4("172,16,254,1"));
        assertFalse(ex.isValidIPv4("172.16.1"));
        assertFalse(ex.isValidIPv4("172.16..1"));
        assertFalse(ex.isValidIPv4("172.16.256.1"));
        assertFalse(ex.isValidIPv4(".172.16.255.1"));
        assertFalse(ex.isValidIPv4("172.16.255.1."));
        assertFalse(ex.isValidIPv4("0.0.0.-0"));
        assertFalse(ex.isValidIPv4("0.0.0.0.1.2"));

        assertTrue(ex.isValidIPv4("172.16.254.1"));
        assertTrue(ex.isValidIPv4("172.16.0.1"));
    }

    @Test
    public void isValidIPv6() {

        assertFalse(ex.isValidIPv4(null));
        assertFalse(ex.isValidIPv4(""));
        assertFalse(ex.isValidIPv6("02001:0db8:85a3:0000:0000:8a2e:0370:7334"));
        assertFalse(ex.isValidIPv6("2001:0db8:85a3:0000::8a2e:0370:7334"));
        assertFalse(ex.isValidIPv6("2001:0db8:85a3:0:8a2e:0370:7334"));
        assertFalse(ex.isValidIPv6("2001:0db8:85a3:0:0:8A2E:0370:7334:"));

        assertTrue(ex.isValidIPv6("2001:0db8:85a3:0:0:8a2e:0370:7334"));
        assertTrue(ex.isValidIPv6("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
    }

    @Test
    public void patternMatchTest() {
        System.out.println("123".matches("\\d+"));
        System.out.println("-123".matches("-[\\d]+"));
    }
}