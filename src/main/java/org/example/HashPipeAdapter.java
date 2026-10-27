
package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class HashPipeAdapter implements OrderSource {

    private final String delimiter;

    protected HashPipeAdapter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public List<Order> readOrders(Path path) throws IOException {
        List<Order> orders = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] fields = line.split(Pattern.quote(delimiter));

                LocalDateTime localDate = LocalDateTime.parse(fields[0]);
                String company = fields[1];
                int kilograms = Integer.parseInt(fields[2]);

                Order order = new Order(localDate, company, kilograms);
                orders.add(order);
            }
        }

        return orders;
    }
}
