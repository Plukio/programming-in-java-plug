package com.harbourspace.lesson05.homework.day7;

import com.harbourspace.lesson07.City;
import com.harbourspace.lesson07.ReadCity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.nio.file.Paths;
import java.util.Collection;

class ReaderTest {
    @Test
    void testReadAll() {
        ReadCity reader = new ReadCity();
        Collection<City> cities = reader.readAll(Paths.get("test-country.csv"));
        Assertions.assertEquals(2, cities.size(), "expect: 2" + " actual: " + cities.size());
        Assertions.assertEquals("Tokyo", cities.stream().toList().get(0).name(), "expect: Tokyo" + " actual: " + cities.stream().toList().get(0).name());
        Assertions.assertEquals("New York", cities.stream().toList().get(1).name(), "expect: New York" + " actual: " + cities.stream().toList().get(1).name());
    }
}