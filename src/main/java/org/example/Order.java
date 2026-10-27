package org.example;

import java.time.LocalDateTime;

public class Order {
    private LocalDateTime date;
    private String company;
    private int kilograms;

    public Order(LocalDateTime date, String company, int kilograms) {
        this.date = date;
        this.company = company;
        this.kilograms = kilograms;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public String getCompany() {
        return company;
    }
    public int getKilograms() {
        return kilograms;
    }
}
