package com.harbourspace.lesson05.homework.day9;


import com.harbourspace.lesson09.GiftBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GiftTest {

    @Test
    public void testGiftBoxWithString() {
        GiftBox<String> stringBox = new GiftBox<>();
        stringBox.put("Test");
        Assertions.assertEquals("Test", stringBox.get());
    }

    @Test
    public void testGiftBoxWithInteger() {
        GiftBox<Integer> intBox = new GiftBox<>();
        intBox.put(123);
        Assertions.assertEquals(Integer.valueOf(123), intBox.get());
    }

    @Test
    public void testGiftBoxWithGiftBox() {
        GiftBox<GiftBox<String>> intBox = new GiftBox<>();
        GiftBox<String> insidBox = new GiftBox<>();
        insidBox.put("test");
        intBox.put(insidBox);
        Assertions.assertEquals("test", intBox.get().get());
    }
}
