package com.harbourspace.lesson07;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FileCity {

    public static void main(String[] args) {
        try {

            URL url = FileCity.class.getClassLoader().getResource("world-cities.csv");

            if (url == null) {
                System.out.println("File not found!");

            } else {
                URI uri = url.toURI();
                File file = new File(uri);
                System.out.println("File exists: " + file.exists());
                System.out.println("File path: " + file.getAbsolutePath());
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}