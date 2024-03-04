package com.harbourspace.lesson05.homework.day9;

import com.harbourspace.lesson09.Magic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class MagicTest {

    @Test
    public void testConcatenateStrings() {
        BiFunction<String, String, String> concatenate = (a, b) -> a + b;
        String result = Magic.doMagic("Hello, ", "world!", concatenate);
        Assertions.assertEquals("Hello, world!", result);
    }
}
