package com.harbourspace.lesson05.homework.day6;

import com.harbourspace.lesson06.MathTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RandNumTest {

    @Test
    public void testRandomNumInRange() {
        for (int i = 0; i < 100; i++) {
            double min = -10.0;
            double max = 10.0;
            int d = 3;
            double randomValue = MathTask.randomNum(min, max, d);

            // Check that the number is within the range
            Assertions.assertTrue(randomValue >= min && randomValue <= max, " " + max + " "+  randomValue + " " + min);
        }
    }

    @Test
    public void testRandomNumDecimalPlaces() {
        for (int i = 0; i < 100; i++) {
            double min = -10.0;
            double max = 10.0;
            int d = 4;
            double randomValue = MathTask.randomNum(min, max, d);
            String textValue = Double.toString(randomValue);
            int decimalPlaces = textValue.length() - textValue.indexOf('.') - 1;
            Assertions.assertTrue(d >= decimalPlaces, "d = " + d + " decimalPlaces = " + decimalPlaces );
        }
    }
}