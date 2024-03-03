package com.harbourspace.lesson05.homework.day7;

import com.harbourspace.lesson07.City;
import com.harbourspace.lesson07.WriteCity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriteTest {

    @Test
    void testWriteData() throws Exception {

        WriteCity writer = new WriteCity();
        List<City> cities = Arrays.asList(new City("Tokyo", "Japan", 37732000), new City("Nagachi", "Japan", 123456));
        Path tempFile = Files.createTempFile("test-cities-output", ".csv");

        writer.writeAll(cities, tempFile);

        List<String> lines = Files.readAllLines(tempFile);
        Assertions.assertEquals("Tokyo|Japan|37732000", lines.stream().toList().get(1));
        Assertions.assertEquals("Nagachi|Japan|123456", lines.stream().toList().get(2));
        Files.deleteIfExists(tempFile); // Clean up
    }
}
