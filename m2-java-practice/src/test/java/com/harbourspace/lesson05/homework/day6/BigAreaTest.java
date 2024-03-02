package com.harbourspace.lesson05.homework.day6;

import com.harbourspace.lesson06.BigDecimalTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BigAreaTest{

    @Test
    public void testBigArea() {
        BigDecimal radius = new BigDecimal("3.1233455677899");
        int decimalPlaces = 9;
        BigDecimal expectedArea = new BigDecimal("30.647139656"); // PI * radius^2 rounded to 2 decimal places

        BigDecimal resultArea = BigDecimalTask.BigArea(radius, decimalPlaces);
        Assertions.assertEquals(0, expectedArea.compareTo(resultArea), " " + expectedArea + " " + resultArea);
    }

    @Test
    public void testBigAreaZero() {
        BigDecimal radius = new BigDecimal("0.0000");
        int decimalPlaces = 3;
        BigDecimal expectedArea = new BigDecimal("0.000"); // PI * radius^2 rounded to 2 decimal places

        BigDecimal resultArea = BigDecimalTask.BigArea(radius, decimalPlaces);

        Assertions.assertEquals(0, expectedArea.compareTo(resultArea), " " + expectedArea + " " + resultArea);
    }
}

