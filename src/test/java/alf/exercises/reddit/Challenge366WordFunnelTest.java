package alf.exercises.reddit;

import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class Challenge366WordFunnelTest {

    Challenge366WordFunnel exercise = new Challenge366WordFunnel();

    @Test
    public void funnel() {

        assertTrue(exercise.funnel("leave", "eave"));

        assertTrue(exercise.funnel("reset", "rest"));
        assertTrue(exercise.funnel("dragoon", "dragon"));
        assertFalse(exercise.funnel("eave", "leave"));
        assertFalse(exercise.funnel("sleet", "lets"));
        assertFalse(exercise.funnel("skiff", "ski"));
    }

    @Test
    public void bonus() {
/*
        bonus("dragoon") => ["dragon"]
        bonus("boats") => ["oats", "bats", "bots", "boas", "boat"]
        bonus("affidavit") => []
*/
        Set<String> result = exercise.bonus("dragoon");
        assertEquals(1, result.size());
        assertTrue(result.contains("dragon"));

        result = exercise.bonus("aaa");
        assertEquals(1, result.size());
        assertTrue(result.contains("aa"));

        result = exercise.bonus("zyzzyvas");
        assertEquals(1, result.size());
        assertTrue(result.contains("zyzzyva"));

        result = exercise.bonus("boats");
        assertEquals(5, result.size());
        assertTrue(result.contains("oats"));
        assertTrue(result.contains("bats"));
        assertTrue(result.contains("bots"));
        assertTrue(result.contains("boas"));
        assertTrue(result.contains("boat"));

        result = exercise.bonus("affidavit");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testBonus2() {

        List<Set<String>> result = exercise.bonus2();
        assertEquals(28, result.size());

        result.forEach(System.out::println);

        IntStream.rangeClosed(1, 10)
                .forEach((i) -> {
                    long start = System.currentTimeMillis();
                    exercise.bonus2();
                    System.out.println("bonus2 time (second pass)" + i + " = " + (System.currentTimeMillis() - start));
                });
    }
}