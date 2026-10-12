package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

public class PipeOrderAdapter implements OrderSource{

    @Override
    public List<Order> readOrders(Path path) throws IOException {
        List<Order> orders = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] newOrder = line.split("\\|");
                LocalDateTime localDate = LocalDateTime.parse(newOrder[0]);
                String company = newOrder[1];
                int kilograms = Integer.parseInt(newOrder[2]);

                Order order = new Order(localDate, company, kilograms);
                orders.add(order);
            }
        }

        return orders;
    }

}
