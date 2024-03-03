package com.harbourspace.lesson07;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public interface Statistics {
    Collection<City> getTopCountryCities(String country, int size);

    Collection<City> filterAndSortByPopulation(String country);
}
