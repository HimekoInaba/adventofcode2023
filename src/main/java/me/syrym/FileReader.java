package me.syrym;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class FileReader {

    public static Stream<String> readLinesAsStream(String path) {
        try {
            return Files.readAllLines(Path.of(path))
                    .stream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
