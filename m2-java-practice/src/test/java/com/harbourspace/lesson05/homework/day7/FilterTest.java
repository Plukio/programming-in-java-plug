package com.harbourspace.lesson05.homework.day7;

import com.harbourspace.lesson07.City;
import com.harbourspace.lesson07.SortCity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

class FilterTest {

    @Test
    void testFilter() {
        SortCity stats = new SortCity();
        Collection<City> filteredCities = stats.filterAndSortByPopulation("Japan");
        for (int i = 0; i < filteredCities.size(); i++){
            Assertions.assertEquals("Japan", filteredCities.stream().toList().get(i).country(), "expected: Japan" + " actual: " +  filteredCities.stream().toList().get(i).country());
        }
    }
}