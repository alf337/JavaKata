package alf.exercises.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class RobotReturnToOriginTest {

    RobotReturnToOrigin ex = new RobotReturnToOrigin();

    @Test
    public void judgeCircle() {
        assertTrue(ex.judgeCircle("UD"));
        assertFalse(ex.judgeCircle("LL"));
        assertFalse(ex.judgeCircle("ULLDRLURDLR"));
    }
}