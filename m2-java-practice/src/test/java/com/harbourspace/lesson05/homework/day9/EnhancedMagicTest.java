package com.harbourspace.lesson05.homework.day9;

import com.harbourspace.lesson09.EnhancedMagic;
import com.harbourspace.lesson09.GiftBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class EnhancedMagicTest {

    @Test
    public void testConcatenateStrings() {
        BiFunction<GiftBox<String>, GiftBox<String>, String> concatenate = (a, b) -> a.get() + b.get();
        GiftBox<String> Box1 = new GiftBox<>();
        Box1.put("Hello, ");
        GiftBox<String> Box2 = new GiftBox<>();
        Box2.put("Test");
        String result = EnhancedMagic.doMagic(Box1, Box2, concatenate);
        Assertions.assertEquals("Hello, Test", result);
    }

    @Test
    public void testSumIntegers() {
        BiFunction<Integer, Integer, Integer> sum = Integer::sum;
        Integer result = EnhancedMagic.doMagic(99, 0, sum);
        assertEquals(Integer.valueOf(99), result);
    }

    @Test
    public void testCombineLists() {
        BiFunction<List<String>, List<String>, List<String>> combine = (list1, list2) -> {
            List<String> newList = new ArrayList<>(list1);
            newList.addAll(list2);
            return newList;
        };

        List<String> list1 = Arrays.asList("a", "b");
        List<String> list2 = Arrays.asList("1", "d");
        List<String> result = EnhancedMagic.doMagic(list1, list2, combine);

        assertEquals(Arrays.asList("a", "b", "1", "d"), result);
    }
}
