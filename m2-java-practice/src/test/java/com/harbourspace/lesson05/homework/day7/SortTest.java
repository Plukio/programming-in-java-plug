package com.harbourspace.lesson05.homework.day7;

import com.harbourspace.lesson07.City;
import com.harbourspace.lesson07.SortCity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class SortTest {

    @Test
    void testSort() {
        SortCity stats = new SortCity();
        Collection<City> sortedCities = stats.getTopCountryCities("Japan", 4);
        Assertions.assertEquals(4, sortedCities.size(), "Four cities should match the criteria.");
        for (int i = 1; i < sortedCities.size(); i++){
            Assertions.assertTrue(sortedCities.stream().toList().get(i).population() >= sortedCities.stream().toList().get(i-1).population(), "i" + sortedCities.stream().toList().get(i).population() + " i-1: " +  sortedCities.stream().toList().get(i-1).population());
        }
    }

}
