package com.harbourspace.lesson07;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class WriteCity implements DataWriter{

    @Override
    public void writeAll(List<City> cities, Path path) {
        String data = getFormattedCityData(cities);

        try (FileOutputStream outputStream = new FileOutputStream(String.valueOf(path))) {
            byte[] bytesArray = data.getBytes();
            outputStream.write(bytesArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getFormattedCityData(List<City> cities) {
        StringBuilder sb = new StringBuilder();
        sb.append("City|Country|Population\n");
        for (City city : cities) {
            sb.append(city.name())
                    .append("|")
                    .append(city.country())
                    .append("|")
                    .append(city.population())
                    .append("\n");
        }
        return sb.toString();
    }
}
