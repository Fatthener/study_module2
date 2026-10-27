package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.math.RoundingMode;
import java.util.ArrayList;

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

        Map<String, BigDecimal> totals = new HashMap<>();

        final BigDecimal pricePerKg = new BigDecimal("100.00");
        BigDecimal discount = new BigDecimal("0.50");
        final BigDecimal discountStep = new BigDecimal("0.05");

        for(Order order : orders) {
            BigDecimal cost = BigDecimal.valueOf(order.getKilograms())
                    .multiply(pricePerKg)
                    .multiply(BigDecimal.ONE.subtract(discount));
            totals.merge(
                    order.getCompany(),
                    cost,
                    BigDecimal::add);

            discount = discount.subtract(discountStep);

            if (discount.compareTo(BigDecimal.ZERO) < 0) {
                discount = BigDecimal.ZERO;
            }
        }

        for (Map.Entry<String, BigDecimal> entry : totals.entrySet()) {
            BigDecimal total = entry.getValue()
                    .setScale(2, RoundingMode.HALF_UP);

                    System.out.println(entry.getKey() + " : " + total.toPlainString());
        }

        List<String> outOrder = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : totals.entrySet()) {
            BigDecimal total = entry.getValue()
                    .setScale(2, RoundingMode.HALF_UP);

            String line = entry.getKey() + " - " + total.toPlainString();
            outOrder.add(line);
        }

        Path outputPath = Path.of("src/main/resources/output.txt");
        Files.write(outputPath, outOrder);








    }

}

