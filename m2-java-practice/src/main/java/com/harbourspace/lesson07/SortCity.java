package com.harbourspace.lesson07;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SortCity implements Statistics {

    @Override
    public Collection<City> getTopCountryCities(String country, int size) {
        Collection<City> sortedCity = filterAndSortByPopulation(country);
        return sortedCity.stream().limit(size).toList();
    }

    @Override
    public Collection<City> filterAndSortByPopulation(String country) {
        Path path = Path.of("world-cities.csv");
        ReadCity process = new ReadCity();
        Collection<City> city =  process.readAll(path);
        return city.stream()
                .filter(city1 -> city1.country().equalsIgnoreCase(country))
                .sorted(Comparator.comparingLong(City::population))
                .collect(Collectors.toList());
    }
}