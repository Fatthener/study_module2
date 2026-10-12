package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public interface OrderSource {
    List<Order> readOrders(Path path) throws IOException;
}
