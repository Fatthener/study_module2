package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OrderSourceFactory {

    public OrderSource create(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                if (line.contains("|") && !line.contains("#")) {
                    return new PipeOrderAdapter();
                }

                if (line.contains("#") && !line.contains("|")) {
                    return new HashOrderAdapter();
                }
            }
        }

        throw new IllegalArgumentException("Unknown order format");

    }
}
