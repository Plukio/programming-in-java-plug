package com.harbourspace.lesson07;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public interface DataReader {
    Collection<City> readAll(Path path) throws IOException;


}


