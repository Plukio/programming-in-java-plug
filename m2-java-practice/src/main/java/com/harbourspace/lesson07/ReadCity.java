package com.harbourspace.lesson07;


import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ReadCity implements DataReader{

    @Override
    public Collection<City> readAll(Path path) {
        List<City> cities = new ArrayList<>();
        try (InputStream inputStream = FileCity.class.getClassLoader().getResourceAsStream(path.toString());
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            reader.readLine(); // Skip the header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                cities.add(new City(parts[0], parts[4], Long.parseLong(parts[9])));
            }
        } catch (Exception e) {
            System.out.println("File inputStream" + e);;
        }
        return cities;
    }

    public static void main(String[] args) throws IOException {
        Path path = Path.of("world-cities.csv");
        ReadCity process = new ReadCity();
        Collection<City> city =  process.readAll(path);
        System.out.println(city);
    }

}
