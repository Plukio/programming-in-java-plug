package com.harbourspace.lesson05.homework.day6;


import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.harbourspace.lesson06.OptionalTask.getWord;

public class OptionalTest {

    String word = "words";
    String text = "text is in words";
    String no_word = "pizza";

    @Test
    public void testGetWord() {
        Optional<String> option_have = getWord(text, word);
        Assertions.assertTrue(option_have.isPresent(), "Expected: " + true + ", Actual: " + option_have.isPresent());

    }

    @Test
    public void testGetNoWord() {
        Optional<String> option_dont = getWord(text, no_word);
        Assertions.assertTrue(option_dont.isEmpty(), "Expected: " + true + ", Actual: " + option_dont.isEmpty());
    }

}
