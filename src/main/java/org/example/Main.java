package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Path firstPath = Path.of("src/main/resources/discount_day.txt");
        Path secondPath = Path.of("src/main/resources/discount_day_without_ext");

        OrderSourceFactory factory = new OrderSourceFactory();

        OrderSource source = factory.create(firstPath);
        List<Order> orders = source.readOrders(firstPath);

        OrderSource secondSource = factory.create(secondPath);
        orders.addAll(secondSource.readOrders(secondPath));

        orders.sort(Comparator.comparing(Order::getDate));

        Map<String, Double> totals = new HashMap<>();

        double pricePerKg = 100.0;
        double discount = 0.50;

        for(Order order : orders) {
            double cost = order.getKilograms() * pricePerKg * (1 - discount);

            totals.merge(
                    order.getCompany(),
                    cost,
                    (oldCost, newCost) -> oldCost + newCost
            );

            discount = Math.max(0.0, discount - 0.05);
        }

        for (Map.Entry<String, Double> entry : totals.entrySet()) {
            System.out.printf("%s : %.2f%n", entry.getKey(), entry.getValue());
        }

        System.out.println("Количество заказов: " + orders.size());






    }

}

